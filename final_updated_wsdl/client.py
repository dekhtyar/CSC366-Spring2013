#!/usr/local/bin/python2.7
# db2.thepicard.org

from CoreServiceService_services import *
import MySQLdb
import time
import re
import csv
from subprocess import Popen, PIPE

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

# Reset database, prepare for populating
#db = MySQLdb.connect("localhost", "root", "busmajorz", "inventory")
#destroyDatabase(db)
#time.sleep(.5)
#createDatabase(db)
#time.sleep(.5)
#clearDatabase(db)
#db.close()

# Set up connection to inventory service
loc = CoreServiceServiceLocator()
port = loc.getCoreService()


## createFulfiller
## Bulk load Fulfillers
## STATUS: Working with updated wsdl.
#with open("../data/fulfiller locations.csv", "rb") as csv_file:
#    reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
#    for row in reader:
#        req = createFulfillerRequest()
#        request = req.new_request()
#        request.set_element_FulfillerID(int(row['fulfiller_id']))
#        request.set_element_Name(row['name'])
#        req.set_element_request(request)
#        print 'createFulfillerRequest:', (row['fulfiller_id'], row['name'])
#        res = port.createFulfiller(req)
#        print 'createFulfillerResponse', res.get_element_createFulfillerReturn()


## createFulfillmentLocationRequest
## Bulk load Fulfillment locations
## STATUS: Working with latest wsdl.
#with open("../data/fulfiller locations.csv", "rb") as csv_file:
#    reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
#    for row in reader:
#        req = createFulfillmentLocationRequest() 
#        request = req.new_request()
#        request.set_element_CountryCode('USA')
#        request.set_element_ExternalLocationID(row['external_fulfiller_location_id'])
#        request.set_element_FulfillerID(int(row['fulfiller_id']))
#        request.set_element_Latitude(float(row['latitude']))
#        request.set_element_LocationName(row['name'])
#        request.set_element_LocationType(row['description'])
#        request.set_element_Longitude(float(row['longitude']))
#        request.set_element_RetailerLocationID(int(row['internal_fulfiller_location_id']))
#        request.set_element_Status(1 if row['status'] == 'active' else 2)
#        req.set_element_request(request)
#        print 'createFulfillmentLocationRequest:', ('USA', row['external_fulfiller_location_id'],
#                                                    row['fulfiller_id'], row['latitude'], row['name'],
#                                                    row['description'], row['longitude'],
#                                                    row['internal_fulfiller_location_id'],
#                                                    1 if row['status'] == 'active' else 2)
#        res = port.createFulfillmentLocation(req)
#        print 'createFulfillmentLocationResponse:', res.get_element_createFulfillmentLocationReturn()


## createBin
## STATUS: Working with updated wsdl.
## external_fulfiller_location_id,internal_fulfiller_location_id,bin_name,bin_type,bin_status
#with open("../data/fulfiller location_bins.csv", "rb") as csv_file:
#    reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
#    for row in reader:
#        req = createBinRequest()
#        request = req.new_request()
#        request.set_element_BinID(1) # Not provided
#        request.set_element_BinStatus(row['bin_status'])
#        request.set_element_BinType(row['bin_type'])
#        request.set_element_FulfillerID(1) # Not provided
#        request.set_element_ExternalLocationID(row['external_fulfiller_location_id'])
#        request.set_element_Name(row['bin_name'])
#        req.set_element_request(request)
#        print 'createBinRequest:', (1, row['bin_status'], row['bin_type'], 1,
#                                    row['external_fulfiller_location_id'], row['bin_name'])
#        res = port.createBin(req)
#        print 'createBinResponse:', res.get_element_createBinReturn()


## refreshInventory
## STATUS: Working with updated wsdl.
## Example of manual testing of multiple items per request
#req = RefreshInventorySoapIn()
#Items = req.new_Items()
#items1 = Items.new_items()
#items1.set_element_BinID(123)
#items1.set_element_LTD(1.0)
#items1.set_element_PartNumber('PartNumber')
#items1.set_element_Quantity(12)
#items1.set_element_SafetyStock(10)
#items1.set_element_UPC('UPC')
#items2 = Items.new_items()
#items2.set_element_BinID(123)
#items2.set_element_LTD(1.0)
#items2.set_element_PartNumber('PartNumber2')
#items2.set_element_Quantity(12)
#items2.set_element_SafetyStock(10)
#items2.set_element_UPC('UPC2')
#Items.set_element_items([items1, items2])
#req.set_element_Items(Items)
#req.set_element_FulfillerID(123)
#req.set_element_ExternalLocationID('600')
#res = port.refreshInventory(req)
#print 'refreshInventory', res # res is an empty string
#
# CSV header
# product_name,SKU,UPC,safety_stock,ltd,mfg_id,catalog_id,onhand,bin_name,external_fulfiller_location_id,internal_fulfiller_location_id
#def refreshInventoryWithFile(file_name):
#    with open(file_name, 'rb') as csv_file:
#        reader = csv.DictReader(csv_file, delimiter=",", quotechar="'")
#        for row in reader:
#            req = RefreshInventorySoapIn()
#            Items = req.new_Items()
#            items1 = Items.new_items()
#            items1.set_element_BinID(1) # Not provided
#            items1.set_element_LTD(1.0) # Not provided
#            items1.set_element_PartNumber(row['SKU'])
#            items1.set_element_Quantity(int(row['onhand']))
#            items1.set_element_SafetyStock(int(row['safety_stock']))
#            items1.set_element_UPC(row['UPC'])
#            Items.set_element_items([items1])
#            req.set_element_Items(Items)
#            req.set_element_FulfillerID(1) # Not provided, queried later
#            req.set_element_ExternalLocationID(row['external_fulfiller_location_id'])
#
#            print 'refreshInventoryRequest:', (row['bin_name'], 1.0, row['SKU'],
#                                               row['onhand'], row['safety_stock'],
#                                               row['UPC'], 1, row['external_fulfiller_location_id'])
#            res = port.refreshInventory(req)
#            print 'refreshInventory', res # res is an empty string
#
#refreshInventoryWithFile("../data/fulfiller inventory available.csv")
#refreshInventoryWithFile("../data/fulfiller inventory available bins.csv")
#refreshInventoryWithFile("../data/fulfiller inventory not available.csv")


### getBins
### STATUS: Working with updated wsdl.
#req = getBinsRequest()
#request = req.new_request()
#request.set_element_FulfillerID(48590)
#request.set_element_ExternalLocationID(600)
#request.set_element_NumResults(30)
#request.set_element_ResultsStart(1)
#request.set_element_SearchTerm('5305')
#req.set_element_request(request)
#print '\ngetBinsRequest'
#res = port.getBins(req)
#getBinsReturn = res.get_element_getBinsReturn()
#ResultCount = getBinsReturn.get_element_ResultCount()
#Bins = getBinsReturn.get_element_Bins()
#items = Bins.get_element_items()
#if items:
#    print 'getBins: Bin: FulfillerID, ExternalLocationID, BinStatus, BinType, Name'
#for item in items:
#    FulfillerID = item.get_element_FulfillerID()
#    ExternalLocationID = item.get_element_ExternalLocationID()
#    BinStatus = item.get_element_BinStatus()
#    BinType = item.get_element_BinType()
#    Name = item.get_element_Name()
#    print 'getBins: Bin:', FulfillerID, ExternalLocationID, BinStatus, BinType, Name
#print 'getBins: ResultCount:', ResultCount

## getFulfillerStatus
## STATUS: Working with updated wsdl, realized that this took external location id.
#req = getFulfillerStatusRequest()
#req.set_element_fulfillerID(112) # Alternate test: 600. Integer, external location id
#print '\ngetFulfillerStatusRequest:', req.get_element_fulfillerID()
#res = port.getFulfillerStatus(req)
#print 'getFulfillerStatusResponse:', res.get_element_getFulfillerStatusReturn()


## getBinTypes
## STATUS: Working with updated wsdl.
#req = getBinTypesRequest()
#res = port.getBinTypes(req)
#print '\ngetBinTypesRequest'
#getBinTypesReturn = res.get_element_getBinTypesReturn()
#if not getBinTypesReturn:
#    print 'No results'
#else:
#    for binType in getBinTypesReturn:
#        print 'getBinTypes: BinType:', binType.get_element_BinType()
#
#
## getFulfillmentLocationsRequest
## STATUS: Working with updated wsdl, with workaround
#req = getFulfillmentLocationsRequest()
#request = req.new_request()
#Catalog = request.new_Catalog()
#Catalog.set_element_CatalogID(1) # Required, underflow error, cannot be 0 according to wsdl, positiveInteger, ignoring CatalogID as workaround
#Catalog.set_element_ManufacturerID(10636) # Required
#request.set_element_Catalog(Catalog) 
#Location = request.new_Location()
#Location.set_element_CountryCode('CountryCode')
#Location.set_element_Latitude(1.0)
#Location.set_element_Longitude(2.0)
#Location.set_element_PostalCode('PostalCode')
#Location.set_element_Radius(32)
#Location.set_element_Unit('MILES')
#request.set_element_Location(Location)
#request.set_element_FulfillerID(48590) # Required
#request.set_element_MaxLocations(30) # Required
#req.set_element_request(request)
#print '\ngetFulfillmentLocationsRequest'
#res = port.getFulfillmentLocations(req)
#fulfillmentLocationsReturn = res.get_element_getFulfillmentLocationsReturn()
#if fulfillmentLocationsReturn:
#    print 'getFulfillmentLocations: FulfillerID, ExternalLocationID'
#    for fulfillmentLocation in fulfillmentLocationsReturn:
#        FulfillerID = fulfillmentLocation.get_element_FulfillerID()
#        ExternalLocationID = fulfillmentLocation.get_element_ExternalLocationID()
#        print 'getFulfillmentLocations:', FulfillerID, ExternalLocationID
#else:
#    print 'getFulfillmentLocations: No results'


req = getBinStatusesRequest()
res = port.getBinStatuses(req)
print 'getBinStatuses'


#req = getFulfillmentLocationTypesRequest()
#res = port.getFulfillmentLocationTypes(req)
#print 'getFulfillmentLocationTypes'


#req = AdjustInventorySoapIn()
#Items = req.new_Items()
#items = Items.new_items()
#items.set_element_BinID()
#items.set_element_PartNumber()
#items.set_element_Quantity()
#items.set_element_UPC()
#Items.set_element_items(items)
#req.set_element_Items(Items)
#req.set_element_FulfillerID()
#req.set_element_LocationName()
#res = port.Adjust(req)
#print 'AdjustInventorySoap'


#req = getInventoryRequest()
#request = req.new_request()
#Catalog = request.new_Catalog()
#Catalog.set_element_CatalogID()
#Catalog.set_element_ManufacturerID()
#request.set_element_Catalog(Catalog)
#Location = request.new_Location()
#Location.set_element_CountryCode()
#Location.set_element_Latitude()
#Location.set_element_Longitude()
#Location.set_element_PostalCode()
#Location.set_element_Radius()
#Location.set_element_Unit()
#request.set_element_Location(Location)
#LocationNames = request.new_LocationNames()
#LocationNames.set_element_LocationNames()
#request.set_element_LocationNames(LocationNames)
#Quantities = request.new_Quantities()
#items = Quantities.new_items()
#items.set_element_PartNumber()
#items.set_element_Quantity()
#items.set_element_UPC()
#Quantities.set_element_items(items)
#request.set_element_Quantities(Quantities)
#Type = request.new_Type(1)
#request.set_element_Type(Type)
#request.set_element_FulfillerID()
#request.set_element_IgnoreSafetyStock()
#request.set_element_IncludeNegativeInventory()
#request.set_element_Limit()
#request.set_element_OrderByLTD()
#req.set_element_request(request)
#res = port.get(req)
#print 'getInventory'


#req = allocateInventoryRequest()
#request = req.new_request()
#FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
#ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
#ManufacturerCatalog.set_element_CatalogID(1)
#ManufacturerCatalog.set_element_ManufacturerID(1)
#FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
#FulfillerLocationCatalog.set_element_ExternalLocationID(600)
#request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
#Items = request.new_Items()
#item = Items.new_items()
#item.set_element_PartNumber('201594729')
#item.set_element_Quantity(1)
#item.set_element_UPC('201594729')
#item.set_element_OrderID(1)
#item.set_element_OrderItemID(1)
#item.set_element_ShipmentID(1)
#Items.set_element_items([item])
#request.set_element_Items(Items)
#request.set_element_FulfillerID(48590)
#req.set_element_request(request)
#res = port.allocateInventory(req)
#print 'allocateInventory'


#req = deallocateInventoryRequest()
#request = req.new_request()
#FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
#ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
#ManufacturerCatalog.set_element_CatalogID(1)
#ManufacturerCatalog.set_element_ManufacturerID(1)
#FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
#FulfillerLocationCatalog.set_element_ExternalLocationID(600)
#request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
#Items = request.new_Items()
#item = Items.new_items()
#item.set_element_PartNumber('201594729')
#item.set_element_Quantity(1)
#item.set_element_UPC('201594729')
#item.set_element_OrderID(1)
#item.set_element_OrderItemID(1)
#item.set_element_ShipmentID(1)
#Items.set_element_items([item])
#request.set_element_Items(Items)
#request.set_element_FulfillerID(48590)
#req.set_element_request(request)
#res = port.deallocateInventory(req)
#print 'deallocateInventory'


#req = fulfillInventoryRequest()
#request = req.new_request()
#FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
#ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
#ManufacturerCatalog.set_element_CatalogID(1)
#ManufacturerCatalog.set_element_ManufacturerID(1)
#FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
#FulfillerLocationCatalog.set_element_ExternalLocationID(600)
#request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
#Items = request.new_Items()
#item = Items.new_items()
#item.set_element_PartNumber('201594729')
#item.set_element_Quantity(1)
#item.set_element_UPC('201594729')
#item.set_element_OrderID(1)
#item.set_element_OrderItemID(1)
#item.set_element_ShipmentID(1)
#Items.set_element_items([item])
#request.set_element_Items(Items)
#request.set_element_FulfillerID(48590)
#req.set_element_request(request)
#res = port.fulfillInventory(req)
#print 'fulfillInventory'





# NOTE: This function has been removed
#req = getItemLocationsByFulfillerRequest()
#request = req.new_request()
#FulfillerIDs = request.new_FulfillerIDs()
#FulfillerIDs.set_element_items([1, 2, 3])
#request.set_element_FulfillerIDs(FulfillerIDs)
#request.set_element_LocationID(1)
#request.set_element_PartNumber('PartNumber')
#request.set_element_PostalCode('PostalCode')
#request.set_element_UPC('UPC')
#req.set_element_request(request)
#res = port.getItemLocationsByFulfiller(req)
#print 'getItemLocationsByFulfiller'
