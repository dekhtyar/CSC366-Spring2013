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
    pass

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

