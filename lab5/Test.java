import java.sql.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Test {

	private static Connection conn;
	private static api apiCall = new api();
	private static boolean debug = false;
	private static boolean setup = false;
	private static boolean test = true;
	private static boolean cleanup = false;

	public static void main(String[] args) {

		setupConnection();

		if (setup) {
			createDatabase();

			parseFulfillerLocations("fulfiller locations.csv");
			parseLocationBins("fulfiller location_bins.csv");
			parseFulfillerInventory("fulfiller inventory available bins.csv");
			parseFulfillerInventory("fulfiller inventory available.csv");
			parseFulfillerInventory("fulfiller inventory not available.csv");
		}

		if (test) {
			/*
			 * TESTS HERE if (testCreateFulfiller())
			 * System.out.println("createFulfiller failed");
			 */
			System.out
					.println(!createBinTest1() ? "" : "createBinTest1 failed");

			if (!createBinTest2()) {
				System.out.println("createBinTest2 failed");
			}
			if (!createBinTest3()) {
				System.out.println("createBinTest3 failed");
			}

			if (!getBinsTest1()) {
				System.out.println("getBinsTest1 failed");
			}
			if (!getBinsTest2()) {
				System.out.println("getBinsTest2 failed");
			}
			if (!getBinsTest3()) {
				System.out.println("getBinsTest3 failed");
			}
			if (!getBinsTest4()) {
				System.out.println("getBinsTest4 failed");
			}

			if (!getBinTypesTest()) {
				System.out.println("getBinTypesTest failed");
			}

			if (!getBinStatusesTest()) {
				System.out.println("getBinStatusesTest failed");
			}

			if (!allocateInventoryTest1()) {
				System.out.println("allocateInventoryTest1 failed");
			}
			if (!deallocateInventoryTest()) {
				System.out.println("deallocateInventoryTest failed");
			}

			if (!allocateInventoryTest2()) {
				System.out.println("allocateInventoryTest2 failed");
			}
			if (!fulfillInventoryTest()) {
				System.out.println("fulfillInventoryTest failed");
			}

			if (!getInventoryTest1()) {
				System.out.println("getInventoryTest1 failed");
			}
			if (!getInventoryTest2()) {
				System.out.println("getInventoryTest2 failed");
			}
			if (!getInventoryTest3()) {
				System.out.println("getInventoryTest3 failed");
			}
			if (!getInventoryTest4()) {
				System.out.println("getInventoryTest4 failed");
			}
			if (!getInventoryTest5()) {
				System.out.println("getInventoryTest5 failed");
			}
			if (!getInventoryTest6()) {
				System.out.println("getInventoryTest6 failed");
			}
			if (!getInventoryTest7()) {
				System.out.println("getInventoryTest7 failed");
			}
			if (!getInventoryTest8()) {
				System.out.println("getInventoryTest8 failed");
			}
		}

		if (cleanup) {
			clearDatabase();
			destroyDatabase();
		}

		closeConnection();
	}

	// valid case 1
	public static boolean createBinTest1() {
		return apiCall.createBin(48590, null, "600", "General", "Pickable",
				System.currentTimeMillis() + "") >= 0;
	}

	// valid case 2: passing nulls case
	public static boolean createBinTest2() {
		return apiCall.createBin(48590, null, "600", "General", "Pickable",
				null) >= 0;
	}

	// invalid case: negative fulfillerId and binId
	public static boolean createBinTest3() {
		return apiCall.createBin(-1, -1, "600", "General", "Pickable", null) < 0;
	}

	// valid case 1
	public static boolean getBinsTest1() {
		ArrayList<Object[]> bins = apiCall
				.getBins(48590, "600", "", null, null);

		return bins != null && bins.size() > 0;
	}

	// valid case 2: 1 bin or less
	public static boolean getBinsTest2() {
		ArrayList<Object[]> bins = apiCall.getBins(48590, "600", "", 1, 0);

		return bins != null && (bins.size() == 1 || bins.size() == 0);
	}

	// invalid case 1: Non-existant searchTerm
	public static boolean getBinsTest3() {
		ArrayList<Object[]> bins = apiCall.getBins(48590, "600",
				"DOESNOTEXIST", null, 0);

		return bins != null && bins.size() == 0;
	}

	// invalid case 2: No results
	public static boolean getBinsTest4() {
		ArrayList<Object[]> bins = apiCall.getBins(-1, null, "", 100000, 0);

		return bins != null && bins.size() == 0;
	}

	// only case
	public static boolean getBinTypesTest() {
		ArrayList<String> binTypes = apiCall.getBinTypes();

		return binTypes != null && binTypes.size() > 0;
	}

	// only case
	public static boolean getBinStatusesTest() {
		ArrayList<String> binStatuses = apiCall.getBinStatuses();

		return binStatuses != null && binStatuses.size() > 0;
	}

	public static boolean allocateInventoryTest1() {
		Object[][] fulfillerLocationCatalog = null;
		Object[][] items = {
				{ "200033103", "200033103", new Integer(1), new Integer(600) },
				{ "201279746", "201279746", new Integer(1), "600" } };
		apiCall.allocateInventory(48590, fulfillerLocationCatalog, items);

		return true;
	}

	public static boolean deallocateInventoryTest() {
		Object[][] fulfillerLocationCatalog = null;
		Object[][] items = {
				{ "200033103", "200033103", new Integer(1), new Integer(600) },
				{ "201279746", "201279746", new Integer(1), "600" } };
		apiCall.deallocateInventory(48590, fulfillerLocationCatalog, items);

		return true;
	}

	public static boolean allocateInventoryTest2() {
		Object[][] fulfillerLocationCatalog = null;
		Object[][] items = {
				{ "200033103", "200033103", new Integer(1), new Integer(600) },
				{ "201279746", "201279746", new Integer(1), "600" } };
		apiCall.allocateInventory(48590, fulfillerLocationCatalog, items);

		return true;
	}

	public static boolean fulfillInventoryTest() {
		Object[][] fulfillerLocationCatalog = null;
		Object[][] items = {
				{ "200033103", "200033103", new Integer(1), new Integer(600) },
				{ "201279746", "201279746", new Integer(1), "600" } };
		apiCall.fulfillInventory(48590, fulfillerLocationCatalog, items);

		return true;
	}

	// valid case 1: "PARTIAL" type
	public static boolean getInventoryTest1() {
		int fulfillerId = 69170;
		int[] manCatalog = null;
		Object[][] quantities = { { "8888069843", "8888069843", 1 },
				{ "8888074813", "8888074813", 1 },
				{ "8888052689", "8888052689", 1 } };
		String[] locationIds = null;
		Object[] location = null;
		String type = "PARTIAL";
		int limit = 1000;
		Boolean ignoreSafetyStock = true;
		Boolean includeNegativeInventory = false;
		boolean orderByLtd = true;

		ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
				manCatalog, quantities, locationIds, location, type, limit,
				ignoreSafetyStock, includeNegativeInventory, orderByLtd);

		return inventory != null && inventory.size() > 0;
	}

	// valid case 2: "ANY" type
	public static boolean getInventoryTest2() {
		int fulfillerId = 69170;
		int[] manCatalog = null;
		Object[][] quantities = { { "8888069843", "8888069843", 1 },
				{ "8888074813", "8888074813", 1 },
				{ "8888052689", "8888052689", 1 } };
		String[] locationIds = null;
		Object[] location = null;
		String type = "ALL";
		int limit = 1000;
		Boolean ignoreSafetyStock = false;
		Boolean includeNegativeInventory = true;
		boolean orderByLtd = true;

		ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
				manCatalog, quantities, locationIds, location, type, limit,
				ignoreSafetyStock, includeNegativeInventory, orderByLtd);

		return inventory != null && inventory.size() > 0;
	}

	// valid case 3: "ALL" type
	public static boolean getInventoryTest3() {
		int fulfillerId = 69170;
		int[] manCatalog = null;
		Object[][] quantities = { { "8888069843", "8888069843", 1 },
				{ "8888074813", "8888074813", 1 },
				{ "8888052689", "8888052689", 1 } };
		String[] locationIds = null;
		Object[] location = null;
		String type = "ALL";
		int limit = 1000;
		Boolean ignoreSafetyStock = null;
		Boolean includeNegativeInventory = null;
		boolean orderByLtd = false;

		ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
				manCatalog, quantities, locationIds, location, type, limit,
				ignoreSafetyStock, includeNegativeInventory, orderByLtd);

		return inventory != null && inventory.size() > 0;
	}

	// valid case 4: "ALL_STORES" type
	public static boolean getInventoryTest4() {
		int fulfillerId = 69170;
		int[] manCatalog = null;
		Object[][] quantities = { { "8888069843", "8888069843", 1 },
				{ "8888074813", "8888074813", 1 },
				{ "8888052689", "8888052689", 1 } };
		String[] locationIds = null;
		Object[] location = null;
		String type = "ALL_STORES";
		int limit = 1000;
		Boolean ignoreSafetyStock = false;
		Boolean includeNegativeInventory = false;
		boolean orderByLtd = true;

		ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
				manCatalog, quantities, locationIds, location, type, limit,
				ignoreSafetyStock, includeNegativeInventory, orderByLtd);

		return inventory != null && inventory.size() > 0;
	}

	// valid case 5: Location Id's
	public static boolean getInventoryTest5() {
		int fulfillerId = 69170;
		int[] manCatalog = null;
		Object[][] quantities = { { "8888069843", "8888069843", 1 } };
		String[] locationIds = { "440777", "440777", "440004" };
		Object[] location = null;
		String type = "ALL";
		int limit = 1000;
		Boolean ignoreSafetyStock = null;
		Boolean includeNegativeInventory = null;
		boolean orderByLtd = false;

		ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
				manCatalog, quantities, locationIds, location, type, limit,
				ignoreSafetyStock, includeNegativeInventory, orderByLtd);

		return inventory != null && inventory.size() > 0;
	}

	// valid case 6: requestLocation
	public static boolean getInventoryTest6() {
		int fulfillerId = 69170;
		int[] manCatalog = null;
		Object[][] quantities = { { "8888069843", "8888069843", 1 },
				{ "8888074813", "8888074813", 1 },
				{ "8888052689", "8888052689", 1 } };
		String[] locationIds = null;
		Object[] location = { "MILES", 100, 0, new Float(40.742300),
				new Float(-73.987900), "USA" };
		String type = "ALL";
		int limit = 1000;
		Boolean ignoreSafetyStock = null;
		Boolean includeNegativeInventory = null;
		boolean orderByLtd = false;

		ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
				manCatalog, quantities, locationIds, location, type, limit,
				ignoreSafetyStock, includeNegativeInventory, orderByLtd);

		return inventory != null && inventory.size() > 0;
	}

	// valid case 7: Test limit value
	public static boolean getInventoryTest7() {
		int fulfillerId = 69170;
		int[] manCatalog = null;
		Object[][] quantities = { { "8888069843", "8888069843", 1 },
				{ "8888074813", "8888074813", 1 },
				{ "8888052689", "8888052689", 1 } };
		String[] locationIds = null;
		Object[] location = null;
		String type = "PARTIAL";
		int limit = 1;
		Boolean ignoreSafetyStock = null;
		Boolean includeNegativeInventory = null;
		boolean orderByLtd = false;

		ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
				manCatalog, quantities, locationIds, location, type, limit,
				ignoreSafetyStock, includeNegativeInventory, orderByLtd);

		return inventory != null && inventory.size() <= limit;
	}

	// invalid case
	public static boolean getInventoryTest8() {
		int fulfillerId = 69170;
		int[] manCatalog = null;
		Object[][] quantities = { { "DOES_NOT_EXIST", "DOES_NOT_EXIST", 1 } };
		String[] locationIds = {};
		Object[] location = null;
		String type = "ALL";
		int limit = 10000;
		Boolean ignoreSafetyStock = null;
		Boolean includeNegativeInventory = null;
		boolean orderByLtd = false;

		ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
				manCatalog, quantities, locationIds, location, type, limit,
				ignoreSafetyStock, includeNegativeInventory, orderByLtd);

		return inventory != null && inventory.size() == 0;
	}

	public static void createDatabase() {
		Scanner in = null;
		String text = "", sqlCmd = "";
		StringTokenizer token;
		Statement st = null;

		try {
			in = new Scanner(new File("DB-setup.sql"));
		} catch (FileNotFoundException e) {
			System.out.println("Error occured while opening DB-create.sql");
		}

		in.nextLine();

		while (in.hasNextLine()) {
			text += in.nextLine();
		}

		System.out.println();
		try {
			st = conn.createStatement();
			token = new StringTokenizer(text, ";");

			while (token.hasMoreTokens()) {
				sqlCmd = token.nextToken();

				System.out.println(sqlCmd);
				st.addBatch(sqlCmd);
			}

			if (debug)
				System.out.println("Create statement: " + sqlCmd);

			st.executeBatch();
		} catch (Exception e) {
			System.out.println("Error occured while creating DB tables" + e);
		}
	}

	public static void clearDatabase() {
		Scanner in = null;
		String text = "", sqlCmd = "";
		StringTokenizer token;
		Statement st = null;

		try {
			in = new Scanner(new File("DB-clear.sql"));
		} catch (FileNotFoundException e) {
			System.out.println("Error occured while opening DB-clear.sql");
		}

		in.nextLine();

		while (in.hasNextLine()) {
			text += in.nextLine();
		}

		try {
			st = conn.createStatement();
			token = new StringTokenizer(text, ";");

			while (token.hasMoreTokens()) {
				sqlCmd = token.nextToken();

				st.addBatch(sqlCmd);
			}

			st.executeBatch();
		} catch (Exception e) {
			System.out.println("Error occured while clearing DB tables");
		}
	}

	public static void destroyDatabase() {
		Scanner in = null;
		String text = "", sqlCmd = "";
		StringTokenizer token;
		Statement st = null;

		try {
			in = new Scanner(new File("DB-cleanup.sql"));
		} catch (FileNotFoundException e) {
			System.out.println("Error occured while opening DB-cleanup.sql");
		}

		in.nextLine();

		while (in.hasNextLine()) {
			text += in.nextLine();
		}

		try {
			st = conn.createStatement();
			token = new StringTokenizer(text, ";");

			while (token.hasMoreTokens()) {
				sqlCmd = token.nextToken();

				System.out.println(sqlCmd);
				st.addBatch(sqlCmd);
			}

			st.executeBatch();
		} catch (Exception e) {
			System.out.println("Error occured while destroying DB tables");
		}

	}

	public static void parseFulfillerLocations(String filename) {
		Scanner in = null;
		String line, temp;
		StringTokenizer token;

		int fulfillerId, internalFulfillerLocationId, manufacturerId, catalogId, safetyStockLimit;
		String externalLocationId, locationName, locationType, type, status;
		double latitude, longitude;

		try {
			in = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Exception thrown: " + e);
		}

		// Disregard the first line
		line = in.nextLine();

		while (in.hasNextLine()) {
			line = in.nextLine();
			token = new StringTokenizer(line, ",");

			locationName = token.nextToken();
			temp = token.nextToken();

			if (locationName.contains("\"") && temp.contains("\"")) {
				fulfillerId = (new Integer(token.nextToken())).intValue();
				locationName += temp;
			} else
				fulfillerId = (new Integer(temp)).intValue();

			externalLocationId = token.nextToken();
			internalFulfillerLocationId = (new Integer(token.nextToken()))
					.intValue();
			type = token.nextToken();
			latitude = (new Double(token.nextToken())).doubleValue();
			longitude = (new Double(token.nextToken())).doubleValue();
			status = token.nextToken();
			safetyStockLimit = (new Integer(token.nextToken())).intValue();
			manufacturerId = (new Integer(token.nextToken())).intValue();
			catalogId = (new Integer(token.nextToken())).intValue();

			if (debug) {
				System.out.println("Line: " + locationName + ", " + fulfillerId
						+ ", " + externalLocationId + ", "
						+ internalFulfillerLocationId + ", " + type + ", "
						+ latitude + ", " + longitude + ", " + status + ", "
						+ safetyStockLimit + ", " + manufacturerId + ", "
						+ catalogId);
			}

			apiCall.createFulfillmentLocation(fulfillerId,
					internalFulfillerLocationId, externalLocationId,
					locationName, type, latitude, longitude, status, null);
		}

	}

	public static void parseLocationBins(String filename) {
		Scanner in = null;
		String line, temp;
		StringTokenizer token;
		int i = 0;
		int ret;

		int internalFulfillerLocationId;
		String externalLocationId, binName, binType, binStatus;

		try {
			in = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Exception thrown: " + e);
		}

		// Disregard the first line
		line = in.nextLine();

		while (in.hasNextLine()) {
			line = in.nextLine();
			token = new StringTokenizer(line, ",");

			// while(token.hasMoreTokens()) {
			temp = token.nextToken();
			if (temp.equals("NOT USED"))
				externalLocationId = null;
			else
				externalLocationId = temp.toString();
			// externalLocationId = (new Integer(token.nextToken())).intValue();
			// externalLocationId = (new Integer(temp)).intValue();

			internalFulfillerLocationId = (new Integer(token.nextToken()))
					.intValue();
			binName = token.nextToken();
			binType = token.nextToken();
			binStatus = token.nextToken();
			// }

			if (debug) {
				System.out.println("Line: " + externalLocationId + ", "
						+ internalFulfillerLocationId + ", " + binName + ", "
						+ binType + ", " + binStatus);
			}

			int fulfillerId = -1;

			try {
				String sql = "SELECT FulfillerId FROM Location WHERE InternalFulfillerLocationId = ?";

				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, internalFulfillerLocationId);

				ResultSet r = ps.executeQuery();

				if (r.next()) {
					fulfillerId = r.getInt(1);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

			ret = apiCall.createBin(fulfillerId, null, externalLocationId,
					binType, binStatus, binName);

			// System.out.println(i);
			// i++;
			if (ret == -1)
				System.out.println("createBin() ret val: " + ret);
		}
	}

	public static void parseFulfillerInventory(String filename) {
		Scanner in = null;
		String line;
		StringTokenizer token;

		String productName, SKU, UPC, binName, externalLocationId;
		int safetyStock, manufacturerId, catalogId, onhand, internalFulfillerLocationId;
		double ltd;

		// apiCall.RefreshItem[] item;
		int binId = 0;
		try {
			in = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Exception thrown: " + e);
		}

		// Disregard the first line
		line = in.nextLine();

		while (in.hasNextLine()) {
			line = in.nextLine();
			token = new StringTokenizer(line, ",");

			// productName = token.nextToken();
			productName = "";
			SKU = token.nextToken();
			UPC = token.nextToken();
			safetyStock = (new Integer(token.nextToken())).intValue();
			ltd = (new Double(token.nextToken())).doubleValue();
			manufacturerId = (new Integer(token.nextToken())).intValue();
			catalogId = (new Integer(token.nextToken())).intValue();
			onhand = (new Integer(token.nextToken())).intValue();
			binName = token.nextToken();
			externalLocationId = token.nextToken();
			internalFulfillerLocationId = (new Integer(token.nextToken()))
					.intValue();

			// item = new apiCall.RefreshItem[1];
			// item[0] = new apiCall.RefreshItem(SKU, UPC, null, onhand, ltd,
			// safetyStock);
			int fulfillerId = -1;

			try {
				String sql = "SELECT b.Id, l.FulfillerId FROM StoreBin b, Location l "
						+ "WHERE b.Name = ? AND b.ExternalFulfillerLocationId  = ? "
						+ " AND b.ExternalFulfillerLocationId  = l.ExternalFulfillerLocationId ";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, binName);
				ps.setString(2, externalLocationId);

				ResultSet r = ps.executeQuery();

				if (r.next()) {
					binId = r.getInt(1);
					fulfillerId = r.getInt(2);
				} else {
					binId = apiCall.createBin(fulfillerId, binId,
							externalLocationId, null, null, binName);
				}
			} catch (Exception e) {
				// System.out.println("binId does not exist in the DB");
				System.out.println(e.toString());
			}

			apiCall.refreshInventory(fulfillerId, externalLocationId, SKU, UPC,
					binId, onhand, ltd, safetyStock);
		}
	}

	public static void testGetBins(int fulfillerId, String externalLocationId,
			String searchTerm, int numResults, int resultsStart) {

		System.out.println("Testing getBins");

		ArrayList<Object[]> bins = apiCall.getBins(fulfillerId,
				externalLocationId, searchTerm, numResults, resultsStart);

		/*
		 * (for(int ndx = 0; ndx < bins.size(); ndx++) { }
		 */
		System.out.println(bins.size() + " rows selected");
	}

	public static void testGetBinTypes() {
		ArrayList<String> binTypes = apiCall.getBinTypes();

		if (binTypes == null) {
			System.out.println("getBinTypes: Query failed");
			return;
		}

		System.out.println("\nTesting api call: getBinTypes()");

		/*
		 * for(int ndx = 0; ndx < binTypes.size(); ndx++) { String description =
		 * binTypes.get(ndx)[1].toString(); if(description.equals("")) {
		 * description = "<No description provided>"; }
		 * 
		 * System.out.println(binTypes.get(ndx)[0] + " " + description); }
		 */
		System.out.println(binTypes.size() + " rows selected");
	}

	public static void testGetBinStatuses() {
		ArrayList<String> binTypes = apiCall.getBinStatuses();

		if (binTypes == null) {
			System.out.println("getBinStatuses: Query failed");
			return;
		}

		System.out.println("\nTesting api call: getBinStatuses()");

		/*
		 * for(int ndx = 0; ndx < binTypes.size(); ndx++) { String description =
		 * binTypes.get(ndx)[1].toString(); if(description.equals("")) {
		 * description = "<No description provided>"; }
		 * 
		 * System.out.println(binTypes.get(ndx)[0] + " " + description); }
		 */
		System.out.println(binTypes.size() + " rows selected");
	}

	public static void testModifyInventory(int fulfillerId,
			Object[][] fulfillerLocationCatalog, Object[][] items) {

		System.out.println("Testing modification of Inventory");

		// apiCall.allocateInventory(fulfillerId, fulfillerLocationCatalog,
		// items);
		// apiCall.deallocateInventory(fulfillerId, fulfillerLocationCatalog,
		// items);
		apiCall.fulfillInventory(fulfillerId, fulfillerLocationCatalog, items);
	}

	/*
	 * public static int testCreateFulfiller() {
	 * 
	 * //Test if exists
	 * 
	 * //Test if has all right values
	 * 
	 * //Test if created and returns correct value
	 * 
	 * return 0; }
	 * 
	 * public static int testGetFulfillerStatus() { int fulfillerId = -1; if
	 * (apiCall.getFulfillerStatus(fulfillerId)) { return }
	 * 
	 * return 0; }
	 * 
	 * public static int testCreateFulfilmentLocation() {
	 * 
	 * return 0; }
	 * 
	 * public static int testGetFulfilmentLocations() {
	 * 
	 * return 0; }
	 * 
	 * public static int testGetFulfillmentLocationTypes() {
	 * 
	 * return 0; }
	 * 
	 * public static int testAllocateInventory() {
	 * 
	 * return 0; }
	 * 
	 * public static int testDeallocateInventory() {
	 * 
	 * return 0; }
	 * 
	 * public static int testFulfillInventory() {
	 * 
	 * return 0; }
	 * 
	 * public static int testCreateBin() {
	 * 
	 * return 0; }
	 * 
	 * public static int testGetBins() {
	 * 
	 * return 0; }
	 * 
	 * public static int testGetBinTypes() {
	 * 
	 * return 0; }
	 * 
	 * public static int testGetBinStatuses() {
	 * 
	 * return 0; }
	 * 
	 * public static int testAdjustInventory() {
	 * 
	 * return 0; }
	 * 
	 * public static int testRefreshInventory() {
	 * 
	 * return 0; }
	 */
	public static boolean setupConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Driver not found");
			return false;
		}

		conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/glade",
					"root", "");
			System.out.println("Connection obtained!");
		} catch (Exception e) {
			System.out.println("Could not open connection" + e);
			return false;
		}

		return true;
	}

	public static boolean closeConnection() {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		return true;
	}
}
