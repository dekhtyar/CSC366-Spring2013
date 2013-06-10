package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	//	"errors"
	"fmt"
	"net/http"
)

const mile_meter_conversion float64 = 1609.34

func getFulfillmentLocations(w http.ResponseWriter, r *http.Request) (err error) {
	req, err := input.GetFulfillmentLocationsRequest(r.Body)
	if err != nil {
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	/*rows, err := conn.Query("SELECT ST_Distance(coordinates, Geography(ST_MakePoint($1, $2))) FROM Locations WHERE fulfillerId = $3 AND status = 'active'",
		req.Location.Longitude, req.Location.Latitude, req.FulfillerID)

	var count float64

	if rows.Next() {
		err = rows.Scan(&count)
		if err != nil {
			return err
		}
		rows.Close()
	} else {
		return errors.New("Distance bad ugh")
	}

	fmt.Println(count)
	*/
	rows, err := conn.Query(`SELECT fulfillerId, coordinates FROM Locations
							 WHERE fulfillerId = $1 AND status = 'active'
							 AND ST_DWithin(coordinates, Geography(ST_MakePoint($2, $3)), $4)`,
		req.FulfillerID, req.Location.Longitude, req.Location.Latitude, req.Location.Radius*mile_meter_conversion)

	fmt.Println(req.Location.Radius * mile_meter_conversion)

	if err != nil {
		return
	}

	var resp soap.GetFulfillerLocationsResponse
	for rows.Next() {
		var location soap.FulfillmentLocationsReturn
		err = rows.Scan(&location.FulfillerID, &location.ExternalLocationID)
		if err != nil {
			return
		}
		fmt.Println("here")

		resp.Return = append(resp.Return, location)
	}
	err = rows.Err()
	if err != nil {
		return
	}

	err = output.GetFulfillmentLocationsResponse(w, resp.Return)
	if err != nil {
		return
	}

	return
}
