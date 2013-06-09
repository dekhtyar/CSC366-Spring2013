#!/usr/local/bin/python2.7

import sys
import api
import MySQLdb
from ZSI.ServiceContainer import AsServer
from CoreServiceService_services_server import *

class Service(CoreServiceService):

    def soap_createFulfiller(self, ps):
        res = CoreServiceService.soap_createFulfiller(self, ps)
        req = self.request

        r = req.get_element_request()

        FulfillerID = r.get_element_FulfillerID()
        Name = r.get_element_Name()
        print "soap_createFulfiller:", FulfillerID, Name

        # Call API function with arguments above
        response = api.createFulfiller({'fulfiller_id': FulfillerID,
                                        'name': Name}, db)

        # Set these values with results from calliing API function
        createFulfillerReturn = response

        res.set_element_createFulfillerReturn(createFulfillerReturn)
        
        return res

    def soap_getFulfillerStatus(self, ps):
        res = CoreServiceService.soap_getFulfillerStatus(self, ps)
        req = self.request

        fulfillerID = req.get_element_fulfillerID()
        print "soap_getFulfillerStatus:", fulfillerID

        # Call API function with arguments above

        # Set these values with results from calliing API function
        getFulfillerStatusReturn = 0

        res.set_element_getFulfillerStatusReturn(getFulfillerStatusReturn)
        return res

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

        api.createFulfillmentLocation({'fulfiller_id': FulfillerID,
                                       'external_fulfiller_location_id': ExternalLocationID,
                                       'name': LocationName, 'description': LocationType,
                                       'latitude': Latitude, 'longitude': Longitude,
                                       'status': Status,
                                       'safety_stock': 0}, db)

        # Set these values with results from calliing API function
        createFulfillmentLocationReturn = 1 

        res.set_element_createFulfillmentLocationReturn(createFulfillmentLocationReturn)

        return res

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

        # Call API function with arguments above

        # Set these values with results from calliing API function
        getFulfillmentLocationsReturn = res.new_getFulfillmentLocationsReturn()
        getFulfillmentLocationsReturn.set_element_FulfillerID(1)
        getFulfillmentLocationsReturn.set_element_FulfillerLocationID(2)

        res.set_element_getFulfillmentLocationsReturn([getFulfillmentLocationsReturn])

        return res

    def soap_getFulfillmentLocationTypes(self, ps):
        res = CoreServiceService.soap_getFulfillmentLocationTypes(self, ps)
        req = self.request

        # Call API function with arguments above

        # Set these values with results from calliing API function

        return res

    def soap_allocateInventory(self, ps):
        res = CoreServiceService.soap_allocateInventory(self, ps)
        req = self.request

        r = req.new_request()

        FulfillerID = r.get_element_FulfillerID()
        FulfillerLocationCatalog = r.get_element_FulfillerLocationCatalog()
        Items = r.get_element_Items()
        print "soap_allocateInventory:", FulfillerID, FulfillerLocationCatalog, Items

        # Call API function with arguments above

        # Set these values with results from calliing API function

        return res

    def soap_deallocateInventory(self, ps):
        res = CoreServiceService.soap_deallocateInventory(self, ps)
        req = self.request

        r = req.new_request()

        FulfillerID = r.get_element_FulfillerID()
        FulfillerLocationCatalog = r.get_element_FulfillerLocationCatalog()
        Items = r.get_element_Items()
        print "soap_deallocateInventory:", FulfillerID, FulfillerLocationCatalog, Items

        # Call API function with arguments above

        # Set these values with results from calliing API function

        return res

    def soap_fulfillInventory(self, ps):
        res = CoreServiceService.soap_fulfillInventory(self, ps)
        req = self.request

        request = req.get_element_request()
        FulfillerLocationCatalog = request.get_element_FulfillerLocationCatalog()
        ManufacturerCatalog = FulfillerLocationCatalog.get_element_ManufacturerCatalog()

        CatalogID = ManufacturerCatalog.get_element_CatalogID()
        ManufacturerID = ManufacturerCatalog.get_element_ManufacturerID()

        FulfillerLocationID = FulfillerLocationCatalog.get_element_FulfillerLocationID()
        Items = request.get_element_Items()
        items = Items.get_element_items()
        ItemFulfillerLocationID = items[0].get_element_FulfillerLocationID()
        OrderID = items[0].get_element_OrderID()
        OrderItemID = items[0].get_element_OrderItemID()
        PartNumber = items[0].get_element_PartNumber()
        Quantity = items[0].get_element_Quantity()
        ShipmentID = items[0].get_element_ShipmentID()
        UPC = items[0].get_element_UPC()
        FulfillerID = request.get_element_FulfillerID()

        print "soap_fulfillInventory:", CatalogID, ManufacturerID, FulfillerLocationID, ItemFulfillerLocationID, OrderID, OrderItemID, PartNumber, Quantity, ShipmentID, UPC, FulfillerID 

        # Call API function with arguments above

        # Set these values with results from calliing API function

        return res

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

        # Call API function with arguments above

        # Set these values with results from calliing API function

        return res

    def soap_createBin(self, ps):
        res = CoreServiceService.soap_createBin(self, ps)
        req = self.request

        r = req.get_element_request()

        BinID = r.get_element_BinID()
        BinStatus = r.get_element_BinStatus()
        BinType = r.get_element_BinType()
        FulfillerID = r.get_element_FulfillerID()
        FulfillerLocationID = r.get_element_FulfillerLocationID()
        Name = r.get_element_Name()
        print "soap_createBin:", BinID, BinStatus, BinType, FulfillerID, FulfillerLocationID, Name

        # Call API function with arguments above

        print FulfillerLocationID
        api.createBin({'fulfiller_id': FulfillerID,
                       'external_fulfiller_location_id': FulfillerLocationID,
                       'bin_name': BinID, 'bin_type': BinType, 'bin_status': BinStatus}, db)

        # Set these values with results from calliing API function
        createBinReturn = 1

        res.set_element_createBinReturn(createBinReturn)

        return res

    #def getBins(FulfillerID, FulfillerLocationID, searchTerm, NumResults, ResultsStart, db):
    #   cursor = db.cursor()
    #
    #   try:
    #         sqlCommand = """SELECT *
    #                         FROM Bins b
    #                         WHERE b.FulfillerID = %s AND b.FulfillerLocationID = %s
    #                               AND b.Name LIKE '%s%%'
    #                         LIMIT %d, %d""" % (FulfillerID, FulfillerLocationID, searchTerm, ResultsStart, NumResults)
    #         cursor.execute(sqlCommand)
    #         results = cursor.fetchall()
    #         resultsCount = cursor.rowcount
    #         return results,resultsCount
    #
    #   except Exception, e:
    #      print e
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

        # Call API function with arguments above
        results, resultsCount = api.getBins(FulfillerID, ExternalLocationID,
                                            SearchTerm, NumResults, ResultsStart, db)
        print results

        getBinsReturn = res.new_getBinsReturn()
        Bins = getBinsReturn.new_Bins()
        
        #FulfillerId              VARCHAR(50),
        #FulfillerLocationId      VARCHAR(50),
        #Name                     VARCHAR(20),
        #BinType                  VARCHAR(20),
        #BinStatus                VARCHAR(20),
        #'set_element_BinID', 'set_element_BinStatus', 'set_element_BinType', 'set_element_FulfillerID', 'set_element_FulfillerLocationID', 'set_element_Name'
        items = []
        for result in results:
            item = Bins.new_items()
            print dir(item)
            #item.set_element_BinID()
            item.set_element_BinStatus(result[4])
            item.set_element_BinType(result[3])
            item.set_element_FulfillerID(int(result[0]))
            item.set_element_ExternalLocationID(int(result[1])) # num required, not a string
            item.set_element_Name(result[2])
            items.append(item)

        Bins.set_element_items(items)
        #print 'Bins', dir(Bins)
        #print 'Bins.new_items()', dir(Bins.new_items())

        getBinsReturn.set_element_ResultCount(resultsCount)
        getBinsReturn.set_element_Bins(Bins)

        res.set_element_getBinsReturn(getBinsReturn)
        # Set these values with results from calliing API function
        #print dir(getBinsReturn)

        return res

    def soap_getBinTypes(self, ps):
        res = CoreServiceService.soap_getBinTypes(self, ps)
        req = self.request

        getBinTypesReturn = res.new_getBinTypesReturn()
        getBinTypesReturn.set_element_BinType('BinType')
        res.set_element_getBinTypesReturn([getBinTypesReturn])
        print "soap_getBinTypes:"

        return res

    def soap_getBinStatuses(self, ps):
        res = CoreServiceService.soap_getBinStatuses(self, ps)
        req = self.request

        getBinStatusesReturn = res.new_getBinStatusesReturn()
        getBinStatusesReturn.set_element_BinStatus('BinStatus')
        res.set_element_getBinStatusesReturn([getBinStatusesReturn])

        return res

    def soap_getInventory(self, ps):
        res = CoreServiceService.soap_getInventory(self, ps)
        req = self.request

        r = req.new_request()
        
        Catalog = r.get_element_Catalog()
        FulfillerID = r.get_element_FulfillerID()
        IgnoreSafetyStock = r.get_element_IgnoreSafetyStock()
        IncludeNegativeInventory = r.get_element_IncludeNegativeInventory()
        Limit = r.get_element_Limit()
        Location = r.get_element_Location()
        LocationNames = r.get_element_LocationNames()
        OrderByLTD = r.get_element_OrderByLTD()
        Quantities = r.get_element_Quantities()
        Type = r.get_element_Type()
        print "soap_getInventory:", Catalog, FulfillerID, IgnoreSafetyStock, IncludeNegativeInventory, Limit, Location, LocationNames, OrderByLTD, Quantities, Type

        # Call API function with arguments above

        # Set these values with results from calliing API function

        return res

    def soap_adjustInventory(self, ps):
        res = CoreServiceService.soap_adjustInventory(self, ps)
        req = self.request
        return res

    def soap_refreshInventory(self, ps):
        res = CoreServiceService.soap_refreshInventory(self, ps)
        req = self.request

        Items = req.get_element_Items()
        items = Items.get_element_items()
        BinID = items[0].get_element_BinID()
        LTD = items[0].get_element_LTD()
        PartNumber = items[0].get_element_PartNumber()
        Quantity = items[0].get_element_Quantity()
        SafetyStock = items[0].get_element_SafetyStock()
        UPC = items[0].get_element_UPC()
        FulfillerID = req.get_element_FulfillerID()
        LocationName = req.get_element_LocationName()

        print "soap_refreshInventory:", items, BinID, LTD, PartNumber, Quantity, SafetyStock, UPC, FulfillerID, LocationName

        api.refreshInventory(req, db)
        return res



if __name__ == "__main__" :
    db = MySQLdb.connect("localhost", "root", "busmajorz", "inventory")
    port = 444 
    AsServer(port, (Service(),))
    db.close()

