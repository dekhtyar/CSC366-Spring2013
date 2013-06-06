def createSubscribesTo(db):
    with open('../data/fulfiller locations.csv', 'rb') as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.createSubscribesTo(row, db)
        
            
#this goes in api
def createSubscribesTo(row, db):
    query = """\
        INSERT INTO SubscribesTo (FulfillerId, FulfillerLocationId, CatalogueId)
        VALUES (%s, %s, %s)
        
     parameters = (row[fulfiller_id], row[external_fulfiller_location_id], row[catalog_id])
     
      try:
       with db as cursor:
           cursor.execute(query, parameters)
           print 'SubscribesTp: inserted', parameters
   except MySQLdb.IntegrityError, e:
       pass
       #print e
       #print parameters1