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
	res, err := conn.Exec("--TODO")
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	rows, _ := res.RowsAffected()
	err = output.RefreshInventoryResponse(w, fmt.Sprint(rows))
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}

/*
func dbConn(i soap.RefreshItem) error {
	conn, err := getDBConnection()
		return err
	}
	defer conn.Close()

	rows, err := conn.Exec("SELECT COUNT(*) FROM BinsProducts WHERE binId =" + i.BinID + " AND sku =" + i.PartNumber)
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

	if iv > 0 {
		s, err := conn.Prepare("UPDATE BinsProducts SET onhandinventory = onhandinventory +" + i.Quantity + " WHERE sku = " + i.PartNumber + " AND binId = " + i.BinID)
	}

	return nil
}
*/
