import csv
import mysql.connector
import sys
import os

NAME           = 0
FULFILLER_ID   = 1
EXT_FUL_LOC_ID = 2
INT_FUL_LOC_ID = 3
DESC           = 4
LATITUDE       = 5
LONGITUDE      = 6
STATUS         = 7
SAFETY_STOCK   = 8
MAN_ID         = 9
CAT_ID         = 10

conn = mysql.connector.connect(user='root', database='inventory',
        password=os.environ.get('MYSQL_PASS'))

insertFulfiller = 'INSERT IGNORE INTO Fulfiller(id) VALUES(%s);'

insertManufacturer = 'INSERT IGNORE INTO Manufacturer(id) VALUES(%s);'

insertLocation = ('REPLACE INTO Location(ext_ful_loc_id, int_ful_loc_id,'
                  'fulfiller_id, name, type, latitude, longitude, status, default_safety_stock)'
                  'VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s);')

INSERT_BIN_STR = ('INSERT IGNORE INTO Bin(name, ext_ful_loc_id, fulfiller_id, type, '
                  'status) VALUES(%s, %s, %s, %s, %s)')

def insertBin(cursor, name, ext_ful_loc_id, fulfiller_id, typ, status):
    return cursor.execute(INSERT_BIN_STR, (name, ext_ful_loc_id, fulfiller_id, typ, status))

cursor = conn.cursor()

with open(sys.argv[1], 'r') as csvfile:
    csvreader = csv.reader(csvfile, delimiter=',', quotechar="'")
    csvreader.next()
    for row in csvreader:
        cursor.execute(insertFulfiller, (row[FULFILLER_ID],))
        cursor.execute(insertManufacturer, (row[MAN_ID],))
        cursor.execute(insertLocation, (row[EXT_FUL_LOC_ID],
                row[INT_FUL_LOC_ID], row[FULFILLER_ID], row[NAME], row[DESC],
                row[LATITUDE], row[LONGITUDE], row[STATUS], row[SAFETY_STOCK]))
conn.commit()
with open(sys.argv[1], 'r') as csvfile:
    csvreader = csv.reader(csvfile, delimiter=',', quotechar="'")
    csvreader.next()
    for row in csvreader:
	insertBin(cursor, 'Default', row[EXT_FUL_LOC_ID], row[FULFILLER_ID],
                  'General', 'Pickable')

conn.commit()
conn.close()