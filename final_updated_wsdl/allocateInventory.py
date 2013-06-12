import MySQLdb
from util import *

def itemCanBeAllocated(fulfiller_id, location_id, item, db):
    cur = db.cursor()
    onhand = 0
    allocated = 0

    try:
        cur.execute('''SELECT SafetyStockLimit
                       FROM StoredAt
                       WHERE SKU = %s AND FulfillerId = %s AND
                             FulfillerLocationId = %s''',
                    (item['SKU'], fulfiller_id, location_id))
        safety = cur.fetchone()[0]

        for bin in selectItemBins(fulfiller_id, location_id, item['SKU'], db):
            onhand = onhand + bin['OnHand']
            allocated = allocated + bin['Allocated']

        if item['Quantity'] <= (onhand - allocated - safety):
            return True
    except Exception, e:
        print e
    finally:
        cur.close()

    return False

def allocateFromBin(fulfiller_id, location_id, sku, quantity, name, db):
    cur = db.cursor()

    try:
        cur.execute('''UPDATE StoredIn
                       SET Allocated = Allocated + %s
                       WHERE SKU = %s AND FulfillerId = %s AND
                             FulfillerLocationId = %s AND Name = %s''',
                    (quantity, sku, fulfiller_id, location_id, name))
    except Exception, e:
        print e
    finally:
        cur.close()

def allocateItem(fulfiller_id, location_id, item, db):
    cur = db.cursor()
    quantity = item['Quantity']
    sku = item['SKU']
    bins = selectItemBins(fulfiller_id, location_id, sku, db)

    for bin in bins:
        available = bin['OnHand'] - bin['Allocated']
        name = bin['Name']
        allocation = quantity if available >= quantity else available

        allocateFromBin(fulfiller_id, location_id, sku, allocation, name, db)
        quantity = quantity - allocation
        
        if quantity == 0:
            return

def allocateInventory(fulfiller_id, location_id, man_id, cat_id, items, db):
    if not tenancyCheck(fulfiller_id, location_id, man_id, cat_id, db):
        return False
    
    for item in items:
        if not itemCanBeAllocated(fulfiller_id, location_id, item, db):
            print 'Insufficient available inventory.'
            return False

    for item in items:
        allocateItem(fulfiller_id, location_id, item, db)

    print 'Successfully allocated inventory.'
    return True
