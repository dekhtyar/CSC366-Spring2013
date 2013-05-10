package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func refreshInventory(w http.ResponseWriter, r *http.Request) {
	data, err := input.RefreshInventoryRequest(r.Body)
	fmt.Println(err)
	fmt.Println(data)
	output.RefreshInventoryResponse(w, "Inventory Updated yo!")
}

func dbConn(i soap.RefreshItem) error {
	conn, err := sql.Open("postgres", "dbname=cait password=cait")
	if err != nil {
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
