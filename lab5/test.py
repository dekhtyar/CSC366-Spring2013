# Kevin Stein  kestein@calpoly.edu
# Alex Spotnitz  aspotnitz@gmail.com
# Stephen Calabrese  sccalabr@calpoly.edu
# Alex Boltunov  aboltunov@gmail.com
# Luke Larson  lplarson@calpoly.edu


import MySQLdb
import api
import time
import csv
from subprocess import Popen, PIPE

# Each of these testing functions calls the appropriate API function
# for each row in each CSV file.
def createFulfillers(db):
    with open("../data/fulfiller locations.csv", "rb") as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.createFulfiller(row, db)  

def createFulfillmentLocations(db):
    with open("../data/fulfiller locations.csv", "rb") as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.createFulfillmentLocation(row, db)  

def createManufacturerCatalogs(db):
    with open('../data/fulfiller inventory available bins.csv', 'rb') as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.createManufacturerCatalog(row, db)  

    with open('../data/fulfiller inventory available.csv', 'rb') as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.createManufacturerCatalog(row, db)  

    with open('../data/fulfiller inventory not available.csv', 'rb') as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.createManufacturerCatalog(row, db)  

    with open('../data/fulfiller locations.csv', 'rb') as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.createManufacturerCatalog(row, db)  

def createBins(db):
    with open('../data/fulfiller location_bins.csv', 'rb') as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.createBin(row, db) 

def refreshInventoryWithFile(file_name, db):
    with open(file_name, 'rb') as csv_file:
        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
        for row in reader:
            api.refreshInventory(row, db)

def refreshInventories(db):
    refreshInventoryWithFile("../data/fulfiller inventory available.csv", db)
    refreshInventoryWithFile("../data/fulfiller inventory available bins.csv", db)
    refreshInventoryWithFile("../data/fulfiller inventory not available.csv", db)

def createDatabase(db):
    process = Popen('mysql %s -u%s -p%s < DB-setup.sql' % ('inventory', 'root', 'busmajorz'),
                    stdout=PIPE, stdin=PIPE, shell=True)

def clearDatabase(db):
    cur = db.cursor()
    try:
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
    except Exception, e:
        print e

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
    createManufacturerCatalogs(db)
    createFulfillmentLocations(db)
    createBins(db)
    refreshInventories(db)

    db.close()
