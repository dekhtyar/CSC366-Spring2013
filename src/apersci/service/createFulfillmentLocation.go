package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func createFulfillmentLocation(w http.ResponseWriter, r *http.Request) {
	l, err := input.CreateFulfillmentLocationRequest(r.Body)
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

	res, err := conn.Exec("INSERT INTO Locations VALUES($1,$2,$3,$4,$5,$6,ST_SetSRID(ST_MakePoint($7,$8),4326),$9)",
		l.LocationName, l.FulfillerID, l.ExternalLocationID, l.RetailerLocationID,
		l.LocationType, l.LocationType, l.Longitude, l.Latitude, l.Status)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	rows, _ := res.RowsAffected()
	err = output.CreateFulfillmentLocationResponse(w, fmt.Sprint(rows))
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
