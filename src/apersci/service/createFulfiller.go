package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func createFulfiller(w http.ResponseWriter, r *http.Request) {
	f, err := input.CreateFulfillerRequest(r.Body)
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

	res, err := conn.Exec("INSERT INTO Fulfillers VALUES($1,$2)",
		f.FulfillerID, f.Name)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	rows, _ := res.RowsAffected()
	err = output.CreateFulfillerResponse(w, fmt.Sprint(rows))
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
