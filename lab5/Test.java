import java.sql.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Test {

   private static Connection conn;
   private static api apiCall= new api();
   private static boolean debug = false;

	public static void main(String[] args) {
      
      setupConnection();
      
      //createDatabase();
      
      //parseFulfillerLocations("fulfiller locations.csv");
      //parseLocationBins("fulfiller location_bins.csv");
      //parseFulfillerInventory("fulfiller inventory available bins.csv");
      //parseFulfillerInventory("fulfiller inventory available.csv");
      //parseFulfillerInventory("fulfiller inventory not available.csv");
      
      testGetBins(54802, "", 100000, 10);
      //testGetBinTypes(48590);
      //testGetBinStatuses(48590);

      //clearDatabase();
      //destroyDatabase();
      
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
     	
			ret = apiCall.createBin(externalLocationId, 0, internalFulfillerLocationId,
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

              //  apiCall.RefreshItem[] item;
		
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
     		
     	//	productName = token.nextToken();
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

     		apiCall.refreshInventory(internalFulfillerLocationId, externalLocationId, SKU, UPC, 0, onhand, ltd,
                  safetyStock);
     	}
	}

   public static void testGetBins(int fulfillerLocationId, String searchTerm, int numResults, int resultsStart) {
      ArrayList<Object[]> bins = apiCall.getBins(fulfillerLocationId, searchTerm, numResults, resultsStart);

      /*(for(int ndx = 0; ndx < bins.size(); ndx++) {
           
      }*/
      System.out.println(bins.size() + " rows selected");
   }

   public static void testGetBinTypes(int fulfillerId) {
      ArrayList<Object[]> binTypes = apiCall.getBinTypes(fulfillerId);

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
      ArrayList<Object[]> binTypes = apiCall.getBinStatuses(fulfillerId);

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
