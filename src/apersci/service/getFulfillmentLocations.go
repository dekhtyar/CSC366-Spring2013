package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"net/http"
)

const mile_meter_conversion = 1609.34

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

	var maxLocs uint

	if req.MaxLocations == 0 {
		maxLocs = 9000
	} else {
		maxLocs = req.MaxLocations
	}

	rows, err := conn.Query(`SELECT fulfillerId, externalFulfillerId
							 FROM Locations
							 WHERE fulfillerId = $1 AND status = 'active'
							 AND ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint($2,$3),4326), $4)
							 ORDER BY ST_Distance(coordinates, ST_SetSRID(ST_MakePoint($2, $3), 4326))
							 LIMIT $5`,
		req.FulfillerID, req.Location.Longitude, req.Location.Latitude, req.Location.Radius*mile_meter_conversion, maxLocs)

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
