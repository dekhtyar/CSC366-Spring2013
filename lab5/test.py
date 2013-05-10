# Testing Operations
# createDatabase (runs destroyDatabase then DB-create.sql)
# createFulfillers (Stephen)
# createFulfillmentLocations (Kevin)
# createManufacturerCatalogs (Luke)
# createBins (Alex S.)
# refreshInventories (Alex B.)
# destroyDatabase (runs DB-clean.sql)
# clearDatabase (removes data from all tables without destroying the tables)

import api

# Each of these testing functions calls the appropriate API function
# for each row in each CSV file.
def createFulfillers():
    pass

def createFulfillmentLocations():
   csvFile = open("../data/fulfiller locations.csv", 'r')
   numAttributes = len(csvFile.readline().split(','))
   
   for row in csvFile:
      tuple = row.split(',')
      if(len(tuple) > numAttributes):
         #to fix stores that have a ',' in their name
         tuple[0] += tuple[1]
         tuple.pop(1)
      #send the row from the CSV and the pointer to the open MySQL database
      createFulfillmentLocation(tuple, db)  

def createManufacturerCatalogs():
    pass

def createBins():
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

# Initiates everything
def createDatabase():
    createFulfillers()
    createFulfillmentLocations()
    createManufacturerCatalogs()
    createBins()
    refreshInventories()

def clearDatabase():
    pass

def destroyDatabase():
    pass

refreshInventories()
