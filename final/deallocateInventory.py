import MySQLdb

def deallocateInventory(fulfiller_id, catalog_id, location_id, items, db):
    cur = db.cursor()

