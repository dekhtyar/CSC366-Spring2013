import MySQLdb
from util import *

def itemCanBeDeallocated(fulfiller_id, location_id, item, db):
    cur = db.cursor()
    allocated = 0

    try:
        for bin in selectItemBins(fulfiller_id, location_id, item['SKU'], db):
            allocated = allocated + bin['Allocated']

        if item['Quantity'] <= allocated:
            return True
    except Exception, e:
        print e
    finally:
        cur.close()

    return False

def deallocateFromBin(fulfiller_id, location_id, sku, quantity, name, db):
    cur = db.cursor()

    try:
        cur.execute('''UPDATE StoredIn
                       SET Allocated = Allocated - %s
                       WHERE SKU = %s AND FulfillerId = %s AND
                             FulfillerLocationId = %s AND Name = %s''',
                    (quantity, sku, fulfiller_id, location_id, name))
    except Exception, e:
        print e
    finally:
        cur.close()

def deallocateItem(fulfiller_id, location_id, item, db):
    cur = db.cursor()
    quantity = item['Quantity']
    sku = item['SKU']
    bins = selectItemBins(fulfiller_id, location_id, sku, db)

    for bin in bins:
        allocated = bin['Allocated']
        name = bin['Name']
        deallocation = quantity if allocated >= quantity else allocated

        deallocateFromBin(fulfiller_id, location_id, sku, deallocation, name, db)
        quantity = quantity - deallocation
        
        if quantity == 0:
            return

def deallocateInventory(fulfiller_id, location_id, man_id, cat_id, items, db):
    if not tenancyCheck(fulfiller_id, location_id, man_id, cat_id, db):
        return False
    
    for item in items:
        if not itemCanBeDeallocated(fulfiller_id, location_id, item, db):
            print 'Insufficient allocated inventory.'
            return False

    for item in items:
        deallocateItem(fulfiller_id, location_id, item, db)

    print 'Successfully deallocated inventory.'
    return True
