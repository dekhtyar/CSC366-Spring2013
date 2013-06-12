package com.shopatron.api.coexprivate.core.v4;

import java.math.BigInteger;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

public class TrickleInventoryHelper{
	
	public static String refreshInventory(RefreshRequest refRequest){
		
		String query;
        int successCount, newInvCount, failedCount;
		ArrayOfImplRefreshItem itemArray;
        
		itemArray = refRequest.getItems();

		successCount = 0;
		newInvCount = 0;
        failedCount = 0;
		
		/* TODO: if it's not in inventory table, then INSERT into table, set allocation = 0 */
		/* but can not create inventory because we do not know sku ? */
		for (RefreshItem item : itemArray.getItems()){
			query = new StringBuilder()
				.append("update Holds set OnHand = " + item.getQuantity())
				.append(" where sku = '" + item.getPartNumber())
				.append("' and binid = " + item.getBinID())
				.append(" and upc = '" + item.getUPC() + "';")
				.toString();
			
			try {
				Connection conn = DbConnect.getConnection();
				Statement s = conn.createStatement();
				int result = s.executeUpdate(query);
				if(result == 0){
                    if (TrickleInventoryHelper.insertNewInventory(item, refRequest.getExternalLocationID(), refRequest.getFulfillerID()) > 0) {
                        newInvCount++;
                    }
                    else {
                        failedCount++;
                    }
				}
                else {
                   successCount++;
				   System.out.println("Set OnHand = " + item.getQuantity() + " for bin: " + item.getBinID() + " upc: " + item.getUPC() + " and sku: " + item.getPartNumber());	
				}
				
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				failedCount++;
			}
		}
		return ("Updated " + successCount + " item(s).\n" + "Inserted " + newInvCount + " item(s).\n" + "Failed Operations: " + failedCount);
	}
	
	
    public static int insertNewInventory(RefreshItem item, String ExtLocID, BigInteger fulfillerID) {
        
        Connection conn = DbConnect.getConnection();
		
        /* We currently assume product exists in products table.
           We can't add a row to the products table because 
           the CatID and ManID are not specified. */
        
		try {
            String sql;
            Statement s = conn.createStatement();
			conn.setAutoCommit(false);
			
			/* Insert into Inventory table */
			sql =	"INSERT INTO Inventory (" +
					"SKU, FulfillerID, UPC) " +
					"VALUES ('" +
					item.getPartNumber() + "', " +
					fulfillerID + ", '" +
					item.getUPC() + 
					"');";
			s.executeUpdate(sql);
	
			/* Insert into Holds table */
			sql = 	"INSERT INTO Holds (BinID, SKU, UPC, Allocation, " +
					"OnHand) VALUES (" +
					item.getBinID() + ", '" +
					item.getPartNumber() + "', '" +
					item.getUPC() + "', " +
					"0, " +
					item.getQuantity() +
					");";
	
			s.executeUpdate(sql);
			
			/* Insert into Stocks table */
			sql = 	"INSERT INTO Stocks " +
					"(FulfillerID, ExtFulfillerID, SKU, UPC, " +
					"LTD, SafetyStock) VALUES (" +
					fulfillerID + ", '" +
					ExtLocID + "', '" +
					item.getPartNumber() + "', '" +
					item.getUPC() + "', " +
					item.getLTD() + ", " +
					item.getSafetyStock() + 
					");";
	
			s.executeUpdate(sql);
	
			conn.commit();
	
		} 
        catch (SQLException e) {
			
			try {
                if (e.getMessage().contains("REFERENCES `Products` (`UPC`)")) {
                    System.err.println("Cannot create new Product with UPC " + item.getUPC() + ". Catalog ID and Manufacturer ID not specified");
                }
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				System.err.println("Error with rollback after failed transaction");	
				return 0;
			}
			return 0;
		}
		
		return 1;
	}
	
	public static String adjustInventory(AdjustRequest adjRequest){
	
		String query;
		int successCount;
		
		ArrayOfImplAdjustItem itemArray = new ArrayOfImplAdjustItem();
		
		String extLocID = adjRequest.getExternalLocationID();
		BigInteger fulfillerID = adjRequest.getFulfillerID();
		itemArray = adjRequest.getItems();
		successCount = 0;
		
		for (AdjustItem item : itemArray.getItems()){
			query = new StringBuilder()
			.append("update Holds set OnHand = OnHand + " + item.getQuantity())
			.append(" where sku = '" + Utils.getSKUByUPCAndFulfiller(item.getUPC(), fulfillerID))
			.append("' and binid = " + item.getBinID())
			.append(" and upc = '" + item.getUPC() + "';")
			.toString();
			
			
			try {
				Connection conn = DbConnect.getConnection();
				Statement s = conn.createStatement();
				s.executeUpdate(query);
				System.out.println("Adjust onhand for bin: " + item.getBinID() + " upc: " + item.getUPC() + " and sku: " + Utils.getSKUByUPCAndFulfiller(item.getUPC(), fulfillerID) + " by " + item.getQuantity());
				successCount++;
				
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				return "failed adjust inventory operation";
			}
			
			
		}
		
		
		
		return ("successful with " + successCount + " item(s)\n");
			
			
	}
	
}