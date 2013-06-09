import MySQLdb

def selectItemBins(fulfiller_id, location_id, sku, db):
    cur = db.cursor(MySQLdb.cursors.DictCursor)

    try:
        cur.execute('SELECT OnHand, Allocated, Name ' +
                    'FROM StoredIn ' +
                    'WHERE SKU = %s AND FulfillerId = %s AND FulfillerLocationId = %s',
                    (sku, fulfiller_id, location_id))
        return cur.fetchall()
    except Exception, e:
        print e
        return () 
    finally:
        cur.close()

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
        cur.execute('UPDATE StoredIn ' +
                    'SET OnHand = OnHand + %s, Allocated = Allocated - %s ' +
                    'WHERE SKU = %s AND FulfillerId = %s AND FulfillerLocationId = %s AND Name = %s',
                    (quantity, quantity, sku, fulfiller_id, location_id, name))
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

def deallocateInventory(fulfiller_id, location_id, items, db):
    cur = db.cursor()

    for item in items:
        if not itemCanBeDeallocated(fulfiller_id, location_id, item, db):
            print 'Insufficient allocated inventory. Aborting...'
            return

    for item in items:
        deallocateItem(fulfiller_id, location_id, item, db)
