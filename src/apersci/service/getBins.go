package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"net/http"
)

func getBinTypes(w http.ResponseWriter, r *http.Request) {
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

	rows, err := conn.Query(`	SELECT * FROM Bins b
								WHERE b.LocationId = $1
									AND b.Name LIKE '%$2%'
								LIMIT $3 OFFSET $4`,
								req.FulfillerLocationId, req.SearchTerm,
								req.NumResults, req.ResultsStart)
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}

	/* TODO
	var response soap.GetBinsResponse
	for rows.Next() {
		var bin string
		err = rows.Scan(&binType)
		if err != nil {
			writeStatusInternalServerError(w, err)
			return
		}
		response.Return = append(response.Return, binType)
	}
	err = rows.Err()
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	}

	err = output.GetBinTypesResponse(w, *response)
	if err != nil {
		writeStatusInternalServerError(w, err)
		return
	} */
}
