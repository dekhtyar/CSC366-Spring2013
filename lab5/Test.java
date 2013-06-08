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


      //Object[][] fulfillerLocationCatalog = {{}};
      //Object[][] items = {{"SKU", "UPC", new Integer(1), new Integer(0)}};
      //testAllocateInventory(0, fulfillerLocationCatalog, items);
      //testGetBins(54802, "", 100000, 10);
      //testGetBinTypes(48590);
      //testGetBinStatuses(48590);

      if(test) {
         int fulfillerId = 48590;
         int[] manCatalog = {11416, 0};
         Object[][] quantities = {{"200235977", "200235977", 1},
                                  {"200235976", "200235976", 1}};
         String[] locationIds = {};
         Object[] location = null;
         String type = "ALL";
         int limit = 1000;
         Boolean ignoreSafetyStock = null;
         Boolean includeNegativeInventory = null;
         boolean orderByLtd = false;
         /*int fulfillerId = 69170;
         int[] manCatalog = {11416, 0};
         Object[][] quantities = {{"8888076828", "8888076828", 1}};
         String[] locationIds = {};
         Object[] location = null;
         String type = "ANY";
         int limit = 1000;
         Boolean ignoreSafetyStock = null;
         Boolean includeNegativeInventory = null;
         boolean orderByLtd = false;*/
      
         System.out.println("Testing getInventory");
         ArrayList<Object[]> inventory = apiCall.getInventory(fulfillerId,
          manCatalog, quantities, locationIds, location, type, limit,
          ignoreSafetyStock, includeNegativeInventory, orderByLtd);
         System.out.println(inventory.size() + " results");
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
     
apiCall.createFulfillmentLocation(locationName, fulfillerId, externalLocationId,
            internalFulfillerLocationId, description, latitude, longitude,
            status, safetyStockLimit, manufacturerId, catalogId);
      }

}

public static void parseLocationBins(String filename) {
Scanner in = null;
String line, temp;
StringTokenizer token;
                int i = 0;
      int ret;

int internalFulfillerLocationId, externalLocationId;
String binName, binType, binStatus;

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
                 externalLocationId = 0;
              else
                 //externalLocationId = (new Integer(token.nextToken())).intValue();
                 externalLocationId = (new Integer(temp)).intValue();

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
     
ret = apiCall.createBin(externalLocationId, null, internalFulfillerLocationId,
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
      
      try {
         String sql = "SELECT Id FROM StoreBin " +
                      "WHERE Name = ? AND InternalFulfillerLocationId = ?";
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setString(1, binName);
         ps.setInt(2, internalFulfillerLocationId);

         ResultSet r = ps.executeQuery();

         if(r.next()) {
            binId = r.getInt(1);
         }
         else {
            binId = apiCall.createBin(0, binId, internalFulfillerLocationId, null, null, binName);
         }
      }
      catch(Exception e) {
         //System.out.println("binId does not exist in the DB");
         System.out.println(e.toString());
      }
			   
      apiCall.refreshInventory(internalFulfillerLocationId, externalLocationId, SKU, UPC, binId, onhand, ltd, safetyStock);
      }
}

   public static void testGetBins(int fulfillerLocationId, String searchTerm, int numResults, int resultsStart) {
      ArrayList<Object[]> bins = apiCall.getBins(fulfillerLocationId, searchTerm, numResults, resultsStart);

      /*(for(int ndx = 0; ndx < bins.size(); ndx++) {
}*/
      System.out.println(bins.size() + " rows selected");
   }

   public static void testGetBinTypes(int fulfillerId) {
      ArrayList<String> binTypes = apiCall.getBinTypes(fulfillerId);

      if(binTypes == null) {
         System.out.println("getBinTypes: Query failed");
         return;
      }

      System.out.println("\nTesting api call: getBinTypes("+fulfillerId+")");

      /*for(int ndx = 0; ndx < binTypes.size(); ndx++) {
String description = binTypes.get(ndx)[1].toString();
if(description.equals("")) {
description = "<No description provided>";
}

System.out.println(binTypes.get(ndx)[0] + " " + description);
}*/
      System.out.println(binTypes.size() + " rows selected");
   }

   public static void testGetBinStatuses(int fulfillerId) {
      ArrayList<String> binTypes = apiCall.getBinStatuses(fulfillerId);

      if(binTypes == null) {
         System.out.println("getBinStatuses: Query failed");
         return;
      }

      System.out.println("\nTesting api call: getBinStatuses("+fulfillerId+")");

      /*for(int ndx = 0; ndx < binTypes.size(); ndx++) {
String description = binTypes.get(ndx)[1].toString();
if(description.equals("")) {
description = "<No description provided>";
}

System.out.println(binTypes.get(ndx)[0] + " " + description);
}*/
      System.out.println(binTypes.size() + " rows selected");
   }
 
   public static void testAllocateInventory(int fulfillerId, Object[][] fulfillerLocationCatalog, Object[][] items) {
      apiCall.allocateInventory(fulfillerId, fulfillerLocationCatalog, items);
   } 

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
