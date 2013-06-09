I have tested getInventory and believe it to be functioning as it is supposed to. If you guys want to run more tests feel free.
It is difficult to test PARTIAL since there does not appear to be 2 different store names that carry the same item, but within different locations works.

What the call is expecting (in order)
   catalogue - dictionary with keys "CatalogID" and "ManufacturerID" for their respective values
   FulfillerID
   ignoreSafetyStock - boolean
   includeNegativeInventory - boolean
   Limit - int for maximum number of entries to return
   location - anything really, I don't use it
   locationids - an array of ExternalLocationIds (FulfillerLocationId)
   orderbyLTD - boolean
   quantities - dictionary with keys "Quantity", "partnumber", and "UPC" for number of that item wanted, SKU, and UPC
   type - String value (ALL, ALL_STORES, ANY, PARTIAL)
   db - pointer to the open database connection (like in api)

What the call returns (in order): a list of lists containing:
   locationname
   catalogid
   manufacturerid
   onhand
   (onhand - allocated) (available units)
   sku
   upc
   ltd
   safetystocklimit

   i assume that reutrning a list of these represents what stores fulfil which part of the order, but it is unclear from WSDL
