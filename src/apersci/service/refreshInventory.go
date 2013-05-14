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
			_, err = tx.Exec("UPDATE BinsProducts SET onhandinventory = onhandinventory + $1 WHERE sku = $2 AND binId = $3",
				i.Quantity, i.PartNumber, i.BinID)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
			}
			_, err = tx.Exec(`UPDATE LocationsProducts SET ltd = $1, safetyStock = $2
				WHERE locationid = (SELECT DISTINCT i.locationid
				FROM BinsProducts bp, Bins b WHERE bp.sku = $3 AND
				binID = $4 AND bp.binID = i.id)`,
				i.LTD, i.SafetyStock, i.PartNumber, i.BinID)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
			}
		} else {
			// INSERT INTO BinsProducts =)
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
