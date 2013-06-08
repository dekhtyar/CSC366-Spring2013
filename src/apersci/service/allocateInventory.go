package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"database/sql"
	"errors"
	"net/http"
)

func allocateInventory(w http.ResponseWriter, r *http.Request) (err error) {
	req, err := input.AllocateInventoryRequest(r.Body)
	if err != nil {
		return
	}

	tx, err := getDBTransaction()
	if err != nil {
		return err
	}
	defer tx.Rollback()

	isPossible, err := isAllocationPossible(tx, req)
	if err != nil {
		return err
	}
	if !isPossible {
		return errors.New("All of the specified items were not available for allocation.")
	}

	err = allocateAllItems(tx, req)
	if err != nil {
		return
	}

	err = output.AllocateInventoryResponse(w, "")
	if err != nil {
		return
	}

	err = tx.Commit()
	if err != nil {
		return err
	}

	return
}

func isAllocationPossible(tx *sql.Tx, req soap.UpdateRequest) (isPossible bool, err error) {
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
			SELECT (SUM(bp.OnHand) - SUM(bp.Allocated) - lp.SafetyStock) AS Available
			FROM BinProducts bp
				JOIN FulfillerProducts fp ON (fp.FulfillerID = bp.FulfillerID AND fp.SKU = bp.SKU)
				JOIN LocationProducts lp ON (lp.FulfillerID = bp.FulfillerID and lp.SKU = bp.SKU)
				JOIN Bins b ON (b.ID = bp.BinID)
				JOIN Locations l ON (l.ID = b.LocationID)
				JOIN Products p ON (p.UPC = fp.UPC)
			WHERE l.ExternalFulfillerID = $1`+whereClause_UPCorSKU+`
			GROUP BY lp.SafetyStock`,
			item.ExternalLocationID)
		if err != nil {
			return isPossible, err
		}

		adjustedAvailable, err := readIntAndCloseRows(rows)
		if err != nil {
			return isPossible, err
		}

		// check for available amount
		if adjustedAvailable < item.Quantity {
			isPossible = false
			break
		}
	}

	return
}

func allocateAllItems(tx *sql.Tx, req soap.UpdateRequest) (err error) {
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
		quantityToAllocate := item.Quantity
		for i, binID := range binIDs {
			// determine maximum amount to allocate
			rows, err = tx.Query(`
				SELECT (OnHand - Allocated) AS MaxToAllocate
				FROM BinProducts
				WHERE BinID = $1`,
				binID)

			maxToAllocate, err := readIntAndCloseRows(rows)
			if err != nil {
				return err
			}

			// this will force allocation in case
			// safety stock is a negative value
			isLastBin := i == len(binIDs)-1

			if maxToAllocate > 0 || isLastBin {
				var toAllocate int
				if maxToAllocate > quantityToAllocate || isLastBin {
					toAllocate = quantityToAllocate
				} else {
					toAllocate = maxToAllocate
				}
				quantityToAllocate = quantityToAllocate - toAllocate

				// allocate
				_, err = tx.Exec(`
					UPDATE BinProducts
					SET Allocated = Allocated + $1
					WHERE BinID = $2`,
					toAllocate, binID)
				if err != nil {
					return err
				}
			}
		}
	}

	return
}
