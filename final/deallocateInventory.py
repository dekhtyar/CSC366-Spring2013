import MySQLdb

def deallocateInventory(fulfiller_id, location_id, items, db):
    cur = db.cursor()

