package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"fmt"
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
	fmt.Println("getting response")
	var response soap.GetBinStatusesResponse
	for rows.Next() {
		var binStatus string
		err = rows.Scan(&binStatus)
		if err != nil {
			return
		}
		response.Return = append(response.Return, binStatus)

	}
	fmt.Println("got responses")
	err = rows.Err()
	if err != nil {
		return
	}

	fmt.Println("Converting...\n")
	err = output.GetBinStatusesResponse(w, response)
	if err != nil {
		return
	}

	fmt.Println("Returning...\n")
	return
}
