package com.shopatron.api.coexprivate.core.v4;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.math.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AllocateInventoryHelper {
   private static final Logger LOG = Logger.getLogger(AllocateInventoryHelper.class.getName());

   public static int allocateInventory(UpdateRequest updateRequest){
      
      if (updateRequest == null) {
         return -1;
      }

      BigInteger fulfillerID = updateRequest.getFulfillerID();
      FulfillmentLocationCatalog fulfillmentLocationCatalog = updateRequest.getFulfillerLocationCatalog();
      ArrayOfImplUpdateItem items = updateRequest.getItems();
      String externalLocationID = fulfillmentLocationCatalog.getExternalLocationID();

      int retVal = 1;

      for (UpdateItem item : items.getItems()) { 
         int quantity = item.getQuantity();
         String UPC = item.getUPC();
         String SKU = item.getPartNumber();
         String itemExternalLocationID = item.getExternalLocationID();

         //  make sure the location fulfills the catalog
         if (itemExternalLocationID.equals(externalLocationID)) {
            String query;
            BigInteger binID = findFirstAvailableBin(externalLocationID, fulfillerID, SKU, quantity);
            
            if (binID.compareTo(BigInteger.ZERO) > 0) {
               Connection conn = DbConnect.getConnection();
               
               query = new StringBuilder()
                        .append ("update Holds set Allocation = Allocation + " + Math.abs(quantity))
                        .append(" where BinID = " + binID + " and SKU = '" + SKU + "';")
                        .toString();
                System.out.println("Allocating: " + query);
             
               try {
                  Statement s = conn.createStatement();
            	   s.executeUpdate(query);
               }
               catch (SQLException e) {
               	System.err.println(e.getMessage());
            	   retVal = -1;
               }
            }
            else {
               System.out.println("No available bin for " + UPC + " at fulfiller " + fulfillerID);
               retVal = -1;
            }
         }
         // location doesn't fulfill catalog
         else {
            retVal = -1;
         }
      }

      return retVal;
   }
   
   public static BigInteger findFirstAvailableBin(String externalLocationID,
         BigInteger fulfillerID, String SKU, int quantity) {

      ArrayOfImplBin bins;
      BinResponse binResponse;
   
      BinRequest binRequest = new BinRequest();
      binRequest.setExternalLocationID(externalLocationID);
      binRequest.setFulfillerID(fulfillerID);
      
      System.out.println("Getting First Available Bin for external " + externalLocationID + 
            " and fulfiller " + fulfillerID);
      
      binResponse = GetBinsHelper.getBins(binRequest);
      bins = binResponse.getBins();
      
      for (Bin bin : bins.getItems()){
         BigInteger binID = bin.getBinID();
         
         if (Utils.isItemAvailableForAllocation(binID, SKU, quantity)){
            return binID;
         }
      }
      
      return BigInteger.ZERO;   
   }
 
}
