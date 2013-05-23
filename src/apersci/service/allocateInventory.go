package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func allocateInventory(w http.ResponseWriter, r *http.Request) {
	b, err := input.CreateAllocateInventoryRequest(r.Body)
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
	/*
		res, err := conn.Exec("INSERT INTO Bins(locationId, name, type, status) VALUES($1,$2,$3,$4)",
			b.FulfillerLocationID, b.Name, b.BinType, b.BinStatus)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
	*/
	rows, _ := res.RowsAffected()
	err = output.CreateAllocateInventoryResponse(w, fmt.Sprint(rows))
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
