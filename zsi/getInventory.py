import mysql.connector
import os

def getConnection():
   return mysql.connector.connect(user='root', database='inventory', 
                                  password=os.environ.get('MYSQL_PASS'))
def getInventory (response, fulID, catID, manID):
   cur = getConnection().cursor()
