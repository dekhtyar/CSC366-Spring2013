import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import edu.calpoly.channel4.bulk.BinCreator;
import edu.calpoly.channel4.bulk.bean.BinBean;
import edu.calpoly.channel4.bulk.csv.BinParser;
import edu.calpoly.channel4.db.DBOps;
import edu.calpoly.channel4.db.DbConnect;
import edu.calpoly.channel4.model.Bin;
import edu.calpoly.channel4.model.BinFactory;
import edu.calpoly.channel4.bulk.bean.InventoryBean;
import edu.calpoly.channel4.bulk.csv.InventoryParser;
import edu.calpoly.channel4.inventory.RefreshInventory;
import edu.calpoly.channel4.locations.Fulfiller;
import edu.calpoly.channel4.locations.Locations;

public class Main {

	public static void main(String[] args) {
		Connection c = DbConnect.getConnection();
		Console console = System.console();
		String input, prompt = "Press any key to continue ";
		boolean askUser = true, autoDestroy = false, autoClear = true;

		if (c != null) {
			System.out.println(" ");
			System.out.println("----- Connected to database ----- ");
		}

		/*
		 * Database Creation
		 */
		System.out.println(" ");
		System.out.println("----- Setting up database -----");

		DBOps.createDatabase(c);

		if (autoClear || confirm(console, "Do you want to clear the db? (y/n): ")) {
			/*
			 * Clear Database
			 */
			System.out.println(" ");
			System.out.println("----- Clearing db -----");
			DBOps.clearDatabase(c);

		}
		
		if (console != null && askUser)
			input = console.readLine(prompt);

		/*
		 * Create Fulfillment Locations
		 */
		System.out.println(" ");
		
		Locations.createFulfillmentLocations(c);

		if (console != null && askUser)
			input = console.readLine(prompt);


		/*
		 * Create Bins
		 */
		System.out.println(" ");
		System.out.println("----- Creating Bins -----");
		
		int binCount = 0;
		try {
			// for every record from the CSV file
	        for (BinBean binBean : BinParser.getBins("res/csv/fulfiller location_bins.csv")) {
	            Bin bin = BinFactory.createBin(binBean, c);	// construct a Bin
	            if (BinCreator.createBin(bin, c) > 0) { 	// call createBin on the current Bin
	                binCount++;
	            }
	        }
		}
		catch(IOException e) { // likely a file not found
			System.err.println(e.getMessage());
		}
		// show bins created in db
        System.out.println(binCount + " bins inserted.");
        
		/*
		 * Refresh Inventory
		 */
		System.out.println(" ");
		System.out.println("----- Refreshing/Updating Inventory -----");
		
		int inventorySucesses = 0, badBin = 0;
		try {
			for (InventoryBean inventory : InventoryParser.getInventories("res/csv/fulfiller inventory available bins.csv")) {
				try{
					RefreshInventory.refreshInventory(inventory, c);
					inventorySucesses++;
				}
			
				catch (Exception e) {
					if (e.getMessage().equals("BinID")){
						badBin++;
						
					}
				}	
			}
		}
		catch(Exception e){
		
				System.out.println("InventoryParser failed");
				e.printStackTrace();
			
		}
		System.out.println("Inserted " + inventorySucesses + " records successfuly");
		if (badBin > 0){
			System.out.println(badBin +" rows could not be inserted due to invalid External Fulfiller Location IDs");
		}
		

		if (autoDestroy || confirm(console, "Do you want to destroy all tables in the db? (y/n): ")) {

			/*
			 * Destroy Database
			 */
			System.out.println(" ");
			System.out.println("----- Destorying db -----");

			DBOps.destroyDatabase(c);

		}

		try {
			c.close();
			System.out.println(" ");
			System.out.println("----- Connection closed -----");
		} catch (SQLException e) {
			System.out.println("Problem closing connection to db");
			e.printStackTrace();
		}

	}

	public static boolean confirm(Console console, String message) {
		return console != null && console.readLine(message).equals("y");
	}

}
