package edu.calpoly.channel4.bulk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.calpoly.channel4.model.Bin;

public class BinCreator {

	/**
	 * Inserts a Bin into the database.
	 * 
	 * @param bin the Bin to be inserted
	 * @param conn the connection to the database
	 * @return status code: < 0 if failure, > 0 if success
	 */
    public static int createBin(Bin bin, Connection conn) {

        String sql = "INSERT INTO Bins (name, EXTFULFILLERID, FulfillerID, status, type) VALUES('" +
                      bin.getName() + "', '" +
                      getExternalFulfillerLocationID(bin.getFulfillerLocationID(), conn) + "', " +
                      bin.getFulfillerID() + ", '" + bin.getBinStatus() + "', '" + 
                      bin.getBinType() + "')";
        
    	try {
    		Statement s = conn.createStatement();
            s.executeUpdate(sql);
        }
        catch (SQLException e) {
        	System.err.println(e.getMessage());
            return -1;
        }
        
        return 1;
    }

    /**
     * Get the external fulfiller location id given the internal fulfiller location id
     * 
     * @param intFulfillerLocationID the internal fulfiller location id
     * @param conn the connection to the database
     * @return the external fulfiller location id associated with the internal fulfiller location id
     */
    private static String getExternalFulfillerLocationID(int intFulfillerLocationID, Connection conn) {
        String sql = "SELECT ExtFulfillerID FROM Locations " +
                     "WHERE InternalFulfillerLocationID = " + intFulfillerLocationID;
                     
        try {
            Statement s = conn.createStatement();
            ResultSet result = s.executeQuery(sql);
            result.next();
            return result.getString(1);
        }
        catch (SQLException e) {
        	e.printStackTrace();
            return null;
        }
    }
}
