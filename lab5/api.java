import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class api {
   // The Connection var needed to connect to the database
   private Connection conn;

   public boolean setUpConnection()
   {
      try
      {
         Class.forName("com.mysql.jdbc.Driver");
      }
      catch(Exception e)
      {
         System.out.println("Driver not found");
         return false;
      }

      conn = null;

      try
      {
         conn = DriverManager.getConnection("jdbc:mysql:@cslvm54.csc.calpoly.edu", "amille35", "dedb59bc");
      }
      catch(Exception e)
      {
         System.out.println("Could not open connection");
         return false;
      }

      return true;
   }

   public boolean closeConnection()
   {
      try
      {
         conn.close();
      }
      catch(Exception e)
      {
         System.out.println(e);
         return false;
      }

      return true;
   }


   public void createFulfiller ()
   {
      if(setUpConnection() == false)
         return;

      if(closeConnection() == false)
         return;

      createNewRetailer(fulfillerId, locationName);
      createNewLocation(fulfillerId, externalLocationId, internalFulfillerLocationId,
         "", description, latitude, longitude, status, safetyStockLimit);
      createNewManufacturer(manufacturerId);
      createNewCatalog(manufacturerId, catalogId);
   }

   public void createNewRetailer(int fulfillerId, String locationName)
   {
      String sql = "INSERT INTO Retailer VALUES(?, ?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, fulfillerId);
         ps.setString(2, locationName);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new Retailer tuple: ");
      }
   }

   public void createNewLocation(int fulfillerId, String externalLocationId,
      int internalFulfillerLocationId, String type, String description,
      double latitude, double longitude, int status, int safetyStockLimit)
   {
       String sql = "INSERT INTO Location VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, fulfillerId);
         ps.setString(2, externalLocationId);
         ps.setInt(3, internalFulfillerLocationId);
         ps.setString(4, type);
         ps.setString(5, description);
         ps.setDouble(6, latitude);
         ps.setDouble(7, longitude);
         ps.setInt(8, status);
         ps.setInt(9, safetyStockLimit);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new Location tuple: ");
      }
   }

   public void createNewManufacturer(int manufacturerId)
   {
      String sql = "INSERT INTO Manufacturer VALUES(?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, manufacturerId);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new Manufacturer tuple: ");
      }
   }

   public void createNewCatalog(int manufacturerId, int catalogId)
   {
      String sql = "INSERT INTO Catlog VALUES(?, ?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, manufacturerId);
         ps.setInt(2, catalogId);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new Catalog tuple: ");
      }
   }
	
   public void createFullfillmentLocation ()
   {
      System.out.println("in create Fulfillment Location");
   }
	
   public void createManufacturerCatalog ()
   {
      System.out.println("in createManufacturer Catalog");
   }
	
   public int createBin ()
   {
      if(fulfillerId < 0 || binId < 0 || fulfillerLocationId < 0) {
         return -1;
      }

      if(setUpConnection() == false)
         return -1;

      try {
         String sql = "INSERT INTO StoreBin VALUES(NULL, ?, ?, ?, ?, ?)";
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setInt(1, fulfillerLocationId);
         ps.setString(2, binStatus);
         ps.setString(3, binType);
         ps.setString(4, binName);
         ps.setString(5, "");

         ps.executeUpdate();
      }
      catch (Exception e) {
         return -1;
      }
      
      closeConnection();
      		
      return 1;
   }
	
   public void refreshInventory()
   {
      System.out.println("refreshInventory");		
   }	
}






