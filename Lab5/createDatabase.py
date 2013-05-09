import csv
import mysql.connector
import sys
import os
import re

conn = mysql.connector.connect(user='root', database='inventory')
cursor = conn.cursor()

with open('DB-setup.sql', 'r') as create_db:
    for query in create_db.read().split(';')[:-1]:
        cursor.execute(query)

conn.close()
