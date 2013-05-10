package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"fmt"
	"net/http"
)

func createFulfillmentLocation(w http.ResponseWriter, r *http.Request) {
	data, err := input.CreateFulfillmentLocationRequest(r.Body)
	fmt.Println(err)
	fmt.Println(data)
	output.CreateFulfillmentLocationResponse(w, "lalalala")
}

func dbConn(l soap.FulfillmentLocation) error {
	conn, err := sql.Open("postgres", "dbname=cait password=cait")
	if err != nil {
		return err
	}
	defer conn.Close()

	res, err := conn.Exec("INSERT INTO Locations VALUES(" + l.LocationName + "," + l.FulfillerID + "," + l.ExternalLocationID + "," + l.RetailerLocationID + "," + l.LocationType + "," + l.LocationType + ",ST_MakePoint(" + l.Longitude + "," + l.Latitude + ")," + l.Status + ")")
	if err != nil {
		return err
	}

	return nil
}
