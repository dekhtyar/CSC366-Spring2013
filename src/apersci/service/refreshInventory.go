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
	}

	conn, err := getDBConnection()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
	defer conn.Close()

	for _, i := range rr.Items {
		tx, err := conn.Begin()
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
		defer tx.Rollback()

		rows, err := tx.Query("SELECT COUNT(*) FROM BinsProducts WHERE binId = $1 AND sku = $2",
			i.BinID, i.PartNumber)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}

		var count uint

		rows.Next()
		err = rows.Scan(&count)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
		rows.Close()

		if count > 0 {
			_, err = tx.Exec("UPDATE BinsProducts SET onhandinventory = $1 WHERE sku = $2 AND binId = $3",
				i.Quantity, i.PartNumber, i.BinID)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
			}
		} else {
			rows, err = tx.Query("SELECT COUNT(*) FROM Products WHERE upc = $1", i.UPC)
			rows.Next()
			err = rows.Scan(&count)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
			}
			rows.Close()

			if count == 0 {
				_, err = tx.Exec("INSERT INTO Products(upc) VALUES($1)", i.UPC)
				if err != nil {
					http.Error(w, err.Error(), http.StatusInternalServerError)
				}
			}
			_, err = tx.Exec("INSERT INTO BinsProducts VALUES($1, $2, $3, $4, 0)", i.BinID, rr.FulfillerID, i.PartNumber, i.Quantity)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
			}
		}

		rows, err = tx.Query(`SELECT DISTINCT b.locationid
							  FROM BinsProducts bp, Bins b WHERE bp.sku = $1 AND
				              bp.binID = $2 AND bp.binID = b.id`, i.PartNumber, i.BinID)
		var locationid uint

		rows.Next()
		err = rows.Scan(&locationid)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
		rows.Close()

		rows, err = tx.Query(`SELECT COUNT(*) FROM LocationsProducts
							  WHERE locationid = $2
							  AND sku = $1`, i.PartNumber, locationid)
		rows.Next()
		err = rows.Scan(&count)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
		rows.Close()

		if count == 0 {
			_, err = tx.Exec("INSERT INTO LocationsProducts VALUES($1, $2, $3, $4, $5)",
				locationid, rr.FulfillerID, i.PartNumber, i.LTD, i.SafetyStock)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
			}
		} else {
			_, err = tx.Exec(`UPDATE LocationsProducts SET ltd = $1, safetyStock = $2 WHERE locationid = $4 AND sku = $3`,
				i.LTD, i.SafetyStock, i.PartNumber, locationid)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
			}
		}

		err = tx.Commit()
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
	}

	err = output.RefreshInventoryResponse(w, fmt.Sprint(len(rr.Items)))
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
