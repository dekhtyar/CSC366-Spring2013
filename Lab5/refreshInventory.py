import csv
import mysql.connector
import sys
import os

PRODUCT_NAME   = 0
SKU            = 1
UPC            = 2
SAFETY_STOCK   = 3
LTD            = 4
MANU_ID        = 5
CAT_ID         = 6
ON_HAND        = 7
BIN_NAME       = 8
EXT_FUL_LOC_ID = 9
INT_FUL_LOC_ID = 10

conn = mysql.connector.connect(user='root', database='inventory',
        password=os.environ.get('MYSQL_PASS'))

cursor = conn.cursor()

insertManufacturer = 'INSERT IGNORE INTO Manufacturer(id) VALUES(%s);'

insertCatalog = 'INSERT IGNORE INTO Catalog(id, manufacturer_id) VALUES(%s, %s);'

insertProduct = 'INSERT IGNORE INTO Product(upc, catalog_id, manufacturer_id, name) VALUES(%s, %s, %s, %s);'

insertFulfillerSpecificProduct = 'INSERT INTO FulfillerSpecificProduct(sku, fulfiller_id, upc) VALUES(%s, %s, %s);'

insertHeldAt = 'REPLACE HeldAt(fulfiller_id, ext_ful_loc_id, sku, ltd, safety_stock) VALUES(%s, %s, %s, %s, %s);'

insertStoredIn = 'REPLACE StoredIn(sku, fulfiller_id, bin_name, ext_ful_loc_id, on_hand) VALUES(%s, %s, %s, %s, %s);'

GET_FULFILLER_ID_STR = ('SELECT fulfiller_id FROM Location WHERE '
                        'int_ful_loc_id = %s')

def getFulfillerId(cursor, int_ful_loc_id):
    cursor.execute(GET_FULFILLER_ID_STR, (int_ful_loc_id,))
    return cursor.fetchone()


with open(sys.argv[1], 'r') as csvfile:
    csvreader = csv.reader(csvfile, delimiter=',', quotechar="'")
    csvreader.next()
    for row in csvreader:
	fulfiller_id = getFulfillerId(cursor, row[INT_FUL_LOC_ID])
        cursor.execute(insertManufacturer, (row[MANU_ID],))
	cursor.execute(insertCatalog, (row[CAT_ID], row[MANU_ID]))
	cursor.execute(insertProduct, (row[UPC], row[CAT_ID], row[MANU_ID],
                       row[PRODUCT_NAME]));
        cursor.execute(insertFulfillerSpecificProduct, (row[SKU], fulfiller_id[0],
                       row[UPC]))
        cursor.execute(insertHeldAt, (fulfiller_id[0], row[EXT_FUL_LOC_ID],
                       row[SKU], row[LTD], row[SAFETY_STOCK]))
	cursor.execute(insertStoredIn, (row[SKU], fulfiller_id[0], row[BIN_NAME],
		       row[EXT_FUL_LOC_ID], row[ON_HAND]))
conn.commit()
conn.close()
