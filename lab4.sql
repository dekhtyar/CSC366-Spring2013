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
