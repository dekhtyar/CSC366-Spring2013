package edu.calpoly.channel4.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.calpoly.channel4.bulk.bean.BinBean;

/**
 * A Factory class to create a Bin (WSDL representation) from a BinBean (CSV representation).
 */
public class BinFactory {

	/**
	 * Create a Bin (WSDL representation) given a BinBean (CSV representation) and a Connection to the database in order to look up necessary fields
	 *
	 * @param binBean the BinBean (record from the CSV file) specifying the properties of the Bin being created.
	 * @param conn a Connection to the database in order to look up any other properties needed for the Bin being created.
	 * @return a Bin with the properties specified by the BinBean.
	 */
	public static Bin createBin(BinBean binBean, Connection conn) {
    	
    	int fulfillerId = getFulfillerID(binBean.getIntFulfillerLocId(), conn);
    	
    	return new Bin(fulfillerId, binBean.getIntFulfillerLocId(), binBean.getType(), binBean.getStatus(), binBean.getName());
    }
    
    /**
     * Get the fulfiller id given an internal fulfiller location id.
     * 
     * @param intFulfillerLocId the internal fulfiller location id (unique system wide)
     * @param conn a connection to the database containing locations
     * @return the fulfiller id associated with the  internal fulfiller location id
     */
    private static int getFulfillerID(int intFulfillerLocId, Connection conn) {
        String sql = "SELECT FulfillerID FROM Locations " +
                     "WHERE InternalFulfillerLocationID = " + intFulfillerLocId;
                     
        try {
            Statement s = conn.createStatement();
            ResultSet result = s.executeQuery(sql);
            while(result.next()) {
            	return result.getInt(1);
            }
            return -1;
        }
        catch (SQLException e) {
        	e.printStackTrace();
            return -1;
        }
    }
    
}