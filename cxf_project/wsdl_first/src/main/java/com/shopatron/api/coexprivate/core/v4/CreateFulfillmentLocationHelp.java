package com.shopatron.api.coexprivate.core.v4;

import java.math.BigInteger;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CreateFulfillmentLocationHelp{
   
    public static void createLocation(CreateFulfillmentLocation request) {
      Connection conn = DbConnect.getConnection();
      FulfillmentLocation loc = request.getRequest();
      PreparedStatement statement;
      
      statement = setupFulfillmentLocations(conn);
      insertCreateFulfillmentLocation(conn, loc, statement);
      
      
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

   public static void insertCreateFulfillmentLocation(Connection conn, FulfillmentLocation loc, PreparedStatement s1) {
      int count = 0, locCount = 0;
      String selectStr = "select count(*) from Locations where ExtFulfillerID = '" + loc.getExternalLocationID() + "' and FulfillerID = " + loc.getFulfillerID();

      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(selectStr);
	
         while(result.next()) {
            count = result.getInt("COUNT(*)");
         }
      } catch(Exception e) { e.printStackTrace(); }
         
      if(count == 0) {          
         try {
        	 
            s1.setString(1, loc.getExternalLocationID());
            s1.setInt(2, loc.getFulfillerID().intValue());
            s1.setInt(3, loc.getRetailerLocationID().intValue());
            s1.setString(4, loc.getLocationName());
            s1.setString(5, loc.getLocationType());
            s1.setDouble(6, loc.getLatitude());
            s1.setDouble(7, loc.getLongitude());
            s1.setInt(8, loc.getStatus());
            s1.setInt(9, 0);
            s1.executeUpdate();
            System.out.println("Added");
            
         } catch(Exception e) { e.printStackTrace(); }
      }
      

   }
}