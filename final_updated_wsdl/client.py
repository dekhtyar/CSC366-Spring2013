#!/usr/local/bin/python2.7

# Kevin Stein  kestein@calpoly.edu
# Alex Spotnitz  aspotnitz@gmail.com
# Stephen Calabrese  sccalabr@calpoly.edu
# Alex Boltunov  aboltunov@gmail.com
# Luke Larson  lplarson@calpoly.edu

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

## Reset database, prepare for populating
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
#
#
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
#
#
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
#
#
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
## CSV header
## product_name,SKU,UPC,safety_stock,ltd,mfg_id,catalog_id,onhand,bin_name,external_fulfiller_location_id,internal_fulfiller_location_id
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


# getBins
# STATUS: Working with updated wsdl.
req = getBinsRequest()
request = req.new_request()
request.set_element_FulfillerID(48590)
request.set_element_ExternalLocationID(600)
request.set_element_NumResults(30)
request.set_element_ResultsStart(1)
request.set_element_SearchTerm('5305')
req.set_element_request(request)
print '\ngetBinsRequest'
res = port.getBins(req)
getBinsReturn = res.get_element_getBinsReturn()
ResultCount = getBinsReturn.get_element_ResultCount()
Bins = getBinsReturn.get_element_Bins()
items = Bins.get_element_items()
if items:
    print 'getBins: Bin: FulfillerID, ExternalLocationID, BinStatus, BinType, Name'
for item in items:
    FulfillerID = item.get_element_FulfillerID()
    ExternalLocationID = item.get_element_ExternalLocationID()
    BinStatus = item.get_element_BinStatus()
    BinType = item.get_element_BinType()
    Name = item.get_element_Name()
    print 'getBins: Bin:', FulfillerID, ExternalLocationID, BinStatus, BinType, Name
print 'getBins: ResultCount:', ResultCount


# getFulfillerStatus
# STATUS: Working with updated wsdl, realized that this took external location id.
req = getFulfillerStatusRequest()
req.set_element_fulfillerID(112) # Alternate test: 600. Integer, external location id
print '\ngetFulfillerStatusRequest:', req.get_element_fulfillerID()
res = port.getFulfillerStatus(req)
print 'getFulfillerStatusResponse:', res.get_element_getFulfillerStatusReturn()


# getBinTypes
# STATUS: Working with updated wsdl.
req = getBinTypesRequest()
res = port.getBinTypes(req)
print '\ngetBinTypesRequest'
getBinTypesReturn = res.get_element_getBinTypesReturn()
if not getBinTypesReturn:
    print 'No results'
else:
    for binType in getBinTypesReturn:
        print 'getBinTypes: BinType:', binType.get_element_BinType()


# getFulfillmentLocationsRequest
# STATUS: Working with updated wsdl, with workaround
req = getFulfillmentLocationsRequest()
request = req.new_request()
Catalog = request.new_Catalog()
Catalog.set_element_CatalogID(1) # Required, underflow error, cannot be 0 according to wsdl, positiveInteger, ignoring CatalogID as workaround
Catalog.set_element_ManufacturerID(10636) # Required
request.set_element_Catalog(Catalog) 
Location = request.new_Location()
Location.set_element_CountryCode('CountryCode')
Location.set_element_Latitude(1.0)
Location.set_element_Longitude(2.0)
Location.set_element_PostalCode('PostalCode')
Location.set_element_Radius(32)
Location.set_element_Unit('MILES')
request.set_element_Location(Location)
request.set_element_FulfillerID(48590) # Required
request.set_element_MaxLocations(30) # Required
req.set_element_request(request)
print '\ngetFulfillmentLocationsRequest'
res = port.getFulfillmentLocations(req)
fulfillmentLocationsReturn = res.get_element_getFulfillmentLocationsReturn()
if fulfillmentLocationsReturn:
    print 'getFulfillmentLocations: FulfillerID, ExternalLocationID'
    for fulfillmentLocation in fulfillmentLocationsReturn:
        FulfillerID = fulfillmentLocation.get_element_FulfillerID()
        ExternalLocationID = fulfillmentLocation.get_element_ExternalLocationID()
        print 'getFulfillmentLocations:', FulfillerID, ExternalLocationID
else:
    print 'getFulfillmentLocations: No results'


# getBinStatuses
# STATUS: Working with updated wsdl
req = getBinStatusesRequest()
print '\ngetBinStatusesRequest'
res = port.getBinStatuses(req)
getBinStatusesReturn = res.get_element_getBinStatusesReturn()
if getBinStatusesReturn:
    print 'getBinStatuses: BinStatus'
    for BinStatusObject in getBinStatusesReturn:
        BinStatus = BinStatusObject.get_element_BinStatus()
        print 'getBinStatuses:', BinStatus
else:
    print 'getBinStatuses: No results'


# getFulfillmentLocationTypes
# STATUS: Working with updated wsdl
req = getFulfillmentLocationTypesRequest()
print '\ngetFulfillmentLocationTypesRequest'
res = port.getFulfillmentLocationTypes(req)
getFulfillmentLocationTypesReturn = res.get_element_getFulfillmentLocationTypesReturn()
if getFulfillmentLocationTypesReturn:
    print 'getFulfillmentLocationTypes: LocationType'
    for getFulfillmentLocationType in getFulfillmentLocationTypesReturn:
        LocationType = getFulfillmentLocationType.get_element_LocationType()
        print 'getFulfillmentLocationTypes:', LocationType 
else:
    print 'getFulfillmentLocationTypes: No results'


# adjustInventory test #1
# STATUS: Working with updated wsdl
req = AdjustInventorySoapIn()
Items = req.new_Items()
items = Items.new_items()
items.set_element_BinID(3180102)
items.set_element_PartNumber('200216741')
items.set_element_Quantity(100)
items.set_element_UPC('') # Unused
Items.set_element_items([items]) # Add as many as you like
req.set_element_Items(Items)
req.set_element_FulfillerID(48590)
req.set_element_ExternalLocationID('600')
print '\nadjustInventoryRequest #1'
res = port.adjustInventory(req)
print 'adjustInventoryResponse #1:\n', res

# adjustInventory test #2
# STATUS: Working with updated wsdl
req = AdjustInventorySoapIn()
Items = req.new_Items()
items = Items.new_items()
items.set_element_BinID(-1) # Negative BinID is default bin
items.set_element_PartNumber('21-05324-002')
items.set_element_Quantity(100)
items.set_element_UPC('')
items2 = Items.new_items()
items2.set_element_BinID(-1) # Negative BinID is default bin
items2.set_element_PartNumber('21-05324-003')
items2.set_element_Quantity(50)
items2.set_element_UPC('')
Items.set_element_items([items, items2]) # Add as many as you like
req.set_element_Items(Items)
req.set_element_FulfillerID(76061)
req.set_element_ExternalLocationID('1')
print '\nadjustInventoryRequest #2'
res = port.adjustInventory(req)
print 'adjustInventoryResponse #2:\n', res


# getInventory test #1
# STATUS: Working with updated wsdl
# should return list of 2: both exist
# cat = {"CatalogID": 0, "ManufacturerID" : 10636}
# locs = [600, 103]
# order = [{"partnumber": "201052399", "UPC": "201052399", "Quantity": 1}, {"partnumber": "200230544", "UPC": "200230544", "Quantity": 1}]
# getInventory(cat, '48590', 0, 0, 10000, cat, locs, 0, order, "ALL", MySQLdb.connect("localhost", "root", "busmajorz", "inventory"))
req = getInventoryRequest()
request = req.new_request()
Catalog = request.new_Catalog()
Catalog.set_element_CatalogID(1) # Cannot be zero, wsdl underflow constraint
Catalog.set_element_ManufacturerID(10636)
request.set_element_Catalog(Catalog)
Location = request.new_Location()
Location.set_element_CountryCode('CountryCode')
Location.set_element_Latitude(1.0)
Location.set_element_Longitude(1.0)
Location.set_element_PostalCode('PostalCode')
Location.set_element_Radius(1.0)
Location.set_element_Unit('MILES')
request.set_element_Location(Location)
LocationIDs = request.new_LocationIDs()
LocationIDs.set_element_ExternalLocationID(['600', '103'])
request.set_element_LocationIDs(LocationIDs)
Quantities = request.new_Quantities()
items1 = Quantities.new_items()
items1.set_element_PartNumber('201052399')
items1.set_element_Quantity(1)
items1.set_element_UPC('201052399')
items2 = Quantities.new_items()
items2.set_element_PartNumber('200230544')
items2.set_element_Quantity(1)
items2.set_element_UPC('200230544')
Quantities.set_element_items([items1, items2])
request.set_element_Quantities(Quantities)
Type = request.new_Type('ALL')
request.set_element_Type(Type)
request.set_element_FulfillerID(48590)
request.set_element_IgnoreSafetyStock(0)
request.set_element_IncludeNegativeInventory(1)
request.set_element_Limit(10000)
request.set_element_OrderByLTD(1)
req.set_element_request(request)
print '\ngetInventoryRequest'
res = port.getInventory(req)
results = res.get_element_getInventoryReturn()
if results:
    print 'getInventoryReturn: LocationName, CatalogID, ManufacturerID, OnHand, Available, PartNumber, UPC, LTD, SafetyStock, CountryCode, Distance'
    for result in results:
        LocationName = result.get_element_LocationName()
        CatalogID = result.get_element_CatalogID()
        ManufacturerID = result.get_element_ManufacturerID()
        OnHand = result.get_element_OnHand()
        Available = result.get_element_Available()
        PartNumber = result.get_element_PartNumber()
        UPC = result.get_element_UPC()
        LTD = result.get_element_LTD()
        SafetyStock = result.get_element_SafetyStock()
        CountryCode = result.get_element_CountryCode()
        Distance = result.get_element_Distance()
        print 'getInventoryReturn:', (LocationName, CatalogID, ManufacturerID, OnHand, Available, PartNumber, UPC, LTD, SafetyStock, CountryCode, Distance)
else:
    print 'getInventoryReturn: No results'

# getInventory test #2
# STATUS: Working with updated wsdl
# should return 2: 2 diff FulfillerLocationIds can make up the order   
# cat = {"CatalogID": 0, "ManufacturerID" : 11416}
# locs = [44077, 440001, 440029]
# order = [{"partnumber": "8888076942", "UPC": "8888076942", "Quantity": 1}, {"partnumber": "8888076757", "UPC": "8888076757", "Quantity": 1}]
# getInventory(cat, '69170', 0, 0, 10000, cat, locs, 0, order, "PARTIAL", MySQLdb.connect("localhost", "root", "busmajorz", "inventory"))
req = getInventoryRequest()
request = req.new_request()
Catalog = request.new_Catalog()
Catalog.set_element_CatalogID(1) # Cannot be zero, wsdl underflow constraint
Catalog.set_element_ManufacturerID(11416)
request.set_element_Catalog(Catalog)
Location = request.new_Location()
Location.set_element_CountryCode('CountryCode')
Location.set_element_Latitude(1.0)
Location.set_element_Longitude(1.0)
Location.set_element_PostalCode('PostalCode')
Location.set_element_Radius(1.0)
Location.set_element_Unit('MILES')
request.set_element_Location(Location)
LocationIDs = request.new_LocationIDs()
LocationIDs.set_element_ExternalLocationID(['44077', '440001', '440029'])
request.set_element_LocationIDs(LocationIDs)
Quantities = request.new_Quantities()
items1 = Quantities.new_items()
items1.set_element_PartNumber('8888076942')
items1.set_element_Quantity(1)
items1.set_element_UPC('8888076942')
items2 = Quantities.new_items()
items2.set_element_PartNumber('8888076757')
items2.set_element_Quantity(1)
items2.set_element_UPC('8888076757')
Quantities.set_element_items([items1, items2])
request.set_element_Quantities(Quantities)
Type = request.new_Type('PARTIAL')
request.set_element_Type(Type)
request.set_element_FulfillerID(69170)
request.set_element_IgnoreSafetyStock(0)
request.set_element_IncludeNegativeInventory(1)
request.set_element_Limit(10000)
request.set_element_OrderByLTD(1)
req.set_element_request(request)
print '\ngetInventoryRequest'
res = port.getInventory(req)
results = res.get_element_getInventoryReturn()
if results:
    print 'getInventoryReturn: LocationName, CatalogID, ManufacturerID, OnHand, Available, PartNumber, UPC, LTD, SafetyStock, CountryCode, Distance'
    for result in results:
        LocationName = result.get_element_LocationName()
        CatalogID = result.get_element_CatalogID()
        ManufacturerID = result.get_element_ManufacturerID()
        OnHand = result.get_element_OnHand()
        Available = result.get_element_Available()
        PartNumber = result.get_element_PartNumber()
        UPC = result.get_element_UPC()
        LTD = result.get_element_LTD()
        SafetyStock = result.get_element_SafetyStock()
        CountryCode = result.get_element_CountryCode()
        Distance = result.get_element_Distance()
        print 'getInventoryReturn:', (LocationName, CatalogID, ManufacturerID, OnHand, Available, PartNumber, UPC, LTD, SafetyStock, CountryCode, Distance)
else:
    print 'getInventoryReturn: No results'

req = allocateInventoryRequest()
request = req.new_request()
FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
ManufacturerCatalog.set_element_CatalogID(1)
ManufacturerCatalog.set_element_ManufacturerID(10636)
FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
FulfillerLocationCatalog.set_element_ExternalLocationID(1)
request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
Items = request.new_Items()
item = Items.new_items()
item.set_element_PartNumber('22-14579-002')
item.set_element_Quantity(8)
item.set_element_UPC('22-14579-002')
item.set_element_OrderID(1)
item.set_element_OrderItemID(1)
item.set_element_ShipmentID(1)
Items.set_element_items([item])
request.set_element_Items(Items)
request.set_element_FulfillerID(76061)
req.set_element_request(request)
res = port.allocateInventory(req)
print '\nallocateInventory'

req = deallocateInventoryRequest()
request = req.new_request()
FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
ManufacturerCatalog.set_element_CatalogID(1)
ManufacturerCatalog.set_element_ManufacturerID(10636)
FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
FulfillerLocationCatalog.set_element_ExternalLocationID(1)
request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
Items = request.new_Items()
item = Items.new_items()
item.set_element_PartNumber('22-14579-002')
item.set_element_Quantity(7)
item.set_element_UPC('22-14579-002')
item.set_element_OrderID(1)
item.set_element_OrderItemID(1)
item.set_element_ShipmentID(1)
Items.set_element_items([item])
request.set_element_Items(Items)
request.set_element_FulfillerID(76061)
req.set_element_request(request)
res = port.deallocateInventory(req)
print '\ndeallocateInventory'

req = fulfillInventoryRequest()
request = req.new_request()
FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
ManufacturerCatalog.set_element_CatalogID(1)
ManufacturerCatalog.set_element_ManufacturerID(10636)
FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
FulfillerLocationCatalog.set_element_ExternalLocationID(1)
request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
Items = request.new_Items()
item = Items.new_items()
item.set_element_PartNumber('22-14579-002')
item.set_element_Quantity(1)
item.set_element_UPC('22-14579-002')
item.set_element_OrderID(1)
item.set_element_OrderItemID(1)
item.set_element_ShipmentID(1)
Items.set_element_items([item])
request.set_element_Items(Items)
request.set_element_FulfillerID(76061)
req.set_element_request(request)
res = port.fulfillInventory(req)
print '\nfulfillInventory'



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
