package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"fmt"
	"net/http"
)

func getBins(w http.ResponseWriter, r *http.Request) (err error) {
	fmt.Println("Entering\n")

	req, err := input.GetBinsRequest(r.Body)
	if err != nil {
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	fmt.Println("Querying (fulfillerID=%d, locationID=%d, Search=%s, numR=%d, start=%d)\n",
		req.FulfillerID, req.FulfillerLocationID, req.SearchTerm, req.NumResults, req.ResultsStart)
	rows, err := conn.Query(`
		SELECT l.FulfillerID, b.ID, l.ID, b.Type, b.Status, b.Name
		FROM Bins b
			JOIN Locations l ON (l.Id = b.LocationID)
		WHERE l.FulfillerID = $2
			AND l.ID = $2
			AND b.Name LIKE '%$3%'
		LIMIT $4 OFFSET $5`,
		req.FulfillerID, req.FulfillerLocationID, req.SearchTerm, req.NumResults, req.ResultsStart)
	fmt.Println("here1\n")
	if err != nil {
		return
	}

	fmt.Println("here2\n")
	var response soap.GetBinsResponse
	for rows.Next() {
		var bin soap.Bin
		err = rows.Scan(&bin.FulfillerID, &bin.BinID, &bin.FulfillerLocationID,
			&bin.BinType, &bin.BinStatus, &bin.Name)
		if err != nil {
			return
		}

		response.Return.Bins = append(response.Return.Bins, bin)
	}
	err = rows.Err()
	fmt.Println("here3\n")
	if err != nil {
		return
	}

	fmt.Println("converting\n")
	err = output.GetBinResponse(w, response)
	if err != nil {
		return
	}

	fmt.Println("Returning\n")

	return
}
