namespaces = {'soapenv': "http://schemas.xmlsoap.org/soap/envelope/",
              'v4': "http://v4.core.coexprivate.api.shopatron.com"}

def getElement(element, xpath):
   return element.xpath(xpath, namespaces=namespaces)[0]

def getElementList(element, xpath):
   return element.xpath(xpath, namespaces=namespaces)

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

class getFulfillerStatus(object):
   def __init__(self, element):
      self.FulfillerID = getElement(element, "v4:fulfillerID").text

class getBinTypes(object):
   def __init__(self, element):
      pass
