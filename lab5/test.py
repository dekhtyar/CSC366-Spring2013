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

def refreshInventories():
    pass

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
