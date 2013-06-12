package com.shopatron.api.coexprivate.core.v4;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.math.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DeallocateHelper {
   private static final Logger LOG = Logger.getLogger(DeallocateHelper.class.getName());

   public static int deallocateInventory(UpdateRequest updateRequest){
      BigInteger fulfillerID = updateRequest.getFulfillerID();
      
      FulfillmentLocationCatalog fulfillmentLocationCatalog = updateRequest.getFulfillerLocationCatalog();
      
      ArrayOfImplUpdateItem items = updateRequest.getItems();
      
      String externalLocationID = fulfillmentLocationCatalog.getExternalLocationID();
      
      for (UpdateItem item : items.getItems()){
         int quantity = item.getQuantity();
         String UPC = item.getUPC();
         String SKU = item.getPartNumber();
         String query;
         
         BigInteger binID = findFirstAvailableBin(externalLocationID, fulfillerID, UPC, SKU, quantity);
         if (binID.compareTo(BigInteger.ZERO) > 0){
            Connection conn = DbConnect.getConnection();
            
            int subt = -1 * Math.abs(quantity);
            
            query = new StringBuilder()
            .append ("update Holds set Allocation = (case when ((Allocation + " + subt + ") <= 0) then 0 else Allocation + " + subt + " end)")
            .append(" where BinID = " + binID + " and SKU = '" + SKU + "';")
            .toString();
             System.out.println("Deallocating " + quantity + " item(s) with UPC " + SKU + " in bin # " + binID);
             System.out.println(query);
            try {
               Statement s = conn.createStatement();
            	s.executeUpdate(query);
            }
            catch (SQLException e) {
            	System.err.println(e.getMessage());
            	return -1;
            }
         }
         else{
            System.out.println("No available bin for " + UPC + " at fulfiller " + fulfillerID);
         }
      }
      return -1;
   }
   
   public static BigInteger findFirstAvailableBin(String external, BigInteger fulfiller, String UPC, String SKU, int quantity){
      ArrayOfImplBin bins;
      BinResponse binResponse;
   
      BinRequest binRequest = new BinRequest();
      binRequest.setExternalLocationID(external);
      binRequest.setFulfillerID(fulfiller);
      
      //System.out.println("Getting First Available Bin for external " + external + " and fulfiller " + fulfiller);
      
      binResponse = GetBinsHelper.getBins(binRequest);
      bins = binResponse.getBins();
      
      for (Bin bin : bins.getItems()){
         BigInteger binID = bin.getBinID();
         
         if (Utils.isItemInBin(binID, SKU)){
            return binID;
         }
      }
      
      return BigInteger.ZERO;   
   }
 
}