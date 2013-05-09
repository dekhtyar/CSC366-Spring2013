import MySQLdb
def createFulfillmentLocation():
   #some pound defines
   FulfillerId = 1
   FulfillerLocationId = 3
   Name = 0
   Type = 4
   Latitude = 5
   Longitude = 6
   Status = 7
   DefaultSafetyStock = 8

   db = MySQLdb.connect("localhost", "root", "busmajorz", "inventory")
   cursor = db.cursor()

   #execute queries for all the CSV files
   csvFile = open("../data/fulfiller locations.csv", 'r')
   numAttributes = len(csvFile.readline().split(','))

   tuple = []
   insertStatement = ""
   for row in csvFile:
      tuple = row.split(',')
      if(len(tuple) > numAttributes):
         #to fix stores that have a ',' in their name
         tuple[0] += tuple[1]
         tuple.pop(1)
 
      insertStatement = "INSERT INTO Locations (FulfillerId, FulfillerLocationId, Name, Type, Latitude, Longitude, Status, DefaultSafetyStockLimit) VALUES (%d, %d, %s, %s, %f, %f, %s, %d);" % (int(tuple[FulfillerId]), int(tuple[FulfillerLocationId]), tuple[Name], tuple[Type], float(tuple[Latitude]), float(tuple[Longitude]), tuple[Status], int(tuple[DefaultSafetyStock]))
      try:
         cursor.execute(insertStatement)
         db.commit()
      except Exception, e:
         print e
         db.rollback()

   csvFile.close()
   db.close

createFulfillmentLocation()
