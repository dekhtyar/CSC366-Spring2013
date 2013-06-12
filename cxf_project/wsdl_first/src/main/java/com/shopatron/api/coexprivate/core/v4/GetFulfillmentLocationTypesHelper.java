package com.shopatron.api.coexprivate.core.v4;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.math.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GetFulfillmentLocationTypesHelper {
   private static final Logger LOG = Logger.getLogger(GetFulfillmentLocationTypesHelper.class.getName());

   public static List<FulfillmentLocationType> getFulfillmentLocationTypes() {
      
      ArrayList<FulfillmentLocationType> locationTypes = new ArrayList<FulfillmentLocationType>();
      Connection conn = DbConnect.getConnection();
      
      String query = new StringBuilder()
               .append ("SELECT DISTINCT Type FROM Locations")
               .toString();
           
      try {
         Statement s = conn.createStatement();
         ResultSet res = s.executeQuery(query);
         
         while (res.next()) {
            FulfillmentLocationType locType = new FulfillmentLocationType();
            locType.setLocationType(res.getString("Type"));
            locationTypes.add(locType);
         }
      }
      catch (SQLException e) {
         System.err.println(e.getMessage());
      }

      return locationTypes;
   }
   
}
