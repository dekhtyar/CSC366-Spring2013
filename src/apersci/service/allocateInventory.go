package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"database/sql"
	"errors"
	//	"fmt"
	"net/http"
)

// TODO catalog lookup is currently commented out
// because when an item is created it does not have
// an associated catalog...

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
	whereClause_ManCat := ""
	/*
		if req.FulfillerLocationCatalog.ManufacturerID != 0 {
			whereClause_ManCat += fmt.Sprintf(" AND c.ManufacturerID = %d", req.FulfillerLocationCatalog.ManufacturerID)
		}
		if req.FulfillerLocationCatalog.CatalogID != 0 {
			whereClause_ManCat += fmt.Sprintf(" AND c.ID = %d", req.FulfillerLocationCatalog.CatalogID)
		}
	*/

	isPossible = true
	for _, item := range req.Items {

		whereClause_UPCorSKU := ""
		if item.PartNumber != "" {
			whereClause_UPCorSKU += " AND fp.SKU = '" + item.PartNumber + "' "
		}
		if item.UPC != "" {
			whereClause_UPCorSKU += " AND fp.UPC = '" + item.UPC + "' "
		}

		rows, err := tx.Query(`
			SELECT (bp.OnHand - bp.Allocated) AS Available
			FROM BinProducts bp
				JOIN FulfillerProducts fp ON (fp.FulfillerID = bp.FulfillerID AND fp.SKU = bp.SKU)
				JOIN Bins b ON (b.ID = bp.BinID)
				JOIN Locations l ON (l.ID = b.LocationID)
				JOIN Products p ON (p.UPC = fp.UPC)
			WHERE l.ID = $1`+whereClause_UPCorSKU+whereClause_ManCat,
			//JOIN Catalogs c ON (c.ID = p.CatalogID)
			item.FulfillerLocationID)
		if err != nil {
			return isPossible, err
		}

		count, err := readIntAndCloseRows(rows)
		if err != nil {
			return isPossible, err
		}

		// get safety stock limit
		rows, err = tx.Query(`
			SELECT lp.SafetyStock
			FROM LocationProducts lp
				JOIN FulfillerProducts fp ON (fp.FulfillerID = lp.FulfillerID AND fp.SKU = lp.SKU)
				JOIN Locations l ON (l.ID = lp.LocationID)
			WHERE l.ID = $1`+whereClause_UPCorSKU+whereClause_ManCat,
			item.FulfillerLocationID)
		if err != nil {
			return isPossible, err
		}

		safetyStockLimit, err := readIntAndCloseRows(rows)
		if err != nil {
			return isPossible, err
		}

		// check for available amount
		if count < item.Quantity+safetyStockLimit {
			isPossible = false
			break
		}
	}

	return
}

/*
	req.FulfillerID
	req.FulfillerLocationCatalog.ManufacturerID
	req.FulfillerLocationCatalog.CatalogID
	req.FulfillerLocationCatalog.FulfillerLocationID
	req.Items[] ...
		Item.PartNumber, UPC, Quantity, FulfillerLocationID
*/
func allocateAllItems(tx *sql.Tx, req soap.UpdateRequest) (err error) {
	whereClause_ManCat := ""
	/*
		if req.FulfillerLocationCatalog.ManufacturerID != 0 {
			whereClause_ManCat += fmt.Sprintf(" AND c.ManufacturerID = %d", req.FulfillerLocationCatalog.ManufacturerID)
		}
		if req.FulfillerLocationCatalog.CatalogID != 0 {
			whereClause_ManCat += fmt.Sprintf(" AND c.ID = %d", req.FulfillerLocationCatalog.CatalogID)
		}
	*/

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
			SET Allocated = Allocated + $1
			WHERE EXISTS (
				SELECT 'exists'
				FROM FulfillerProducts fp
					JOIN Bins b ON (b.ID = BinProducts.BinID)
					JOIN Locations l ON (l.ID = b.LocationID)
					JOIN Products p ON (p.UPC = fp.UPC)
				WHERE fp.FulfillerID = BinProducts.FulfillerID
					AND fp.SKU = BinProducts.SKU
					AND l.ID = $2`+whereClause_UPCorSKU+whereClause_ManCat+`
				)`, // JOIN Catalogs c ON (c.ID = p.CatalogID)
			item.Quantity, item.FulfillerLocationID)
		if err != nil {
			return
		}
	}

	return
}

// TODO move to a utility file
func readIntAndCloseRows(rows *sql.Rows) (count int, err error) {
	rows.Next()
	err = rows.Scan(&count)
	if err != nil {
		return
	}

	err = rows.Close()
	if err != nil {
		return
	}

	return
}
