package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func refreshInventory(w http.ResponseWriter, r *http.Request) (err error) {
	rr, err := input.RefreshInventoryRequest(r.Body)
	if err != nil {
		return
	}
	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	for _, i := range rr.Items {
		tx, err := conn.Begin()
		if err != nil {
			return err
		}
		defer tx.Rollback()

		locationid := rr.LocationName

		rows, err := tx.Query("SELECT id FROM Bins WHERE name = $1 AND locationId = $2", i.BinID, locationid)
		if err != nil {
			return err
		}

		var binid uint

		rows.Next()
		err = rows.Scan(&binid)
		if err != nil {
			return err
		}
		rows.Close()

		rows, err = tx.Query("SELECT DISTINCT fulfillerId FROM Locations WHERE id = $1", locationid)
		if err != nil {
			return err
		}

		var fulfillerid uint

		rows.Next()
		err = rows.Scan(&fulfillerid)
		if err != nil {
			return err
		}
		rows.Close()

		rows, err = tx.Query("SELECT COUNT(*) FROM BinProducts WHERE binId = $1 AND sku = $2",
			binid, i.PartNumber)
		if err != nil {
			return err
		}

		var count uint

		rows.Next()
		err = rows.Scan(&count)
		if err != nil {
			return err
		}
		rows.Close()

		if count > 0 {
			_, err = tx.Exec("UPDATE BinProducts SET onhandinventory = $1 WHERE sku = $2 AND binId = $3",
				i.Quantity, i.PartNumber, binid)
			if err != nil {
				return err
			}
		} else {
			rows, err = tx.Query("SELECT COUNT(*) FROM Products WHERE upc = $1", i.UPC)
			if err != nil {
				return err
			}
			rows.Next()
			err = rows.Scan(&count)
			if err != nil {
				return err
			}
			rows.Close()

			if count == 0 {
				_, err = tx.Exec("INSERT INTO Products(upc) VALUES($1)", i.UPC)
				if err != nil {
					return err
				}
			}

			rows, err = tx.Query("SELECT COUNT(*) FROM FulfillerProducts WHERE upc = $1 AND sku = $2 AND fulfillerId = $3",
				i.UPC, i.PartNumber, fulfillerid)
			if err != nil {
				return err
			}
			rows.Next()
			err = rows.Scan(&count)
			if err != nil {
				return err
			}
			rows.Close()

			if count == 0 {
				_, err = tx.Exec("INSERT INTO FulfillerProducts VALUES($1, $2, $3)",
					i.PartNumber, i.UPC, fulfillerid)
				if err != nil {
					return err
				}
			}

			_, err = tx.Exec("INSERT INTO BinProducts VALUES($1, $2, $3, $4, 0)",
				binid, fulfillerid, i.PartNumber, i.Quantity)
			if err != nil {
				return err
			}
		}

		rows, err = tx.Query(`SELECT COUNT(*) FROM LocationProducts
							  WHERE locationid = $2
							  AND sku = $1`, i.PartNumber, locationid)

		if err != nil {
			return err
		}
		rows.Next()
		err = rows.Scan(&count)
		if err != nil {
			return err
		}
		rows.Close()

		if count == 0 {
			_, err = tx.Exec("INSERT INTO LocationProducts VALUES($1, $2, $3, $4, $5)",
				locationid, fulfillerid, i.PartNumber, i.LTD, i.SafetyStock)
			if err != nil {
				return err
			}
		} else {
			_, err = tx.Exec(`UPDATE LocationProducts SET ltd = $1, safetyStock = $2 WHERE locationid = $4 AND sku = $3`,
				i.LTD, i.SafetyStock, i.PartNumber, locationid)
			if err != nil {
				return err
			}
		}

		err = tx.Commit()
		if err != nil {
			return err
		}
	}

	err = output.RefreshInventoryResponse(w, fmt.Sprint(len(rr.Items)))
	if err != nil {
		return
	}

	return
}
