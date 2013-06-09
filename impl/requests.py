class getFulfillmentLocationTypes(object):
   def __init__(self, element):
      pass

class createFulfiller(object):
   def __init__(self, element):
      self.FulfillerID = int(element['request']['FulfillerID'])
      self.Name = element['request']['Name']

class createFulfillmentLocation(object):
   def __init__(self, element):
      request = element['request']

      self.FulfillerID = int(request["FulfillerID"])
      self.RetailerLocationID = int(request["RetailerLocationID"])
      self.ExternalLocationID = request["ExternalLocationID"]
      self.LocationName = request["LocationName"]
      self.LocationType = request["LocationType"]
      self.Latitude = float(request["Latitude"])
      self.Longitude = float(request["Longitude"])
      self.Status = request["Status"]
      self.CountryCode = request["CountryCode"]

class createBin(object):
   def __init__(self, element):
      request = element['request']

      self.FulfillerID = request["FulfillerID"]
      self.BinID = request["BinID"]
      self.ExternalLocationID = request["ExternalLocationID"]
      self.BinType = request["BinType"]
      self.BinStatus = request["BinStatus"]
      self.Name = request["Name"]

class RefreshRequestItem(object):
   def __init__(self, element):
       self.PartNumber = request["PartNumber"]
       self.UPC = request["UPC"]
       self.BinID = request["BinID"]
       self.Quantity = request["Quantity"]
       self.LTD = request["LTD"]
       self.SafetyStock = request["SafetyStock"]

class RefreshRequest(object):
   def __init__(self, element):
      self.FulfillerID = request["FulfillerID"]
      self.LocationName = request["LocationName"]
      self.Items = []
      for item in element["Items"]:
         self.Items.append(RefreshRequestItem(item["item"]))
