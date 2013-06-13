package com.shopatron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static final String connectionURL = "jdbc:derby:server/db/;create=true";

    public static Connection getConnection() throws SQLException {
	return DriverManager.getConnection(connectionURL);
    }

    public static boolean executeBooleanQuery(String sql, Object... args)
	    throws SQLException {
	try (Connection conn = getConnection()) {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		for (int i = 0; i < args.length; i++) {
		    stmt.setObject(i+1, args[i]);
		}

		try (ResultSet results = stmt.executeQuery()) {
		    if (results.next())
			return results.getBoolean(0);
		    else
			throw new SQLException("No rows returned");
		}
	    }
	}
    }

    public static int executeIntQuery(String sql, Object... args)
	    throws SQLException {
	try (Connection conn = getConnection()) {
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		for (int i = 0; i < args.length; i++) {
		    stmt.setObject(i+1, args[i]);
		}

		try (ResultSet results = stmt.executeQuery()) {
		    if (results.next())
			return results.getInt(0);
		    else
			throw new SQLException("No rows returned");
		}
	    }
	}
    }
    
    public static boolean executeSQLScript(String filename)
	    throws SQLException, IOException {
	for (String command : readSQLScript(filename).split(";")) {
	    System.out.println("starting");
	    if (!executeSQL(command)) {
		System.out.println("failure");
		return false;
	    }
	    System.out.println("success");
	}
	return true;
    }

    public static <T> List<T> executeQuery(DbReader<? extends T> reader, String sql, Object... args)
    	    throws SQLException {
    		ArrayList<T> list = new ArrayList<T>();
    	
    		try (Connection conn = getConnection()) {
    		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    			for (int i = 0; i < args.length; i++) {
	    			    stmt.setObject(i+1, args[i]);
	    			}
	    	
	    			ResultSet results = stmt.executeQuery();
	
	    			while(results.next()) {
	    				list.add(reader.read(results));
	    			}
    		    }
    		}
    		
    		return list;
    }

    public static boolean executeSQL(String sql, Object... args)
	    throws SQLException {
		try (Connection conn = getConnection()) {
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < args.length; i++) {
			    stmt.setObject(i+1, args[i]);
			}
	
			return stmt.execute();
		    }
		}
    }
    
    public static ArrayList<String> executeStringSQL(String sql, Object... args)
    	    throws SQLException {
    		ArrayList<String> list = new ArrayList<String>();
    	
    		try (Connection conn = getConnection()) {
    		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    			for (int i = 0; i < args.length; i++) {
	    			    stmt.setObject(i+1, args[i]);
	    			}
	    	
	    			ResultSet results = stmt.executeQuery();
	
	    			while(results.next()) {
	    				list.add(results.getString("StoreType"));
	    			}
    		    }
    		}
    		
    		return list;
        }
    
    // returns the result set 
    public static ResultSet getResults(String sql, Object... args) throws SQLException {
    	
    	try (Connection conn = getConnection()) {
    		try (PreparedStatement stmnt = conn.prepareStatement(sql)) {
    			for (int i = 0; i < args.length; i++) 
    				stmnt.setObject(i+1, args[i]);
    			
    			return stmnt.executeQuery();
    		}
    	}
    }
    
    public static ArrayList<String> executeGenericStringSQL(String sql, String column,  Object... args)
    	    throws SQLException {
    		ArrayList<String> list = new ArrayList<String>();
    	
    		try (Connection conn = getConnection()) {
    		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    			for (int i = 0; i < args.length; i++) {
	    			    stmt.setObject(i+1, args[i]);
	    			}
	    	
	    			ResultSet results = stmt.executeQuery();
	
	    			while(results.next()) {
	    				list.add(results.getString(column));
	    			}
    		    }
    		}
    		
    		return list;
        }

    private static String readSQLScript(String filename) throws IOException {
	try (BufferedReader reader = new BufferedReader(
		new FileReader(filename))) {
	    StringBuilder builder = new StringBuilder();
	    char[] buffer = new char[1024];
	    int read;

	    while ((read = reader.read(buffer)) > 0) {
		builder.append(buffer, 0, read);
	    }

	    return builder.toString();
	}
    }
}
