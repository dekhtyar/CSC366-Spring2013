package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"net/http"
)

func getBins(w http.ResponseWriter, r *http.Request) {
	req, err := input.GetBinsRequest(r.Body)
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

	rows, err := conn.Query(`
		SELECT l.FulfillerID, b.ID, l.ID, b.Type, b.Status, b.Name	
		FROM Bins b
			JOIN Locations l ON (l.Id = b.LocationID)
		WHERE l.FulfillerID = $2
			AND l.ID = $2
			AND b.Name LIKE '%$3%'
		LIMIT $4 OFFSET $5`,
		req.FulfillerID, req.FulfillerLocationID, req.SearchTerm, req.NumResults, req.ResultsStart)
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}

	var response soap.GetBinsResponse
	for rows.Next() {
		var bin soap.Bin
		err = rows.Scan(&bin.FulfillerID, &bin.BinID, &bin.FulfillerLocationID,
						&bin.BinType, &bin.BinStatus, &bin.Name)
		if err != nil {
			writeStatusInternalServerError(w, err)
			return
		}

		response.Return.Bins = append(response.Return.Bins, bin)
	}
	err = rows.Err()
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}

	err = output.GetBinResponse(w, response)
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}
}
