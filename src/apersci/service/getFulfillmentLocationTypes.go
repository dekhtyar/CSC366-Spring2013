package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"net/http"
)

func getFulfillmentLocationTypes(w http.ResponseWriter, r *http.Request) (err error) {
	err = input.GetFulfillmentLocationTypesRequest(r.Body)
	if err != nil {
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	rows, err := conn.Query("SELECT DISTINCT type FROM locations")
	if err != nil {
		return
	}

	var response soap.GetFulfillerLocationTypesResponse
	for rows.Next() {
		var fulfillmentLocationType string
		err = rows.Scan(&fulfillmentLocationType)
		if err != nil {
			return
		}
		response.Return = append(response.Return, fulfillmentLocationType)
	}
	err = rows.Err()
	if err != nil {
		Return
	}

	err = output.GetFulfillmentLocationTypesResponse(w, response)
	if err != nil {
		return
	}

	return
}
