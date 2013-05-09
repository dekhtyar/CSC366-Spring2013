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


INSERT_BIN_STR = ('INSERT INTO Bin(name, ext_ful_loc_id, fulfiller_id, type, '
                  'status) VALUES(%s, %s, %s, %s, %s)')

GET_FULFILLER_ID_STR = ('SELECT fulfiller_id FROM Location WHERE '
                        'int_ful_loc_id = %s')

cursor = conn.cursor()

def insertBin(cursor, name, ext_ful_loc_id, fulfiller_id, typ, status):
    return cursor.execute(INSERT_BIN_STR, (name, ext_ful_loc_id, fulfiller_id, typ, status))

def getFulfillerId(cursor, int_ful_loc_id):
    cursor.execute(GET_FULFILLER_ID_STR, (int_ful_loc_id,))
    return cursor.fetchone()

with open(sys.argv[1], 'r') as csvfile:
    csvreader = csv.reader(csvfile, delimiter=',', quotechar="'")
    csvreader.next()
    for row in csvreader:
        fulfiller_id = getFulfillerId(cursor, row[INT_FUL_LOC_ID])
        if fulfiller_id is not None:
            insertBin(cursor, row[NAME], row[EXT_FUL_LOC_ID], fulfiller_id[0], 
                      row[TYPE], row[STATUS])
        else:
            print('Invalid internal fulfiller location id %s' %
                row[INT_FUL_LOC_ID])

conn.commit()
conn.close()
