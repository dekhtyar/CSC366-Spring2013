package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"net/http"
)

func getBinTypes(w http.ResponseWriter, r *http.Request) (err error) {
	err = input.GetBinTypesRequest(r.Body)
	if err != nil {
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	rows, err := conn.Query("SELECT DISTINCT type FROM Bins")
	if err != nil {
		return
	}

	var response soap.GetBinTypesResponse
	for rows.Next() {
		var binType string
		err = rows.Scan(&binType)
		if err != nil {
			return
		}
		response.Return = append(response.Return, binType)
	}
	err = rows.Err()
	if err != nil {
		return
	}

	err = output.GetBinTypesResponse(w, response)
	if err != nil {
		return
	}
	return
}
