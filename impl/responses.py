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
