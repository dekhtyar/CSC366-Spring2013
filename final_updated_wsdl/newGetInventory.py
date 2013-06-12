#@param: quantites has sku, upc, amount wanted, limit is the max number of entries to return
#fetchall() to get all results from the cursor
#all: can fulfill request in whole
#partial: can fulfill part of the request with other locations
#any: any store that has a sectio of the order

#def getInventory(fulfillerID, manufacturerID, catalogueID, quantities, locIDs, unit, rad, code, lat, long, country, type, limit, ignoreSafetyStock, includeNegativeInventory, orderByLTD):
import MySQLdb

def getInventory(catalogue, fulfillerID, ignoreSafetyStock, includeNegativeInventory, limit, location, locIDs, orderByLTD, quantities, type, db):
   #db = MySQLdb.connect("localhost", "root", "busmajorz", "inventory")
   cursor = db.cursor()
   ordering = ""
   #safe = """AND ((si.OnHand > sa.SafetyStockLimit && sa.SafetystockLimit >= l.DefaultSafetyStockLimit) 
   #                OR (si.OnHand > l.DefaultSafetyStockLimit && l.DefaultSafetyStockLimit >= sa.SafetystockLimit))"""
   safe = "- sa.SafetyStockLimit "
   negative = "AND si.OnHand - si.Allocated > 0"
   #negative = "- l.DefaultSafetyStockLimit "
   values = []
   numTuplesProcessed = 0
   callResults = [] #contains the raw values for the calls
   resultsToGiveBack = [] #the parsed values to give back

   if(orderByLTD): ordering = "ORDER BY LTD"
   if(ignoreSafetyStock): safe = ""
   if(includeNegativeInventory): negative = ""

   # WORKAROUND: Removed catalog because underflow constraint won't allow zero values
   constantSQLCommand = """SELECT l.Name, st.CatalogueId, st.ManufacturerId, si.OnHand, si.OnHand - si.Allocated %s  as available, si.SKU,
                 fb.UPC, sa.LTD, sa.SafetyStockLimit, l.DefaultSafetyStockLimit
                 FROM Locations l, SubscribeTo st, StoredIn si, FulfilledBy fb, StoredAt sa
                 WHERE st.ManufacturerId  = %s AND l.FulfillerId = %s  
                 AND st.FulfillerId = l.FulfillerId AND st.FulfillerLocationId = l.FulfillerLocationId AND sa.FulfillerLocationId = l.FulfillerLocationId
                 AND si.SKU = fb.SKU AND si.FulfillerId = fb.FulfillerId AND fb.fulfillerID = l.FulfillerId
                 AND sa.SKU = si.SKU AND sa.FulfillerId = fb.FulfillerId AND si.FulfillerLocationId = l.FulfillerLocationId %s""" % (safe, catalogue["ManufacturerID"], fulfillerID, negative) #still have to put ordering, sku, upc, FulfillerLocationID, OnHand 

   # With catalog
   #constantSQLCommand = """SELECT l.Name, st.CatalogueId, st.ManufacturerId, si.OnHand, si.OnHand - si.Allocated as available, si.SKU,
   #              fb.UPC, sa.LTD, sa.SafetyStockLimit, l.DefaultSafetyStockLimit
   #              FROM Locations l, SubscribeTo st, StoredIn si, FulfilledBy fb, StoredAt sa
   #              WHERE st.CatalogueId  = %s AND st.ManufacturerId  = %s AND l.FulfillerId = %s  
   #              AND st.FulfillerId = l.FulfillerId AND st.FulfillerLocationId = l.FulfillerLocationId AND sa.FulfillerLocationId = l.FulfillerLocationId
   #              AND si.SKU = fb.SKU AND si.FulfillerId = fb.FulfillerId AND fb.fulfillerID = l.FulfillerId
   #              AND sa.SKU = si.SKU AND sa.FulfillerId = fb.FulfillerId AND si.FulfillerLocationId = l.FulfillerLocationId %s %s """ % (catalogue["CatalogID"], catalogue["ManufacturerID"], fulfillerID, safe, negative) #still have to put ordering, sku, upc, FulfillerLocationID, OnHand 

   if(type=="ALL" or type=="ALL_STORES"):
      #remove locIDs stores that are not returned by the query
      #remove from return list stores that are not returned by teh query
      try:
        for item in quantities:
           remove = []
           onHand = "AND si.OnHand >= %d" % item["Quantity"]
           sqlCommand = constantSQLCommand
           for x in range(0, len(locIDs)):
              sqlCommand += "AND si.SKU = %s " % item["partnumber"]
              sqlCommand += "AND fb.UPC = %s " % item["UPC"]
              sqlCommand += onHand
              sqlCommand += " AND l.FulfillerLocationId = %s %s " % (str(locIDs[x]), ordering)

              cursor.execute(sqlCommand)
              results = cursor.fetchone() #fetchone makes it such that if an item is in multiple bins it only gets 1 (bin not needed in response)
              if(results is None): #no results found for this store for this item
                 remove.append(locIDs[x])
                 if(locIDs[x] in callResults):
                    del callResults[callResults.index(locIDs[x])]
                 x -= 1
              else:
                 resultsAsList = list(results)
                 #keep only 1 of the safetystocks (whichever one is larger)
                 if(results[-1] > results[-2]):
                    del resultsAsList[-2]
                 else:
                    del resultsAsList[-1]
                 entry = [locIDs[x], resultsAsList]
                 if(not (entry in callResults)):
                    callResults.append(entry)
              res = cursor.fetchall() #so that we can re-query
              sqlCommand = constantSQLCommand
           for val in remove:
              del locIDs[locIDs.index(val)]
              for store in callResults:
                 if(val == store[0]): del callResults[callResults.index(store)] #delete the store that could not fulfill from the callResults
      except Exception, e:
         print e
         db.rollback()

   if(type=="PARTIAL"):
      #add stores that can fulfill any part of the full order
      try:
        for item in quantities:
           onHand = " AND si.OnHand >= %d " % item["Quantity"]
           sqlCommand = constantSQLCommand
           #sqlCommand += "AND si.SKU = %s" % item[partnumber]
           #sqlCommand += "AND fb.UPC = %s" % item[UPC]
           for loc in locIDs:
              sqlCommand += " AND si.SKU = %s " % item["partnumber"]
              sqlCommand += " AND fb.UPC = %s " % item["UPC"]
              sqlCommand += onHand
              sqlCommand += " AND l.FulfillerLocationId = %s %s " % (loc, ordering)
                 
              cursor.execute(sqlCommand)
              results = cursor.fetchone() #fetchone makes it such that if an item is in multiple bins it only gets 1 (bin not needed in response)
              if(results is None): #no results found for this store for this item
                 sqlCommand = constantSQLCommand
                 continue
              else:
                 resultsAsList = list(results)
                 #keep only 1 of the safetystocks (whichever one is larger)
                 if(results[-1] > results[-2]):
                    del resultsAsList[-2]
                 else:
                    del resultsAsList[-1]
                 entry = [item, resultsAsList] #the item it fulfills
                 if(not (item in callResults)):
                    callResults.append(entry)
                 break
              res = cursor.fetchall() #so that we can re-query
              sqlCommand = constantSQLCommand
      except Exception, e:
         print e
         db.rollback()
      #process the array to give a list of stores to fulfill the order. Else return false.

   if(type=="ANY"):
      #add stores that have the item
      try:
        for item in quantities:
           onHand = "AND si.OnHand >= %d " % item["Quantity"]
           sqlCommand = constantSQLCommand
           #sqlCommand += "AND si.SKU = %s" % item[partnumber]
           #sqlCommand += "AND fb.UPC = %s" % item[UPC]
           for loc in locIDs:
              sqlCommand += " AND si.SKU = %s " % item["partnumber"]
              sqlCommand += " AND fb.UPC = %s " % item["UPC"]
              sqlCommand += onHand
              sqlCommand += " AND l.FulfillerLocationId = %s %s" % (loc, ordering)

              cursor.execute(sqlCommand)
              results = cursor.fetchone() #fetchone makes it such that if an item is in multiple bins it only gets 1 (bin not needed in response)
              if(results is None): #no results found for this store for this item
                 sqlCommand = constantSQLCommand
                 continue
              else:
                 resultsAsList = list(results)
                 #keep only 1 of the safetystocks (whichever one is larger)
                 if(results[-1] > results[-2]):
                    del resultsAsList[-2]
                 else:
                    del resultsAsList[-1]
                 entry = [loc, resultsAsList]
                 if(not (loc in callResults)):
                    callResults.append(entry)
              res = cursor.fetchall() #so that we can re-query
              sqlCommand = constantSQLCommand
      except Exception, e:
         print e
         db.rollback()
   
   #mod callResults to give a list of stores
   if(type=="PARTIAL"):
      for store in callResults:
         resultsToGiveBack.append(store[1])
      if(len(resultsToGiveBack) != len(quantities)): #no combination of stores could fulfill the order
         failed = []
         return failed

   if(type=="ALL" or type=="ALL_STORES" or type=="ANY"):
      for store in callResults:
         if(not(store[1] in resultsToGiveBack)): # if the name of the store is not already in the return
            resultsToGiveBack.append(store[1])

   print resultsToGiveBack
   return resultsToGiveBack 



#def getInventory(catalogue, filfillerID, ignoreSafetyStock, includeNegativeInventory, limit, location, locIDs, orderbyLTD, quantities, type, db):
# fid:48590  flocid:101    manfac:10636    cat: 0     

# For Testing
#cat = {"CatalogID": 0, "ManufacturerID" : 11416}
#locs = [44077, 440001, 440029]
#order = [{"partnumber": "888076942", "UPC": "8888076942", "Quantity": 1}, {"partnumber": "8888076757", "UPC": "8888076757", "Quantity": 1}]
#getInventory(cat, '69170', 0, 0, 10000, cat, locs, 0, order, "PARTIAL", MySQLdb.connect("localhost", "root", "busmajorz", "inventory"))

   
  # try:
       #sql
   #    for item in quantities: #searches for each item in the order ("quantity")
   #      for FulLocId in locIDs: #searches each fulfillerlocationid per fulfillerid
   #         sqlCommand = """SELECT l.Name, st.CatalogueId, st.ManufacturerId, si.OnHand, si.OnHand - si.Allocated as available, si.SKU,
   #                        fb.UPC, sa.LTD, sa.SafetyStockLimit, l.DefaultSafetyStockLimit
   #                     FROM Locations l, SubscribeTo st, StoredIn si, FulfilledBy fb, StoredAt sa
   #                     WHERE st.CatalogueId  = %s AND st.ManufacturerId  = %s AND l.FulfillerId = %s AND si.SKU =  %s 
   #                      AND fb.UPC =  %s AND l.FulfillerLocationId = %s
   #                      AND st.FulfillerId = l.FulfillerId AND st.FulfillerLocationId = l.FulfillerLocationId AND sa.FulfillerLocationId = l.FulfillerLocationId
   #                      AND si.SKU = fb.SKU AND si.FulfillerId = fb.FulfillerId AND fb.fulfillerID = l.FulfillerId
   #                      AND sa.SKU = si.SKU AND sa.FulfillerId = fb.FulfillerId AND si.FulfillerLocationId = l.FulfillerLocationId %s %s %s %s""" 
   #                     % (catalogueid, manufacturerId, fulfillerID, item[partnumber], item[UPC], FulLocId, onHand, safe, negative, ordering)
   #         cursor.execute(sqlCommand)
   #         results = cursor.fetchall()
  # except Exception, e:
    #  print e
   #   db.rollback()
      
   #return values
