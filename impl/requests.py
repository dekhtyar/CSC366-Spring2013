namespaces = {'soapenv': "http://schemas.xmlsoap.org/soap/envelope/",
              'v4': "http://v4.core.coexprivate.api.shopatron.com"}

def getElement(element, xpath):
   toRet = element.xpath(xpath, namespaces=namespaces)
   if toRet == []:
      return None
   else:
      return toRet[0]

def getElementList(element, xpath):
   return element.xpath(xpath, namespaces=namespaces)

class UpdateRequest(object):
   def __init__(self, element):
      self.FulfillerID = int(getElement(request, "v4:FulfillerID").text)
      self.FulfillerLocationCatalog = FulfillerLocationCatalog(
            getElement(request, "v4:FulfillerLocationCatalog"))
      self.Items = []
      for item in getElement(element, "v4:Items"):
         self.Items.append(UpdateItem(item))

   class UpdateItem(object):
      def __init__(self, element):
         self.PartNumber         =     getElement(element, "v4:PartNumber").text
         self.UPC                =     getElement(element, "v4:UPC").text
         self.Quantity           = int(getElement(element, "v4:Quantity").text)
         self.OrderItemID        = int(getElement(element, "v4:OrderItemID").text)
         self.ShipmentID         = int(getElement(element, "v4:ShipmentID").text)
         self.ExternalLocationID =     getElement(element, "v4:ExternalLocationID").text

class allocateInventory(object):
   def __init__(self, element):
       self.request = UpdateRequest(getElement(element, "v4:request"))

class getFulfillmentLocationTypes(object):
   def __init__(self, element):
      pass

class createFulfiller(object):
   def __init__(self, element):
      request = getElement(element, "v4:request")

      self.FulfillerID = int(getElement(request, "v4:FulfillerID").text)
      self.Name = getElement(request, "v4:Name").text

class createFulfillmentLocation(object):
   def __init__(self, element):
      request = getElement(element, 'v4:request')

      self.FulfillerID = int(getElement(request, "v4:FulfillerID").text)
      self.RetailerLocationID = int(getElement(request, "v4:RetailerLocationID").text)
      self.ExternalLocationID = getElement(request, "v4:ExternalLocationID").text
      self.LocationName = getElement(request, "v4:LocationName").text
      self.LocationType = getElement(request, "v4:LocationType").text
      self.Latitude = float(getElement(request, "v4:Latitude").text)
      self.Longitude = float(getElement(request, "v4:Longitude").text)
      self.Status = getElement(request, "v4:Status").text
      self.CountryCode = getElement(request, "v4:CountryCode").text

class createBin(object):
   def __init__(self, element):
      request = getElement(element, 'v4:request')

      self.FulfillerID = getElement(request, "v4:FulfillerID").text
      self.BinID = getElement(request, "v4:BinID").text
      self.ExternalLocationID = getElement(request, "v4:ExternalLocationID").text
      self.BinType = getElement(request, "v4:BinType").text
      self.BinStatus = getElement(request, "v4:BinStatus").text
      self.Name = getElement(request, "v4:Name").text

class FulfillerLocationCatalog(object):
   def __init__(self, element):
      self.ManufacturerCatalog = ManufacturerCatalog(
            getElement(element, "v4:ManufacturerCatalog"))

class ManufacturerCatalog(object):
   def __init__(self, element):
      self.ManufacturerID = int(getElement(element, "v4:ManufacturerID").text)
      self.CatalogID      = int(getElement(element, "v4:CatalogID").text)

class RefreshRequestItem(object):
   def __init__(self, element):
      self.PartNumber = getElement(element, "v4:PartNumber").text
      self.UPC = getElement(element, "v4:UPC").text
      self.BinID = getElement(element, "v4:BinID").text
      self.Quantity = getElement(element, "v4:Quantity").text
      self.LTD = getElement(element, "v4:LTD").text
      self.SafetyStock = getElement(element, "v4:SafetyStock").text

class RefreshRequest(object):
   def __init__(self, element):
      self.FulfillerID = getElement(element, "v4:FulfillerID").text
      self.ExternalLocationID = getElement(element,
            "v4:ExternalLocationID").text
      self.Items = []
      for item in getElement(element, "v4:Items"):
         self.Items.append(RefreshRequestItem(item))

class GetInventoryItem(object):
   def __init__(self, element):
      self.PartNumber = getElement(element, "v4:PartNumber").text
      self.UPC = getElement(element, "v4:UPC").text
      self.Quantity = getElement(element, "v4:Quantity").text

class getInventory(object):
   def __init__(self, element):
      request = getElement(element, "v4:request")
      self.FulfillerID = getElement(request, "v4:FulfillerID").text
      catalog = getElement(request, "v4:Catalog")
      self.ManufacturerID = getElement(catalog, "v4:ManufacturerID").text
      self.CatalogID = getElement(catalog, "v4:CatalogID").text
      self.LocationIDs = []
      if getElement(request, "v4:LocationIDs") is not None:
         #for item in getElement(request, "v4:LocationIDs"):
         if(getElement(getElement(request, "v4:LocationIDs"), "v4:ExternalLocationID")) is not None:
            self.LocationIDs.append(getElement(getElement(request, "v4:LocationIDs"), "v4:ExternalLocationID").text)

      self.Items = []
      for item in getElement(request, "v4:Quantities"):
         self.Items.append(GetInventoryItem(item))

      self.Type = getElement(request, "v4:Type").text
      self.Limit = getElement(request, "v4:Limit").text
      self.IgnoreSafetyStock = getElement(request, "v4:IgnoreSafetyStock").text
      self.IncludeNegativeInventory = getElement(request, "v4:IncludeNegativeInventory").text
      self.OrderByLTD = getElement(request, "v4:OrderByLTD").text

class getFulfillerStatus(object):
   def __init__(self, element):
      self.FulfillerID = getElement(element, "v4:fulfillerID").text

class getBinTypes(object):
   def __init__(self, element):
      pass

class getBinStatuses(object):
   def __init__(self, element):
      pass

class getBins(object):
   def __init__(self, element):
      request = getElement(element, "v4:request")
      
      self.FulfillerID = getElement(request, "v4:FulfillerID").text
      self.ExternalLocationID = getElement(request, "v4:ExternalLocationID").text
      self.SearchTerm = getElement(request, "v4:SearchTerm").text
      self.NumResults = getElement(request, "v4:NumResults").text
      self.ResultsStart = getElement(request, "v4:ResultsStart").text

class getFulfillmentLocations(object):
   def __init__(self, element):
      request = getElement(element, "v4:request")
      
