package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"database/sql"
	"errors"
	"net/http"
	"strconv"
)

const TYPE_ALL_STORES = "ALL_STORES"
const TYPE_ALL = "ALL"
const TYPE_PARTIAL = "PARTIAL"
const TYPE_ANY = "ANY"

func getInventory(w http.ResponseWriter, r *http.Request) (err error) {
	req, err := input.GetInventoryRequest(r.Body)
	if err != nil {
		return
	}

	tx, err := getDBTransaction()
	if err != nil {
		return
	}
	defer tx.Rollback()

	resp, err := processGetInventoryRequest(req, tx)
	if err != nil {
		return
	}

	err = output.GetInventoryResponse(w, resp)
	if err != nil {
		return
	}

	err = tx.Commit()
	if err != nil {
		return
	}

	return
}

/*
	type InventoryRequest struct {
		FulfillerID              uint
		Quantities               []InventoryItem
		Type                     string
			// ALL = ALL_STORES
			// PARTIAL (can fulfill any one quantity of items)
			// ANY (has any items at all)

		LocationIDs              []uint         // Limit to these locations, if any
		Location                 Location		// Limit to a distance, if any
		Limit                    int			// upper limit on how many locations returned

		IgnoreSafetyStock        bool			// use the safety stock value?
		IncludeNegativeInventory bool		    // include negative available inventory
		OrderByLTD               bool			// Order by LTD or, if false, quantity available
	}

	type InventoryItem struct {
		PartNumber string	// SKU
		UPC        string	// UPC
		Quantity   int		// minimum available stock theshold
	}
*/
func processGetInventoryRequest(req soap.InventoryRequest, tx *sql.Tx) (resp soap.GetInventoryResponse, err error) {
	var result []soap.InventoryReturn

	locationIDs, err := getSearchableLocationIDs(req, tx)
	if err != nil {
		return
	}

	for _, locationID := range locationIDs {
		var partialItemsFound []soap.InventoryReturn
		var anyItemsFound []soap.InventoryReturn

		// check distance
		locationDistance := 0.0
		if req.Location.Radius != 0 {
			rows, err := tx.Query(`
			SELECT ST_Distance(coordinates, ST_SetSRID(ST_MakePoint($1, $2), 4326)) / $3 AS Distance
			FROM Locations
			WHERE ID = $4`,
				req.Location.Longitude, req.Location.Latitude, mile_meter_conversion, locationID)
			if err != nil {
				return resp, err
			}

			locationDistance, err = readFloatAndCloseRows(rows, "Distance")
			if err != nil {
				return resp, err
			}

		}

		if locationDistance <= req.Location.Radius {
			for _, item := range req.Quantities {
				// FIXME remove possible SQL injection
				whereClause_UPCorSKU := ""
				if item.PartNumber != "" {
					whereClause_UPCorSKU += " AND fp.SKU = '" + item.PartNumber + "' "
				}
				if item.UPC != "" {
					whereClause_UPCorSKU += " AND fp.UPC = '" + item.UPC + "' "
				}
				if item.UPC == "" && item.PartNumber == "" {
					err = errors.New("UPC or PartNumber is required")
					return resp, err
				}

				rows, err := tx.Query(`
				SELECT l.Name, SUM(bp.OnHand), SUM(bp.OnHand - bp.Allocated) AS Available,
					fp.SKU, fp.UPC, lp.LTD, lp.SafetyStock
				FROM BinProducts bp
					JOIN FulfillerProducts fp ON (fp.FulfillerID = bp.FulfillerID AND fp.SKU = bp.SKU)
					JOIN Bins b ON (b.ID = bp.BinID)
					JOIN Locations l ON (l.ID = b.LocationID)
					JOIN LocationProducts lp ON (lp.LocationID = l.ID AND lp.SKU = bp.SKU)
				WHERE l.ID = $1`+whereClause_UPCorSKU+`
				GROUP BY l.Name, fp.SKU, fp.UPC, lp.LTD, lp.SafetyStock
				`, locationID)

				if err != nil {
					return resp, err
				}

				// if item was found
				if rows.Next() {
					var ret soap.InventoryReturn
					/*
					   type InventoryReturn struct {
					   	LocationName string
					   	OnHand       int
					   	Available    int
					   	PartNumber   string
					   	UPC          string
					   	LTD          float64
					   	SafetyStock  int
					   	CountryCode  string // ??
					   	Distance     float64
					   }
					*/
					ret.Distance = locationDistance
					err = rows.Scan(&ret.LocationName, &ret.OnHand, &ret.Available, &ret.PartNumber,
						&ret.UPC, &ret.LTD, &ret.SafetyStock)
					if err != nil {
						return resp, err
					}

					err = rows.Close()
					if err != nil {
						return resp, err
					}

					// conditionally use safety stock
					effectiveAvailableItems := ret.Available - ret.SafetyStock
					if req.IgnoreSafetyStock {
						effectiveAvailableItems = ret.Available
					}

					if effectiveAvailableItems > 0 || req.IncludeNegativeInventory {
						// determine if all the items were found
						if effectiveAvailableItems >= item.Quantity {
							partialItemsFound = append(partialItemsFound, ret)
						} else {
							anyItemsFound = append(anyItemsFound, ret)
						}
					}
				}
			}

			// potentially include the result depending on the type of request
			switch {
			case req.Type == TYPE_ALL || req.Type == TYPE_ALL_STORES:
				// only include if all the items were found
				if len(partialItemsFound) == len(req.Quantities) {
					result = append(result, partialItemsFound...)
				}
			case req.Type == TYPE_PARTIAL:
				result = append(result, partialItemsFound...)
			case req.Type == TYPE_ANY:
				result = append(result, partialItemsFound...)
				result = append(result, anyItemsFound...)
			default:
				err = errors.New("Invalid Get Inventory request type: '" + req.Type + "'")
				return resp, err
			}
		}
	}

	// sort and limit the results
	result = sortGetInventoryResponse(req, result)
	if req.Limit > 0 && req.Limit < len(result) {
		result = result[:req.Limit]
	}

	resp.Return = result
	return
}

// it seems there is no easy way to sort a slice of InventoryReturns
// using different types of comparison functions
func sortGetInventoryResponse(req soap.InventoryRequest, resp []soap.InventoryReturn) (sortedResp []soap.InventoryReturn) {
	var isLessThan func(soap.InventoryReturn, soap.InventoryReturn) bool

	if req.OrderByLTD {
		isLessThan = func(inv1 soap.InventoryReturn, inv2 soap.InventoryReturn) bool {
			return inv1.LTD > inv2.LTD
		}
	} else {
		isLessThan = func(inv1 soap.InventoryReturn, inv2 soap.InventoryReturn) bool {
			return inv1.Available < inv2.Available
		}
	}

	// simple insertion sort
	for _, invReturn := range resp {
		x := 0
		for x < len(sortedResp) && isLessThan(invReturn, sortedResp[x]) {
			x = x + 1
		}

		invReturnToAppend := invReturn
		for x < len(sortedResp) {
			swp := sortedResp[x]
			sortedResp[x] = invReturnToAppend
			invReturnToAppend = swp
			x = x + 1
		}

		sortedResp = append(sortedResp, invReturnToAppend)
	}

	return
}

func getSearchableLocationIDs(req soap.InventoryRequest, tx *sql.Tx) (locationIDs []uint, err error) {
	if len(req.LocationIDs) != 0 {
		// get the location ID for each external location ID
		for _, extLocationID := range req.LocationIDs {
			locationID, err := getInternalFromExternalLocationID(tx, extLocationID)
			if err != nil {
				return locationIDs, err
			}

			locationIDs = append(locationIDs, locationID)
		}
	} else {
		// get all locations of the fulfiller
		rows, err := tx.Query(`SELECT id FROM Locations WHERE FulfillerID = $1`, req.FulfillerID)
		if err != nil {
			return locationIDs, err
		}

		locationIDs, err = readUintSliceAndCloseRows(rows)
		if err != nil {
			return locationIDs, err
		}

		if len(locationIDs) == 0 {
			err = errors.New("Could not find any locations with Fulfiller ID = '" + strconv.Itoa(int(req.FulfillerID)) + "'")
			return locationIDs, err
		}
	}

	return
}
