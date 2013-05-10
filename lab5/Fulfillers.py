import MySQLdb
def createFulfillers():
   #some pound defines
   FulfillerId = 1

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
 
      insertStatement = "INSERT INTO Fulfillers(FulfillerId) VALUES (%d);" % (int(tuple[FulfillerId]))
      try:
         cursor.execute(insertStatement)
         db.commit()
      except Exception, e:
         print e
         db.rollback()

   csvFile.close()
   db.close

createFulfillmentLocation()
