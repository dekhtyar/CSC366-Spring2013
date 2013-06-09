################################################## 
# CoreServiceService_services.py 
# generated by ZSI.generate.wsdl2python
##################################################


from CoreServiceService_services_types import *
import urlparse, types
from ZSI.TCcompound import ComplexType, Struct
from ZSI import client
import ZSI
from ZSI.generate.pyclass import pyclass_type

# Locator
class CoreServiceServiceLocator:
    CoreService_address = "http://localhost:443/inventoryService/"
    def getCoreServiceAddress(self):
        return CoreServiceServiceLocator.CoreService_address
    def getCoreService(self, url=None, **kw):
        return CoreServiceSoapBindingSOAP(url or CoreServiceServiceLocator.CoreService_address, **kw)

# Methods
class CoreServiceSoapBindingSOAP:
    def __init__(self, url, **kw):
        kw.setdefault("readerclass", None)
        kw.setdefault("writerclass", None)
        # no resource properties
        self.binding = client.Binding(url=url, **kw)
        # no ws-addressing

    # op: createFulfiller
    def createFulfiller(self, request):
        if isinstance(request, createFulfillerRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(createFulfillerResponse.typecode)
        return response

    # op: getFulfillerStatus
    def getFulfillerStatus(self, request):
        if isinstance(request, getFulfillerStatusRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(getFulfillerStatusResponse.typecode)
        return response

    # op: createFulfillmentLocation
    def createFulfillmentLocation(self, request):
        if isinstance(request, createFulfillmentLocationRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(createFulfillmentLocationResponse.typecode)
        return response

    # op: getFulfillmentLocations
    def getFulfillmentLocations(self, request):
        if isinstance(request, getFulfillmentLocationsRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(getFulfillmentLocationsResponse.typecode)
        return response

    # op: getFulfillmentLocationTypes
    def getFulfillmentLocationTypes(self, request):
        if isinstance(request, getFulfillmentLocationTypesRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(getFulfillmentLocationTypesResponse.typecode)
        return response

    # op: allocateInventory
    def allocateInventory(self, request):
        if isinstance(request, allocateInventoryRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(allocateInventoryResponse.typecode)
        return response

    # op: deallocateInventory
    def deallocateInventory(self, request):
        if isinstance(request, deallocateInventoryRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(deallocateInventoryResponse.typecode)
        return response

    # op: fulfillInventory
    def fulfillInventory(self, request):
        if isinstance(request, fulfillInventoryRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(fulfillInventoryResponse.typecode)
        return response

    # op: createBin
    def createBin(self, request):
        if isinstance(request, createBinRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(createBinResponse.typecode)
        return response

    # op: getBins
    def getBins(self, request):
        if isinstance(request, getBinsRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(getBinsResponse.typecode)
        return response

    # op: getBinTypes
    def getBinTypes(self, request):
        if isinstance(request, getBinTypesRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(getBinTypesResponse.typecode)
        return response

    # op: getBinStatuses
    def getBinStatuses(self, request):
        if isinstance(request, getBinStatusesRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(getBinStatusesResponse.typecode)
        return response

    # op: getInventory
    def getInventory(self, request):
        if isinstance(request, getInventoryRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="", **kw)
        # no output wsaction
        response = self.binding.Receive(getInventoryResponse.typecode)
        return response

    # op: adjustInventory
    def adjustInventory(self, request):
        if isinstance(request, AdjustInventorySoapIn) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="urn:#NewOperation", **kw)
        # no output wsaction
        response = self.binding.Receive(AdjustInventorySoapOut.typecode)
        return AdjustInventorySoapOut(response)

    # op: refreshInventory
    def refreshInventory(self, request):
        if isinstance(request, RefreshInventorySoapIn) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        kw = {}
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="urn:#NewOperation", **kw)
        # no output wsaction
        response = self.binding.Receive(RefreshInventorySoapOut.typecode)
        return RefreshInventorySoapOut(response)

createFulfillerRequest = ns0.createFulfiller_Dec().pyclass

createFulfillerResponse = ns0.createFulfillerResponse_Dec().pyclass

getFulfillerStatusRequest = ns0.getFulfillerStatus_Dec().pyclass

getFulfillerStatusResponse = ns0.getFulfillerStatusResponse_Dec().pyclass

createFulfillmentLocationRequest = ns0.createFulfillmentLocation_Dec().pyclass

createFulfillmentLocationResponse = ns0.createFulfillmentLocationResponse_Dec().pyclass

getFulfillmentLocationsRequest = ns0.getFulfillmentLocations_Dec().pyclass

getFulfillmentLocationsResponse = ns0.getFulfillmentLocationsResponse_Dec().pyclass

getFulfillmentLocationTypesRequest = ns0.getFulfillmentLocationTypes_Dec().pyclass

getFulfillmentLocationTypesResponse = ns0.getFulfillmentLocationTypesResponse_Dec().pyclass

allocateInventoryRequest = ns0.allocateInventory_Dec().pyclass

allocateInventoryResponse = ns0.allocateInventoryResponse_Dec().pyclass

deallocateInventoryRequest = ns0.deallocateInventory_Dec().pyclass

deallocateInventoryResponse = ns0.deallocateInventoryResponse_Dec().pyclass

fulfillInventoryRequest = ns0.fulfillInventory_Dec().pyclass

fulfillInventoryResponse = ns0.fulfillInventoryResponse_Dec().pyclass

createBinRequest = ns0.createBin_Dec().pyclass

createBinResponse = ns0.createBinResponse_Dec().pyclass

getBinsRequest = ns0.getBins_Dec().pyclass

getBinsResponse = ns0.getBinsResponse_Dec().pyclass

getBinTypesRequest = ns0.getBinTypes_Dec().pyclass

getBinTypesResponse = ns0.getBinTypesResponse_Dec().pyclass

getBinStatusesRequest = ns0.getBinStatuses_Dec().pyclass

getBinStatusesResponse = ns0.getBinStatusesResponse_Dec().pyclass

getInventoryRequest = ns0.getInventory_Dec().pyclass

getInventoryResponse = ns0.getInventoryResponse_Dec().pyclass

AdjustInventorySoapIn = ns0.AdjustRequest_Dec().pyclass

AdjustInventorySoapOut = ns0.AdjustResponse_Dec().pyclass

RefreshInventorySoapIn = ns0.RefreshRequest_Dec().pyclass

RefreshInventorySoapOut = ns0.RefreshResponse_Dec().pyclass
