#@param: fulfillerID, LocationName, dictionary w/ the refreshItem type

def adjustInventory(fulfillerID, fulfillerLocationID, refreshItems, db):
   cursor = db.cursor()
   
   try:
       #sql
       #Can't select the table we are modding, so get the SKU here
       skuQuery = "SELECT fb.SKU from FulfilledBy fb, StoredIn si,
                     Bins b
                     WHERE si.SKU = fb.SKU AND fb.UPC = %s AND fb.fulfillerId 
                     = si.fulfillerId AND b.FulfillerId = fb.fulfillerId
                     AND b.Name = %s AND b.FulfillerLocationId = 
                     si.FulillerLocationId" % (refreshItems[UPC], refreshItems[BinID])
       cursor.execute(skuQuery)
       SKU = cursor.fetchone() #returns a tuple with one element
       #run the update query
       sqlCommand = "UPDATE TABLE StoredIn 
                     SET OnHand = OnHand + %d  
                     WHERE FulfillerID = %s  AND FulfillerLocationId = %s
                     AND SKU = %s
                     % (refreshItems[Quantity], fulfillerID, fulfillerLocationID, SKU)
      cursor.execute(sqlCommand)
      db.commit()
   except Exception, e:
      print e
      db.rollback()
   return "%s at %s at bin %s successfully updated" % 
    (refreshItems[UPC], LocationName, refreshItems[BinID])