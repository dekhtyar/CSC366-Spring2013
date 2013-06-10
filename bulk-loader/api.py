# Kevin Stein  kestein@calpoly.edu
# Alex Spotnitz  aspotnitz@gmail.com
# Stephen Calabrese  sccalabr@calpoly.edu
# Alex Boltunov  aboltunov@gmail.com
# Luke Larson  lplarson@calpoly.edu


import MySQLdb

def createFulfiller(row, db):
   # name,fulfiller_id,external_fulfiller_location_id,internal_fulfiller_location_id,description,latitude,longitude,status,safety_stock,mfg_id,catalog_id
   query = """\
       INSERT INTO Fulfillers (FulfillerId, FulfillerName) VALUES (%s, %s)"""

   parameters = (row['fulfiller_id'], row['name'])

   try:
       with db as cursor:
           cursor.execute(query, parameters)
           print 'createFulfiller: inserted', parameters 
   except MySQLdb.IntegrityError, e:
       pass
       #print e
       #print parameters

def createBin(row, db):
   # external_fulfiller_location_id,internal_fulfiller_location_id,bin_name,bin_type,bin_status
   query = """\
       INSERT INTO Bins (FulfillerId, FulfillerLocationId,
                         Name, BinType, BinStatus)
       VALUES (%s, %s, %s, %s, %s)"""

   try:
       with db as cursor:
           cursor.execute('SELECT FulfillerId FROM Locations WHERE FulfillerLocationId = %s',
                          (row['external_fulfiller_location_id'],))
           location = cursor.fetchone()
           fulfiller_id = 0 if location is None else location[0]

           parameters = (fulfiller_id, row['external_fulfiller_location_id'],
                         row['bin_name'], row['bin_type'], row['bin_status'])

           cursor.execute(query, parameters)
           print 'createBin: inserted', parameters 
   except MySQLdb.IntegrityError, e:
       pass
       #print e
       #print parameters

#@param: FulfillerID, FulfillerLocationID, searchTerm, NumResults
#unused: resultsStart (pagination) optional
#return: array of Bins (fulfillerID, binID, fulfillerlocationID, bintype, binstatus, name), and ResultCount
#NOTE: assume ResultsStart must be 0, searchTerm?, returning rowcount?, assume if searchTerm is NULL then the LIKE
#      operator searches everything
def getBins(FulfillerID, FulfillerLocationID, searchTerm, NumResults, ResultsStart, db):
   cursor = db.cursor()

   try:
         sqlCommand = """SELECT *
                         FROM Bins b
                         WHERE b.FulfillerID = %s AND b.FulfillerLocationID = %s
                               AND b.Name LIKE '%s%%'
                         LIMIT %d, %d""" % (FulfillerID, FulfillerLocationID, searchTerm, ResultsStart, NumResults)
         cursor.execute(sqlCommand)
         results = cursor.fetchall()
         resultsCount = cursor.rowcount
         return results,resultsCount

   except Exception, e:
      print e

#@param: no input 
#return: array of BinStatuses
#Distinct?
def getBinStatuses(db):
   cursor = db.cursor()

   try:
         sqlCommand = """SELECT DINSTINCT b.BinStatus
                          FROM Bins b""" 
                   
         cursor.execute(sqlCommand)
         results = cursor.fetchall()

   except Exception, e:
      print e

   return results

#@param: no inputs
#return: array of BinTypes
#Distinct?
def getBinTypes(db):
   cursor = db.cursor()

   try:
         sqlCommand = """SELECT DINSTINCT b.BinType
              FROM Bins b""" 

         cursor.execute(sqlCommand)
         results = cursor.fetchall()

   except Exception, e:
      print e

   return results

def createFulfillmentLocation(row, db):
   query = """\
       INSERT INTO Locations (FulfillerId, FulfillerLocationId, Name,
                              Type, Latitude, Longitude, Status,
                              DefaultSafetyStockLimit)
       VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"""

   parameters = (row['fulfiller_id'], row['external_fulfiller_location_id'],
                 row['name'], row['description'], row['latitude'], row['longitude'],
                 1 if row['status'] == 'active' else 2, row['safety_stock'])

   # This doesn't work with SOAP because the request doesn't contain the manufacturer or catalogue information
   # but we need to do it any way because we have no other way to get this information
   # after losing createManufacturerCatalog
   SubscribeTo_query = """\
       INSERT INTO SubscribeTo (FulfillerId, FulfillerLocationId,
                                ManufacturerId, CatalogueId) 
       VALUES (%s, %s, %s, %s)"""
   SubscribeTo_parameters = (row['fulfiller_id'], row['external_fulfiller_location_id'],
                             row['mfg_id'], row['catalog_id'])

   try:
       with db as cursor:
           cursor.execute(query, parameters)
           print 'createFulfillmentLocation: Locations: inserted', parameters 
           cursor.execute(SubscribeTo_query, SubscribeTo_parameters)
           print 'createFulfillmentLocation: SubscribeTo: inserted', SubscribeTo_parameters 
       createBin({'fulfiller_id': row['fulfiller_id'],
                  'external_fulfiller_location_id': row['external_fulfiller_location_id'],
                  'bin_name': 'Default', 'bin_type': 'General', 'bin_status': 'Pickable'}, db)
   except MySQLdb.IntegrityError, e:
       pass
       #print e
       #print parameters

def createManufacturerCatalog(row, db):
   query1 = "INSERT INTO Manufacturers VALUES (%s)"
   query2 = "INSERT INTO Catalogues VALUES (%s, %s)"

   parameters1 = (row['mfg_id'],)
   parameters2 = (row['mfg_id'], row['catalog_id'])

   try:
       with db as cursor:
           cursor.execute(query1, parameters1)
           print 'createManufacturerCatalog: inserted', parameters1
   except MySQLdb.IntegrityError, e:
       pass
       #print e
       #print parameters1

   try:
       with db as cursor:
           cursor.execute(query2, parameters2)
           print 'createManufacturerCatalog: inserted', parameters2 
   except MySQLdb.IntegrityError, e:
       pass
       #print e
       #print parameters2

def refreshInventory(row, db):
    cur = db.cursor()

    # Find the fulfiller_id
    cur.execute('SELECT FulfillerId FROM Locations WHERE FulfillerLocationId = %s',
                (row['external_fulfiller_location_id'],))
    location = cur.fetchone()
    fulfiller_id = 0 if location is None else location[0]

    sql_stored_at_where = 'WHERE SKU = %s AND FulfillerId = %s AND FulfillerLocationId = %s'
    sql_stored_in_where = sql_stored_at_where+' AND Name = %s' 
    args_stored_at_where = (row['SKU'], fulfiller_id, row['external_fulfiller_location_id'])
    args_stored_in_where = args_stored_at_where + (row['bin_name'],)

    try:
        # Update 'Items' table
        cur.execute('SELECT UPC FROM Items WHERE UPC = %s', (row['UPC'],)) 
        if not cur.fetchone():
            cur.execute('INSERT INTO Items VALUES (%s, %s, %s, %s)',
                        (row['UPC'], row['mfg_id'], row['catalog_id'], row['product_name']))
        else:
            cur.execute('UPDATE Items SET Name = %s, ManufacturerId = %s, CatalogueId = %s WHERE UPC = %s',
                        (row['bin_name'], row['mfg_id'], row['catalog_id'], row['UPC']))

        # FulfilledBy
        cur.execute('INSERT INTO FulfilledBy VALUES (%s, %s, %s)',
                    (row['UPC'], fulfiller_id, row['SKU']))

        # Update 'StoredIn' table
        cur.execute('SELECT SKU FROM StoredIn '+sql_stored_in_where,
                    args_stored_in_where)
        if not cur.fetchone():
            cur.execute('INSERT INTO StoredIn VALUES (%s, %s, %s, %s, %s, %s)',
                        args_stored_in_where+(row['onhand'], 0))
        else:
            cur.execute('UPDATE StoredIn SET OnHand = %s ' + sql_stored_in_where,
                        (row['onhand'],)+args_stored_in_where)

        # Update 'StoredAt' table
        cur.execute('SELECT SKU FROM StoredAt '+sql_stored_at_where,
                    args_stored_at_where)
        if not cur.fetchone():
            cur.execute('INSERT INTO StoredAt VALUES (%s, %s, %s, %s, %s)',
                        args_stored_at_where+(row['ltd'], row['safety_stock']))
        else:
            cur.execute('UPDATE StoredAt SET LTD = %s, SafetyStockLimit = %s '+
                        sql_stored_at_where,
                        (row['ltd'], row['safety_stock'])+args_stored_at_where)
        db.commit()

        print 'refreshInventory: Items: inserted', str((row['UPC'], row['mfg_id'], row['catalog_id'], row['bin_name']))
        print 'refreshInventory: FulfilledBy: inserted', str((row['UPC'], fulfiller_id, row['SKU']))
        print 'refreshInventory: StoredIn: inserted', str(args_stored_in_where+(row['onhand'], 0))
        print 'refreshInventory: StoredAt: inserted', str(args_stored_at_where+(row['ltd'], row['safety_stock']))
    except Exception, e:
        #print e
        db.rollback()
    finally:
        cur.close()

def getFulfillerStatus(fID, fLID, db):
   cursor = db.cursor()
   
   try: 
      cursor.execute('SELECT Status FROM Locations WHERE FulfillerId = %s AND FulfillerLocationId = %s',fID,fLID)
   
      status = cursor.fetchone()
      if status == 'active': 
         return 1
      else: 
         return  2  
      
   except Exception, e:
      print e
 
def getFulfillmentLocations(fID, CID, MID, maxLocation, db):
 
   cursor = db.cursor()
   
   try:
      cursor.execute('SELECT FulfillerId, FulfillerLocationId FROM SubscribeTo WHERE FulfillerId = %s and ManufacturerId = %s and CatalogueId = %s ' , fID, MID, CID)
 
 
      listIDs = cursor.fetchall()
   except:
      pass
 
   return  listIDs[:maxLocation]
 
def getFulfillmentLocationTypes(fID, fLID, db):
   cursor = db.cursor()
   
   try:
     cursor.execute('SELECT Type FROM Locations WHERE FulfillerId = %s AND FulfillerLocationId = %s',fID,fLID)
 
     locationType = cursor.fetchone()
     
     return locationType
   except Exception, e:
     print e
