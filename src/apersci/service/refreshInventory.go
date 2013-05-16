package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func refreshInventory(w http.ResponseWriter, r *http.Request) {
	rr, err := input.RefreshInventoryRequest(r.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	defer conn.Close()

	for _, i := range rr.Items {
		tx, err := conn.Begin()
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		defer tx.Rollback()

		rows, err := tx.Query("SELECT COUNT(*) FROM BinProducts WHERE binId = $1 AND sku = $2",
			i.BinID, i.PartNumber)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}

		var count uint

		rows.Next()
		err = rows.Scan(&count)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		rows.Close()

		if count > 0 {
			_, err = tx.Exec("UPDATE BinProducts SET onhandinventory = $1 WHERE sku = $2 AND binId = $3",
				i.Quantity, i.PartNumber, i.BinID)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}
		} else {
			rows, err = tx.Query("SELECT COUNT(*) FROM Products WHERE upc = $1", i.UPC)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}
			rows.Next()
			err = rows.Scan(&count)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}
			rows.Close()

			if count == 0 {
				_, err = tx.Exec("INSERT INTO Products(upc) VALUES($1)", i.UPC)
				if err != nil {
					http.Error(w, err.Error(), http.StatusInternalServerError)
					return
				}
			}
			_, err = tx.Exec("INSERT INTO BinProducts VALUES($1, $2, $3, $4, 0)", i.BinID, rr.FulfillerID, i.PartNumber, i.Quantity)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}
		}

		rows, err = tx.Query(`SELECT DISTINCT b.locationid
							  FROM BinProducts bp, Bins b WHERE bp.sku = $1 AND
				              bp.binID = $2 AND bp.binID = b.id`, i.PartNumber, i.BinID)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}

		var locationid uint

		rows.Next()
		err = rows.Scan(&locationid)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		rows.Close()

		rows, err = tx.Query(`SELECT COUNT(*) FROM LocationProducts
							  WHERE locationid = $2
							  AND sku = $1`, i.PartNumber, locationid)

		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		rows.Next()
		err = rows.Scan(&count)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		rows.Close()

		if count == 0 {
			_, err = tx.Exec("INSERT INTO LocationProducts VALUES($1, $2, $3, $4, $5)",
				locationid, rr.FulfillerID, i.PartNumber, i.LTD, i.SafetyStock)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}
		} else {
			_, err = tx.Exec(`UPDATE LocationProducts SET ltd = $1, safetyStock = $2 WHERE locationid = $4 AND sku = $3`,
				i.LTD, i.SafetyStock, i.PartNumber, locationid)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
				return
			}
		}

		err = tx.Commit()
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
	}

	err = output.RefreshInventoryResponse(w, fmt.Sprint(len(rr.Items)))
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
}
