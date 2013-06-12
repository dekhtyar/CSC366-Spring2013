package com.shopatron.api.coexprivate.core.v4;

import java.sql.*;
import java.math.*;
import java.util.*;

public class getFulfillerStatusHelper {
   public static int getStatus(int fulfillerID)
   {
      Connection conn = DbConnect.getConnection();
      int status = 0;

      status = statusQuery(conn, fulfillerID);

      if(status > 0)
      {
         return 1;
      }
      else
      {
         return 2;
      }
   }

   public static int statusQuery(Connection conn, int fulfillerID)
   {
      String query = "SELECT count(*) FROM Locations l " +
                     "WHERE l.fulfillerID = " + fulfillerID + " AND l.status = 1";
      int status = 0;

      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(query);
         while(result.next()) {
            status = result.getInt("COUNT(*)");
         }
      } catch (Exception e) { e.printStackTrace(); }

      return status;
   }
}
