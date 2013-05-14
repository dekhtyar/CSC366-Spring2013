import csv
import mysql.connector
import sys
import os
import re

conn = mysql.connector.connect(user='root')
cursor = conn.cursor()

with open('DB-clean.sql', 'r') as create_db:
    for query in create_db.read().split(';')[:-1]:
        cursor.execute(query)

conn.close()
