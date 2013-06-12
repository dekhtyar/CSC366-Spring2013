#@param: fulfillerID, LocationName, dictionary w/ the refreshItem type
import MySQLdb
def adjustInventory(fulfillerID, fulfillerLocationID, refreshItems, db):
   cursor = db.cursor()
   
   try:
       #sql
       #Can't select the table we are modding, so get the SKU here
       #even though a sku is passed in, this one ensure the SKU at the BinID given is used
       return_string_list = []
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
                     SET OnHand = OnHand + %s  
                     WHERE FulfillerID = %s  AND FulfillerLocationId = %s
                     AND SKU = %s AND Name = %s"""
         # print sqlCommand
         cursor.execute(sqlCommand, (item["Quantity"], fulfillerID, fulfillerLocationID, item["partnumber"], item["BinId"])
)
         db.commit() #might only need to commit once

         # Get the new value
         sqlQuery = """SELECT OnHand FROM StoredIn 
                       WHERE FulfillerID = %s AND FulfillerLocationId = %s
                       AND SKU = %s AND Name = %s"""
         cursor.execute(sqlQuery, (fulfillerID, fulfillerLocationID,
                                   item["partnumber"], item["BinId"]))
         updated_value = cursor.fetchone()[0]

         return_string = "successfully adjusted %s in (%s, %s) to %s" % (item['partnumber'], 
                                                                         fulfillerLocationID,
                                                                         item['BinId'],
                                                                         updated_value)
         return_string_list.append(return_string)
       return '\n'.join(return_string_list)
   except Exception, e:
      print sqlCommand
      print "adjustInventory: something went wrong %s" % e
      return "something went wrong %s" % e
      db.rollback()


#def adjustInventory(fulfillerID, fulfillerLocationID, refreshItems, db):
#i = {"Quantity" : 919, "partnumber" : "0000001595", "BinId":"'Default'"}

#For Testing
#ii = {"Quantity" : -900, "partnumber" : "0000001595", "BinId":"'Default'"}
#refitems = [ii]
#adjustInventory(69170, 440001, refitems, MySQLdb.connect("localhost", "root", "busmajorz", "inventory"))
