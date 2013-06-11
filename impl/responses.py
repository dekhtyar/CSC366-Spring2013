class allocateInventoryResponse(object):
   def __init__(self):
      pass

class getFulfillmentLocationTypesResponse(object):
   types = ["RETAILER", "FULFILLER", "MANUFACTURER"]

class createFulfillerResponse(object):
   def __init__(self, createFulfillerReturn):
      self.createFulfillerReturn = createFulfillerReturn

class createFulfillmentLocationResponse(object):
   def __init__(self, createFulfillmentLocationReturn):
      self.createFulfillmentLocationReturn = createFulfillmentLocationReturn

class createBinResponse(object):
   def __init__(self, createBinReturn):
      self.createBinReturn = createBinReturn

class RefreshResponse(object):
   def __init__(self, response_str):
      self.RefreshResponse = response_str

class getFulfillerStatusResponse(object):
   def __init__(self, getFulfillerStatusReturn):
      self.getFulfillerStatusReturn = getFulfillerStatusReturn

class getBinTypesResponse(object):
   def __init__(self, types):
      self.types = types
    
class getBinStatusesResponse(object):
   def __init__(self, statuses):
      self.statuses = statuses

class item(object):
   def __init__(self, fulfiller_id, bin_id, ext_ful_loc_id, bin_type, bin_status, bin_name):
      self.FulfillerId = fulfiller_id
      self.BinID = bin_id
      self.ExternalLocationID = ext_ful_loc_id
      self.BinType = bin_type
      self.BinStatus = bin_status
      self.Name = bin_name

class getBinsResponse(object):
   def __init__(self, resultCount, items):
      self.items = items
      self.ResultCount = resultCount

class location(object):
   def __init__(self, FulfillerID, ExternalLocationID):
      self.FulfillerID = FulfillerID
      self.ExternalLocationID = ExternalLocationID

class getFulfillmentLocationsResponse(object):
   def __init__(self, locations):
      self.locations = locations
