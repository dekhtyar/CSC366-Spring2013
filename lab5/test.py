# Kevin Stein  kestein@calpoly.edu
# Alex Spotnitz  aspotnitz@gmail.com
# Stephen Calabrese  sccalabr@calpoly.edu
# Alex Boltunov  aboltunov@gmail.com
# Luke Larson  lplarson@calpoly.edu


import MySQLdb
import api
import time
from subprocess import Popen, PIPE

# Each of these testing functions calls the appropriate API function
# for each row in each CSV file.
def createFulfillers(db):
    csvFile = open("../data/fulfiller locations.csv", 'r')
    numAttributes = len(csvFile.readline().split(','))

    for row in csvFile:
       tuple = row.split(',')
       if(len(tuple) > numAttributes):
          # to fix stores that have a ',' in their name
          tuple[0] += tuple[1]
          tuple.pop(1)
       # send the row from the CSV and the pointer to the open MySQL database
       api.createFulfiller(tuple, db)  

def createFulfillmentLocations(db):
    csvFile = open("../data/fulfiller locations.csv", 'r')
    numAttributes = len(csvFile.readline().split(','))
    
    for row in csvFile:
       tuple = row.split(',')
       if(len(tuple) > numAttributes):
          #to fix stores that have a ',' in their name
          tuple[0] += tuple[1]
          tuple.pop(1)
       #send the row from the CSV and the pointer to the open MySQL database
       api.createFulfillmentLocation(tuple, db)  

def createManufacturerCatalogs(db):
    csvFile = open('../data/fulfiller inventory available bins.csv', 'r')
    csvFile.readline() # Skip header
    for row in csvFile:
        row = row.split(',')
        manufacturer_id = row[5]
        catalogue_id = row[6]
        api.createManufacturerCatalog(manufacturer_id, catalogue_id, db)  
    csvFile.close()

    csvFile = open('../data/fulfiller inventory available.csv', 'r')
    csvFile.readline() # Skip header
    for row in csvFile:
        row = row.split(',')
        manufacturer_id = row[5]
        catalogue_id = row[6]
        api.createManufacturerCatalog(manufacturer_id, catalogue_id, db)  
    csvFile.close()

    csvFile = open('../data/fulfiller inventory not available.csv', 'r')
    csvFile.readline() # Skip header
    for row in csvFile:
        row = row.split(',')
        manufacturer_id = row[5]
        catalogue_id = row[6]
        api.createManufacturerCatalog(manufacturer_id, catalogue_id, db)  
    csvFile.close()
   
    csvFile = open('../data/fulfiller locations.csv', 'r')
    csvFile.readline() # Skip header
    for row in csvFile:
        row = row.split(',')
        manufacturer_id = row[9]
        catalogue_id = row[10]
        api.createManufacturerCatalog(manufacturer_id, catalogue_id, db)  
    csvFile.close()

def createBins(db):
    pass

def refreshInventoryWithFile(file_name):
    csv_file = open(file_name, 'r')
    csv_file.readline()
    for row in csv_file:
        tuple = row.split(',')
        item = {
            'upc': tuple[2],
            'name': tuple[0],
            'binname': tuple[8],
            'onhand': tuple[7],
            'safety': tuple[3],
            'ltd': tuple[4]
        }
        api.refreshInventory(0, tuple[10], item)
    csv_file.close()

def refreshInventories():
    refreshInventoryWithFile("../data/fulfiller inventory available.csv")
    refreshInventoryWithFile("../data/fulfiller inventory available bins.csv")
    refreshInventoryWithFile("../data/fulfiller inventory not available.csv")

def createDatabase(db):
    process = Popen('mysql %s -u%s -p%s < DB-setup.sql' % ('inventory', 'root', 'busmajorz'),
                    stdout=PIPE, stdin=PIPE, shell=True)

def clearDatabase(db):
    cur = db.cursor()
    cur.execute('DELETE FROM Bins')
    cur.execute('DELETE FROM Catalogues')
    cur.execute('DELETE FROM FulfilledBy')
    cur.execute('DELETE FROM Fulfillers')
    cur.execute('DELETE FROM Items')
    cur.execute('DELETE FROM Locations')
    cur.execute('DELETE FROM Manufacturers')
    cur.execute('DELETE FROM StoredAt')
    cur.execute('DELETE FROM StoredIn')
    cur.execute('DELETE FROM SubscribeTo')
    db.commit()

def destroyDatabase(db):
    process = Popen('mysql %s -u%s -p%s < DB-cleanup.sql' % ('inventory', 'root', 'busmajorz'),
                    stdout=PIPE, stdin=PIPE, shell=True)
	

if __name__ == '__main__':
    db = MySQLdb.connect("localhost", "root", "busmajorz", "inventory")

    destroyDatabase(db)
    time.sleep(.5)

    createDatabase(db)
    time.sleep(.5)

    clearDatabase(db)

    createFulfillers(db)
    createFulfillmentLocations(db)
    createManufacturerCatalogs(db)
    createBins(db)

    #refreshInventories(db)

    db.close()
    
