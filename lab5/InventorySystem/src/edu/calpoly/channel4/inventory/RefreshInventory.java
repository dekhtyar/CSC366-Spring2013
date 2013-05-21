package edu.calpoly.channel4.inventory;

import edu.calpoly.channel4.bulk.bean.InventoryBean;
import edu.calpoly.channel4.db.DbConnect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RefreshInventory {

	public static void refreshInventory(InventoryBean inv, Connection conn) throws Exception {
	
		Statement s = null;
		try {
			s = conn.createStatement();
		} catch (SQLException e) {
			System.err.println("Couldn't create statement");
			e.printStackTrace();
		}
		
		/* Insert into Inventory table */
		String sql;
		sql =	"INSERT INTO Inventory (" +
				"SKU, FulfillerID, UPC) " +
				"VALUES (" +
				inv.getSKU() + ", " +
				"(SELECT FulfillerID FROM Locations " +
                "WHERE InternalFulfillerLocationID = " + inv.getIntFulfillerLocId() + "), " +
				inv.getUPC() + 
				");";
		try {
			s.executeUpdate(sql);
		} catch (SQLException e) {
			if (!e.getMessage().contains("Duplicate")){
			System.err.println("Couldn't INSERT record into Inventory table.");
			e.printStackTrace();
			return;
			}
		}
		
		/* Insert into Stocks table */
		sql = 	"INSERT INTO Stocks " +
				"(FulfillerID, ExtFulfillerID, SKU, UPC, " +
				"LTD, SafetyStock) VALUES (" +
				"(SELECT FulfillerID FROM Locations " +
                "WHERE InternalFulfillerLocationID = " + inv.getIntFulfillerLocId() + "), " +
				inv.getExtFulfillerLocId() + ", " +
				inv.getSKU() + ", " +
				inv.getUPC() + ", " +
				inv.getLTD() + ", " +
				inv.getSafetyStock() + 
				");";
		
		try {
			s.executeUpdate(sql);
		} catch (SQLException e) {
			if (!e.getMessage().contains("Duplicate")){
			System.err.println("Couldn't INSERT record into Stocks table.");
			e.printStackTrace();
			return;
			}
		}
		
		/* Insert into Holds table */
		sql = 	"INSERT INTO Holds (BinID, SKU, UPC, Allocation, " +
				"OnHand) VALUES (" +
				"(SELECT BinID FROM Bins " +
				"WHERE Name = " +
				inv.getBinName() +
				" AND ExtFulfillerID = " +
				inv.getExtFulfillerLocId() +
				" AND FulfillerID = " +
				"(SELECT FulfillerID FROM Locations " +
                "WHERE InternalFulfillerLocationID = " + inv.getIntFulfillerLocId() + ") " +
				"), " +
				inv.getSKU() + ", " +
				inv.getUPC() + ", " +
				"0, " +
				inv.getOnHand() +
				");";
		
		try {
			s.executeUpdate(sql);
		} 
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){
			//System.out.println("Bad Bin: " + sql);
			
			throw new Exception("BinID");
		}
		catch (SQLException e) {
			System.err.println("Couldn't INSERT record into Holds table.");
			e.printStackTrace();
			return;
		}	
	}
}