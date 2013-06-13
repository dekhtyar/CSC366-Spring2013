package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"database/sql"
	"errors"
	"net/http"
)

func deallocateInventory(w http.ResponseWriter, r *http.Request) (err error) {
	req, err := input.DeallocateInventoryRequest(r.Body)
	if err != nil {
		return
	}

	tx, err := getDBTransaction()
	if err != nil {
		return err
	}
	defer tx.Rollback()

	isPossible, err := isDeallocationPossible(tx, req)
	if err != nil {
		return err
	}
	if !isPossible {
		return errors.New("All of the specified items were not available for deallocation.")
	}

	err = deallocateAllItems(tx, req)
	if err != nil {
		return
	}

	err = output.DeallocateInventoryResponse(w, "")
	if err != nil {
		return
	}

	err = tx.Commit()
	if err != nil {
		return err
	}

	return
}

func isDeallocationPossible(tx *sql.Tx, req soap.UpdateRequest) (isPossible bool, err error) {
	isPossible = true
	for _, item := range req.Items {
		// FIXME remove possible SQL injection
		whereClause_UPCorSKU := ""
		if item.PartNumber != "" {
			whereClause_UPCorSKU += " AND fp.SKU = '" + item.PartNumber + "' "
		}
		if item.UPC != "" {
			whereClause_UPCorSKU += " AND fp.UPC = '" + item.UPC + "' "
		}

		rows, err := tx.Query(`
			SELECT SUM(bp.Allocated)
			FROM BinProducts bp
				JOIN FulfillerProducts fp ON (fp.FulfillerID = bp.FulfillerID AND fp.SKU = bp.SKU)
				JOIN Bins b ON (b.ID = bp.BinID)
				JOIN Locations l ON (l.ID = b.LocationID)
				JOIN Products p ON (p.UPC = fp.UPC)
			WHERE l.ExternalFulfillerID = $1`+whereClause_UPCorSKU+`
			GROUP BY p.UPC`,
			item.ExternalLocationID)
		if err != nil {
			return isPossible, err
		}

		allocated, err := readIntAndCloseRows(rows, "Allocated Inventory")
		if err != nil {
			return isPossible, err
		}

		// check for available amount
		if allocated < item.Quantity {
			isPossible = false
			break
		}
	}

	return
}

func deallocateAllItems(tx *sql.Tx, req soap.UpdateRequest) (err error) {
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
		quantityToDeallocate := item.Quantity
		for _, binID := range binIDs {
			// determine maximum amount to deallocate
			rows, err = tx.Query(`
				SELECT Allocated AS MaxToDeallocate
				FROM BinProducts
				WHERE BinID = $1`,
				binID)

			maxToDeallocate, err := readIntAndCloseRows(rows, "Allocated Inventory")
			if err != nil {
				return err
			}

			if maxToDeallocate > 0 {
				var toDeallocate int
				if maxToDeallocate > quantityToDeallocate {
					toDeallocate = quantityToDeallocate
				} else {
					toDeallocate = maxToDeallocate
				}
				quantityToDeallocate = quantityToDeallocate - toDeallocate

				// deallocate
				_, err = tx.Exec(`
					UPDATE BinProducts
					SET Allocated = Allocated - $1
					WHERE BinID = $2`,
					toDeallocate, binID)
				if err != nil {
					return err
				}
			}
		}
	}

	return
}
