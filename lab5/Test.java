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
      
      if(setup)
      {
         createDatabase();
      
         parseFulfillerLocations("fulfiller locations.csv");
         parseLocationBins("fulfiller location_bins.csv");
         parseFulfillerInventory("fulfiller inventory available bins.csv");
         parseFulfillerInventory("fulfiller inventory available.csv");
         parseFulfillerInventory("fulfiller inventory not available.csv");
      }

      if(test) {
         Object[][] fulfillerLocationCatalog = {{new Integer(10636), new Integer(0)}, {new Integer(600)}};
         Object[][] items = {{"200033103", "200033103", new Integer(1), new Integer(600)}, {"201279746", "201279746", new Integer(1), new Integer(600)}};
         //testModifyInventory(48590, fulfillerLocationCatalog, items);
         testGetBins(48590, "600", "", 100000, 0);
         testGetBinTypes();
         testGetBinStatuses();

         /*int fulfillerId = 48590;
         int[] manCatalog = {11416, 0};
         Object[][] quantities = {{"200235977", "200235977", 1},
                                  {"200235976", "200235976", 1}};
         String[] locationIds = {};
         Object[] location = null;
         String type = "ALL";
         int limit = 1000;
         Boolean ignoreSafetyStock = true;
         Boolean includeNegativeInventory = true;
         boolean orderByLtd = true;*/
         /*int fulfillerId = 76061;
         int[] manCatalog = {10636, 1};
         Object[][] quantities = {{"22-14582-001", "22-14582-001", 1},
                                  {"22-14582-002", "22-14582-002", 1}};
         String[] locationIds = {};
         Object[] location = {}
         String type = "ANY";
         int limit = 1000;
         Boolean ignoreSafetyStock = null;
         Boolean includeNegativeInventory = null;
         boolean orderByLtd = false;*/
      
         int fulfillerId = 69170;
         int[] manCatalog = null;
         Object[][] quantities = {{"8888069843", "8888069843", 1},
                                  {"8888074813", "8888074813", 1},
                                   {"8888052689", "8888052689", 1}};
         String[] locationIds = {};
         Object[] location = {"MILES", 100, 0, new Float(40.742300),
         new Float(-73.987900), "USA"};
         String type = "ALL";
         int limit = 1000;
         Boolean ignoreSafetyStock = null;
         Boolean includeNegativeInventory = null;
         boolean orderByLtd = false;

         System.out.println("Testing getInventory");
         ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
          manCatalog, quantities, locationIds, location, type, limit,
          ignoreSafetyStock, includeNegativeInventory, orderByLtd);
         System.out.println(inventory.size() + " results");

			/*
				TESTS HERE
				if (testCreateFulfiller())
					System.out.println("createFulfiller failed");
			*/
      }

      if(cleanup)
      {
         clearDatabase();
         destroyDatabase();
      }
      
      closeConnection();
   }

   public static void createDatabase() {
      Scanner in = null;
      String text = "", sqlCmd = "";
      StringTokenizer token;
      Statement st = null;
      
      try {
         in = new Scanner(new File("DB-setup.sql"));
      }
      catch(FileNotFoundException e) {
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
         
         if(debug)
            System.out.println("Create statement: " + sqlCmd);

         st.executeBatch();
      }
      catch (Exception e) {
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
      }
      catch(FileNotFoundException e) {
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
      }
      catch (Exception e) {
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
      }
      catch(FileNotFoundException e) {
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
      }
      catch (Exception e) {
         System.out.println("Error occured while destroying DB tables");
      }

   }


public static void parseFulfillerLocations(String filename) {
Scanner in = null;
String line, temp;
StringTokenizer token;

int fulfillerId, internalFulfillerLocationId,
manufacturerId, catalogId, safetyStockLimit;
      String externalLocationId, locationName, locationType, description,
         status;
       double latitude, longitude;

try {
         in = new Scanner(new File(filename));
      }
      catch (FileNotFoundException e) {
         System.out.println("Exception thrown: " + e);
      }
     
      //Disregard the first line
      line = in.nextLine();
     
      while (in.hasNextLine()) {
         line = in.nextLine();
      token = new StringTokenizer(line, ",");
      
      locationName = token.nextToken();
         temp = token.nextToken();
         
         if(locationName.contains("\"") && temp.contains("\"")) {
            fulfillerId = (new Integer(token.nextToken())).intValue();
            locationName += temp;
         }
         else
            fulfillerId = (new Integer(temp)).intValue();
            
         externalLocationId = token.nextToken();
         internalFulfillerLocationId = (new Integer(token.nextToken())).intValue();
         description = token.nextToken();
         latitude = (new Double(token.nextToken())).doubleValue();
         longitude = (new Double(token.nextToken())).doubleValue();
         status = token.nextToken();
         safetyStockLimit = (new Integer(token.nextToken())).intValue();
         manufacturerId = (new Integer(token.nextToken())).intValue();
         catalogId = (new Integer(token.nextToken())).intValue();
         
         if(debug) {
            System.out.println("Line: " + locationName + ", " + fulfillerId +
               ", " + externalLocationId + ", " + internalFulfillerLocationId +
               ", " + description + ", " + latitude + ", " + longitude +
               ", " + status + ", " + safetyStockLimit + ", " + manufacturerId +
               ", " + catalogId);
         }

         apiCall.createFulfillmentLocation(fulfillerId, internalFulfillerLocationId, externalLocationId, locationName, null, latitude, longitude, status, null);
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
      }
      catch (FileNotFoundException e) {
         System.out.println("Exception thrown: " + e);
      }
     
      //Disregard the first line
      line = in.nextLine();
     
      while (in.hasNextLine()) {
         line = in.nextLine();
      token = new StringTokenizer(line, ",");
           
           //while(token.hasMoreTokens()) {
              temp = token.nextToken();
              if(temp.equals("NOT USED"))
                 externalLocationId = null;
              else
		 externalLocationId = temp.toString();
                 //externalLocationId = (new Integer(token.nextToken())).intValue();
                 //externalLocationId = (new Integer(temp)).intValue();

      internalFulfillerLocationId = (new Integer(token.nextToken())).intValue();
      binName = token.nextToken();
      binType = token.nextToken();
      binStatus = token.nextToken();
           //}

         if(debug) {
            System.out.println("Line: " + externalLocationId +
               ", " + internalFulfillerLocationId + ", " + binName +
               ", " + binType + ", " + binStatus);
         }
     
        int fulfillerId = -1;

        try {
           String sql = "SELECT FulfillerId FROM Location WHERE InternalFulfillerLocationId = ?";

           PreparedStatement ps = conn.prepareStatement(sql);
           ps.setInt(1, internalFulfillerLocationId);

           ResultSet r = ps.executeQuery();

           if(r.next()) {
              fulfillerId = r.getInt(1);
           }
        }
        catch(Exception e) {
           System.out.println(e.toString());
        }



	ret = apiCall.createBin(fulfillerId, null, externalLocationId,
         binType, binStatus, binName);

            //System.out.println(i);
            //i++;
            if(ret == -1)
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
      }
      catch (FileNotFoundException e) {
         System.out.println("Exception thrown: " + e);
      }
     
      //Disregard the first line
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
      internalFulfillerLocationId = (new Integer(token.nextToken())).intValue();
     
               // item = new apiCall.RefreshItem[1];
               // item[0] = new apiCall.RefreshItem(SKU, UPC, null, onhand, ltd, safetyStock);
      int fulfillerId = -1;      

      try {
         String sql = "SELECT b.Id, l.FulfillerId FROM StoreBin b, Location l " +
                      "WHERE b.Name = ? AND b.ExternalFulfillerLocationId  = ? " +
                       " AND b.ExternalFulfillerLocationId  = l.ExternalFulfillerLocationId ";
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setString(1, binName);
         ps.setString(2, externalLocationId);

         ResultSet r = ps.executeQuery();

         if(r.next()) {
            binId = r.getInt(1);
            fulfillerId = r.getInt(2);
         }
         else {
            binId = apiCall.createBin(fulfillerId, binId, externalLocationId, null, null, binName);
         }
      }
      catch(Exception e) {
         //System.out.println("binId does not exist in the DB");
         System.out.println(e.toString());
      }

      apiCall.refreshInventory(fulfillerId, externalLocationId, SKU, UPC, binId, onhand, ltd, safetyStock);
      }
}

   public static void testGetBins(int fulfillerId, String externalLocationId, String searchTerm, int numResults, int resultsStart) {
      
      System.out.println("Testing getBins");

      ArrayList<Object[]> bins = apiCall.getBins(fulfillerId, externalLocationId, searchTerm, numResults, resultsStart);

      /*(for(int ndx = 0; ndx < bins.size(); ndx++) {
}*/
      System.out.println(bins.size() + " rows selected");
   }

   public static void testGetBinTypes() {
      ArrayList<String> binTypes = apiCall.getBinTypes();

      if(binTypes == null) {
         System.out.println("getBinTypes: Query failed");
         return;
      }

      System.out.println("\nTesting api call: getBinTypes()");

      /*for(int ndx = 0; ndx < binTypes.size(); ndx++) {
String description = binTypes.get(ndx)[1].toString();
if(description.equals("")) {
description = "<No description provided>";
}

System.out.println(binTypes.get(ndx)[0] + " " + description);
}*/
      System.out.println(binTypes.size() + " rows selected");
   }

   public static void testGetBinStatuses() {
      ArrayList<String> binTypes = apiCall.getBinStatuses();

      if(binTypes == null) {
         System.out.println("getBinStatuses: Query failed");
         return;
      }

      System.out.println("\nTesting api call: getBinStatuses()");

      /*for(int ndx = 0; ndx < binTypes.size(); ndx++) {
String description = binTypes.get(ndx)[1].toString();
if(description.equals("")) {
description = "<No description provided>";
}

System.out.println(binTypes.get(ndx)[0] + " " + description);
}*/
      System.out.println(binTypes.size() + " rows selected");
   }
 
   public static void testModifyInventory(int fulfillerId, Object[][] fulfillerLocationCatalog, Object[][] items) {

      System.out.println("Testing modification of Inventory");

      //apiCall.allocateInventory(fulfillerId, fulfillerLocationCatalog, items);
      //apiCall.deallocateInventory(fulfillerId, fulfillerLocationCatalog, items);
      apiCall.fulfillInventory(fulfillerId, fulfillerLocationCatalog, items);
   } 

/*	public static int testCreateFulfiller() {
		
		//Test if exists

		//Test if has all right values

		//Test if created and returns correct value

		return 0;
	}
	
	public static int testGetFulfillerStatus() {
		
		return 0;
	}
	
	public static int testCreateFulfilmentLocation() {
		
		return 0;
	}
	
	public static int testGetFulfilmentLocations() {
		
		return 0;
	}
	
	public static int testGetFulfillmentLocationTypes() {
		
		return 0;
	}
	
	public static int testAllocateInventory() {
		
		return 0;
	}
	
	public static int testDeallocateInventory() {
		
		return 0;
	}

	public static int testFulfillInventory() {
		
		return 0;
	}
	
	public static int testCreateBin() {
		
		return 0;
	}

	public static int testGetBins() {
		
		return 0;
	}		
	
	public static int testGetBinTypes() {
		
		return 0;
	}

	public static int testGetBinStatuses() {
		
		return 0;
	}		

	public static int testAdjustInventory() {
		
		return 0;
	}

	public static int testRefreshInventory() {
		
		return 0;
	}
*/
   public static boolean setupConnection() {
      try
      {
         Class.forName("com.mysql.jdbc.Driver");
      }
      catch(Exception e)
      {
         System.out.println("Driver not found");
         return false;
      }
      
      conn = null;
      
      try
      {
         conn = DriverManager.getConnection("jdbc:mysql://localhost/glade", "root", "");
         System.out.println("Connection obtained!");
      }
      catch(Exception e)
      {
         System.out.println("Could not open connection" + e);
         return false;
      }
      
      return true;
   }
   
   public static boolean closeConnection() {
      try
      {
         conn.close();
      }
      catch(Exception e)
      {
         System.out.println(e);
         return false;
      }
      
      return true;
   }
}
