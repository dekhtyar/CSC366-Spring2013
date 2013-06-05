# Kevin Stein  kestein@calpoly.edu
# Alex Spotnitz  aspotnitz@gmail.com
# Stephen Calabrese  sccalabr@calpoly.edu
# Alex Boltunov  aboltunov@gmail.com
# Luke Larson  lplarson@calpoly.edu


import MySQLdb

def createFulfiller(row, db):
   # name,fulfiller_id,external_fulfiller_location_id,internal_fulfiller_location_id,description,latitude,longitude,status,safety_stock,mfg_id,catalog_id
   query = """\
       INSERT INTO Fulfillers (FulfillerId) VALUES (%s)"""

   parameters = (row['fulfiller_id'],)

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
                         Name, BinType)
       VALUES (%s, %s, %s, %s)"""

   try:
       with db as cursor:
           cursor.execute('SELECT FulfillerId FROM Locations WHERE FulfillerLocationId = %s',
                          (row['external_fulfiller_location_id'],))
           location = cursor.fetchone()
           fulfiller_id = 0 if location is None else location[0]

           parameters = (fulfiller_id, row['external_fulfiller_location_id'],
                         row['bin_name'], row['bin_type'])

           cursor.execute(query, parameters)
           print 'createBin: inserted', parameters 
   except MySQLdb.IntegrityError, e:
       pass
       #print e
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

   try:
       with db as cursor:
           cursor.execute(query, parameters)
           print 'createFulfillmentLocation: inserted', parameters 
       createBin({'fulfiller_id': row['fulfiller_id'],
                  'external_fulfiller_location_id': row['external_fulfiller_location_id'],
                  'bin_name': 'Default', 'bin_type': 'General'}, db)
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
        print e
        db.rollback()
    finally:
        cur.close()