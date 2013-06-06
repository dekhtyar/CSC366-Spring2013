package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"fmt"
	"net/http"
)

/*
From the Inventory-Spec-v2.pdf:

There are a few use modes for the Get Inventory request. However, there are some common elements
to all requests. All get inventory requests involve:
	• A manufacturer id and catalog id
	• One or more SKU/UPC combinations, with a minimum on-hand stock threshold.
	• Limited to one type of store location.
	• An upper limit on how many locations should be returned.
	• A flag on if safety stock should be enforced.
	• A flag on if negative available inventory should be included.
	• A flag to order by LTD or by quantity available.

The variations of the Get Inventory request are:
	1. Limit the results to one or more locations, identified by name.
	2. Limit the results to locations within a specific distance radius of a given Latitude and Longitude.
	3. Limit the results to locations within a specific distance radius of a given zip code.
*/

func getInventory(w http.ResponseWriter, r *http.Request) (err error) {
	fmt.Println("In getInventory\n")

	req, err := input.GetInventoryRequest(r.Body)
	if err != nil {
		return
	}

	fmt.Println("get connection\n")
	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	/*
		144 type InventoryRequest struct {
		145     FulfillerID              uint
		146     Quantities               []InventoryItem `xml:">items"`
		147     LocationIDs              []uint          `xml:">LocationIDs"`
		148     Location                 Location
		149     Type                     string
		150     Limit                    int
		151     IgnoreSafetyStock        bool
		152     IncludeNegativeInventory bool
		153     OrderByLTD               bool
		154 }
	*/
	req = req
	var resp soap.GetInventoryResponse
	/* TODO
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

	*/

	fmt.Println("converting\n")
	err = output.GetInventoryResponse(w, resp)
	if err != nil {
		return
	}

	fmt.Println("Returning\n")
	return
}
