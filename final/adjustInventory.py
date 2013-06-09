#@param: fulfillerID, LocationName, dictionary w/ the refreshItem type
import MySQLdb
def adjustInventory(fulfillerID, fulfillerLocationID, refreshItems, db):
   cursor = db.cursor()
   
   try:
       #sql
       #Can't select the table we are modding, so get the SKU here
       #even though a sku is passed in, this one ensure the SKU at the BinID given is used
       for item in refreshItems:
         # skuQuery = "SELECT fb.SKU from FulfilledBy fb, StoredIn si,
         #            Bins b
         #            WHERE si.SKU = fb.SKU AND fb.UPC = %s AND fb.FulfillerId 
         #            = si.FulfillerId AND b.FulfillerId = fb.FulfillerId
         #            AND b.Name = %s AND b.FulfillerLocationId = 
         #            si.FulfillerLocationId" % (item["UPC"], item["BinID"])
         # cursor.execute(skuQuery)
         # SKU = cursor.fetchone() #returns a tuple with one element
         # res = cursor.fetchall() #have to fetchall before doing another query
          #run the update query
         sqlCommand = """UPDATE StoredIn 
                     SET OnHand = OnHand + %d  
                     WHERE FulfillerID = %s  AND FulfillerLocationId = %s
                     AND SKU = %s AND Name = %s""" % (item["Quantity"], fulfillerID, fulfillerLocationID, item["partnumber"], item["BinId"])
         print sqlCommand
         cursor.execute(sqlCommand)
         db.commit() #might only need to commit once
   except Exception, e:
      print "something went wrong %s" % e
      db.rollback()
   return "%s successfully updated" % (fulfillerLocationID)


#def adjustInventory(fulfillerID, fulfillerLocationID, refreshItems, db):
#i = {"Quantity" : 919, "partnumber" : "0000001595", "BinId":"'Default'"}

#For Testing
#ii = {"Quantity" : -900, "partnumber" : "0000001595", "BinId":"'Default'"}
#refitems = [ii]
#adjustInventory(69170, 440001, refitems, MySQLdb.connect("localhost", "root", "busmajorz", "inventory"))
