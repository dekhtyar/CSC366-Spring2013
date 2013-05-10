# API functions

import MySQLdb

# Stephen
def createFulfiller():
    pass

# Kevin
def createFulfillmentLocation():
    pass

# Luke
def createManufacturerCatalog():
    pass

# Alex S.
def createBin():
    pass

# Alex B.
def refreshInventory(fulfiller_id, location_id, item):
    sql_stored_at_where = 'WHERE UPC = %s AND FulfillerId = %s AND FulfillerLocationId = %s'
    sql_stored_in_where = sql_stored_at_where+' AND Name = %s' 
    args_stored_at_where = (item['upc'], fulfiller_id, location_id)
    args_stored_in_where = args_stored_at_where + (item['binname'],)

    con = MySQLdb.connect('localhost', 'root', 'busmajorz', 'inventory')
    cur = con.cursor()

    # Update 'Items' table
    cur.execute('SELECT UPC FROM Items WHERE UPC = %s', (item['upc'],)) 
    if not cur.fetchone():
        cur.execute('INSERT INTO Items VALUES (%s, %s)',
                    (item['upc'], item['name']))
    else:
        cur.execute('UPDATE Items SET Name = %s WHERE UPC = %s',
                    (item['name'], item['upc']))

    # Update 'StoredIn' table
    cur.execute('SELECT UPC FROM StoredIn '+sql_stored_in_where,
                args_stored_in_where)
    if not cur.fetchone():
        cur.execute('INSERT INTO StoredIn VALUES (%s, %s, %s, %s, %s, %s)',
                    args_stored_in_where+(item['quantity'], 0))
    else:
        cur.execute('UPDATE StoredIn SET OnHand = %s ' + sql_stored_in_where,
                    (item['quantity'],)+args_stored_in_where)

    # Update 'StoredAt' table
    cur.execute('SELECT UPC FROM StoredAt '+sql_stored_at_where,
                args_stored_at_where)
    if not cur.fetchone():
        cur.execute('INSERT INTO StoredAt VALUES (%s, %s, %s, %s, %s)',
                    args_stored_at_where+(item['ltd'], item['safety']))
    else:
        cur.execute('UPDATE StoredAt SET LTD = %s, SafetyStockLimit = %s '+
                    sql_stored_at_where,
                    (item['ltd'], item['safety'])+args_stored_at_where)

    con.close()
