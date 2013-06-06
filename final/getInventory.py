#@param: quantites has sku, upc, amount wanted, limit is the max number of entries to return
#unused: rad, code, lat, long, country, type, unit, locnames
#fetchall() to get all results from the cursor

def getInventory(fulfillerID, manufacturerID, catalogueID, quantities, locIDs, unit, rad, code, lat, long, country, type, limit, ignoreSafetyStock, includeNegativeInventory, orderByLTD):
   cursor = db.cursor()
   ordering = ""
   safe = """AND ((si.onhand > sa.safetyStock && sa.safetystock > l.defaultsafetystock) 
                   || (si.onhand > l.defaultsafetystock && l.defaultsafetystock > sa.safetystock))"""
   negative = "AND si.onhand > 0"
   values = []
   if(orderByLTD) ordering = "ORDER BY LTD"
   if(ignoreSafetyStock) safe = ""
   if(includeNegativeInventory) negative = ""
   
   try:
       #sql
       for f in quantities:
          sqlCommand = """SELECT l.Name, st.CatalogueId, st.ManufacturerId, si.OnHand, si.OnHand - si.Allocated as available, si.SKU,
                           fb.UPC, sa.LTD, sa.SafetyStockLimit, l.DefaultSafetyStockLimit
                        FROM Locations l, SubscribeTo st, StoredIn si, FulfilledBy fb, StoredAt sa
                        WHERE st.CatalogueId  = %s AND st.ManufacturerId  = %s AND l.FulfillerId = %s AND si.SKU =  %s 
                         AND fb.UPC =  %s  AND si.OnHand >= %d
                         AND st.FulfillerId = l.FulfillerId AND st.FulfillerLocationId = l.FulfillerLocationId 
                         AND si.SKU = fb.SKU AND si.FulfillerId = fb.FulfillerId AND fb.fulfillerID = l.FulfillerId
                         AND sa.SKU = si.SKU AND sa.FulfillerId = fb.FulfillerId %s %s %s""" 
                        % (catalogueid, manufacturerId, fulfillerID, f[partnumber], f[UPC], int(f[quantity]), safe, negative, ordering)
         cursor.execute(sqlCommand)
         results = cursor.fetchall()
         for a in results:
            dummyValueMaker = []
            for val in a:
               dummyValueMaker.append(val)
            #keep only 1 of the safetystocks
            if(dummyValueMaker[8] != 0): #if a different safetystock exists
               del dummyValueMaker[9]
            else:
               del dummyValueMaker[8]
            dummyValueMaker.append("")
            dummyValueMaker.append("")
            values.append(dummyValuemaker)
   except Exception, e:
      print e
      db.rollback()
      
   return values