package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"net/http"
)

func getBins(w http.ResponseWriter, r *http.Request) (err error) {
	req, err := input.GetBinsRequest(r.Body)
	if err != nil {
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	rows, err := conn.Query(`
		SELECT l.FulfillerID, b.ID, l.ID, b.Type, b.Status, b.Name
		FROM Bins b
			JOIN Locations l ON (l.Id = b.LocationID)
		WHERE l.FulfillerID = $1
			AND l.ID = $2
			AND b.Name LIKE '%`+req.SearchTerm+`%'
		LIMIT $3 OFFSET $4`,
		req.FulfillerID, req.FulfillerLocationID, req.NumResults, req.ResultsStart)
	if err != nil {
		return
	}

	var resp soap.GetBinsResponse
	for rows.Next() {
		var bin soap.Bin
		err = rows.Scan(&bin.FulfillerID, &bin.BinID, &bin.FulfillerLocationID,
			&bin.BinType, &bin.BinStatus, &bin.Name)
		if err != nil {
			return
		}

		resp.Return.Bins = append(resp.Return.Bins, bin)
	}
	err = rows.Err()
	if err != nil {
		return
	}

	resp.Return.ResultCount = len(resp.Return.Bins)

	err = output.GetBinResponse(w, resp)
	if err != nil {
		return
	}

	return
}
