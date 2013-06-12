package com.shopatron.api.coexprivate.core.v4;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
 
public class DbConnect {
  
  private static Connection connection = null;

  private DbConnect(){

  }

  public static Connection getConnection() {
    if (connection == null){  
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Couldn't find MySQL JDBC Driver?");
      e.printStackTrace();
      return null;
    }
  
    try {
      connection = DriverManager.getConnection("jdbc:mysql://cslvm56.csc.calpoly.edu:3306/channel4","root", "channel4");

    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return null;
    }
   }
 
   return connection;
  }


}