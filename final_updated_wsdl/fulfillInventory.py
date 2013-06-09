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

def itemCanBeFulfilled(fulfiller_id, location_id, item, db):
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

def fulfillFromBin(fulfiller_id, location_id, sku, quantity, name, db):
    cur = db.cursor()

    try:
        cur.execute('UPDATE StoredIn ' +
                    'SET Allocated = Allocated - %s ' +
                    'WHERE SKU = %s AND FulfillerId = %s AND FulfillerLocationId = %s AND Name = %s',
                    (quantity, sku, fulfiller_id, location_id, name))
    except Exception, e:
        print e
    finally:
        cur.close()

def fulfillItem(fulfiller_id, location_id, item, db):
    cur = db.cursor()
    quantity = item['Quantity']
    sku = item['SKU']
    bins = selectItemBins(fulfiller_id, location_id, sku, db)

    for bin in bins:
        allocated = bin['Allocated']
        name = bin['Name']
        fulfillment = quantity if allocated >= quantity else allocated

        fulfillFromBin(fulfiller_id, location_id, sku, fulfillment, name, db)
        quantity = quantity - fulfillment
        
        if quantity == 0:
            return

def fulfillInventory(fulfiller_id, location_id, items, db):
    cur = db.cursor()

    for item in items:
        if not itemCanBeFulfilled(fulfiller_id, location_id, item, db):
            print 'Insufficient allocated inventory. Aborting...'
            return

    for item in items:
        fulfillItem(fulfiller_id, location_id, item, db)

