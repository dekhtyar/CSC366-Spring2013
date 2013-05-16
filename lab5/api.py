# Kevin Stein  kestein@calpoly.edu
# Alex Spotnitz  aspotnitz@gmail.com
# Stephen Calabrese  sccalabr@calpoly.edu
# Alex Boltunov  aboltunov@gmail.com
# Luke Larson  lplarson@calpoly.edu


import MySQLdb

def createFulfiller(tuple, db):
   FulfillerId = 1
   cursor = db.cursor()

   try:
      cursor.execute("INSERT INTO Fulfillers (FulfillerId) VALUES (%s)", (tuple[FulfillerId],))
      db.commit()
   except Exception, e:
      print e
      db.rollback()

def createFulfillmentLocation(tuple, db):
   FulfillerId = 1
   FulfillerLocationId = 3
   Name = 0
   Type = 4
   Latitude = 5
   Longitude = 6
   Status = 7
   DefaultSafetyStock = 8

   cursor = db.cursor()

   insertStatement = "INSERT INTO Locations (FulfillerId, FulfillerLocationId, Name, Type, Latitude, Longitude, Status, DefaultSafetyStockLimit)  VALUES (%d, %d, %s, %s, %f, %f, %s, %d)" % (int(tuple[FulfillerId]), int(tuple[FulfillerLocationId]), tuple[Name], tuple[Type], float(tuple[Latitude]), float(tuple[Longitude]), tuple[Status], int(tuple[DefaultSafetyStock]))

   try:
      cursor.execute(insertStatement)
      db.commit()
   except Exception, e:
      print e
      db.rollback()

   insertStatement = "INSERT INTO SubscribeTo (FulfillerId, FulfillerLocationId, ManufacturerId, CatalogueId)  VALUES (%d, %d, %d, %d)" % (int(tuple[FulfillerId]), int(tuple[FulfillerLocationId]), int(tuple[9]), int(tuple[10]))

   try:
      cursor.execute(insertStatement)
      db.commit()
   except Exception, e:
      print e
      db.rollback()

def createManufacturerCatalog(manufacturer_id, catalogue_id, db):
    cursor = db.cursor()

    try:
       cursor.execute("INSERT INTO Manufacturers VALUES (%s)", (manufacturer_id,))
       cursor.execute("INSERT INTO Catalogues VALUES (%s, %s)", (manufacturer_id, 
                                                                 catalogue_id))
       db.commit()
    except Exception, e:
       print e
       db.rollback()

def createBin(tuple, db):
   FulfillerId = 0
   FulfillerLocationId = 1
   Name = 2
   BinType = 3
   
   cursor = db.cursor()

   try:
      cursor.execute("INSERT INTO Bins (FulfillerId, FulfillerLocationId, Name, BinType) VALUES (%s, %s, %s, %s)", 
                     (tuple[FulfillerId], tuple[FulfillerLocationId], tuple[Name], tuple[BinType]))
      db.commit()
   except Exception, e:
      print e
      db.rollback()   

def refreshInventory(location_id, item, db):
    cur = db.cursor()

    #cur.execute('SELECT FulfillerId FROM Locations WHERE FulfillerLocationId = %s', (location_id,))
    #fulfiller_id = cur.fetchone()[0]
    fulfiller_id = 0

    sql_stored_at_where = 'WHERE SKU = %s AND FulfillerId = %s AND FulfillerLocationId = %s'
    sql_stored_in_where = sql_stored_at_where+' AND Name = %s' 
    args_stored_at_where = (item['sku'], fulfiller_id, location_id)
    args_stored_in_where = args_stored_at_where + (item['binname'],)

    # Update 'Items' table
    cur.execute('SELECT UPC FROM Items WHERE UPC = %s', (item['upc'],)) 
    if not cur.fetchone():
        cur.execute('INSERT INTO Items VALUES (%s, %s, %s, %s)',
                    (item['upc'], item['mfg_id'], item['catalog_id'], item['name']))
    else:
        cur.execute('UPDATE Items SET Name = %s, ManufacturerId = %s, CatalogueId = %s WHERE UPC = %s',
                    (item['name'], item['mfg_id'], item['catalog_id'], item['upc']))

    # Update 'StoredIn' table
    cur.execute('SELECT SKU FROM StoredIn '+sql_stored_in_where,
                args_stored_in_where)
    if not cur.fetchone():
        cur.execute('INSERT INTO StoredIn VALUES (%s, %s, %s, %s, %s, %s)',
                    args_stored_in_where+(item['onhand'], 0))
    else:
        cur.execute('UPDATE StoredIn SET OnHand = %s ' + sql_stored_in_where,
                    (item['onhand'],)+args_stored_in_where)

    # Update 'StoredAt' table
    cur.execute('SELECT SKU FROM StoredAt '+sql_stored_at_where,
                args_stored_at_where)
    if not cur.fetchone():
        cur.execute('INSERT INTO StoredAt VALUES (%s, %s, %s, %s, %s)',
                    args_stored_at_where+(item['ltd'], item['safety']))
    else:
        cur.execute('UPDATE StoredAt SET LTD = %s, SafetyStockLimit = %s '+
                    sql_stored_at_where,
                    (item['ltd'], item['safety'])+args_stored_at_where)

    db.commit()

