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
-- De-Allocate Inventory