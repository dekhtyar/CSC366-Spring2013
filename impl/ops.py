from response import soap_op
import datatypes
import sql

@soap_op
def getFulfillmentLocationTypes(request):
   return datatypes.getFulfillmentLocationTypesResponse

def getAvailableToAllocate(sku, fulfiller_id, bin_name, ext_ful_loc_id):
   (on_hand, num_allocated) = cur.execute(
      GET_ON_HAND_AND_NUM_ALLOCATED.format(
         sku            = sku, 
         fulfiller_id   = fulfiller_id, 
         bin_name       = bin_name,
         ext_ful_loc_id = ext_ful_loc_id
      )
   ).fetchone()
   
   return on_hand - num_allocated

def getAvailableToDeallocate(sku, fulfiller_id, bin_name, ext_ful_loc_id):
   (num_allocated,) = cur.execute(
      GET_NUM_ALLOCATED.format(
      sku            = sku,
      fulfiller_id   = fulfiller_id,
      bin_name       = bin_name,
      ext_ful_loc_id = ext_ful_loc_id
   )).fetchone()

   return num_allocated

class Modification:
   ALLOCATE   = 1
   DEALLOCATE = 2
   FULFILL    = 3

# Handles allocateInventory, deallocateInventory, or fulfillInventory requests, per |allocate|.
def _modifyInventory(request, modification):
   conn = sql.getConnection()
   cur = conn.cursor()

   if modification == Modification.ALLOCATE:
      response = allocateInventoryResponse
   elif modification == Modification.DEALLOCATE:
      response = deallocateInventoryResponse
   else:
      response = fulfillInventoryResponse

   for item in request.Request.Items.Items:
      quantity_left = item.Quantity

      bin_names = cur.execute(
         GET_BIN_NAMES.format(
         fulfiller_id   = request.Request.FulfillerID,
         ext_ful_loc_id = item.ExternalLocationID
      )).fetchall()

      # Modify as much as possible from each bin and stop once quantity is satisfied.
      modified_all = False
      for (bin_name,) in bin_names:
         if modification == ALLOCATE:
            available_to_modify = getAvailableToAllocate(item.PartNumber, 
                                                         request.Request.FulfillerID, 
                                                         bin_name, 
                                                         item.ExternalLocationID)
         else:
            available_to_modify = getAvailableToDeallocate(item.PartNumber, 
                                                           request.Request.FulfillerID, 
                                                           bin_name, 
                                                           item.ExternalLocationID)

         if available_to_modify > 0:
            max_can_modify = max(available_to_modify, quantity_left)

            d_num_allocated = max_can_modify  if modification == Modification.ALLOCATE else -max_can_modify
            d_on_hand       = d_num_allocated if modification == Modification.FULFILL  else 0

            cur.execute(
               INCREASE_NUM_ALLOCATED_AND_ON_HAND.format(
               d_num_allocated = d_num_allocated,
               d_on_hand       = d_on_hand,
               part_number     = item.PartNumber,
               fulfiller_id    = request.Request.FulfillerID,
               bin_name        = bin_name,
               ext_ful_loc_id  = item.ExternalLocationID
            ))

            quantity_left -= max_can_modify
            if quantity_left <= 0: # Should never be < 0, per max() above
               modified_all = True
               break

      if not modified_all:
         conn.close()
         return response() # TODO: throw soap error

   sql.commitAndClose(conn)
   return response()

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
def adjustInventory(request):
   pass

@soap_op
def refreshInventory(request):
   return datatypes.RefreshResponse(1)
