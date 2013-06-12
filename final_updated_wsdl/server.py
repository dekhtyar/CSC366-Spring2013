#!/usr/local/bin/python2.7

# Kevin Stein  kestein@calpoly.edu
# Alex Spotnitz  aspotnitz@gmail.com
# Stephen Calabrese  sccalabr@calpoly.edu
# Alex Boltunov  aboltunov@gmail.com
# Luke Larson  lplarson@calpoly.edu

import sys
import api
from allocateInventory import allocateInventory
from newGetInventory import getInventory
from deallocateInventory import deallocateInventory
from fulfillInventory import fulfillInventory
from adjustInventory import adjustInventory
import MySQLdb
from ZSI.ServiceContainer import AsServer
from CoreServiceService_services_server import *

class Service(CoreServiceService):

    # STATUS: Working with latest wsdl. 
    def soap_createFulfiller(self, ps):
        res = CoreServiceService.soap_createFulfiller(self, ps)
        req = self.request

        r = req.get_element_request()

        FulfillerID = r.get_element_FulfillerID()
        Name = r.get_element_Name()
        print "soap_createFulfiller:", FulfillerID, Name

        response = api.createFulfiller({'fulfiller_id': FulfillerID,
                                        'name': Name}, db)

        createFulfillerReturn = response

        res.set_element_createFulfillerReturn(createFulfillerReturn)
        
        return res

    # STATUS: Working with latest wsdl. 
    def soap_getFulfillerStatus(self, ps):
        res = CoreServiceService.soap_getFulfillerStatus(self, ps)
        req = self.request

        fulfillerID = req.get_element_fulfillerID()
        print "soap_getFulfillerStatus:", fulfillerID

        getFulfillerStatusReturn = api.getFulfillerStatus(fulfillerID, db)

        res.set_element_getFulfillerStatusReturn(getFulfillerStatusReturn)
        return res

    # STATUS: Working with latest wsdl. 
    def soap_createFulfillmentLocation(self, ps):
        res = CoreServiceService.soap_createFulfillmentLocation(self, ps)
        req = self.request

        r = req.get_element_request()

        CountryCode = r.get_element_CountryCode()
        ExternalLocationID = r.get_element_ExternalLocationID()
        FulfillerID = r.get_element_FulfillerID()
        Latitude = r.get_element_Latitude()
        LocationName = r.get_element_LocationName()
        LocationType = r.get_element_LocationType()
        Longitude = r.get_element_Longitude()
        RetailerLocationID = r.get_element_RetailerLocationID()
        Status = r.get_element_Status()
        print "soap_createFulfillmentLocation:", CountryCode, ExternalLocationID, FulfillerID, Latitude, LocationName, LocationType, Longitude, RetailerLocationID, Status

        createFulfillmentLocationReturn = api.createFulfillmentLocation({'fulfiller_id': FulfillerID,
                                                                         'external_fulfiller_location_id': ExternalLocationID,
                                                                         'name': LocationName, 'description': LocationType,
                                                                         'latitude': Latitude, 'longitude': Longitude,
                                                                         'status': Status,
                                                                         'safety_stock': 0}, db)

        res.set_element_createFulfillmentLocationReturn(createFulfillmentLocationReturn)

        return res

    # STATUS: Working with updated wsdl
    def soap_getFulfillmentLocations(self, ps):
        res = CoreServiceService.soap_getFulfillmentLocations(self, ps)
        req = self.request

        request = req.get_element_request()
        Catalog = request.get_element_Catalog()
        Location = request.get_element_Location()

        CatalogID = Catalog.get_element_CatalogID()
        ManufacturerID = Catalog.get_element_ManufacturerID()
        CountryCode = Location.get_element_CountryCode()
        Latitude = Location.get_element_Latitude()
        Longitude = Location.get_element_Longitude()
        PostalCode = Location.get_element_PostalCode()
        Radius = Location.get_element_Radius()
        Unit = Location.get_element_Unit()
        FulfillerID = request.get_element_FulfillerID()
        MaxLocations = request.get_element_MaxLocations()

        print "soap_getFulfillmentLocations:", CatalogID, ManufacturerID, CountryCode, Latitude, Longitude, PostalCode, Radius, Unit, FulfillerID, MaxLocations

        fulfillmentLocations = api.getFulfillmentLocations(FulfillerID, CatalogID, ManufacturerID, MaxLocations, db)

        fulfillmentLocationsReturn = []
        for fulfillmentLocation in fulfillmentLocations:
            getFulfillmentLocationsReturn = res.new_getFulfillmentLocationsReturn()
            getFulfillmentLocationsReturn.set_element_FulfillerID(int(fulfillmentLocation[0]))
            getFulfillmentLocationsReturn.set_element_ExternalLocationID(fulfillmentLocation[1])
            fulfillmentLocationsReturn.append(getFulfillmentLocationsReturn)

        res.set_element_getFulfillmentLocationsReturn(fulfillmentLocationsReturn)

        return res

    # STATUS: Working with updated wsdl
    def soap_getFulfillmentLocationTypes(self, ps):
        res = CoreServiceService.soap_getFulfillmentLocationTypes(self, ps)
        req = self.request

        FulfillmentLocationTypes = api.getFulfillmentLocationTypes(db)

        FulfillmentLocationTypesResponse= []
        for FulfillmentLocationType in FulfillmentLocationTypes:
            getFulfillmentLocationTypesReturn = res.new_getFulfillmentLocationTypesReturn()
            getFulfillmentLocationTypesReturn.set_element_LocationType(FulfillmentLocationType[0])
            FulfillmentLocationTypesResponse.append(getFulfillmentLocationTypesReturn)
        
        res.set_element_getFulfillmentLocationTypesReturn(FulfillmentLocationTypesResponse)
        print "soap_getFulfillmentLocationTypes" 

        return res

    # STATUS: Needs more testing, but seems to be working
    def soap_allocateInventory(self, ps):
        res = CoreServiceService.soap_allocateInventory(self, ps)
        req = self.request
        request = req.get_element_request()

        FulfillerID = request.get_element_FulfillerID()
        FulfillerLocationCatalog = request.get_element_FulfillerLocationCatalog()
        ExternalLocationID = FulfillerLocationCatalog.get_element_ExternalLocationID()
        ManufacturerCatalog = FulfillerLocationCatalog.get_element_ManufacturerCatalog()
        ManufacturerID = ManufacturerCatalog.get_element_ManufacturerID()
        CatalogID = ManufacturerCatalog.get_element_CatalogID()
        items = request.get_element_Items().get_element_items()
        Items = []

        for item in items:
            Items.append({
                'SKU': item.get_element_PartNumber(),
                'UPC': item.get_element_UPC(),
                'Quantity': item.get_element_Quantity()
            })

        print "soap_allocateInventory:", FulfillerID, ExternalLocationID, ManufacturerID, CatalogID, Items
        allocateInventory(FulfillerID, ExternalLocationID, ManufacturerID, CatalogID, Items, db)

        return res

    # STATUS: Needs more testing, but seems to be working
    def soap_deallocateInventory(self, ps):
        res = CoreServiceService.soap_deallocateInventory(self, ps)
        req = self.request
        request = req.get_element_request()

        FulfillerID = request.get_element_FulfillerID()
        FulfillerLocationCatalog = request.get_element_FulfillerLocationCatalog()
        ExternalLocationID = FulfillerLocationCatalog.get_element_ExternalLocationID()
        ManufacturerCatalog = FulfillerLocationCatalog.get_element_ManufacturerCatalog()
        ManufacturerID = ManufacturerCatalog.get_element_ManufacturerID()
        CatalogID = ManufacturerCatalog.get_element_CatalogID()
        items = request.get_element_Items().get_element_items()
        Items = []

        for item in items:
            Items.append({
                'SKU': item.get_element_PartNumber(),
                'UPC': item.get_element_UPC(),
                'Quantity': item.get_element_Quantity()
            })

        print "soap_deallocateInventory:", FulfillerID, ExternalLocationID, ManufacturerID, CatalogID, Items
        deallocateInventory(FulfillerID, ExternalLocationID, ManufacturerID, CatalogID, Items, db)

        return res

    # STATUS: Needs more testing, but seems to be working
    def soap_fulfillInventory(self, ps):
        res = CoreServiceService.soap_fulfillInventory(self, ps)
        req = self.request
        request = req.get_element_request()

        FulfillerID = request.get_element_FulfillerID()
        FulfillerLocationCatalog = request.get_element_FulfillerLocationCatalog()
        ExternalLocationID = FulfillerLocationCatalog.get_element_ExternalLocationID()
        ManufacturerCatalog = FulfillerLocationCatalog.get_element_ManufacturerCatalog()
        ManufacturerID = ManufacturerCatalog.get_element_ManufacturerID()
        CatalogID = ManufacturerCatalog.get_element_CatalogID()
        items = request.get_element_Items().get_element_items()
        Items = []

        for item in items:
            Items.append({
                'SKU': item.get_element_PartNumber(),
                'UPC': item.get_element_UPC(),
                'Quantity': item.get_element_Quantity()
            })

        print "soap_fulfillInventory:", FulfillerID, ExternalLocationID, ManufacturerID, CatalogID, Items
        fulfillInventory(FulfillerID, ExternalLocationID, ManufacturerID, CatalogID, Items, db)

        return res

    # STATUS: This function has been removed
    def soap_getItemLocationsByFulfiller(self, ps):
        res = CoreServiceService.soap_getItemLocationsByFulfiller(self, ps)
        req = self.request

        request = req.get_element_request()
        FulfillerIDs = request.get_element_FulfillerIDs()
        items = FulfillerIDs.get_element_items()
        LocationID = request.get_element_LocationID()
        PartNumber = request.get_element_PartNumber()
        PostalCode = request.get_element_PostalCode()
        UPC = request.get_element_UPC()
        print "soap_getItemLocationsByFulfiller:", items, LocationID, PartNumber, PostalCode, UPC

        return res
 
    # STATUS: Working with latest wsdl. 
    def soap_createBin(self, ps):
        res = CoreServiceService.soap_createBin(self, ps)
        req = self.request

        r = req.get_element_request()

        BinID = r.get_element_BinID()
        BinStatus = r.get_element_BinStatus()
        BinType = r.get_element_BinType()
        FulfillerID = r.get_element_FulfillerID()
        ExternalLocationID = r.get_element_ExternalLocationID()
        Name = r.get_element_Name()
        print "soap_createBin:", BinID, BinStatus, BinType, FulfillerID, ExternalLocationID, Name

        createBinReturn = api.createBin({'fulfiller_id': FulfillerID,
                                         'external_fulfiller_location_id': ExternalLocationID,
                                         'bin_name': Name, 'bin_type': BinType, 'bin_status': BinStatus}, db)

        res.set_element_createBinReturn(createBinReturn)

        return res

    # STATUS: Working with latest wsdl. 
    def soap_getBins(self, ps):
        res = CoreServiceService.soap_getBins(self, ps)
        req = self.request

        r = req.get_element_request()

        FulfillerID = r.get_element_FulfillerID()
        ExternalLocationID = r.get_element_ExternalLocationID()
        NumResults = r.get_element_NumResults()
        ResultsStart = r.get_element_ResultsStart()
        SearchTerm = r.get_element_SearchTerm()
        print "soap_getBins:", FulfillerID, ExternalLocationID, NumResults, ResultsStart, SearchTerm

        results, resultsCount = api.getBins(FulfillerID, ExternalLocationID,
                                            SearchTerm, NumResults, ResultsStart, db)

        getBinsReturn = res.new_getBinsReturn()
        Bins = getBinsReturn.new_Bins()
        
        items = []
        for result in results:
            item = Bins.new_items()
            item.set_element_BinStatus(result[4])
            item.set_element_BinType(result[3])
            item.set_element_FulfillerID(int(result[0]))
            item.set_element_ExternalLocationID(int(result[1])) # num required, not a string
            item.set_element_Name(result[2])
            items.append(item)

        Bins.set_element_items(items)

        getBinsReturn.set_element_ResultCount(resultsCount)
        getBinsReturn.set_element_Bins(Bins)

        res.set_element_getBinsReturn(getBinsReturn)

        return res

    # STATUS: Working with latest wsdl. 
    def soap_getBinTypes(self, ps):
        res = CoreServiceService.soap_getBinTypes(self, ps)
        req = self.request

        binTypes = api.getBinTypes(db)

        binTypesReturn = []
        for binType in binTypes:
            getBinTypesReturn = res.new_getBinTypesReturn()
            getBinTypesReturn.set_element_BinType(binType[0])
            binTypesReturn.append(getBinTypesReturn)

        res.set_element_getBinTypesReturn(binTypesReturn)
        print "soap_getBinTypes" 

        return res

    # STATUS: Working with latest wsdl. 
    def soap_getBinStatuses(self, ps):
        res = CoreServiceService.soap_getBinStatuses(self, ps)
        req = self.request

        BinStatuses = api.getBinStatuses(db)

        BinStatusesResponse = []
        for BinStatus in BinStatuses:
            getBinStatusesReturn = res.new_getBinStatusesReturn()
            getBinStatusesReturn.set_element_BinStatus(BinStatus[0])
            BinStatusesResponse.append(getBinStatusesReturn)
        
        res.set_element_getBinStatusesReturn(BinStatusesResponse)
        print "soap_getBinStatuses" 

        return res

    # STATUS: Working with latest wsdl. 
    def soap_getInventory(self, ps):
        res = CoreServiceService.soap_getInventory(self, ps)
        req = self.request

        request = req.get_element_request()
        Catalog = request.get_element_Catalog()
        CatalogID = Catalog.get_element_CatalogID() 
        ManufacturerID = Catalog.get_element_ManufacturerID()
        Location = request.get_element_Location()
        CountryCode = Location.get_element_CountryCode()
        Latitude = Location.get_element_Latitude()
        Longitude = Location.get_element_Longitude()
        PostalCode = Location.get_element_PostalCode()
        Radius = Location.get_element_Radius()
        Unit = Location.get_element_Unit()
        LocationIDs = request.get_element_LocationIDs().get_element_ExternalLocationID() # array of loc ids
        Quantities = request.get_element_Quantities()
        items = Quantities.get_element_items()
        itemList = []
        for item in items:
            PartNumber = item.get_element_PartNumber()
            Quantity = item.get_element_Quantity()
            UPC = item.get_element_UPC()
            itemList.append({
                'partnumber': PartNumber,
                'UPC': UPC,
                'Quantity': Quantity
            })
        Type = request.get_element_Type()
        FulfillerID = request.get_element_FulfillerID()
        IgnoreSafetyStock = request.get_element_IgnoreSafetyStock()
        IncludeNegativeInventory = request.get_element_IncludeNegativeInventory()
        Limit = request.get_element_Limit()
        OrderByLTD = request.get_element_OrderByLTD()

        print 'soap_getInventory:', CatalogID, FulfillerID, IgnoreSafetyStock, IncludeNegativeInventory, Limit, 0, LocationIDs, OrderByLTD, itemList, Type

        results = getInventory({'CatalogID': CatalogID,
                                'ManufacturerID': ManufacturerID},
                                FulfillerID, IgnoreSafetyStock,
                                IncludeNegativeInventory, Limit, 0,
                                LocationIDs, OrderByLTD, itemList, Type, db)

        InventoryReturnList = []
        for result in results:
            getInventoryReturn = res.new_getInventoryReturn()
            getInventoryReturn.set_element_LocationName(result[0])
            getInventoryReturn.set_element_CatalogID(int(result[1]))
            getInventoryReturn.set_element_ManufacturerID(int(result[2]))
            getInventoryReturn.set_element_OnHand(int(result[3]))
            getInventoryReturn.set_element_Available(int(result[4]))
            getInventoryReturn.set_element_PartNumber(result[5])
            getInventoryReturn.set_element_UPC(result[6])
            getInventoryReturn.set_element_LTD(float(result[7]))
            getInventoryReturn.set_element_SafetyStock(int(result[8]))
            getInventoryReturn.set_element_CountryCode('USA')
            getInventoryReturn.set_element_Distance(0)
            InventoryReturnList.append(getInventoryReturn)

        res.set_element_getInventoryReturn(InventoryReturnList)

        return res

    # STATUS: Working with latest wsdl. 
    def soap_adjustInventory(self, ps):
        res = CoreServiceService.soap_adjustInventory(self, ps)
        req = self.request

        itemList = []
        items = req.get_element_Items().get_element_items()
        for item in items:
            BinID = item.get_element_BinID()
            PartNumber = item.get_element_PartNumber()
            Quantity = item.get_element_Quantity()
            UPC = item.get_element_UPC()
            FulfillerID = req.get_element_FulfillerID()
            ExternalLocationID = req.get_element_ExternalLocationID()
            itemList.append({
                'BinId': BinID if BinID >= 0 else 'Default', # Negative BinID will select default
                'Quantity': Quantity,
                'partnumber': PartNumber,
                'UPC': UPC
            })

        print 'adjustInventory:', FulfillerID, ExternalLocationID, itemList
        response = adjustInventory(FulfillerID, ExternalLocationID, itemList, db)

        return AdjustInventorySoapOut(response)

    # STATUS: Working with latest wsdl. 
    def soap_refreshInventory(self, ps):
        res = CoreServiceService.soap_refreshInventory(self, ps)
        req = self.request

        # We unpack these simply to print the first item for debugging purposes
        # the request is then passed to api.refreshInventory unchanged
        Items = req.get_element_Items()
        items = Items.get_element_items()
        BinID = items[0].get_element_BinID()
        LTD = items[0].get_element_LTD()
        PartNumber = items[0].get_element_PartNumber()
        Quantity = items[0].get_element_Quantity()
        SafetyStock = items[0].get_element_SafetyStock()
        UPC = items[0].get_element_UPC()
        FulfillerID = req.get_element_FulfillerID()
        ExternalLocationID = req.get_element_ExternalLocationID()
        print "soap_refreshInventory:", items, BinID, LTD, PartNumber, Quantity, SafetyStock, UPC, FulfillerID, ExternalLocationID 

        api.refreshInventory(req, db)
        return res


if __name__ == "__main__" :
    db = MySQLdb.connect("localhost", "root", "busmajorz", "inventory")
    port = 444
    AsServer(port, (Service(),))
    db.close()
