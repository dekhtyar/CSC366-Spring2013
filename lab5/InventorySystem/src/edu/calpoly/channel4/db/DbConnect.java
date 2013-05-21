package edu.calpoly.channel4.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
 
public class DbConnect {
  
  public static Connection getConnection() {
  
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("Couldn't find MySQL JDBC Driver?");
		e.printStackTrace();
		return null;
	}
 
	Connection connection = null;
 
	try {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/channel4","root", "channel4");
 
	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return null;
	}
 
	return connection;
  }


}