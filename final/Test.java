import java.sql.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Test {

   private static Connection conn;
   private static api apiCall = new api();
   private static boolean debug = false;
   private static boolean setup = false;
   private static boolean test = false;
   private static boolean cleanup = false;

   public static void main(String[] args) {

      if (args.length > 0 && args[0].equals("setup")) {
         setupConnection();
         createDatabase();

         parseFulfillerLocations("fulfiller locations.csv");
         parseLocationBins("fulfiller location_bins.csv");
         parseFulfillerInventory("fulfiller inventory available bins.csv");
         parseFulfillerInventory("fulfiller inventory available.csv");
         parseFulfillerInventory("fulfiller inventory not available.csv");
         closeConnection();
      }

      if(args.length == 7 && args[0].equals("createBin")) {
         if(!createBinTest(Integer.parseInt(args[1]),
              nillableInt(args[2]), args[3], args[4], args[5],
              nillableStr(args[6])) ) {
            System.out.println("createBin failed");
         }
         else {
            System.out.println("createBin succeeded");
         }
      }
      else if(args.length == 6 && args[0].equals("getBins")) {
         if(!getBinsTest(Integer.parseInt(args[1]), args[2],
              nillableStr(args[3]), nillableInt(args[4]),
              nillableInt(args[5])) )  {
            System.out.println("getBins failed");
         }
      }
      else if(args.length == 1 && args[0].equals("getBinTypes")) {
         if(!getBinTypesTest()) {
            System.out.println("getBinTypes failed");
         }
      }
      else if(args.length == 1 && args[0].equals("getBinStatuses")) {
         if(!getBinStatusesTest()) {
            System.out.println("getBinStatuses failed");
         }
      }
      else if(args.length == 6 && args[0].equals("allocateInventory")) {
         if(!allocateInventoryTest(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), nillableStr(args[5])) ) {
            System.out.println("allocateInventory failed");
         }
      }
      else if(args.length == 6 && args[0].equals("deallocateInventory")) {
         if(!deallocateInventoryTest(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), nillableStr(args[5])) ) {
            System.out.println("deallocateInventory failed");
         }
      }
      else if(args.length == 6 && args[0].equals("fulfillInventory")) {
         if(!fulfillInventoryTest(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), nillableStr(args[5])) ) {
            System.out.println("fulfillInventory failed");
         }
     }
      else if(args.length == 10 && args[0].equals("getInventory")) {
         if(!getInventoryTest(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), args[5],
              Integer.parseInt(args[6]), nillableBoolean(args[7]),
              nillableBoolean(args[8]), Boolean.parseBoolean(args[9])) ) {
            System.out.println("getInventory failed");
         }
      }
      else if(args.length == 13 && args[0].equals("getInventory2")) {
         if(!getInventory2Test(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), args[5],
              args[6], Integer.parseInt(args[7]), args[8],
              Integer.parseInt(args[9]), nillableBoolean(args[10]),
              nillableBoolean(args[11]), Boolean.parseBoolean(args[12])) ) {
            System.out.println("getInventory2 failed");
         }
      }
      else if(args.length == 12 && args[0].equals("getInventoryLocationIds")) {
         if(!getInventoryLocationIdsTest(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), args[5], args[6], args[7],
              Integer.parseInt(args[8]), nillableBoolean(args[9]),
              nillableBoolean(args[10]), Boolean.parseBoolean(args[11])) ) {
            System.out.println("getInventoryLocationIds failed");
         }
      }
      else if(args.length == 15 && args[0].equals("getInventory2LocationIds")) {
         if(!getInventory2LocationIdsTest(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), args[5],
              args[6], Integer.parseInt(args[7]), args[8], args[9], args[10],
              Integer.parseInt(args[11]), nillableBoolean(args[12]),
              nillableBoolean(args[13]), Boolean.parseBoolean(args[14])) ) {
            System.out.println("getInventory2LocationIds failed");
         }
      }
      else if(args.length == 16 && args[0].equals("getInventoryRequestLocation")) {
         if(!getInventoryRequestLocationTest(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), args[5],
              Integer.parseInt(args[6]), args[7], Float.parseFloat(args[8]),
              Float.parseFloat(args[9]), args[10], args[11],
              Integer.parseInt(args[12]), nillableBoolean(args[13]),
              nillableBoolean(args[14]), Boolean.parseBoolean(args[15])) ) {
            System.out.println("getInventoryRequestLocation failed");
         }
      }
      else if(args.length == 19 && args[0].equals("getInventory2RequestLocation")) {
         if(!getInventory2RequestLocationTest(Integer.parseInt(args[1]), args[2],
              args[3], Integer.parseInt(args[4]), args[5],
              args[6], Integer.parseInt(args[7]), args[8],
              Integer.parseInt(args[9]), args[10], Float.parseFloat(args[11]),
              Float.parseFloat(args[12]), args[13], args[14],
              Integer.parseInt(args[15]), nillableBoolean(args[16]),
              nillableBoolean(args[17]), Boolean.parseBoolean(args[18])) ) {
            System.out.println("getInventory2RequestLocation failed");
         }
      }
	  else if (args.length == 3 && args[0].equals("createFulfiller")) {
		 apiCall.setUpConnection();
		 if (!testCreateFulfiller(Integer.parseInt(args[1]), args[2])) {
			 System.out.println("createFulfiller failed");
		 }	  
		 apiCall.closeConnection();
	  }
	  else if (args.length == 2 && args[0].equals("getFulfillerStatus")) {
		 if (!testGetFulfillerStatus(Integer.parseInt(args[1]))) {
			 System.out.println("getFulfillerStatus failed");
		 }
	  }
	  else if (args.length == 10 && args[0].equals("createFulfillmentLocation")) {
		 if (!testCreateFulfillmentLocation(Integer.parseInt(args[1]),Integer.parseInt(args[2]),
			 args[3], args[4], args[5], Double.parseDouble(args[6]), Double.parseDouble(args[7]),
			 args[8], args[9])) {
			System.out.println("createFulfillmentLocation failed");
		 }
	  }
	  else if (args.length == 11 && args[0].equals("getFulfillmentLocations")) {
		 if (!testGetFulfillmentLocations(Integer.parseInt(args[1]), Float.parseFloat(args[2]),
			Float.parseFloat(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]),
			args[6], Integer.parseInt(args[7]), Integer.parseInt(args[8]), args[9],
			Integer.parseInt(args[10]))) {
			 System.out.println("getFulfillmentLocations failed");
		 }
	  }
	  else if (args.length == 1 && args[0].equals("getFulfillmentLocationTypes")) {
		 if (!testGetFulfillmentLocationTypes()) {
			System.out.println("getFulfillmentLocationTypes failed");
		 }
	  }
	  else if (args.length == 11 && args[0].equals("adjustInventory")) {
		 if (!testAdjustInventory(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
			Integer.parseInt(args[3]), Double.parseDouble(args[4]), Integer.parseInt(args[5]),
			args[6], args[7], Integer.parseInt(args[8]), args[9], Integer.parseInt(args[10]))) {
			 System.out.println("adjustInventory failed");
		 }
	  }
	  else if (args.length == 9 && args[0].equals("refreshInventory")) {
		 if (!testRefreshInventory(Integer.parseInt(args[1]), args[2], args[3],
			args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]),
			Double.parseDouble(args[7]), Integer.parseInt(args[8]))) {
			 System.out.println("refreshInventory failed");
		 }
	  }

      if (args.length > 0 && args[0].equals("cleanup")) {
         setupConnection();
         clearDatabase();
         destroyDatabase();
         closeConnection();
      }
   }

   public static Boolean nillableBoolean(String str) {
      if(str.equalsIgnoreCase("false")) {
         return false;
      }
      else if(str.equalsIgnoreCase("true")) {
         return true;
      }

      return null;
   }

   public static String nillableStr(String str) {
      return str.equals("null") ? null : str;
   }

   public static Integer nillableInt(String str) {
      return str.equals("null") ? null : new Integer(str);
   }

    public static boolean createBinTest(int fulfillerId, Integer binId, String externalLocationId, String binType, String binStatus, String name) {
      return apiCall.createBin(fulfillerId, binId, externalLocationId,
       binStatus, binType, name) >= 0;
   }

   public static boolean getBinsTest(int fulfillerId,
    String externalLocationId, String searchTerm, Integer numResults,
    Integer resultsStart) {
      ArrayList<Object[]> bins = apiCall.getBins(fulfillerId,
       externalLocationId, searchTerm, numResults, resultsStart);

      for(int ndx = 0; bins != null && ndx < bins.size(); ndx++) {
         Object[] item = bins.get(ndx);
         System.out.println("\nBinResponse #" + (ndx+1));
         System.out.println("FulfillerId: " + item[0]);
         System.out.println("BinId: " + item[1]);
         System.out.println("ExternalLocationId: " + item[2]);
         System.out.println("BinType: " + item[3]);
         System.out.println("BinStatus: " + item[4]);
         System.out.println("Name: " + item[5]);
      }

      System.out.println("\nTotal bins returned: " + bins.size());

      return bins != null;
   }

   public static boolean getBinTypesTest() {
      ArrayList<String> binTypes = apiCall.getBinTypes();

      System.out.println("\nBinTypesResponses: " + binTypes.size());

      for(int ndx = 0; binTypes != null && ndx < binTypes.size(); ndx++) {
         System.out.println("BinType: " + binTypes.get(ndx));
      }

      System.out.println("Total bin types returned: " + binTypes.size());

      return binTypes != null && binTypes.size() > 0;
   }

   public static boolean getBinStatusesTest() {
      ArrayList<String> binStatuses = apiCall.getBinStatuses();

      System.out.println("\nBinStatusessResponses: " + binStatuses.size());

      for(int ndx = 0; binStatuses != null && ndx < binStatuses.size(); ndx++) {
         System.out.println("BinType: " + binStatuses.get(ndx));
      }

      System.out.println("Total bin Statuses returned: " + binStatuses.size());

      return binStatuses != null && binStatuses.size() > 0;
   }

   public static boolean allocateInventoryTest(int fulfillerId,
    String partNumber, String upc, int quantity, String externalLocationId) {
      Object[][] fulfillerLocationCatalog = null;
      Object[][] items = {{partNumber, upc, quantity, externalLocationId}};
      apiCall.allocateInventory(fulfillerId, fulfillerLocationCatalog, items);

      return true;
   }

   public static boolean deallocateInventoryTest(int fulfillerId,
    String partNumber, String upc, int quantity, String externalLocationId) {
      Object[][] fulfillerLocationCatalog = null;
      Object[][] items = {{partNumber, upc, quantity, externalLocationId}};
      apiCall.deallocateInventory(fulfillerId, fulfillerLocationCatalog, items);

      return true;
   }

   public static boolean fulfillInventoryTest(int fulfillerId,
    String partNumber, String upc, int quantity, String externalLocationId) {
      Object[][] fulfillerLocationCatalog = null;
      Object[][] items = {{partNumber, upc, quantity, externalLocationId}};
      apiCall.fulfillInventory(fulfillerId, fulfillerLocationCatalog, items);

      return true;
   }

   public static void printInventory(ArrayList<Object[]> inventory) {

      for(int ndx = 0; inventory != null && ndx < inventory.size(); ndx++) {
         Object[] item = inventory.get(ndx);
         System.out.println("\nResponse #" + (ndx+1));
         System.out.println("LocationName: " + item[0]);
         System.out.println("CatalogId: " + item[1]);
         System.out.println("ManufacturerId: " + item[2]);
         System.out.println("OnHand: " + item[3]);
         System.out.println("Available: " + item[4]);
         System.out.println("PartNumber: " + item[5]);
         System.out.println("UPC: " + item[6]);
         System.out.println("LTD: " + item[7]);
         System.out.println("SafetyStock: " + item[8]);
         System.out.println("CountryCode: " + item[9]);
         System.out.println("Distance: " + item[10]);
      }

      if(inventory != null && inventory.size() == 0) {
         System.out.println("No inventory returned");
      }
   }

   public static boolean getInventoryTest(int fulfillerId, String partNumber,
    String upc, int quantity, String type, int limit, Boolean ignoreSafetyStock,
    Boolean includeNegativeInventory, boolean orderByLtd) {
      Object[][] quantities = {{partNumber, upc, quantity}};

      ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId, null,
       quantities, null, null, type, limit, ignoreSafetyStock,
       includeNegativeInventory, orderByLtd);

      printInventory(inventory);

      return inventory != null;
   }

   public static boolean getInventory2Test(int fulfillerId, String partNumber,
    String upc, int quantity, String partNumber2, String upc2, int quantity2,
    String type, int limit, Boolean ignoreSafetyStock,
    Boolean includeNegativeInventory, boolean orderByLtd) {
      Object[][] quantities = {{partNumber, upc, quantity},
                               {partNumber2, upc2, quantity2}};

      ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId, null,
       quantities, null, null, type, limit, ignoreSafetyStock,
       includeNegativeInventory, orderByLtd);

      printInventory(inventory);

      return inventory != null;
   }

   public static boolean getInventoryLocationIdsTest(int fulfillerId,
    String partNumber, String upc, int quantity, String location,
    String location2, String type, int limit, Boolean ignoreSafetyStock,
    Boolean includeNegativeInventory, boolean orderByLtd) {
      Object[][] quantities = {{partNumber, upc, quantity}};
      String[] locationIds = {location, location2};

      ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId, null,
       quantities, locationIds, null, type, limit, ignoreSafetyStock,
       includeNegativeInventory, orderByLtd);

      printInventory(inventory);

      return inventory != null;
   }

   public static boolean getInventory2LocationIdsTest(int fulfillerId,
    String partNumber, String upc, int quantity, String partNumber2,
    String upc2, int quantity2,String location, String location2, String type,
    int limit, Boolean ignoreSafetyStock, Boolean includeNegativeInventory,
    boolean orderByLtd) {
      Object[][] quantities = {{partNumber, upc, quantity},
                               {partNumber2, upc2, quantity2}};
      String[] locationIds = {location, location2};

      ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId, null,
       quantities, locationIds, null, type, limit, ignoreSafetyStock,
       includeNegativeInventory, orderByLtd);

      printInventory(inventory);

      return inventory != null;
   }

   public static boolean getInventoryRequestLocationTest(int fulfillerId,
    String partNumber, String upc, int quantity, String unit, int radius,
    String postalCode, float latitude, float longitude, String countryCode,
    String type, int limit, Boolean ignoreSafetyStock,
    Boolean includeNegativeInventory, boolean orderByLtd) {
      Object[][] quantities = {{partNumber, upc, quantity}};
      Object[] location = {unit, radius, postalCode, latitude, longitude,
                           countryCode};

      ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId, null,
       quantities, null, location, type, limit, ignoreSafetyStock,
       includeNegativeInventory, orderByLtd);

      printInventory(inventory);

      return inventory != null;
   }

   public static boolean getInventory2RequestLocationTest(int fulfillerId,
    String partNumber, String upc, int quantity, String partNumber2,
    String upc2, int quantity2, String unit, int radius,
    String postalCode, float latitude, float longitude, String countryCode,
    String type, int limit, Boolean ignoreSafetyStock,
    Boolean includeNegativeInventory, boolean orderByLtd) {
      Object[][] quantities = {{partNumber, upc, quantity},
                               {partNumber2, upc2, quantity2}};
      Object[] location = {unit, radius, postalCode, latitude, longitude,
                           countryCode};

      ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId, null,
       quantities, null, location, type, limit, ignoreSafetyStock,
       includeNegativeInventory, orderByLtd);

      printInventory(inventory);

      return inventory != null;
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

   public static boolean testCreateFulfiller(int fulfillerId, String locationName) {
      int returnFulfiller = apiCall.createFulfiller(fulfillerId, locationName);
	  System.out.println("FulfillerId: " +returnFulfiller);
      return returnFulfiller >= 0;
   }

   public static boolean testGetFulfillerStatus(int fulfillerId) { 
      int returnFulfiller = apiCall.getFulfillerStatus(fulfillerId);
	  System.out.println("FulfillerId: " +returnFulfiller);
      return returnFulfiller >=0;
   } 

   public static boolean testCreateFulfillmentLocation(int fulfillerId,
         int internalFulfillerLocationId, String externalLocationId,
         String locationName, String type, double latitude,
         double longitude, String status, String countryCode) {
		  
	  System.out.println("FulfillerId: " +fulfillerId);
	  System.out.println("InternalFulfillerLocationId: " +internalFulfillerLocationId);
	  System.out.println("ExternalLocationId: " +externalLocationId);
	  System.out.println("LocationName: " +locationName);
	  System.out.println("Type: " +type);
	  System.out.println("Latitude: " +latitude);
	  System.out.println("Longitude: " +longitude);
	  System.out.println("Status: " +status);
	  System.out.println("CountryCode: " +countryCode);
	  
      int returnVal = apiCall.createFulfillmentLocation(fulfillerId, internalFulfillerLocationId,
	   externalLocationId, locationName, type, latitude, longitude, status, countryCode);
	   
	  return returnVal >= 0;
   }

   public static boolean testGetFulfillmentLocations(int fulfillerId, Float latitude,
		Float longitude, int manuOne, int manuTwo, String reqOne, int reqTwo,
		int reqThree, String reqFour, int maxLocations) {
      Object[] manufacturerCatalog = {manuOne, manuTwo};
      Object[] requestLocation = {reqOne, reqTwo, reqThree, latitude, longitude, reqFour};

      ArrayList<Object[]> results = apiCall.getFulfillmentLocations(fulfillerId, manufacturerCatalog, requestLocation, maxLocations);
 
	  if (results == null) return false;
	  
	  for (int i = 0; i < results.size(); i++) {
	     System.out.println("Location: " +results.get(i)[0] +", " +results.get(i)[1]);
		 System.out.println("Distance: " +results.get(i)[2]);
	  }
	  
      return true;
   }

   public static boolean testGetFulfillmentLocationTypes() {
      ArrayList<String> types = apiCall.getFulfillmentLocationTypes();

	  if (types == null) return false;
	  
	  for (int i = 0; i < types.size(); i++) {
		System.out.println("Type: " +types.get(i));
	  }
	  
      return true;
   }

   public static boolean testAdjustInventory(int internalFulfillerLocationId, 
		int binId, int onhand, double ltd, int safetyStock, String UPC, 
		String partnum, int fulfillerid, String externalocation, int adjust) {
	  
	  int returnVal = apiCall.adjustInventory(internalFulfillerLocationId, binId, onhand, ltd, 
		safetyStock, UPC, partnum, fulfillerid, externalocation, adjust);
			
	  System.out.println("InternalFulfillerLocationId: " +internalFulfillerLocationId);
	  System.out.println("BinId: " +binId);
	  System.out.println("Onhand: " +onhand);
	  System.out.println("LTD: " +ltd);
	  System.out.println("SafetyStock: " +safetyStock);
	  System.out.println("UPC: " +UPC);
	  System.out.println("Partnum: " +partnum);
	  System.out.println("FulfillerId: " +fulfillerid);
	  System.out.println("ExternalLocation: " +externalocation);
	  
      return returnVal >= 0;
   }

   public static boolean testRefreshInventory(int fulfillerId, String externalLocId,
         String SKU, String UPC, int binId, int onhand, double ltd, int safetyStock) {
      int returnVal = apiCall.refreshInventory(fulfillerId, externalLocId, SKU,
		UPC, binId, onhand, ltd, safetyStock);
      return returnVal >= 0; 
   }

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
