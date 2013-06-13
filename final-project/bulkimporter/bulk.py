#!/usr/bin/python

from csv import DictReader, reader
from sys import argv

import sqlite3
import urllib2
import sys

def ful_row(row):
    return (
        int(row['fulfiller_id']),)
    
def cat_row(row):
    return (
        int(row['mfg_id']),
        int(row['catalog_id']))

def sell_row(row):
    return (
        int(row['mfg_id']),
        int(row['catalog_id']),
        int(row['internal_fulfiller_location_id']))

def loc_row(row):
    return (
        int(row['fulfiller_id']),
        int(row['internal_fulfiller_location_id']),
        row['external_fulfiller_location_id'],
        row['name'],
        row['description'],
        float(row['latitude']),
        float(row['longitude']),
        row['status'],
        int(row['safety_stock']))    

def bin_row(row):
    return (
        int(row['internal_fulfiller_location_id']),
        row['bin_name'],
        row['bin_type'],
        row['bin_status'])

def locations(filename):
    with open(filename) as loc_file:
        loc_reader = DictReader(loc_file)
        return [(int(row['internal_fulfiller_location_id']), loc_row(row), sell_row(row)) for row in loc_reader]

def fulfillers(filename):
    with open (filename) as ful_csv:
        ful_reader = DictReader(ful_csv)
        return [ful_row(row) for row in ful_reader]

def catalogs(filename):
    with open(filename) as cat_csv:
        cat_reader = DictReader(cat_csv)
        return [cat_row(row) for row in cat_reader]

def find_loc(reader, id):
    for l in reader:
        l_id = l['internal_fulfiller_location_id']
        if l_id == id:
            return l

def bins(filename, loc_filename):
    with open(loc_filename) as loc_file:
        loc_reader = DictReader(loc_file)
        locs = [x for x in loc_reader]
        with open(filename) as bin_file:
            bin_reader = DictReader(bin_file, quotechar = "'")
            return [(row, find_loc(locs, row['internal_fulfiller_location_id'])) for row in bin_reader]

class Shopatron:
    def createFulfillers(self, filename):
        for f in fulfillers(filename):
            self.createFulfiller(f)
    
    def createLocations(self, filename):
        for (loc_id, l, s) in locations(filename):
            self.createLocation(loc_id, l)
            self.createSeller(s)
    
    def createCatalogs(self, filename):
        for (m, c) in catalogs(filename):
            self.createCatalog(m, c)
    
    def createBins(self, filename, loc_filename):
        i = 1
        for b, l in bins(filename, loc_filename):
            if l:
                self.createBin(b, l, i)
                i += 1
    
    def refreshInventory(self, filename):
        """
        Refresh Invetory will access the csv defined in argv[2], connect to the DB 
        and then for every row in that csv it will query the DB to find if the 
        item exists and update the qty, or it will insert the item into the DB with
        the values that are in the CSV. 
        """
        with open(filename) as item_file:
            for attbt in reader(open(filename), quotechar = "'"):
               if attbt[0] == 'product_name':
                  continue
               # name = attbt['product_name']
               # sku = attbt['SKU']
               # upc = attbt['UPC']
               # safety = attbt['safety_stock']
               # ltd = attbt['ltd']
               # mfc = attbt['mfg_id']
               # catalog = attbt['catalog_id']
               # onhand = attbt['onhand']
               # bin_name = attbt['bin_name']
               # ext_id = attbt['external_fulfiller_location_id']
               # loc = attbt['internal_fulfiller_location_id']
               self.createItem(int(attbt[2]), int(attbt[5]), int(attbt[6]), int(attbt[10]), int(attbt[1]))
               self.createStock(int(attbt[10]), int(attbt[9]), int(attbt[1]), int(attbt[3]), int(attbt[7]), float(attbt[4]))
               self.createOnHand(int(attbt[10]), attbt[0], int(attbt[9]), int(attbt[1]), int(attbt[7]), attbt[8])

class SoapImporter(Shopatron):
    def sendReq(self, req, **fmt):
        headers = {"SOAPAction": "test"}
        data = req.format(**fmt)
        req = urllib2.Request('http://slightlyblue.rwell.org:8080/shopatron/services/CoreService', data, headers)
        resp = urllib2.urlopen(req)
        print resp.read()

    def createFulfiller(self, fulfiller):
        tmp = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
  <soapenv:Header/>
  <soapenv:Body>
    <v4:createFulfiller>
      <v4:request>
        <v4:FulfillerID>{FulfillerID}</v4:FulfillerID>
      </v4:request>
    </v4:createFulfiller>
  </soapenv:Body>
</soapenv:Envelope>
        """
        return self.sendReq(tmp, FulfillerID = fulfiller[0])

    def createCatalog(self, mfc, catalog):
        pass

    def createLocation(self, loc_id, loc):
        tmp = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
  <soapenv:Header/>
  <soapenv:Body>
    <v4:createFulfillmentLocation>
      <v4:request>
        <v4:FulfillerID>{FulfillerID}</v4:FulfillerID>
        <v4:RetailerLocationID>{RetailerLocationID}</v4:RetailerLocationID>
        <v4:ExternalLocationID>{ExternalLocationID}</v4:ExternalLocationID>
        <v4:LocationName>{LocationName}</v4:LocationName>
        <v4:LocationType>{LocationType}</v4:LocationType>
        <v4:Latitude>{Latitude}</v4:Latitude>
        <v4:Longitude>{Longitude}</v4:Longitude>
        <v4:Status>{Status}</v4:Status>
      </v4:request>
    </v4:createFulfillmentLocation>
  </soapenv:Body>
</soapenv:Envelope>
        """
        return self.sendReq(tmp, FulfillerID = loc[0], RetailerLocationID = loc[1], ExternalLocationID = loc[2], LocationName = loc[3], LocationType = loc[4], Latitude = loc[5], Longitude = loc[6], Status = loc[7])

    def createSeller(self, seller):
        pass

    def createBin(self, b, l, i):
        tmp = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
  <soapenv:Header/>
  <soapenv:Body>
    <v4:createBin>
      <v4:request>
        <v4:FulfillerID>{FulfillerID}</v4:FulfillerID>
        <v4:BinID>{BinID}</v4:BinID>
        <v4:ExternalLocationID>{ExternalLocationID}</v4:ExternalLocationID>
        <v4:BinType>{BinType}</v4:BinType>
        <v4:BinStatus>{BinStatus}</v4:BinStatus>
        <v4:Name>{Name}</v4:Name>
      </v4:request>
    </v4:createBin>
  </soapenv:Body>
</soapenv:Envelope>
        """
        self.sendReq(tmp, FulfillerID = l['fulfiller_id'], BinID = i, ExternalLocationID = b['external_fulfiller_location_id'], BinType = b['bin_type'], BinStatus = b['bin_status'], Name = b['bin_name'])
        
    def createItem(self, upc, mfc_id, catalog_id, location_id, sku):
        pass
    
    def createStock(self, location_id, fulfiller_id, sku, quantity, safety, ltd):
        pass
    
    def createOnHand(self, location_id, name, fulfiller_id, sku, quantity, bin_name):
        tmp = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:q0="http://v4.core.coexprivate.api.shopatron.com" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <soapenv:Body>
        <q0:AdjustRequest>
            <q0:FulfillerID>{FulfillerID}</q0:FulfillerID> 
            <q0:ExternalLocationID>{LocationID}</q0:ExternalLocationID>
            <q0:Items>
                <q0:items>
                <q0:PartNumber>{SKU}</q0:PartNumber>
                <q0:BinID>{BinID}</q0:BinID> 
                <q0:Quantity>{Quantity}</q0:Quantity> 
                </q0:items>
            </q0:Items>
        </q0:AdjustRequest>
    </soapenv:Body>
</soapenv:Envelope>
        """
        self.sendReq(tmp, FulfillerID = fulfiller_id, LocationID = location_id, SKU = sku, BinID = bin_name, Quantity = quantity)

class PrintSql(Shopatron):
    def createFulfiller(self, fulfiller):
        print "INSERT INTO Fulfiller VALUES (%d);" % fulfiller

    def createCatalog(self, mfc, catalog):
        print "INSERT INTO Manufacturer VALUES (%d);" % mfc
        print "INSERT INTO Catalog VALUES (%d, %d);" % (mfc, catalog)

    def createLocation(self, loc_id, loc):
        print "INSERT INTO Location VALUES (%d, %d, '%s', '%s', '%s', %f, %f, '%s', %d);" % loc

    def createSeller(self, seller):
        print "INSERT INTO Seller VALUES (%d, %d, %d);" % seller

    def createBin(self, b):
        print "INSERT INTO Bin VALUES (%d, %s, %s, %s);" % b
        
    def createItem(self, upc, mfc_id, catalog_id, location_id, sku):
        print "INSERT INTO Item VALUES (%d, %d, %d)" % (upc, mfc_id, catalog_id)
        print "INSERT INTO FulfillerItem (%d, %d, %d) SELECT FulfillerID, %d, %d FROM Location WHERE InternalID = %d" % (sku, upc, location_id)
    
    def createStock(self, location_id, fulfiller_id, sku, quantity, safety, ltd):
        print "INSERT INTO Stock VALUES (%d, %d, %d, %d, %d)" % (location_id, fulfiller_id, sku, quantity, safety, ltd)
    
    def createOnHand(self, location_id, name, fulfiller_id, sku, quantity, allocated):
        print "INSERT INTO OnHand VALUES (%d, '%s', %d, %d, %d, %d)" % (location_id, name, fulfiller_id, sku, quantity, allocated)

class SqlDatabase(Shopatron):
    def __init__(self, conn):
        self.conn = conn
    
    def exists(self, sql, params):
        cur = self.conn.cursor()
        cur.execute(sql, params)
        return len(cur.fetchall()) > 0
    
    def insert(self, sql, params):
        cur = self.conn.cursor()
        cur.execute(sql, params)
    
    def update(self, sql, params):
        insert(self.conn, sql, params)
    
    def select(self, sql, params):
        cur = self.conn.cursor()
        cur.execute(sql, params)
        return cur.fetchone()[0]
    
    def createFulfiller(self, fulfiller):
        if not self.exists("SELECT * FROM Fulfiller WHERE FulfillerID = ?", fulfiller):
            self.insert("INSERT INTO Fulfiller VALUES (?)", fulfiller)

    def createCatalog(self, mfc, catalog):
        if not self.exists("SELECT * FROM Manufacturer WHERE MfcID = ?", (mfc,)):
            self.insert("INSERT INTO Manufacturer VALUES (?)", (mfc,))
        mfc_catalog = (mfc, catalog)
        if not self.exists("SELECT * FROM Catalog WHERE MfcID = ? AND CatalogID = ?", mfc_catalog):
            self.insert("INSERT INTO Catalog VALUES (?, ?)", mfc_catalog)

    def createLocation(self, loc_id, loc):
        CREATE_LOC="INSERT INTO Location VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        if not self.exists("SELECT * FROM Location WHERE InternalID = ?", (loc_id,)):
            self.insert(CREATE_LOC, loc)

    def createSeller(self, seller):
        self.insert("INSERT INTO Seller VALUES (?, ?, ?)", seller)

    def createBin(self, b):
        self.insert("INSERT INTO Bin VALUES (?, ?, ?, ?)", b)
        
    def createItem(self, upc, mfc_id, catalog_id, location_id, sku):
        fulfiller_id = int(select(conn, "SELECT FulfillerID FROM Location WHERE InternalID = ?", (location_id,)))
        if not self.exists("SELECT * FROM Item WHERE UPC = ?", (upc, )):
            self.insert("INSERT INTO Item VALUES (?, ?, ?)", (upc, mfc_id, catalog_id))
        if not self.exists("SELECT * FROM FulfillerItem WHERE FulfillerID = ? AND SKU = ?", (fulfiller_id, sku)):
            self.insert("INSERT INTO FulfillerItem VALUES (?, ?, ?)", (fulfiller_id, sku, upc))
    
    def createStock(self, location_id, fulfiller_id, sku, quantity, safety, ltd):
        if not self.exists("SELECT * FROM Stock WHERE LocationID = ? AND FulfillerID = ? AND SKU = ?", (location_id, fulfiller_id, sku)):
            self.insert("INSERT INTO Stock VALUES (?, ?, ?, ?, ?, ?)", (location_id, fulfiller_id, sku, quantity, safety, ltd))
        else:
            self.update("UPDATE Stock SET Quantity = Quantity + ? WHERE LocationID = ? AND FulfillerID = ? AND SKU = ?", (quantity, location_id, fulfiller_id, sku))
    
    def createOnHand(self, location_id, name, fulfiller_id, sku, quantity, allocated):
        if not self.exists("SELECT * FROM OnHand WHERE LocationID = ? AND Name = ? AND FulfillerID = ? AND SKU = ?", (location_id, name, fulfiller_id, sku)):
            self.insert("INSERT INTO OnHand VALUES (?, ?, ?, ?, ?, ?)", (location_id, name, fulfiller_id, sku, quantity, allocated))
        else:
            self.update("UPDATE OnHand SET Quantity = ? WHERE LocationID = ? AND Name = ? AND FulfillerID = ? AND SKU = ?", (quantity, location_id, name, fulfiller_id, sku))

def run_sql_file(conn, sql_path):
    with open(sql_path) as sql_file:
        conn.executescript(sql_file.read())

def createDatabase(conn):
    run_sql_file(conn, "sql/setup.sql")

def destroyDatabase(conn):
    run_sql_file(conn, "sql/destroy.sql")

def clearDatabase(conn):
    run_sql_file(conn, "sql/clear.sql")

if __name__ == "__main__":
    if len(argv) < 2:
        print "Commands: create, clear, destroy, refresh, import"
        sys.exit(1)

    conn = sqlite3.connect('slightlyblue.sqlite')
    db = SoapImporter()
    locs_file = "csv/locations.csv"
    bins_file = "csv/bins.csv"

    if argv[1] == "create":
        createDatabase(conn)
    elif argv[1] == "clear":
        clearDatabase(conn)
    elif argv[1] == "destroy":
        destroyDatabase(conn)
    elif argv[1] == "refresh": 
        db.refreshInventory("csv/newProduct.csv") 
    elif argv[1] == "import":
        if len(argv) > 2:
            if argv[2] == "fulfillers":
                db.createFulfillers(locs_file)
            elif argv[2] == "locations":
                db.createLocations(locs_file)
            elif argv[2] == "catalogs":
                db.createCatalogs(locs_file)
            elif argv[2] == "bins":
                db.createBins(bins_file, locs_file)
            else:
                print "{0} is not an entity set".format(argv[2])
                sys.exit(1)
        else:
            db.createFulfillers(locs_file)
            db.createLocations(locs_file)
            db.createCatalogs(locs_file)
            db.createBins(bins_file, locs_file)
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
