import csv
import mysql.connector
import sys
import os

EXT_FUL_LOC_ID = 0
INT_FUL_LOC_ID = 1
NAME           = 2
TYPE           = 3
STATUS         = 4

conn = mysql.connector.connect(user='root', database='inventory',
        password=os.environ.get('MYSQL_PASS'))


INSERT_BIN_STR = ('INSERT INTO Bin(name, fulfiller_id, ext_ful_loc_id, type, '
                  'status) VALUES(?, ?, ?, ?, ?)')

GET_FULFILLER_ID_STR = ('SELECT fulfiller_id FROM Location WHERE '
                        'int_ful_loc_id = ?')

cursor = conn.cursor()

def insertBin(cursor, name, fulfiller_id, ext_ful_loc_id, typ, status):
    return cursor.execute(INSERT_BIN_STR, name, fulfiller_id,
                          ext_ful_loc_id, typ, status)

def getFulfillerId(cursor, int_ful_loc_id):
    cursor.execute(GET_FULFILLER_ID_STR, int_ful_loc_id)
    return cursor.fetchone()

with open(sys.argv[1], 'r') as csvfile:
    csvreader = csv.reader(csvfile, delimiter=',')
    for row in csvreader:
        fulfiller_id = getFulfillerId(cursor, row[INT_FUL_LOC_ID])
        if fulfiller_id is not None:
            insertBin(cursor, row[NAME], fulfiller_id, row[EXT_FUL_LOC_ID],
                row[TYPE], row[STATUS])
        else:
            print('Invalid internal fulfiller location id %s' %
                row[INT_FUL_LOC_ID])
