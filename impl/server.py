from CoreServiceService_services import *
from CoreServiceService_services_server import *
from ZSI.ServiceContainer import ServiceSOAPBinding
from ZSI.ServiceContainer import AsServer
from operations import *

class PinkyBrainService(CoreServiceService):
    # Andrew
    def soap_createFulfiller(self, ps):
        self.request = ps.Parse(createFulfillerRequest.typecode)
        return createFulfiller(self.request) 
 
    # Halli
    def soap_getFulfillerStatus(self, ps):
        self.request = ps.Parse(getFulfillerStatusRequest.typecode)
        return getFulfillerStatusResponse()

    # Andrew
    def soap_createFulfillmentLocation(self, ps):
        self.request = ps.Parse(createFulfillmentLocationRequest.typecode)
        return createFulfillmentLocation(self.request)

    # Halli
    def soap_getFulfillmentLocations(self, ps):
        self.request = ps.Parse(getFulfillmentLocationsRequest.typecode)
        return getFulfillmentLocationsResponse()

    # Halli
    def soap_getFulfillmentLocationTypes(self, ps):
        self.request = ps.Parse(getFulfillmentLocationTypesRequest.typecode)
        response = getFulfillmentLocationTypesResponse()
        
        type = FulfillmentLocationType()
        types = (type,)
        #types = ('FULFILLER', 'RETAILER', 'MANUFACTURER')

        response.GetFulfillmentLocationTypesReturn = types

        return response
  
    # Mitch
    def soap_allocateInventory(self, ps):
        self.request = ps.Parse(allocateInventoryRequest.typecode)
        return allocateInventory(self.request)

    # Mitch
    def soap_deallocateInventory(self, ps):
        self.request = ps.Parse(deallocateInventoryRequest.typecode)
        return deallocateInventory(self.request)

    # Mitch
    def soap_fulfillInventory(self, ps):
        self.request = ps.Parse(fulfillInventoryRequest.typecode)
        return fulfillInventory(self.request)

    # Andrew
    def soap_createBin(self, ps):
        self.request = ps.Parse(createBinRequest.typecode)
        return createBin(self.request)

    # Halli
    def soap_getBins(self, ps):
        self.request = ps.Parse(getBinsRequest.typecode)
        return getBinsResponse()

    # Halli
    def soap_getBinTypes(self, ps):
        self.request = ps.Parse(getBinTypesRequest.typecode)
        response = getBinTypesResponse()
       
        f_bin = ns0.BinType_Def('BinType').pyclass
        f_bin.BinType = 'TYPE'

        types = [f_bin]
        response.GetBinTypesReturn = types
        #b = BinTypes()

        #print(dir(response))# = b
        #print(dir(response.GetBinTypesReturn))# = b

        return response

    # Halli
    def soap_getBinStatuses(self, ps):
        self.request = ps.Parse(getBinStatusesRequest.typecode)
        return getBinStatusesResponse()

    # Haikal
    def soap_getInventory(self, ps):
        self.request = ps.Parse(getInventoryRequest.typecode)
        response = getInventoryResponse()
        #print(request.Request.FulfillerID)
        #for i in request.Request.Quantities.items
        #   print(i.PartNumber + " " + i.UPC + " " + i.Quantity)
        return response

    # Andrew
    def soap_adjustInventory(self, ps):
        self.request = ps.Parse(AdjustInventorySoapIn.typecode)
        return AdjustInventorySoapOut()

    # Andrew
    def soap_refreshInventory(self, ps):
        self.request = ps.Parse(RefreshInventorySoapIn.typecode)
        return RefreshInventorySoapOut()

if __name__ == '__main__':
   AsServer(8080, (PinkyBrainService(),))
