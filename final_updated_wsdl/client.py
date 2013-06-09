#!/usr/local/bin/python2.7
# db2.thepicard.org

from CoreServiceService_services import *
import re

#print dir()

loc = CoreServiceServiceLocator()
#print dir(loc)

port = loc.getCoreService()
#print dir(port)

#req = getFulfillerStatusRequest()
#req.set_element_fulfillerID(1)
#res = port.getFulfillerStatus(req)
#print 'getFulfillerStatus', res.get_element_getFulfillerStatusReturn()
#
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
#
#req = getBinTypesRequest()
#res = port.getBinTypes(req)
#print 'getBinTypes'
#
#req = getFulfillmentLocationsRequest()
#request = req.new_request()
#Catalog = request.new_Catalog()
#Catalog.set_element_CatalogID(1)
#Catalog.set_element_ManufacturerID(2)
#request.set_element_Catalog(Catalog)
#Location = request.new_Location()
#Location.set_element_CountryCode('CountryCode')
#Location.set_element_Latitude(1.0)
#Location.set_element_Longitude(2.0)
#Location.set_element_PostalCode('PostalCode')
#Location.set_element_Radius(32)
#Location.set_element_Unit('MILES')
#request.set_element_Location(Location)
#request.set_element_FulfillerID(3)
#request.set_element_MaxLocations(4)
#req.set_element_request(request)
#res = port.getFulfillmentLocations(req)
#print 'getFulfillmentLocations'
#
#req = createFulfillmentLocationRequest() 
#request = req.new_request()
#request.set_element_CountryCode('CC')
#request.set_element_ExternalLocationID('ELID')
#request.set_element_FulfillerID(1)
#request.set_element_Latitude(1.0)
#request.set_element_LocationName('LOCNAME')
#request.set_element_LocationType('LOCTYPE')
#request.set_element_Longitude(1.0)
#request.set_element_RetailerLocationID(2)
#request.set_element_Status(1)
#req.set_element_request(request)
#res = port.createFulfillmentLocation(req)
#print 'createFulfillmentLocation', res.get_element_createFulfillmentLocationReturn()
#
#req = createFulfillerRequest()
#request = req.new_request()
#request.set_element_FulfillerID(15)
#request.set_element_Name('Name')
#req.set_element_request(request)
#res = port.createFulfiller(req)
#print 'createFulfiller', res.get_element_createFulfillerReturn()
#
#req = fulfillInventoryRequest()
#request = req.new_request()
#FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
#ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
#ManufacturerCatalog.set_element_CatalogID(1)
#ManufacturerCatalog.set_element_ManufacturerID(2)
#FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
#FulfillerLocationCatalog.set_element_FulfillerLocationID(3)
#request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
#Items = request.new_Items()
#items = Items.new_items()
#items.set_element_FulfillerLocationID(2)
#items.set_element_OrderID(1)
#items.set_element_OrderItemID(1)
#items.set_element_PartNumber('PartNumber')
#items.set_element_Quantity(10)
#items.set_element_ShipmentID(1)
#items.set_element_UPC('UPC')
#Items.set_element_items([items])
#request.set_element_Items(Items)
#request.set_element_FulfillerID(1)
#req.set_element_request(request)
#res = port.fulfillInventory(req)
#print 'fulfillInventory'
#
#req = getBinStatusesRequest()
#res = port.getBinStatuses(req)
#print 'getBinStatuses'
#
#req = createBinRequest()
#request = req.new_request()
#request.set_element_BinID(2)
#request.set_element_BinStatus('BinStatus')
#request.set_element_BinType('BinType')
#request.set_element_FulfillerID(2)
#request.set_element_FulfillerLocationID(2)
#request.set_element_Name('Name')
#req.set_element_request(request)
#res = port.createBin(req)
#print 'createBin'
#
#req = RefreshInventorySoapIn()
#Items = req.new_Items()
#items1 = Items.new_items()
#items1.set_element_BinID(123)
#items1.set_element_LTD(1.0)
#items1.set_element_PartNumber('PartNumber2')
#items1.set_element_Quantity(12)
#items1.set_element_SafetyStock(10)
#items1.set_element_UPC('UPC12')
#items2 = Items.new_items()
#items2.set_element_BinID(123)
#items2.set_element_LTD(1.0)
#items2.set_element_PartNumber('PartNumber12')
#items2.set_element_Quantity(12)
#items2.set_element_SafetyStock(10)
#items2.set_element_UPC('UPC2')
#Items.set_element_items([items1, items2])
#req.set_element_Items(Items)
#req.set_element_FulfillerID(123)
#req.set_element_LocationName('LocationName')
#res = port.refreshInventory(req)
#print 'refreshInventory', res # res is a string

req = getBinsRequest()
request = req.new_request()
request.set_element_FulfillerID(48590)
request.set_element_ExternalLocationID(600)
request.set_element_NumResults(30)
request.set_element_ResultsStart(1)
request.set_element_SearchTerm('5305')
req.set_element_request(request)
res = port.getBins(req)
getBinsReturn = res.get_element_getBinsReturn()
ResultCount = getBinsReturn.get_element_ResultCount()
Bins = getBinsReturn.get_element_Bins()
items = Bins.get_element_items()
for item in items:
    FulfillerID = item.get_element_FulfillerID()
    ExternalLocationID = item.get_element_ExternalLocationID()
    BinStatus = item.get_element_BinStatus()
    BinType = item.get_element_BinType()
    Name = item.get_element_Name()
    print 'getBins: Bin:', FulfillerID, ExternalLocationID, BinStatus, BinType, Name
print 'getBins: ResultCount:', ResultCount

#req = getFulfillmentLocationTypesRequest()
#res = port.getFulfillmentLocationTypes(req)
#print 'getFulfillmentLocationTypes'

#req = allocateInventoryRequest()
#request = req.new_request()
#FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
#ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
#ManufacturerCatalog.set_element_CatalogID()
#ManufacturerCatalog.set_element_ManufacturerID()
#FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
#FulfillerLocationCatalog.set_element_FulfillerLocationID()
#request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
#Items = request.new_Items()
#items = Items.new_items()
#items.set_element_FulfillerLocationID()
#items.set_element_OrderID()
#items.set_element_OrderItemID()
#items.set_element_PartNumber()
#items.set_element_Quantity()
#items.set_element_ShipmentID()
#items.set_element_UPC()
#Items.set_element_items(items)
#request.set_element_Items(Items)
#request.set_element_FulfillerID()
#req.set_element_request(request)
#res = port.allocate(req)
#print 'allocateInventory'

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
#
#req = deallocateInventoryRequest()
#request = req.new_request()
#FulfillerLocationCatalog = request.new_FulfillerLocationCatalog()
#ManufacturerCatalog = FulfillerLocationCatalog.new_ManufacturerCatalog()
#ManufacturerCatalog.set_element_CatalogID()
#ManufacturerCatalog.set_element_ManufacturerID()
#FulfillerLocationCatalog.set_element_ManufacturerCatalog(ManufacturerCatalog)
#FulfillerLocationCatalog.set_element_FulfillerLocationID()
#request.set_element_FulfillerLocationCatalog(FulfillerLocationCatalog)
#Items = request.new_Items()
#items = Items.new_items()
#items.set_element_FulfillerLocationID()
#items.set_element_OrderID()
#items.set_element_OrderItemID()
#items.set_element_PartNumber()
#items.set_element_Quantity()
#items.set_element_ShipmentID()
#items.set_element_UPC()
#Items.set_element_items(items)
#request.set_element_Items(Items)
#request.set_element_FulfillerID()
#req.set_element_request(request)
#res = port.deallocate(req)
#print 'deallocateInventory'




