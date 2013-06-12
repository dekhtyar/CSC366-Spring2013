import MySQLdb

def tenancyCheck(fulfiller_id, location_id, man_id, cat_id, db):
    cur = db.cursor()

    try:
        cur.execute('''SELECT *
                       FROM SubscribeTo
                       WHERE FulfillerId = %s AND FulfillerLocationId = %s AND
                             ManufacturerId = %s AND CatalogueId = %s''',
                    (fulfiller_id, location_id, man_id, cat_id))
        if cur.fetchone() is None:
            print 'ManufacturerId/CatalogId check failed for location.'
            return False
    except Exception, e:
        print e
        return False
    finally:
        cur.close()

    return True

def selectItemBins(fulfiller_id, location_id, sku, db):
    cur = db.cursor(MySQLdb.cursors.DictCursor)

    try:
        cur.execute('''SELECT OnHand, Allocated, Name
                       FROM StoredIn
                       WHERE SKU = %s AND FulfillerId = %s AND
                             FulfillerLocationId = %s''',
                    (sku, fulfiller_id, location_id))
        return cur.fetchall()
    except Exception, e:
        print e
        return () 
    finally:
        cur.close()
