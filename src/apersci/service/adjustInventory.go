package main

import (
	"apersci/input"
	"apersci/output"
	"errors"
	"fmt"
	"net/http"
)

func adjustInventory(w http.ResponseWriter, r *http.Request) (err error) {
	rr, err := input.AdjustInventoryRequest(r.Body)
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

		locationid, err := getInternalFromExternalLocationID(tx, rr.ExternalLocationID)
		if err != nil {
			return err
		}

		rows, err := tx.Query("SELECT id FROM Bins WHERE name = $2 AND locationId = $1", locationid, i.BinID)
		if err != nil {
			return err
		}
		var binid uint

		if rows.Next() {
			err = rows.Scan(&binid)
			if err != nil {
				return err
			}
			rows.Close()
		} else {
			return errors.New("Unable to find specified bin.\n")
		}

		rows, err = tx.Query("SELECT DISTINCT fulfillerId FROM Locations WHERE id = $1", locationid)
		if err != nil {
			return err
		}

		fulfillerid, err := readIntAndCloseRows(rows)
		if err != nil {
			return err
		}

		rows, err = tx.Query("SELECT COUNT(*) FROM BinProducts WHERE binId = $1 AND sku = $2",
			binid, i.PartNumber)
		if err != nil {
			return err
		}

		count, err := readIntAndCloseRows(rows)
		if err != nil {
			return err
		}

		if count > 0 {
			_, err = tx.Exec("UPDATE BinProducts SET onHand = onHand + $1 WHERE sku = $2 AND binId = $3",
				i.Quantity, i.PartNumber, binid)
			if err != nil {
				return err
			}
		} else {
			rows, err = tx.Query("SELECT COUNT(*) FROM Products WHERE upc = $1", i.UPC)
			if err != nil {
				return err
			}

			count, err = readIntAndCloseRows(rows)
			if err != nil {
				return err
			}

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

			count, err = readIntAndCloseRows(rows)
			if err != nil {
				return err
			}

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

		count, err = readIntAndCloseRows(rows)
		if err != nil {
			return err
		}

		if count == 0 {
			_, err = tx.Exec("INSERT INTO LocationProducts(locationId, fulfillerId, sku) VALUES($1, $2, $3)",
				locationid, fulfillerid, i.PartNumber)
			if err != nil {
				return err
			}
		}

		err = tx.Commit()
		if err != nil {
			return err
		}
	}

	err = output.AdjustResponse(w, fmt.Sprint(len(rr.Items)))
	if err != nil {
		return
	}

	return
}
