package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func refreshInventory(w http.ResponseWriter, r *http.Request) {
	b, err := input.RefreshInventoryRequest(r.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	conn, err := getDBConnection()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
	defer conn.Close()

	fmt.Println(b)
	
	rows, err := conn.Exec("SELECT COUNT(*) FROM BinsProducts WHERE binId = $1 AND sku = $2", i.BinID, i.PartNumber)
	if err != nil {
		return err
	}

	var iv uint

	for rows.Next() {
		err = rows.Scan(&iv)
		if err != nil {
			return err
		}
	}

	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	if iv > 0 {
		rows, err := conn.Exec("UPDATE BinsProducts SET onhandinventory = onhandinventory + $1 
	    WHERE sku = $2 AND binId = $3)", i.Quantity, i.PartNumber, i.BinID)
	    if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
		rows, err := conn.Exec("UPDATE LocationsProducts SET ltd = $1, safetyStock = $2 
		                        WHERE locationid = (SELECT DISTINCT b.locationid 
								FROM BinsProducts bp, Bins b WHERE bp.sku = $3 AND 
								binID = $4 AND bp.binID = b.id)" , i.LTD, i.SafetyStock, i.PartNumber, i.BinID)
	}

	rows, _ := res.RowsAffected()
	err = output.RefreshInventoryResponse(w, fmt.Sprint(rows))
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
