from fault import SoapFault
from response import soap_op
import datatypes
import sql

@soap_op
def getFulfillmentLocationTypes(request):
   return datatypes.getFulfillmentLocationTypesResponse

def getAvailableToAllocate(cur, sku, fulfiller_id, bin_name, ext_ful_loc_id):
   cur.execute(sql.GET_ON_HAND_AND_NUM_ALLOCATED, {
      'sku':            sku,
      'fulfiller_id':   fulfiller_id,
      'bin_name':       bin_name,
      'ext_ful_loc_id': ext_ful_loc_id,
   })
   result = cur.fetchone()
   if result is None:
      return 0
   return result[0] - result[1]

def getAvailableToDeallocate(cur, sku, fulfiller_id, bin_name, ext_ful_loc_id):
   result = cur.execute(sql.GET_NUM_ALLOCATED, {
      'sku':            sku,
      'fulfiller_id':   fulfiller_id,
      'bin_name':       bin_name,
      'ext_ful_loc_id': ext_ful_loc_id,
   })
   cur.fetchone()

   if result is None:
      return 0
   return result[0]

# Ghetto Python 2.7 enum
class Modification:
   ALLOCATE   = 1
   DEALLOCATE = 2
   FULFILL    = 3

class BinInfo:
   fulfiller_id   = 0
   name           = 1
   ext_ful_loc_id = 2
   bin_type       = 3
   bin_status     = 4
   bin_id         = 5

class LocationInfo:
   fulfiller_id   = 0
   ext_ful_loc_id = 1

# Handles allocateInventory, deallocateInventory, or fulfillInventory requests, per |allocate|.
def _modifyInventory(request, modification):
   conn = sql.getConnection()
   cur = conn.cursor()

   update_request = request.update_request
   for item in update_request.Items:
      quantity_left = item.Quantity

      cur.execute(sql.GET_BIN_NAMES, {
         'fulfiller_id':   update_request.FulfillerID,
         'ext_ful_loc_id': item.ExternalLocationID
      })
      bin_names = cur.fetchall()

      if bin_names is None:
         conn.close()
         raise SoapFault("No bins at FulfillerID %s, ExternalLocationID %s" % (
               update_request.FulfillerID, item.ExternalLocationID))

      # Modify as much as possible from each bin and stop once quantity is satisfied.
      modified_all = False
      for (bin_name,) in bin_names:
         if modification == Modification.ALLOCATE:
            available_to_modify = getAvailableToAllocate(
                  cur,
                  item.PartNumber, 
                  update_request.FulfillerID,
                  bin_name, 
                  item.ExternalLocationID)
         else:
            available_to_modify = getAvailableToDeallocate(
                  cur,
                  item.PartNumber, 
                  update_request.FulfillerID, 
                  bin_name, 
                  item.ExternalLocationID)

         if available_to_modify > 0:
            can_modify = min(available_to_modify, quantity_left)

            d_num_allocated = can_modify      if modification == Modification.ALLOCATE else -can_modify
            d_on_hand       = d_num_allocated if modification == Modification.FULFILL  else 0

            cur.execute(sql.INCREASE_NUM_ALLOCATED_AND_ON_HAND, {
               'd_num_allocated': d_num_allocated,
               'd_on_hand':       d_on_hand,
               'part_number':     item.PartNumber,
               'fulfiller_id':    update_request.FulfillerID,
               'bin_name':        bin_name,
               'ext_ful_loc_id':  item.ExternalLocationID,
            })

            quantity_left -= can_modify
            if quantity_left <= 0: # Should never be < 0, per max() above
               modified_all = True
               break

      if not modified_all:
         conn.close()
         raise SoapFault("Insufficient quantity")

   sql.commitAndClose(conn)
   return {
      Modification.ALLOCATE:   datatypes.allocateInventoryResponse(),
      Modification.DEALLOCATE: datatypes.deallocateInventoryResponse(),
      Modification.FULFILL:    datatypes.fulfillInventoryResponse(),
   }[modification]

@soap_op
def allocateInventory(request):
   return _modifyInventory(request, Modification.ALLOCATE)

@soap_op
def deallocateInventory(request):
   return _modifyInventory(request, Modification.DEALLOCATE)

@soap_op
def fulfillInventory(request):
   return _modifyInventory(request, Modification.FULFILL)

@soap_op
def createFulfiller(request):
   conn = sql.getConnection()
   cursor = conn.cursor()

   cursor.execute(sql.CREATE_FULFILLER, (request.FulfillerID,))

   sql.commitAndClose(conn)
   return datatypes.createFulfillerResponse(1)

@soap_op
def createFulfillmentLocation(location):
   values = (
         location.ExternalLocationID,
         location.RetailerLocationID,
         location.FulfillerID,
         location.LocationName,
         location.LocationType,
         location.Latitude,
         location.Longitude,
         location.Status)

   conn = sql.getConnection()
   cursor = conn.cursor()

   cursor.execute(sql.CREATE_LOCATION, values)

   sql.commitAndClose(conn)
   return datatypes.createFulfillmentLocationResponse(1)

@soap_op
def createBin(bin_):
   values = (
         bin_.Name,
         bin_.BinID,
         bin_.ExternalLocationID,
         bin_.FulfillerID,
         bin_.BinType,
         bin_.BinStatus)

   conn = sql.getConnection()
   cursor = conn.cursor()

   cursor.execute(sql.CREATE_BIN, values)

   sql.commitAndClose(conn)
   return datatypes.createBinResponse(1)

@soap_op
def AdjustRequest(request):
   conn = sql.getConnection()
   cursor = conn.cursor(buffered=True)

   for item in request.Items:
      cursor.execute(sql.TEST_ADJUST, (item.Quantity, item.PartNumber,
         request.FulfillerID, item.BinID, request.ExternalLocationID))
      if cursor.rowcount == 0:
         raise SoapFault("Cannot have quantity < 0 for item %s in bin %s" %
               (item.PartNumber, item.BinID))
      cursor.fetchall()
      cursor.execute(sql.MODIFY_STORED_AT, (item.Quantity, item.PartNumber,
         request.FulfillerID, item.BinID, request.ExternalLocationID))

   sql.commitAndClose(conn)
   return datatypes.AdjustResponse(1)

@soap_op
def RefreshRequest(request):
   conn = sql.getConnection()
   cursor = conn.cursor()

   for item in request.Items:
      cursor.execute(sql.CREATE_MANUFACTURER, (0,))
      cursor.execute(sql.CREATE_CATALOG, (0, 0))
      cursor.execute(sql.CREATE_PRODUCT, (item.UPC, 0, 0, ''));
      cursor.execute(sql.CREATE_FULFILLER_SPECIFIC_PRODUCT, (item.PartNumber,
         request.FulfillerID, item.UPC))
      cursor.execute(sql.CREATE_HELD_AT, (request.FulfillerID,
         request.ExternalLocationID, item.PartNumber, item.LTD,
         item.SafetyStock))
      cursor.execute(sql.CREATE_STORED_AT, (item.PartNumber,
         request.FulfillerID, item.BinID, request.ExternalLocationID,
         item.Quantity))

   sql.commitAndClose(conn)
   return datatypes.RefreshResponse(1)

class inventoryLocation(object):
   def __init__(self, LocationName, CatalogID, ManufacturerID, OnHand, Available, PartNumber, UPC, LTD, SafetyStock, CountryCode):
      self.LocationName = LocationName
      self.CatalogID = CatalogID
      self.ManufacturerID = ManufacturerID
      self.OnHand = OnHand
      self.Available = Available
      self.PartNumber = PartNumber
      self.UPC = UPC
      self.LTD = LTD
      self.SafetyStock = SafetyStock
      self.CountryCode = CountryCode

@soap_op
def getInventory(request):
   conn = sql.getConnection()
   cursor = conn.cursor()
   
   IgnoreSafetyStock = ' - ha.safety_stock ' if request.IgnoreSafetyStock == False or request.IgnoreSafetyStock.lower() == "false"  else ''
   LTD = ' ORDER by ha.LTD ' if request.OrderByLTD else ''
   IncludeNegativeInventory = ' - ha.num_allocated ' if request.IncludeNegativeInventory  == False or request.IncludeNegativeInventory.lower() == "false" else ''
   Limit = ' Limit ' + str(request.Limit) if request.Limit != '' else ''
   LocationIDs = ' AND l.ext_ful_loc_id in (' + str(request.LocationIDs)[1:-1] + ') 'if request.LocationIDs != [] else ''

   for item in request.Items:
      PartNumber = item.PartNumber
      UPC = item.UPC
      Quantity = item.Quantity
      query = (sql.GET_INVENTORY.format(
         FulfillerID = request.FulfillerID,
         LocationIDs = LocationIDs,
         ManufacturerID = request.ManufacturerID,
         CatalogID = request.CatalogID,
         Type = request.Type,
         Limit = Limit,
         IgnoreSafetyStock = IgnoreSafetyStock,
         OrderByLTD = LTD,
         IncludeNegativeInventory = IncludeNegativeInventory,
         LocationsIDs = LocationIDs,
         PartNumber = PartNumber,
         UPC = UPC,
         Quantity = Quantity,
      ));
      print(query)
      cursor.execute(query)
      print(cursor.fetchall())
   return datatypes.getInventoryResponse([])

@soap_op
def getFulfillerStatus(request):
   conn = sql.getConnection()
   cur = conn.cursor()

   cur.execute(
      sql.GET_STATUSES.format(
      fulfiller_id   = request.FulfillerID
   ))

   for result in cur:
      if result == ('active',):
         return datatypes.getFulfillerStatusResponse(1)
   return datatypes.getFulfillerStatusResponse(2)

@soap_op
def getBinTypes(request):
   conn = sql.getConnection()
   cur = conn.cursor()

   cur.execute(sql.GET_BIN_TYPES)

   types = []
   for result in cur:
      types.append(result[0])
   return datatypes.getBinTypesResponse(types)

@soap_op
def getBinStatuses(request):
   conn = sql.getConnection()
   cur = conn.cursor()

   cur.execute(sql.GET_BIN_STATUSES)

   statuses = []
   for result in cur:
      statuses.append(result[0])
   return datatypes.getBinStatusesResponse(statuses)

@soap_op
def getBins(request):
   conn = sql.getConnection()
   cur = conn.cursor()
   
   cur.execute(
      sql.GET_BINS.format(
      fulfiller_id   = request.FulfillerID,
      ext_ful_loc_id = request.ExternalLocationID,
      name           = request.SearchTerm,
      num_results    = request.NumResults,
      results_start  = request.ResultsStart
   ))

   count = 0
   items = []
   for result in cur:
      items.append(datatypes.item(result[BinInfo.fulfiller_id], 1, result[BinInfo.ext_ful_loc_id],\
              result[BinInfo.bin_type], result[BinInfo.bin_status], result[BinInfo.name]))
      count = count + 1
   return datatypes.getBinsResponse(count, items)

@soap_op
def getFulfillmentLocations(request):
   conn = sql.getConnection()
   cur = conn.cursor()
   
   cur.execute(
      sql.GET_FULFILLMENT_LOCATIONS.format(
      fulfiller_id   = request.FulfillerID,
      manufacturer_id   = request.ManufacturerID,
      catalog_id   = request.CatalogID,
   ))

   locations = []
   for result in cur:
      locations.append(datatypes.location(result[LocationInfo.fulfiller_id], result[LocationInfo.ext_ful_loc_id]))
   return datatypes.getFulfillmentLocationsResponse(locations)
