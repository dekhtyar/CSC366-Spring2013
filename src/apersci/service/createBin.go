package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func createBin(w http.ResponseWriter, r *http.Request) (err error) {
	b, err := input.CreateBinRequest(r.Body)
	if err != nil {
		return
	}

	tx, err := getDBTransaction()
	if err != nil {
		return
	}
	defer tx.Rollback()

	locationID, err := getInternalFromExternalLocationID(tx, b.ExternalLocationID)
	if err != nil {
		return
	}

	res, err := tx.Exec("INSERT INTO Bins(locationId, name, type, status) VALUES($1,$2,$3,$4)",
		locationID, b.Name, b.BinType, b.BinStatus)
	if err != nil {
		return
	}

	rows, _ := res.RowsAffected()
	err = output.CreateBinResponse(w, fmt.Sprint(rows))
	if err != nil {
		return
	}

	err = tx.Commit()
	if err != nil {
		return
	}

	return
}
