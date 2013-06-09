import MySQLdb

def selectItemBins(fulfiller_id, location_id, sku, db):
    cur = db.cur()

    try:
        cur.execute('SELECT OnHand, Allocated, Name ' +
                    'FROM StoredIn WHERE SKU = %s AND FulfillerId = %s AND FulfillerLocationId = %s',
                    (sku, fulfiller_id, location_id))
        return cur.fetchall()
    except Exception, e:
        print e
        return () 
    finally:
        cur.close()

def itemCanBeAllocated(fulfuller_id, location_id, item, db):
    cur = db.cur()
    onhand = 0

    try:
        cur.execute('SELECT SafetyStockLimit FROM StoredAt WHERE SKU = %s AND FulfillerId = %s AND FulfillerLocationId = %s',
                    (item['SKU'], fulfiller_id, location_id))
        safety = cur.fetchone()

        for bin in selectItemBins(fulfiller_id, location_id, item['SKU'], db):
            onhand = onhand + bin[0] 

        if item['quantity'] <= (onhand - safety):
            return True
    except Exception, e:
        print e
    finally:
        cur.close()

    return False

def allocateFromBin(fulfiller_id, location_id, sku, quantity, name, db):
    cur = db.cursor()

    try:
        cur.execute('UPDATE StoredIn' +
                    'SET OnHand = OnHand - %s, Allocated = Allocated + %s' +
                    'WHERE SKU = %s AND FulfillerId = %s AND FulfillerLocationId = %s AND Name = %s',
                    (quantity, quantity, item['SKU'], fulfiller_id, location_id, name))
    except Exception, e:
        print e
    finally:
        cur.close()

def allocateItem(fulfiller_id, location_id, item, db):
    cur = db.cur()
    quantity = item['quantity']
    bins = selectItemBins(fulfiller_id, location_id, sku, db)

    for bin in bins:
        onhand = bin[0]
        name = bin[2]
        allocation = quantity if onhand >= quantity else onhand

        allocateFromBin(fulfiller_id, location_id, sku, allocation, name, db)
        quantity = quantity - allocation
        
        if quantity == 0:
            return

def allocateInventory(fulfiller_id, catalog_id, location_id, items, db):
    cur = db.cur()

    for item in items:
        if not itemCanBeAllocated(fulfiller_id, location_id, item, db):
            print 'Insufficient available inventory. Aborting...'
            return

    for item in items:
        allocateItem(fulfiller_id, location_id, item, db)


