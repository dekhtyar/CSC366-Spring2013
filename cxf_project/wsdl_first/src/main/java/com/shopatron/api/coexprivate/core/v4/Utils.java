package com.shopatron.api.coexprivate.core.v4;

import java.util.ArrayList;
import java.math.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utils{
   
   /**
      Gets a SUK by a UPC and FulfillerID
      @author Doug
   */
   public static String getSKUByUPCAndFulfiller (String UPC, BigInteger fulfillerID){
      Connection conn = DbConnect.getConnection();
      String SKU = null;      
      String query = "Select SKU from Inventory where FulfillerID = " + fulfillerID + " and UPC = '" + UPC + "';";

      try {
      	Statement s = conn.createStatement();
      	ResultSet result = s.executeQuery(query);
      	if (result.first()){
            SKU = result.getString("SKU");
         }
         else{
            System.out.println("No SKU found");
         }
      }
      catch (SQLException e) {
        System.err.println("getSKUByUPCAndFulfiller");
      	System.err.println(e.getMessage());
      	return null;
      }
      
      return SKU;
   }
   
   
   public static boolean isItemInBin(BigInteger binID, String SKU){
      Connection conn = DbConnect.getConnection();
   
      
      String query = "Select BinID from Holds where BinID = " + binID + " and SKU = '" + SKU + "';"; 
  
      try {
      	Statement s = conn.createStatement();
      	ResultSet result = s.executeQuery(query);
      	
      	return result.first();
   	         
      }
      catch (SQLException e) {
        System.err.println("isItemInBin");
      	System.err.println(e.getMessage());
      	return false;
      }

   }
   
   /**
      Determines if an item (SKU) is available at a specific bin (binID).
      
      An available item is one that:
      - has a positive quantity (a quantity that will be added to the bin)
      - the quantity available (onhand - requested) is more than quantity requested (negative quantity) 
   */
   public static boolean isItemAvailableInBin(BigInteger binID, String SKU, int quantity){
      Connection conn = DbConnect.getConnection();
      int allocated, onhand;
      
      String query = "Select Allocation, OnHand from Holds where BinID = " + binID + " and SKU = '" + SKU + "';"; 
  
      try {
      	Statement s = conn.createStatement();
      	//System.out.println("Query: " + query);
      	ResultSet result = s.executeQuery(query);
      	
      	if (result.next()){
            allocated = result.getInt("Allocation");
            onhand = result.getInt("OnHand");
            
            return (quantity > 0 || ((onhand - allocated) >= Math.abs(quantity)));
               
      	}
      	else{
         	return false;
      	}
   	         
      }
      catch (SQLException e) {
        System.err.println("isItemAvailableInBin");
      	System.err.println(e.getMessage());
      	return false;
      }
            
   }
   
   public static BigInteger bigInt(int value){
      return BigInteger.valueOf(value);
   }

   public static boolean isItemAvailableForAllocation(BigInteger binID, String SKU, int quantity) {
      if (quantity < 0) {
         return false;
      }
      if (quantity == 0) {
         return true;
      }

      Connection conn = DbConnect.getConnection();

      String query = "Select Allocation from Holds where BinID = " + binID + " and SKU = '" + SKU + "' and onHand - Allocation >= " + quantity + ";";

      try {
         Statement s = conn.createStatement();
         ResultSet bins = s.executeQuery(query);
      
         return bins.next(); // return true if there was any bin returned
      }
      catch (SQLException e) {
         System.err.println("isItemAvailableForAllocation");
         System.err.println(e.getMessage());
         return false;
      }
   }

   
   public static void doUpdateQuery(String query) throws SQLException{
      Connection conn = DbConnect.getConnection();
      Statement s = conn.createStatement();
      s.executeUpdate(query);
   }
   
   public static ResultSet doSelectQuery(String query) throws SQLException{
      Connection conn = DbConnect.getConnection();
      Statement s = conn.createStatement();
      return s.executeQuery(query);
   }

	/**
	   Gets a BinID by a fulfillerID and externalLocationID
	   @author Kevin
	 */
	public static int getBinIDByExtLocIDAndFulfiller(String extLocID, BigInteger fulfillerID){
		Connection conn = DbConnect.getConnection();
		int BinID;
      
      String query = "Select BinID from Bins where FulfillerID = " + fulfillerID + " and EXTFULFILLERID = '" + extLocID + "';";
      
      try {
      	Statement s = conn.createStatement();
      	ResultSet result = s.executeQuery(query);
			if(result.next()){
				BinID = result.getInt("BinID");
				System.out.println("query ok, bin id is: " + BinID);
      
			}else{
				BinID = 0;
			}
		} 
		catch (SQLException e) {
			System.out.println("query not ok...");
      	System.err.println(e.getMessage());
      	return 0;
      }
      
      return BinID;
	}
}

