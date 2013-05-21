#data provided by API:
# "UPC", "SKU" "BinID", "Quantity", "LTD", "Floor", "SafetyStock"
#
# NOTE: all data in brackets represent data provided by api

INSERT INTO inventory (SKU, FulfillerID , UPC) VALUES ([sku], (SELECT FulfillerID FROM Bins WHERE BinID = [binid]), [upc]);

INSERT INTO stocks (FulfillerID, ExternalFulfillerID, SKU , UPC, LTD, SafetyStock) 
VALUES ((SELECT FulfillerID, ExternalFulfillerID FROM Bins WHERE BinID = [binid]), [sku], [upc], [ltd], [safetystock]);

INSERT INTO holds (BinID, SKU , UPC , Allocation, OnHand) VALUES ([binid], [sku], [upc], 0, [onhand]);

