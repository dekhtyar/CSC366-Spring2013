package com.shopatron.api.coexprivate.core.v4;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.math.BigInteger;

public class GetInventoryByLocations{
   private BigInteger fulfiller;
   private BigInteger catID;
   private BigInteger manID;
   private ArrayList<InventoryResponse> responses;
   
      
      
   public GetInventoryByLocations(InventoryRequest request){
      this.fulfiller = request.getFulfillerID();
      int limit = request.getLimit();
      List<String> locations = request.getLocationIDs().getExternalLocationID();
      List<ItemQuantity> items = request.getQuantities().getItems();
      this.catID = request.getCatalog().getCatalogID();
      this.manID = request.getCatalog().getManufacturerID();
      boolean ignoreSS = request.isIgnoreSafetyStock(); 
      boolean includeNegative = request.isIncludeNegativeInventory();
      boolean orderByLTD = request.isOrderByLTD();
      
      this.responses = new ArrayList<InventoryResponse>();
      
      for (String extfulfiller: locations){
         if (doesServe (fulfiller, catID, manID, extfulfiller)){
            if (items.size() == 0){
               System.out.println("No items");
            }
            for (ItemQuantity item : items){
               //Get SKU
               String sku = Utils.getSKUByUPCAndFulfiller (item.getUPC(), fulfiller);
               if (sku != null){
                  //Check if location stocks this SKU               
                  ResultSet stockResult = doesStock(fulfiller, extfulfiller, sku);
                  
                  //If this location stocks the SKU, search for bins at that location that contain the SKU
                  
                  try{
                     if (stockResult != null && stockResult.first()){ 
                        ResultSet binSearchResult = searchBins(fulfiller, extfulfiller, sku, item.getQuantity());
                       
                        if (binSearchResult != null && binSearchResult.first()){
                           makeResponse(binSearchResult, stockResult, includeNegative, ignoreSS, extfulfiller, item.getUPC());                    
                        }

                        
                     }
                     else{
                        System.out.println("Stock result problem");
                     }
                  }
                  catch (SQLException e){
                     System.out.println("Couldn't get first");
                     e.printStackTrace();
                  }
               }
               else{
                  System.out.println("SKU is null");
               }
            }
         }
         else{
            System.out.println("Doesn't serve. Cat: " + catID + " Man: " + manID + " Ext: " + extfulfiller + " Ful  " + fulfiller);
         }
      }
   }
   
   public List<InventoryResponse> getResponses(){
      return this.responses;
   }
   
   private void makeResponse(ResultSet binResult, ResultSet stockResult, boolean includeNegative, boolean ignoreSS, String ext, String UPC){
      int ssOverride, LTD, allocation, onHand, available;
      try{
         ssOverride = stockResult.getInt("SafetyStock");
         LTD = stockResult.getInt("LTD");
      }
      catch(SQLException e){
         System.out.println("Couldn't get Safety Stock or LTD from Stock query");
         e.printStackTrace();
         return;
      }
      
      try{
         allocation = binResult.getInt("Allocation");
         onHand = binResult.getInt("OnHand");   
      }  
      catch(SQLException e){
         System.out.println("Couldn't get Safety Stock or LTD from Stock query");
         e.printStackTrace();
         return;
      }
      
      available = onHand - allocation;
      // if there's items available or ignore negative available then create new record of this inventory
      if (available > 0 || includeNegative){
         InventoryResponse response = new InventoryResponse();
         response.setAvailable(available);
         response.setCatalogID(this.catID.intValue());
         response.setLocationName(ext);
         response.setLTD(LTD);
         response.setManufacturerID(this.manID.intValue());
         response.setOnHand(onHand);
         response.setUPC(UPC);
         this.responses.add(response);
      }
      
     
      
   }
   
   private ResultSet searchBins(BigInteger fulfiller, String ext, String SKU, int quantity){
      String query = "Select Allocation, OnHand, b.BinID from Holds h inner join Bins b where EXTFULFILLERID = '" + ext + "' and FulfillerID = " + fulfiller + " and SKU = '" + SKU + "' having OnHand >= " + quantity + ";";
      try{
         return Utils.doSelectQuery(query);
      }
      catch (SQLException e){
         e.printStackTrace();
         System.out.println("Problem searching through bins.");
         System.out.println(query);
         return null;
      }
      
      
   }
   
   private boolean doesServe (BigInteger fulfiller, BigInteger catID, BigInteger manID, String extfulfiller){
      String query = "Select * from Serves where CatID = " + catID + " and ManID = " + manID + " and FulfillerID = " + fulfiller + " and ExtFulfillerID = '" + extfulfiller + "';";
      try{   
         ResultSet s = Utils.doSelectQuery(query);
         return s.first(); //returns true if there is something in the set
      }
      catch(SQLException e){
         System.out.println("Problem querying in doesServe: " + query);
         e.printStackTrace();
         return false;
      } 
   }
   
   private ResultSet doesStock(BigInteger fulfiller, String extfulfiller, String sku){
      String query = "Select LTD, SafetyStock from Stocks where ExtFulfillerID = '" + extfulfiller + "' and FulfillerID = " + fulfiller + " and SKU = '" + sku + "';";
      try{
         return Utils.doSelectQuery(query);
      }
      catch(SQLException e){
         System.out.println("Problem querying in doesStock: " + query);
         e.printStackTrace();
         return null;
      }
   }
   
}