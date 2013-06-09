import MySQLdb

def fulfillInventory(fulfiller_id, location_id, items, db):
    cur = db.cursor()
