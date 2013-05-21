package edu.calpoly.channel4.locations;

import java.sql.*;

import edu.calpoly.channel4.bulk.BinCreator;
import edu.calpoly.channel4.bulk.bean.LocationBean;
import edu.calpoly.channel4.bulk.csv.LocationParser;
import edu.calpoly.channel4.model.Bin;

public class Locations {

   public static void createFulfillmentLocations(Connection conn) {
      int count = 0, clientCount = 0, locCount = 0, servesCount = 0;
      PreparedStatement statement, statement2, statement3;
      
      statement = setupFulfillmentLocations(conn);
      statement2 = setupClients(conn);
      statement3 = setupServes(conn);
      try {
         for(LocationBean loc : LocationParser.getLocations("res/csv/fulfiller locations.csv")) {
            clientCount += insertClients(conn, loc, statement2);
            locCount += insertCreateFulfillmentLocation(conn, loc, statement);
            servesCount += insertServes(conn, loc, statement3);
         }
      } catch (Exception e) {  System.out.println("can't find csv"); }

      System.out.println(" ");
      System.out.println("----- Creating Fulfillers -----");
      System.out.println(clientCount + " clients inserted.");
      System.out.println(" ");
      System.out.println("----- Creating Fulfillment Locations -----");
      System.out.println(locCount + " locations inserted.");
      System.out.println(" ");
      System.out.println("----- Creating Serves -----");
      System.out.println(servesCount + " serves inserted.");
   }

   public static PreparedStatement setupClients(Connection conn) {
      String query;
      PreparedStatement s2 = null;
     
      try {
         query = "INSERT INTO Clients VALUE(?, ?)";
         s2 = conn.prepareStatement(query);
      } catch(Exception e) { e.printStackTrace(); }

      return s2;
   }

   public static PreparedStatement setupFulfillmentLocations(Connection conn) {
      String query;
      PreparedStatement s1 = null;

      try {
         query = "INSERT INTO Locations VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?)";
         s1 = conn.prepareStatement(query);
      } catch (Exception e) { e.printStackTrace(); }
      
      return s1;
   }

   public static PreparedStatement setupServes(Connection conn) {
      String query;
      PreparedStatement s3 = null;
   
      try {
         query = "INSERT INTO Serves VALUE(?, ?, ?, ?)";
         s3 = conn.prepareStatement(query);
      } catch (Exception e) { e.printStackTrace(); }
   
      return s3;
   }

   public static int insertClients(Connection conn, LocationBean loc, PreparedStatement s2) {
      int count = 0, clientCount = 0;
      String selectStr = "select count(*) from Clients where catId = " + loc.getCatalogId() + " and manID = " + loc.getManufacturerId();
      
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(selectStr);
         while(result.next()) {
            count = result.getInt("COUNT(*)");
         }
      } catch(Exception e) { e.printStackTrace(); }
      
      if(count == 0) {
         try {
            s2.setInt(1, loc.getCatalogId());
            s2.setInt(2, loc.getManufacturerId());
            s2.executeUpdate();
            clientCount++;
         } catch(Exception e) { e.printStackTrace(); }
      }
      return clientCount;
   }

   public static int insertCreateFulfillmentLocation(Connection conn, LocationBean loc, PreparedStatement s1) {
      int count = 0, locCount = 0;
      String selectStr = "select count(*) from Locations where ExtFulfillerID = '" + loc.getExtFulfillerLocId() + "' and FulfillerID = " + loc.getFulfillerId();

      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(selectStr);
	
         while(result.next()) {
            count = result.getInt("COUNT(*)");
         }
      } catch(Exception e) { e.printStackTrace(); }
         
      if(count == 0) {          
         try {
        	 
            s1.setString(1, loc.getExtFulfillerLocId());
            s1.setInt(2, loc.getFulfillerId());
            s1.setInt(3, loc.getIntFulfillerLocId());
            s1.setString(4, loc.getName());
            s1.setString(5, loc.getDesc());
            s1.setDouble(6, loc.getLatitude());
            s1.setDouble(7, loc.getLongitude());
            s1.setInt(8, loc.getStatus());
            s1.setInt(9, loc.getSafetyStock());
            s1.executeUpdate();
            locCount++;
            createDefaultBin(conn, loc);
         } catch(Exception e) { e.printStackTrace(); }
      }
      
      return locCount;
   }
   public static void createDefaultBin(Connection conn, LocationBean loc){
	   Bin defaultBin = new Bin(loc.getFulfillerId(), loc.getIntFulfillerLocId(), "General", "Pickable", "Default");
	   BinCreator.createBin(defaultBin, conn);
   }
   
   public static int insertServes(Connection conn, LocationBean loc, PreparedStatement s3) {
      int servesCount = 0, count = 0;
      String selectStr = "select count(*) from Serves where CatID = " + loc.getCatalogId() + " and ManID = " + loc.getManufacturerId() + " and ExtFulfillerID = '" + loc.getExtFulfillerLocId() + "' and FulfillerID = "  + loc.getFulfillerId();
   
      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(selectStr);
         
         while(result.next()) {
            count = result.getInt("COUNT(*)");
         }
      } catch(Exception e) { e.printStackTrace(); }

      if(count == 0) {
         try {
            s3.setInt(1, loc.getCatalogId());
            s3.setInt(2, loc.getManufacturerId());
            s3.setString(3, loc.getExtFulfillerLocId());
            s3.setInt(4, loc.getFulfillerId());
            s3.executeUpdate();
            servesCount++;
         } catch(Exception e) { e.printStackTrace(); }
      } 
      return servesCount;
   }
}
