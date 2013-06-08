package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"database/sql"
	"errors"
	"net/http"
)

func fulfillInventory(w http.ResponseWriter, r *http.Request) (err error) {
	req, err := input.FulfillInventoryRequest(r.Body)
	if err != nil {
		return
	}

	tx, err := getDBTransaction()
	if err != nil {
		return err
	}
	defer tx.Rollback()

	isPossible, err := isFulfillmentPossible(tx, req)
	if err != nil {
		return err
	}
	if !isPossible {
		return errors.New("All of the specified items were not available for fulfillment.")
	}

	err = fulfillAllItems(tx, req)
	if err != nil {
		return
	}

	err = output.FulfillInventoryResponse(w, "")
	if err != nil {
		return
	}

	err = tx.Commit()
	if err != nil {
		return err
	}

	return
}

func isFulfillmentPossible(tx *sql.Tx, req soap.UpdateRequest) (bool, error) {
	return isDeallocationPossible(tx, req)
}

func fulfillAllItems(tx *sql.Tx, req soap.UpdateRequest) (err error) {
	for _, item := range req.Items {
		// FIXME remove possible SQL injection
		whereClause_UPCorSKU := ""
		if item.PartNumber != "" {
			whereClause_UPCorSKU += " AND fp.SKU = '" + item.PartNumber + "' "
		}
		if item.UPC != "" {
			whereClause_UPCorSKU += " AND fp.UPC = '" + item.UPC + "' "
		}

		// collect all bin IDs
		rows, err := tx.Query(`
			SELECT DISTINCT b.ID
			FROM BinProducts bp
				JOIN FulfillerProducts fp ON (fp.FulfillerId = bp.FulfillerID AND fp.SKU = bp.SKU)
				JOIN Bins b ON (b.ID = bp.BinID)
				JOIN Locations l ON (l.ID = b.LocationID)
				JOIN Products p ON (p.UPC = fp.UPC)
			WHERE l.ExternalFulfillerID = $1`+whereClause_UPCorSKU,
			item.ExternalLocationID)
		if err != nil {
			return err
		}

		binIDs, err := readIntSliceAndCloseRows(rows)
		if err != nil {
			return err
		}

		// loop over the bins with the item
		quantityToFulfill := item.Quantity
		for _, binID := range binIDs {
			// determine maximum amount to fulfill
			rows, err = tx.Query(`
				SELECT Allocated AS MaxToFulfill
				FROM BinProducts
				WHERE BinID = $1`,
				binID)

			maxToFulfill, err := readIntAndCloseRows(rows)
			if err != nil {
				return err
			}

			if maxToFulfill > 0 {
				var toFulfill int
				if maxToFulfill > quantityToFulfill {
					toFulfill = quantityToFulfill
				} else {
					toFulfill = maxToFulfill
				}
				quantityToFulfill = quantityToFulfill - toFulfill

				// fulfill
				_, err = tx.Exec(`
					UPDATE BinProducts
					SET Allocated = Allocated - $1,
						OnHand = OnHand - $1
					WHERE BinID = $2`,
					toFulfill, binID)
				if err != nil {
					return err
				}
			}
		}
	}

	return
}
