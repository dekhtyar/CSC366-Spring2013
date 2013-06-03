package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"net/http"
)

func getBinStatuses(w http.ResponseWriter, r *http.Request) (err error) {
	err = input.GetBinStatusesRequest(r.Body)
	if err != nil {
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	rows, err := conn.Query("SELECT DISTINCT status FROM Bins")
	if err != nil {
		return
	}

	var response soap.GetBinStatusesResponse
	for rows.Next() {
		var binStatus string
		err = rows.Scan(&binStatus)
		if err != nil {
			return
		}
		response.Return = append(response.Return, binStatus)
	}
	err = rows.Err()
	if err != nil {
		return
	}

	err = output.GetBinStatusesResponse(w, response)
	if err != nil {
		return
	}

	return
}
