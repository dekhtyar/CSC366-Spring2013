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
		return errors.New("All of the specified items were not allocated enough to be fulfilled.")
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

/*
	req.FulfillerID
	req.FulfillerLocationCatalog.ManufacturerID
	req.FulfillerLocationCatalog.CatalogID
	req.FulfillerLocationCatalog.ExternalLocationID
	req.Items[] ...
		Item.PartNumber, UPC, Quantity, ExternalLocationID
*/
func fulfillAllItems(tx *sql.Tx, req soap.UpdateRequest) (err error) {
	for _, item := range req.Items {
		whereClause_UPCorSKU := ""
		if item.PartNumber != "" {
			whereClause_UPCorSKU += " AND fp.SKU = '" + item.PartNumber + "' "
		}
		if item.UPC != "" {
			whereClause_UPCorSKU += " AND fp.UPC = '" + item.UPC + "' "
		}

		_, err = tx.Exec(`
			UPDATE BinProducts
			SET Allocated = Allocated - $1,
				OnHand = OnHand - $1
			WHERE EXISTS (
				SELECT 'exists'
				FROM FulfillerProducts fp
					JOIN Bins b ON (b.ID = BinProducts.BinID)
					JOIN Locations l ON (l.ID = b.LocationID)
					JOIN Products p ON (p.UPC = fp.UPC)
				WHERE fp.FulfillerID = BinProducts.FulfillerID
					AND fp.SKU = BinProducts.SKU
					AND l.ID = $2`+whereClause_UPCorSKU+`
				)`,
			item.Quantity, item.ExternalLocationID)
		if err != nil {
			return
		}
	}

	return
}
