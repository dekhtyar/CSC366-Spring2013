package edu.calpoly.channel4.bulk;

import java.sql.Connection;
import java.sql.SQLException;

import edu.calpoly.channel4.bulk.bean.BinBean;
import edu.calpoly.channel4.bulk.bean.InventoryBean;
import edu.calpoly.channel4.bulk.bean.LocationBean;
import edu.calpoly.channel4.bulk.csv.BinParser;
import edu.calpoly.channel4.bulk.csv.InventoryParser;
import edu.calpoly.channel4.bulk.csv.LocationParser;
import edu.calpoly.channel4.db.DbConnect;


public class BulkUploader {

   public static void main(String[] args) {
      try {
         for (LocationBean loc : LocationParser.getLocations("res/csv/fulfiller locations.csv")) {
            //System.out.println(loc);
         }
         for (BinBean bin : BinParser.getBins("res/csv/fulfiller location_bins.csv")) {
            System.out.println(bin);
         }
         for (InventoryBean inventory : InventoryParser.getInventories("res/csv/fulfiller inventory.csv")) {
            //System.out.println(inventory);
         }
      }
      catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      Connection connection = DbConnect.getConnection();
      try {
		connection.close();
	} catch (SQLException e) {
		System.out.println("Error closing DB connection");
		e.printStackTrace();
	}
   }
   
}
