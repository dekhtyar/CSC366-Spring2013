package com.shopatron.api.coexprivate.core.v4;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.math.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GetBinStatusesHelper {
   private static final Logger LOG = Logger.getLogger(GetBinStatusesHelper.class.getName());

   public static List<BinStatus> getBinStatuses() {
      
      ArrayList<BinStatus> binStatuses = new ArrayList<BinStatus>();
      Connection conn = DbConnect.getConnection();
      
      String query = new StringBuilder()
               .append ("SELECT DISTINCT Status FROM Bins")
               .toString();
           
      try {
         Statement s = conn.createStatement();
         ResultSet res = s.executeQuery(query);
         
         while (res.next()) {
            BinStatus binStatus = new BinStatus();
            binStatus.setBinStatus(res.getString("Status"));
            binStatuses.add(binStatus);
         }
      }
      catch (SQLException e) {
         System.err.println(e.getMessage());
      }

      return binStatuses;
   }
   
}
