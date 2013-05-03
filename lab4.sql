--Team Ross
-----------
--Ian McBride
--Ross McKelvie
--Matt Tondreau
--Matt Schirle
--Riley Lew

-- createFulfiller
INSERT INTO Fulfillers (fulfillerId)
VALUES (__retailerId__);

-- createFulfillmentLocation
INSERT INTO Locations (locationId, fulfillerId, name, locationType, latitude, longitude, status, safetyStockLimitDefault)
VALUES (__locationId, __retailerId__, __locationName__, __typeId__, __latitude__, __longitude__, __status__, null);

-- createManufacturerCatalog 
INSERT INTO Catalogs ( CatalogID, ManufacturerID ) VALUES ( __CID__, MID__);

-- createBin
INSERT INTO Bins( BinName, FillerId, LocationId, binType, Status )
	VALUES (__Name__,  __FulfillerID__, __FulfillerLocationID__, __BinTypeID__, _BinStatusID__ ); 

-- BulkInventory Update
-- TrickleInventoryUpdate
UPDATE  BinContainsProducts 
	SET Quantity = __Quantity__
	WHERE BinName=BinID
		AND FulfillerID=__FullFillerID__
		AND LocationID=__LocationName__ ?
		AND UPC=__UPC__;
UPDATE LocationSellsProducts ( SafetyStock, LTD ) 
	SET SafetyStock =__SafetyStock__,
		LTD=__LTD__ 		
	WHERE FulfillerID=__FulfillerID__
		AND LocationID=__LocationID__;

-- GetInventory

-- AllocateInventory
-- Our server may have to invoke this on multiple bins if any one bin does not
-- have enough of the product on hand to make the entire allocation.
UPDATE BinContainsProducts 
	SET allocated = allocated + __quantity__
	WHERE binName = BinID
		AND fulfillerID = __FullFillerID__
		AND locationID = __LocationId__
		AND productUpc = __UPC__;

-- De-Allocate Inventory
-- I couldn't figure out how to handle the case where the product is allocated across multiple bins,
-- so I just wrote this to deallocate one from first bin. We can call it "quantity" number of 
-- times to deallocate the correct amount from the given location.
UPDATE BinContainsProducts 
	SET FIRST(allocated) = FIRST(allocated) - 1
	WHERE fulfillerID = __FullFillerID__
		AND locationID = __LocationId__
		AND productUpc = __UPC__
		AND allocated > 0;