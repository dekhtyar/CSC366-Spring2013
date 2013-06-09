import mysql.connector
import os

from CoreServiceService_services import *

INCREASE_NUM_ALLOCATED_AND_ON_HAND = '''
   UPDATE StoredIn(sku, fulfiller_id, bin_name, ext_ful_loc_id, num_allocated)
   SET num_allocated = num_allocated + {d_num_allocated} AND
       on_hand       = on_hand + {d_on_hand}
   WHERE sku            = {part_number}    AND
         fuller_id      = {fulfiller_id}   AND
         bin_name       = {bin_name}       AND
         ext_ful_loc_id = {ext_ful_loc_id}
'''

GET_BIN_NAMES = '''
   SELECT name
   FROM Bin
   WHERE fulfiller_id   = {fulfiller_id} AND
         ext_ful_loc_id = {ext_ful_loc_id} 
'''

GET_ON_HAND_AND_NUM_ALLOCATED = '''
   SELECT on_hand, num_allocated
   FROM StoredIn
   WHERE sku            = {sku}            AND
         fulfiller_id   = {fulfiller_id}   AND
         bin_name       = {bin_name}       AND
         ext_ful_loc_id = {ext_ful_loc_id}
'''

GET_NUM_ALLOCATED = '''
   SELECT num_allocated
   FROM StoredIn
   WHERE sku            = {sku}            AND
         fulfiller_id   = {fulfiller_id}   AND
         bin_name       = {bin_name}       AND
         ext_ful_loc_id = {ext_ful_loc_id}
'''

CREATE_FULFILLER = '''
   INSERT INTO Fulfiller(id) VALUES (%s)
'''

CREATE_LOCATION = '''
   INSERT INTO Location(ext_loc_id, int_loc_id, fulfiller_id, name, type,
      latitude, longitude, status)
   VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
'''

CREATE_BIN = '''
   INSERT INTO Bin(name, ext_ful_loc_id, fulfiller_id, type, status)
   VALUES (%s, %s, %s, %s, %s)
'''

CREATE_MANUFACTURER = '''
   INSERT IGNORE INTO Manufacturer(id)
   VALUES(%s)
'''

CREATE_CATALOG = '''
   INSERT IGNORE INTO Catalog(id, manufacturer_id)
   VALUES(%s, %s)
'''

CREATE_PRODUCT = '''
   INSERT IGNORE INTO Product(upc, catalog_id, manufacturer_id, name)
   VALUES(%s, %s, %s, %s)
'''

CREATE_FULFILLER_SPECIFIC_PRODUCT = '''
   INSERT IGNORE INTO FulfillerSpecificProduct(sku, fulfiller_id, upc)
   VALUES(%s, %s, %s)
'''

CREATE_HELD_AT = '''
   REPLACE HeldAt(fulfiller_id, ext_ful_loc_id, sku, ltd, safety_stock)
   VALUES(%s, %s, %s, %s, %s)
'''

CREATE_STORED_AT = '''
   REPLACE StoredIn(sku, fulfiller_id, bin_name, ext_ful_loc_id, on_hand)
   VALUES(%s, %s, %s, %s, %s)
'''

def getConnection():
   return mysql.connector.connect(user='root', database='inventory', 
                                  password=os.environ.get('MYSQL_PASS'))

def commitAndClose(conn):
   conn.commit()
   conn.close()

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
   conn = getConnection()
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

   commitAndClose(conn)
   return response()

def allocateInventory(request):
   return _modifyInventory(request, Modification.ALLOCATE)

def deallocateInventory(request):
   return _modifyInventory(request, Modification.DEALLOCATE)

def fulfillInventory(request):
   return _modifyInventory(request, Modification.FULFILL)

#TODO(Andrew) check for SQL errors and throw SOAP fault
def createFulfiller(request):
   conn = getConnection()
   cursor = conn.cursor()

   response = createFulfillerResponse()
   cursor.execute(CREATE_FULFILLER, (request.Request.FulfillerID,))
   response.CreateFulfillerReturn = 1

   commitAndClose(conn)
   return response

def createFulfillmentLocation(request):
   fulfiller = request.Request
   values = (
         fulfiller.ExternalLocationID,
         fulfiller.RetailerLocationID,
         fulfiller.FulfillerID,
         fulfiller.LocationName,
         fulfiller.LocationType,
         fulfiller.Latitude,
         fulfiller.Longitude,
         fulfiller.Status)

   conn = getConnection()
   cursor = conn.cursor()

   response = createFulfillmentLocationResponse()
   cursor.execute(CREATE_LOCATION, values) 
   response.CreateFulfillmentLocationReturn = 1

   commitAndClose(conn)
   return response

def createBin(request):
   bin_ = request.Request
   values = (
         bin_.Name,
         bin_.ExternalLocationID,
         bin_.FulfillerID,
         bin_.BinType,
         bin_.BinStatus)

   conn = getConnection()
   cursor = conn.cursor()

   response = createBinResponse()
   cursor.execute(CREATE_BIN, values)
   response.CreateBinReturn = 1

   commitAndClose(conn)
   return response

def adjustInventory(request):
   pass

def refreshInventory(request):
   pass
