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
       return 1
   except MySQLdb.IntegrityError, e:
       print e
       return 0
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
       print e
       #print parameters

def createFulfillmentLocation(row, db):
   query = """\
       INSERT INTO Locations (FulfillerId, FulfillerLocationId, Name,
                              Type, Latitude, Longitude, Status,
                              DefaultSafetyStockLimit)
       VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"""

   parameters = (row['fulfiller_id'], row['external_fulfiller_location_id'],
                 row['name'], row['description'], row['latitude'], row['longitude'],
                 row['status'], row['safety_stock'])

   # This doesn't work because request doesn't contain the manufacturer or catalogue information
   #SubscribeTo_query = """\
   #    INSERT INTO SubscribeTo (FulfillerId, FulfillerLocationId,
   #                             ManufacturerId, CatalogueId) 
   #    VALUES (%s, %s, %s, %s)"""
   #SubscribeTo_parameters = (row['fulfiller_id'], row['external_fulfiller_location_id'],
   #                          row['mfg_id'], row['catalog_id'])

   try:
       with db as cursor:
           cursor.execute(query, parameters)
           print 'createFulfillmentLocation: Locations: inserted', parameters 
           #cursor.execute(SubscribeTo_query, SubscribeTo_parameters)
           #print 'createFulfillmentLocation: SubscribeTo: inserted', SubscribeTo_parameters 
       createBin({'fulfiller_id': row['fulfiller_id'],
                  'external_fulfiller_location_id': row['external_fulfiller_location_id'],
                  'bin_name': 'Default', 'bin_type': 'General', 'bin_status': 'Pickable'}, db)
   except MySQLdb.IntegrityError, e:
       pass
       print e
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
       print e
       #print parameters1

   try:
       with db as cursor:
           cursor.execute(query2, parameters2)
           print 'createManufacturerCatalog: inserted', parameters2 
   except MySQLdb.IntegrityError, e:
       pass
       print e
       #print parameters2

def refreshInventoryElem(row, db):
    cur = db.cursor()

    # Find the fulfiller_id
    #cur.execute('SELECT FulfillerId FROM Locations WHERE FulfillerLocationId = %s',
    #            (row['external_fulfiller_location_id'],))
    
    #location = cur.fetchone()
    fulfiller_id = 0 if row['fulfiller_id'] is None else row['fulfiller_id']

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
        print e
        db.rollback()
    finally:
        cur.close()

def refreshInventory(request, db):
    Items = request.get_element_Items()
    items = Items.get_element_items()
    FulfillerID = request.get_element_FulfillerID()
    LocationName = request.get_element_LocationName()

    for item in items:
        BinID = item.get_element_BinID() # bin_name?
        LTD = item.get_element_LTD()
        PartNumber = item.get_element_PartNumber() # SKU
        Quantity = item.get_element_Quantity()
        SafetyStock = item.get_element_SafetyStock()
        UPC = item.get_element_UPC()
        refreshInventoryElem({'fulfiller_id': FulfillerID,
                              'external_fulfiller_location_id': '600', # Where do we get this?
                              'SKU': PartNumber,
                              'UPC': UPC,
                              'bin_name': '', # Where do we get this?
                              'onhand': Quantity,
                              'safety_stock': SafetyStock,
                              'ltd': LTD,
                              'mfg_id': '1',
                              'catalog_id': '1',
                              'product_name': ''}, db)

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
 
def getFulfillmentLocations(fID, catalogID, ManID, location, maxLocation, db):
 
   cursor = db.cursor()
   
   try:
      cursor.execute('SELECT FulfillerId, FulfillerLocationId FROM Location l join SubscribeTo s on l.FulfillerId = s.FulfillerId and l.FulfillerLocationId = s.FulfillerLocationId join Items i on i.ManufacturerId = s.ManufacturerId and i.CatalogueId = s.CatalogueId' , fid, ManID, caralogID)
 
 
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
