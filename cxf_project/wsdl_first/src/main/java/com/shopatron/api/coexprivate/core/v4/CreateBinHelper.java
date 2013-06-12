package com.shopatron.api.coexprivate.core.v4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.math.BigInteger;

public class CreateBinHelper {

    public static BigInteger createBin(Bin bin) {

        Connection conn = DbConnect.getConnection();

        String sql = "INSERT INTO Bins (name, EXTFULFILLERID, FulfillerID, status, type) VALUES('" +
                      bin.getName() + "', '" +
                      bin.getExternalLocationID() + "', " +
                      bin.getFulfillerID() + ", '" + bin.getBinStatus() + "', '" + 
                      bin.getBinType() + "')";
        
    	try {
    	   Statement s = conn.createStatement();
          s.executeUpdate(sql);
      }
      catch (SQLException e) {
         System.err.println(e.getMessage());
         return BigInteger.valueOf(-1);
      }
        
        return BigInteger.valueOf(1);
    }

}
