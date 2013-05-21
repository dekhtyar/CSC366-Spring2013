package edu.calpoly.channel4.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOps{
	
	private static String [] tables = {"Locations", "Clients", "Serves", "Bins", "Products", "Inventory", "Holds", "Stocks"};
	
	public static Statement createStatement(Connection connection){
		Statement s;
		try {
			s = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Couldn't create connection");
			e.printStackTrace();
			return null;
		}
		return s;
	}
	
	public static void createDatabase(Connection connection){
		Statement s = null;
		s = createStatement(connection);
		
		String query;
		query = "Create Table Locations(" +
				"ExtFulfillerID VARCHAR(15)," +
				"FulfillerID INT," +
				"InternalFulfillerLocationID INT," +
				"Name VARCHAR(100)," +
				"Type VARCHAR(100)," +
				"Latitude double," +
				"Longitude double," +
				"Status INT," +
				"SafetyStock INT CHECK (SafetyStock >=0)," +
				"Primary Key (ExtFulfillerID, FulfillerID));";
		
		try {
			s.executeUpdate(query);
			System.out.println("Created table Locations");
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")){
				System.err.println(e.getMessage());
			}
			else{
			System.err.println("Couldn't create locations");
			e.printStackTrace();
			}
		}
		
		query = "Create Table Clients(" +
				"CatID INT," +
				"ManID INT," +
				"Primary Key (CatID, ManID))";
		
		try {
			s.executeUpdate(query);
			System.out.println("Created table Clients");
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")){
				System.err.println(e.getMessage());
			}
			else{
			System.err.println("Couldn't create clients");
			e.printStackTrace();
			}
		}
		
		query = "Create Table Serves(" +
		"CatID INT," +
		"ManID INT," +
		"EXTFULFILLERID VARCHAR(100)," +
		"FulfillerID INT," +
		"Primary Key (CatID, ManID, ExtFulfillerID, FulfillerID)," +
		"Foreign Key (ExtFulfillerID, FulfillerID) References Locations(ExtFulfillerID, FulfillerID) ON DELETE CASCADE," +
		"Foreign Key (CatID,ManID) references Clients(CatID, ManID) ON DELETE CASCADE);";
		
		try {
			s.executeUpdate (query);
			System.out.println("Created table Serves");
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")){
				System.err.println(e.getMessage());
			}
			else{
			System.err.println("Couldn't create serves");
			e.printStackTrace();
			}
		}
		
		query = "CREATE TABLE Bins(" +
		"BinID int AUTO_INCREMENT," +
		"Name VARCHAR(30)," +
		"EXTFULFILLERID VARCHAR(100)," +
		"FulfillerID INT," +
		"status VARCHAR(30)," +
		"type VARCHAR(30)," +
		"PRIMARY KEY (BinID)," +
		"FOREIGN KEY (ExtFulfillerID, FulfillerID) references Locations(ExtFulfillerID, FulfillerID) ON DELETE CASCADE);";
		
		try {
			s.executeUpdate (query);
			System.out.println("Created table Bins");
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")){
				System.err.println(e.getMessage());
			}
			else{
			System.err.println("Couldn't create bins");
			e.printStackTrace();
			}
		}
		
		query= "Create Table Products(" +
				"UPC VARCHAR(100)," +
				"CatID INT," +
				"ManID INT," +
				"Primary Key (UPC)," +
				"Foreign Key (CatID, ManID) References Clients(CatID, ManID) ON DELETE CASCADE);";
		
		try {
			s.executeUpdate(query);
			System.out.println("Created table Products");
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")){
				System.err.println(e.getMessage());
			}
			else{
			System.err.println("Couldn't create products");
			e.printStackTrace();
			}
		}
		
		query = "Create Table Inventory(" +
				"UPC VARCHAR(100)," +
				"SKU VARCHAR(100)," +
				"FulfillerID INT," +
				"Primary Key (SKU, FulfillerID),Foreign Key (UPC) References Products(UPC));";
		
		try {
			s.executeUpdate(query);
			System.out.println("Created table Inventory");
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")){
				System.err.println(e.getMessage());
			}
			else{
			System.err.println("Couldn't create inventory");
			e.printStackTrace();
			}
		}
		
		query = "CREATE TABLE Holds(" +
				"BinID INT," +
				"SKU VARCHAR(100)," +
				"UPC VARCHAR(100)," +
				"Allocation INT," +
				"OnHand INT," +
				"PRIMARY KEY (BinID, SKU)," +
				"FOREIGN KEY (BinID) references Bins(BinID)," +
				"FOREIGN KEY (SKU) references Inventory(SKU)," +
				"FOREIGN KEY (UPC) references Products(UPC));";
		
		try {
			s.executeUpdate(query);
			System.out.println("Created table Holds");
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")){
				System.err.println(e.getMessage());
			}
			else{
			System.err.println("Couldn't create holds");
			e.printStackTrace();
			}
		}
		
		query = "Create Table Stocks(EXTFULFILLERID VARCHAR(100),FulfillerID INT, SKU VARCHAR(100),UPC VARCHAR(100),LTD INT,SafetyStock Int," +
				"Primary Key (ExtFulfillerID, FulfillerID, SKU)," +
				"Foreign Key (ExtFulfillerID, FulfillerID) References Locations(ExtFulfillerID, FulfillerID) ON DELETE CASCADE," +
				"Foreign Key (UPC) References Products(UPC) ON DELETE CASCADE," +
				"Foreign Key (FulfillerID, SKU) References Inventory(FulfillerID, SKU) ON DELETE CASCADE);";
		
		try {
			s.executeUpdate (query);
			System.out.println("Created table Stocks");
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")){
				System.err.println(e.getMessage());
			}
			else{
			System.err.println("Couldn't create stocks");
			e.printStackTrace();
			}
		}
	}
	
	public static void destroyDatabase(Connection connection){
		String query = "";
		Statement s = createStatement(connection);
		for (String table : tables){
			query = "Drop table "+ table;
			
			try{
				s.executeUpdate(query);
				System.out.println("Dropped table " + table);
			}
			catch(SQLException e){
				System.err.println("Problem dropping table " + table);
				e.printStackTrace();
			}	
		}
	}
	
	public static void clearDatabase (Connection connection){
		String query = "";
		Statement s = createStatement(connection);
		for (String table : tables){
			query = "Truncate table "+ table;
			
			try{
				s.executeUpdate(query);
				System.out.println("Cleared table " + table);
			}
			catch(SQLException e){
				System.err.println("Problem clearing table " + table);
				e.printStackTrace();
			}	
		}
	}
}
