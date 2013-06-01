package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func createFulfillmentLocation(w http.ResponseWriter, r *http.Request) (err error) {
	l, err := input.CreateFulfillmentLocationRequest(r.Body)
	if err != nil {
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	res, err := conn.Exec("INSERT INTO Locations VALUES($1,$2,$3,$4,$5,$6,ST_SetSRID(ST_MakePoint($7,$8),4326),$9)",
		l.LocationName, l.FulfillerID, l.ExternalLocationID, l.RetailerLocationID,
		l.LocationType, l.LocationType, l.Longitude, l.Latitude, l.Status)
	if err != nil {
		return
	}

	_, err = conn.Exec("INSERT INTO Bins(locationid, name, type) VALUES($1, 'Default', 'General')", l.RetailerLocationID)
	if err != nil {
		return
	}

	rows, _ := res.RowsAffected()
	err = output.CreateFulfillmentLocationResponse(w, fmt.Sprint(rows))
	if err != nil {
		return
	}
	return
}
