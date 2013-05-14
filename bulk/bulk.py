#!/usr/bin/python

from csv import DictReader
from sys import argv

import sqlite3
import sys

def sqlstr(s):
    return "'%s'" % s.replace("'", "''")

def exists(conn, sql, params):
    cur = conn.cursor()
    cur.execute(sql, params)
    return len(cur.fetchall()) > 0

def insert(conn, sql, params):
    cur = conn.cursor()
    cur.execute(sql, params)

def ful_row(row):
    return (
        int(row['fulfiller_id']),)

def createFulfiller(conn, fulfiller):
    if not exists(conn, "SELECT * FROM Fulfiller WHERE FulfillerID = ?", fulfiller):
        insert(conn, "INSERT INTO Fulfiller VALUES (?)", fulfiller)

def cat_row(row):
    return (
        int(row['mfg_id']),
        int(row['catalog_id']))

def createCatalog(conn, mfc, catalog):
    if not exists(conn, "SELECT * FROM Manufacturer WHERE MfcID = ?", (mfc,)):
        insert(conn, "INSERT INTO Manufacturer VALUES (?)", (mfc,))
    mfc_catalog = (mfc, catalog)
    if not exists(conn, "SELECT * FROM Catalog WHERE MfcID = ? AND CatalogID = ?", mfc_catalog):
        insert(conn, "INSERT INTO Catalog VALUES (?, ?)", mfc_catalog)

def sell_row(row):
    return (
        int(row['mfg_id']),
        int(row['catalog_id']),
        int(row['internal_fulfiller_location_id']))

def loc_row(row):
    return (
        int(row['fulfiller_id']),
        int(row['internal_fulfiller_location_id']),
        sqlstr(row['external_fulfiller_location_id']),
        sqlstr(row['name']),
        sqlstr(row['description']),
        float(row['latitude']),
        float(row['longitude']),
        sqlstr(row['status']),
        int(row['safety_stock']))

def locations(filename):
    with open(filename) as loc_file:
        loc_reader = DictReader(loc_file)
        return [(int(row['internal_fulfiller_location_id']), loc_row(row), sell_row(row)) for row in loc_reader]

CREATE_LOC="INSERT INTO Location VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"

def createLocation(conn, loc_id, loc):
    if not exists (conn, "SELECT * FROM Location WHERE InternalID = ?", (loc_id,)):
        insert(conn, CREATE_LOC, loc)

def createSeller(conn, seller):
    insert(conn, "INSERT INTO Seller VALUES (?, ?, ?)", seller)

def bin_row(row):
    return (
        int(row['internal_fulfiller_location_id']),
        sqlstr(row['bin_name']),
        sqlstr(row['bin_type']),
        sqlstr(row['bin_status']))

def createBin(conn, b):
    insert(conn, "INSERT INTO Bin VALUES (?, ?, ?, ?)", b)

def run_sql_file(conn, sql_path):
    with open(sql_path) as sql_file:
        conn.executescript(sql_file.read())

def createDatabase(conn):
    run_sql_file(conn, "sql/setup.sql")

def destroyDatabase(conn):
    run_sql_file(conn, "sql/destroy.sql")

def clearDatabase(conn):
    run_sql_file(conn, "sql/clear.sql")

# Refresh Invetory will access the csv defined in argv[2], connect to the DB and then 
# for every row in that csv it will query the DB to find if the item exists and update the qty, 
# or it will insert the item into the DB with the values that are in the CSV. 
def refreshInventory(conn, filename):
    cur = conn.cursor()
    invUpdate = [line.strip() for line in open(filename)]
    for row in invUpdate:
       attbt = row.split(",")
       if cmp(attbt[0], "product_name") == 0: 
          continue
       cur.execute("SELECT * FROM OnHand WHERE SKU = (?)", [attbt[1]])
       results = cur.fetchall()
       if len(results) != 0: 
          cur.execute("UPDATE OnHand SET Quantity = (?) WHERE SKU = (?)", [attbt[7]], [attbt[1]])
       else: 
          toInsert = [int(attbt[10]), attbt[0], int(attbt[9]), int(attbt[1]), int(attbt[7]), 0]
          print toInsert 
          cur.executemany("INSERT INTO OnHand VALUES(?,?,?,?,?,?)", [toInsert])

def fulfillers(filename):
    with open (filename) as ful_csv:
        ful_reader = DictReader(ful_csv)
        return [ful_row(row) for row in ful_reader]

def createFulfillers(conn, filename):
    for f in fulfillers(filename):
        createFulfiller(conn, f)

def catalogs(filename):
    with open (filename) as cat_csv:
        cat_reader = DictReader(cat_csv)
        return [cat_row(row) for row in cat_reader]

def createLocations(conn, filename):
    for (loc_id, l, s) in locations(filename):
        createLocation(conn, loc_id, l)
        createSeller(conn, s)

def createCatalogs(conn, filename):
    for (m, c) in catalogs(filename):
        createCatalog(conn, m, c)

def bins(filename):
    with open(filename) as bin_file:
        bin_reader = DictReader(bin_file)
        return [bin_row(row) for row in bin_reader]

def createBins(conn, filename):
    for b in bins(filename):
        createBin(conn, b)

if __name__ == "__main__":
    if len(argv) < 2:
        print "Commands: create, clear, destroy, refresh"
        print "Operations: fulfillers, catalogs, locations, bins"
        sys.exit(1)

    conn = sqlite3.connect('slightlyblue.sqlite')
    locs_file = "csv/locations.csv"
    bins_file = "csv/bins.csv"

    if argv[1] == "create":
        createDatabase(conn)
    elif argv[1] == "clear":
        clearDatabase(conn)
    elif argv[1] == "destroy":
        destroyDatabase(conn)
    elif argv[1] == "refresh": 
        refreshInventory(conn, "/csv/newProduct.csv") 
    elif argv[1] == "fulfillers":
        createFulfillers(conn, locs_file)
    elif argv[1] == "locations":
        createLocations(conn, locs_file)
    elif argv[1] == "catalogs":
        createCatalogs(conn, locs_file)
    elif argv[1] == "bins":
        createBins(conn, bins_file)
    elif argv[1] == "print":
        cur = conn.cursor()
        print "=== Manufacturers ==="
        for m in cur.execute("SELECT * FROM Manufacturer"):
            print m 
        print "=== Catalogs ==="
        for c in cur.execute("SELECT * FROM Catalog"):
            print c
        print "=== Fulfillers ==="
        for f in cur.execute("SELECT * FROM Fulfiller"):
            print f
        print "=== Locations ==="
        for l in cur.execute("SELECT * FROM Location"):
            print l
        print "=== Sellers ==="
        for s in cur.execute("SELECT * FROM Seller"):
            print s
        print "=== Bins ==="
        for b in cur.execute("SELECT * FROM Bin"):
            print b
    else:
        print "{0} is not a command".format(argv[1])
        sys.exit(1)

    conn.commit()
    conn.close()
