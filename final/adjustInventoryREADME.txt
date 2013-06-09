Tested, works fine.

What adjustInventory is expecting (in order):
   FulfillerId
   FulfillerLocationId
   refreshItems: list of dictionaries with keys "Quantity", "partnumber", "BinId", "UPC" 
      keys represent quantity to add to set, SKU, bin name, UPC
   pointer to the database

What adjustInventory spits out:
   A string saying that the FulfillerLocationId was successfully updated

NOTE: api call works on an all or nothing basis: if one of the items to update fails, then nothing is commited and everything is rolled back
