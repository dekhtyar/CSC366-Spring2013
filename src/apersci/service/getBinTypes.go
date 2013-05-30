package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"net/http"
)

func getBinTypes(w http.ResponseWriter, r *http.Request) {
	err := input.GetBinTypesRequest(r.Body)
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}
	defer conn.Close()

	rows, err := conn.Query("SELECT DISTINCT type FROM Bins")
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}

	var response soap.GetBinTypesResponse
	for rows.Next() {
		var binType string
		err = rows.Scan(&binType)
		if err != nil {
			writeStatusInternalServerError(w, err)
			return
		}
		response.Return = append(response.Return, binType)
	}
	err = rows.Err()
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}

	err = output.GetBinTypesResponse(w, response)
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}
}
