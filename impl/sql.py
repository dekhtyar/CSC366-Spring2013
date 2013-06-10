import mysql.connector
import os

def getConnection():
   return mysql.connector.connect(user='root', database='inventory',
         password=os.environ.get('MYSQL_PASS'))

def commitAndClose(conn):
   conn.commit()
   conn.close()

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

GET_STATUSES = '''
   SELECT status
   FROM Location
   WHERE fulfiller_id   = {fulfiller_id}
'''

GET_BIN_TYPES = '''
   SELECT DISTINCT type
   FROM Bin
'''

GET_BIN_STATUSES = '''
   SELECT DISTINCT status
   FROM Bin
'''

GET_BINS = '''
   SELECT fulfiller_id, name, ext_ful_loc_id, type, status
   FROM Bin
   WHERE fulfiller_id   = {fulfiller_id} AND
         ext_ful_loc_id = {ext_ful_loc_id} AND
         name           LIKE "%{name}%"
   LIMIT {results_start}, {num_results}
'''

CREATE_FULFILLER = '''
   INSERT INTO Fulfiller(id) VALUES (%s)
'''

CREATE_LOCATION = '''
   INSERT INTO Location(ext_ful_loc_id, int_ful_loc_id, fulfiller_id, name, type,
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

