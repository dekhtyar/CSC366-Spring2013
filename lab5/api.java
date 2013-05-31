/*
 * Team GLADE
 * Lab 5
 * api.java
 */

import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class api {
   // The Connection var needed to connect to the database
   private Connection conn;
   private ArrayList<Integer> ids = new ArrayList<Integer>();
   private ArrayList<Integer> mIds = new ArrayList<Integer>();
   private ArrayList<Integer> cIds = new ArrayList<Integer>();
   private ArrayList<Integer> intFulLocIds = new ArrayList<Integer>();

   public class RefreshItem {
        String partNumber;
        String UPC;
        int BinID;
        int Quantity;
        double LTD;
        int SafetyStock;
      
        public RefreshItem(String partNumber, String UPC, int BinID,
           int Quantity, double LTD, int SafetyStock)
         {
           this.partNumber = partNumber;
           this.UPC = UPC;
           this.BinID = BinID;
           this.Quantity = Quantity;
           this.LTD = LTD;
           this.SafetyStock = SafetyStock;
         }
    }

   public boolean setUpConnection()
   {
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
      }
      catch(Exception e)
      {
         System.out.println("Could not open connection");
         return false;
      }

      return true;
   }

   public boolean closeConnection()
   {
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

   public void updateInventory(String check, String update, int fulfillerId, Object[][] fulfillerLocationCatalog, Object[][] items) {

      int charCount = 0;

      if(setUpConnection() == false) {
         System.out.println("Connection failed");
         return;
      }

      for(char c: update.toCharArray()) {
         if(c == '?') {
            charCount++;
         }
      }

      for(int ndx = 0; ndx < items.length; ndx++) {
         try {
            String sku = items[ndx][0].toString();
            Integer quantity = (Integer)items[ndx][2];

            PreparedStatement ps = conn.prepareStatement(check);
            ps.setString(1, sku);
            ps.setInt(2, quantity);

            ResultSet r = ps.executeQuery();
            int binId;

            if(!r.next()) {
               System.out.println("Product does not exist and cannot be allocated");
               continue;
            }

            binId = r.getInt(1);;

            PreparedStatement ps2 = conn.prepareStatement(update);
            ps2.setInt(1, quantity);

            if(charCount > 2) {
               ps2.setInt(2, quantity);
            }

            ps2.setInt(charCount, binId);

            int rows = ps.executeUpdate();

            System.out.println(rows + " updated");
         }
         catch (Exception e) {
            System.out.println("Allocation/deallocation failed");
            System.out.println(e.toString());
            return;
         }
      }

      closeConnection();
   }

   public void allocateInventory(int fulfillerId, Object[][] fulfillerLocationCatalog, Object[][] items) {
      String check = "SELECT b.Id " +
                     "FROM StoreBin b, ContainedInBin c, LocationProduct lp, RetailerProduct rp " +
                     "WHERE b.Id = c.BinId AND c.LocationProductId = lp.Id AND lp.RetailerProductId = rp.Id AND rp.SKU = ? AND c.OnHand >= ? " +
                     "ORDER BY c.OnHand";

      String allocate = "UPDATE ContainedInBin " +
                        "SET OnHand = OnHand - ?, Allocated = Allocated + ? " +
                        "WHERE BinId = ?";

      updateInventory(check, allocate, fulfillerId, fulfillerLocationCatalog, items);
   }

   public void deallocateInventory(int fulfillerId, Object[][] fulfillerLocationCatalog, Object[][] items) {
      String check = "SELECT b.Id " +
                     "FROM StoreBin b, ContainedInBin c, LocationProduct lp, RetailerProduct rp " +
                     "WHERE b.Id = c.BinId AND c.LocationProductId = lp.Id AND lp.RetailerProductId = rp.Id AND rp.SKU = ? AND c.Allocated >= ? " +
                     "ORDER BY c.Allocated";

      String deallocate = "UPDATE ContainedInBin " +
                          "SET OnHand = OnHand + ?, Allocated = Allocated - ? " +
                          "WHERE BinId = ?";

      updateInventory(check, deallocate, fulfillerId, fulfillerLocationCatalog, items);
   }

   public void fulfillInventory(int fulfillerId, Object[][] fulfillerLocationCatalog, Object[][] items) {
      String check = "SELECT b.Id " +
                     "FROM StoreBin b, ContainedInBin c, LocationProduct lp, RetailerProduct rp " +
                     "WHERE b.Id = c.BinId AND c.LocationProductId = lp.Id AND lp.RetailerProductId = rp.Id AND rp.SKU = ? AND c.Allocated >= ? " +
                     "ORDER BY c.Allocated";

      String fulfill = "UPDATE ContainedInBin " +
                       "SET Allocated = Allocated - ? " +
                       "WHERE BinId = ?";

      updateInventory(check, fulfill, fulfillerId, fulfillerLocationCatalog, items);
   }

   public void createFulfiller(int fulfillerId, String locationName) {
      createNewRetailer(fulfillerId, locationName);
   }

   public void createFulfillmentLocation (String locationName, int fulfillerId,
      String externalLocationId, int internalFulfillerLocationId,
      String description, double latitude, double longitude, String status,
      int safetyStockLimit, int manufacturerId, int catalogId)
   {
      if(setUpConnection() == false)
         return;

      //if(closeConnection() == false)
         //return;

      if(!ids.contains(new Integer(fulfillerId))) {
         createNewRetailer(fulfillerId, locationName);
         ids.add(new Integer(fulfillerId));
      }

      if(!mIds.contains(new Integer(manufacturerId))) {
         createNewManufacturer(manufacturerId);
         mIds.add(new Integer(manufacturerId));
      }

      if(!cIds.contains(new Integer(catalogId))) {
         createNewCatalog(manufacturerId, catalogId);
         cIds.add(new Integer(catalogId));
      }
      
      if(!intFulLocIds.contains(new Integer(internalFulfillerLocationId))) {
         createNewLocation(fulfillerId, externalLocationId, internalFulfillerLocationId,
            "", description, latitude, longitude, status, safetyStockLimit);
         intFulLocIds.add(new Integer(internalFulfillerLocationId));
      }

      //createNewRetailer(fulfillerId, locationName);
      //createNewLocation(fulfillerId, externalLocationId, internalFulfillerLocationId,
         //"", description, latitude, longitude, status, safetyStockLimit);
      //createNewManufacturer(manufacturerId);
      //createNewCatalog(manufacturerId, catalogId);
      
      createNewCatalogServedByLocation(internalFulfillerLocationId, manufacturerId,
         catalogId);
      
      if(closeConnection() == false)
         return;
   }

   public void createNewRetailer(int fulfillerId, String locationName)
   {
      String sql = "INSERT INTO Retailer VALUES(?, ?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, fulfillerId);
         ps.setString(2, locationName);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new Retailer tuple: " + e);
      }
   }

   public void createNewLocation(int fulfillerId, String externalLocationId,
      int internalFulfillerLocationId, String type, String description,
      double latitude, double longitude, String status, int safetyStockLimit)
   {
       String sql = "INSERT INTO Location VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, fulfillerId);
         ps.setString(2, externalLocationId);
         ps.setInt(3, internalFulfillerLocationId);
         ps.setString(4, type);
         ps.setString(5, description);
         ps.setDouble(6, latitude);
         ps.setDouble(7, longitude);
         ps.setString(8, status);
         ps.setInt(9, safetyStockLimit);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new Location tuple: " + e);
      }
   }
   
   public void createNewCatalogServedByLocation(int internalFulfillerLocationId,
      int manufacturerId, int catalogId)
   {
      String sql = "INSERT INTO CatalogServedByLocation (InternalFulfillerLocationId, ManufacturerId, CatalogId) VALUES(?, ?, ?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, internalFulfillerLocationId);
         ps.setInt(2, manufacturerId);
         ps.setInt(3, catalogId);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new CatalogServedByLocation tuple: " + e);
      }
   }

   public void createNewManufacturer(int manufacturerId)
   {
      String sql = "INSERT INTO Manufacturer VALUES(?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, manufacturerId);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new Manufacturer tuple: " + e);
      }
   }

   public void createNewCatalog(int manufacturerId, int catalogId)
   {
      String sql = "INSERT INTO Catalog VALUES(?, ?)";

      try
      {
         PreparedStatement ps = conn.prepareStatement(sql);

         ps.setInt(1, manufacturerId);
         ps.setInt(2, catalogId);

         ps.executeUpdate();
      }
      catch(Exception e)
      {
         System.out.println("Error occured while creating a new Catalog tuple: " + e);
      }
   }

   public void createFullfillmentLocation ()
   {
      System.out.println("in create Fulfillment Location");
   }

   public void createManufacturerCatalog ()
   {
      System.out.println("in createManufacturer Catalog");
   }

   public int createBin (int fulfillerId, int binId, int fulfillerLocationId,
                        String binType, String binStatus, String binName)
   {
      if(fulfillerId < 0 || binId < 0 || fulfillerLocationId < 0) {
         return -1;
      }

      if(setUpConnection() == false)
         return -1;

      try {
         String sql = "INSERT INTO StoreBin VALUES(NULL, ?, ?, ?, ?, ?)";
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setInt(1, fulfillerLocationId);
         ps.setString(2, binStatus);
         ps.setString(3, binType);
         ps.setString(4, binName);
         ps.setString(5, "");

         ps.executeUpdate();
      }
      catch (Exception e) {
         return -1;
      }
      
      closeConnection();
      		
      return 1;
   }

   public ArrayList<Object[]> getBins (int fulfillerLocationId, String searchTerm, int numResults, int resultsStart)
   {
      ArrayList<Object[]> bins = new ArrayList<Object[]>();

      if(numResults < 0 || resultsStart < 0) {
         return null;
      }

      if(setUpConnection() == false)
         return null;

      try {
         String query = "SELECT Id, InternalFulfillerLocationId, Status, Type, Name " +
                        "FROM StoreBin " +
                        "WHERE InternalFulfillerLocationId = " + fulfillerLocationId;
         if(!searchTerm.equals(null) && searchTerm.length() > 0) {
            query += " AND Name = '" + searchTerm + "'";
         }

         Statement s = conn.createStatement();
         ResultSet r = s.executeQuery(query);
         boolean hasNext = r.next();
         int ndx = 0;

         while(hasNext && ndx < resultsStart + numResults) {
            Object[] returnObj = {r.getInt(1), r.getString(2), r.getString(3), r.getString(4)};

            if(ndx >= resultsStart) {
               bins.add(returnObj);
            }

            hasNext = r.next();
            ndx++;
         }
      }
      catch (Exception e) {
         return null;
      }

      closeConnection();

      return bins;
   }

   public ArrayList<Object[]> getBinAttributes(String query, int fulfillerId)
   {
      ArrayList<Object[]> binTypes = new ArrayList<Object[]>();

      if(setUpConnection() == false) {
         System.out.println("Connection failed");
         return null;
      }

      try {
         PreparedStatement ps = conn.prepareStatement(query);
         ps.setInt(1, fulfillerId);
         ResultSet r = ps.executeQuery();
         boolean hasNext = r.next();

         while(hasNext) {
            String type = r.getString(1);
            String description = r.getString(2);
            Object[] returnObj = {type, description};
            binTypes.add(returnObj);
            hasNext = r.next();
         }
      }
      catch (Exception e) {
         System.out.println("Query failed");
         return null;
      }

      closeConnection();

      return binTypes;
   }

   public ArrayList<Object[]> getBinTypes(int fulfillerId)
   {
      String query = "SELECT b.Type, b.Description " +
                     "FROM StoreBin b, Location l " +
                     "WHERE l.FulfillerId = ? AND " +
                     "b.InternalFulfillerLocationId = l.InternalFulfillerLocationId";

      return getBinAttributes(query, fulfillerId);
   }

   public ArrayList<Object[]> getBinStatuses(int fulfillerId)
   {
      String query = "SELECT b.Status, b.Description " +
                     "FROM StoreBin b, Location l " +
                     "WHERE l.FulfillerId = ? AND " +
                     "b.InternalFulfillerLocationId = l.InternalFulfillerLocationId";

      return getBinAttributes(query, fulfillerId);
   }

   public int refreshInventory(int internalFulfillerLocationId, String LocationName, String SKU, String UPC,
      int binId, int onhand, double ltd, int safetyStock)
    {
        int i = 0;
        int InternalFulfillerId = -1;
        int FulfillerId = -1;
		int manuId = -1;
		int catId = -1;
        String InternalFulfillerInt;
        String FulfillerInt;
		String manufacturer;
		String catalog;
		PreparedStatement s0;
        PreparedStatement s1;
        PreparedStatement s2;
		PreparedStatement s3;
        ResultSet InternalFulfillerHolder;
        ResultSet FulfillerHolder;
		ResultSet manuIdHolder;
		ResultSet catHolder;

        setUpConnection();
        RefreshItem[] item = new RefreshItem[1];
        item[0] = new RefreshItem(SKU, UPC, binId, onhand, ltd, safetyStock);
                
        try {
		    catalog = "SELECT C.CatalogId " +
			"FROM CatalogServedByLocation C " +
			"WHERE C.InternalFulfillerLocationId = ?";
			
		    manufacturer = "SELECT C.ManufacturerId " +
			"FROM CatalogServedByLocation C " +
			"WHERE C.InternalFulfillerLocationId = ?";
			
            FulfillerInt = "SELECT R.FulfillerId " +
            "FROM LocationProduct LP, RetailerProduct RP,Retailer R, Location L "+
            "where LP.RetailerProductId = RP.Id AND RP.FulfillerId = R.FulfillerId "+
            "AND R.FulfillerId = L.FulfillerId";
           
            InternalFulfillerInt = "SELECT L.InternalFulfillerLocationId " +
            "FROM LocationProduct LP, RetailerProduct RP,Retailer R, Location L "+
            "where LP.InternalFulfillerLocationId = ?";
			
		    s0 = conn.prepareStatement(catalog);
			s0.setInt(1, internalFulfillerLocationId);
			catHolder = s0.executeQuery();
		
            s1 = conn.prepareStatement(FulfillerInt);
            FulfillerHolder = s1.executeQuery();
 
            s2 = conn.prepareStatement(InternalFulfillerInt);
            s2.setInt(1, internalFulfillerLocationId);
            InternalFulfillerHolder = s2.executeQuery();
		
			s3 = conn.prepareStatement(manufacturer);
			s3.setInt(1, internalFulfillerLocationId);
			manuIdHolder = s3.executeQuery();
       
			if (catHolder.first())
			   catId = catHolder.getInt(1);
			if (InternalFulfillerHolder.first())
               InternalFulfillerId = InternalFulfillerHolder.getInt(1);
			if (FulfillerHolder.first())
               FulfillerId = FulfillerHolder.getInt(1);
			if (manuIdHolder.first())
			   manuId = manuIdHolder.getInt(1);
			
			//System.out.println("RefreshInventory: catId = " + catId + " manuId = " + manuId + " IFID = " + InternalFulfillerId + " FId = " + FulfillerId);
        }
        catch (Exception e) {
            System.out.println("Errors");
        }
        
        for (i = 0; i < item.length; i++) {
            if (InternalFulfillerId == -1) { // not there, so use INSERT
                try {
                    String Product = "INSERT INTO Product (UPC, ManufacturerId, CatalogId) VALUES(?, ?, ?)";
                    String RP = "INSERT INTO RetailerProduct (FulfillerId, UPC, SKU) VALUES(?, ?, ?)";
                    String LP = "INSERT INTO LocationProduct (InternalFulfillerLocationId, LTD, SafeStockLimit) VALUES(?, ?, ?)";
                    String CIB = "INSERT INTO ContainedInBin (BinId, OnHand, Allocated) VALUES(?, ?, ?)";

                    PreparedStatement ps0 = conn.prepareStatement(Product);
                    ps0.setString(1, item[i].UPC);
                    ps0.setInt(2, manuId);
                    ps0.setInt(3, catId);
				
                    PreparedStatement ps1 = conn.prepareStatement(RP);
                    ps1.setInt(1, FulfillerId);
                    ps1.setString(2, item[i].UPC);
                    ps1.setString(3, item[i].partNumber);
					
                    PreparedStatement ps2 = conn.prepareStatement(LP);
                    ps2.setInt(1, InternalFulfillerId);
                    ps2.setDouble(2, item[i].LTD);
                    ps2.setInt(3, item[i].SafetyStock);
                
                    PreparedStatement ps3 = conn.prepareStatement(CIB);
                    ps3.setInt(1, item[i].BinID);
                    ps3.setInt(2, item[i].Quantity);
                    ps3.setInt(3, 0);
                
				    ps0.executeUpdate();
                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    ps3.executeUpdate();
                }
                catch (Exception e) {
                    System.out.println("Can't insert for refresh inventory: " + e);
                }
            }
            else { // use UPDATE statements
                String sql = "UPDATE LocationProduct LP, RetailerProduct RP, ContainedInBin CB, Product P "+
                "SET LP.LTD = ? AND LP.SafeStockLimit = ? AND CB.BinId = ? AND RP.UPC = ? AND CB.OnHand = ? AND P.UPC = ? " +
                "AND P.CatalogId = ? AND P.ManufacturerId = ? " +
                "WHERE LP.RetailerProductId = RP.Id AND CB.LocationProductId = LP.Id AND RP.UPC = P.UPC AND P.CatalogId = 0";
                
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setDouble(1, item[i].LTD);
                    ps.setInt(2, item[i].SafetyStock);
                    ps.setInt(3, item[i].BinID);
                    ps.setString(4, item[i].UPC);
                    ps.setInt(5, item[i].Quantity);
                    ps.setString(6, item[i].UPC);
					ps.setInt(7, catId);
					ps.setInt(8, manuId);
                }
                catch (Exception e) {
                    System.out.println("Can't update for refresh inventory");
                }
                
            }
        }
        
        closeConnection();
        return 1;
    }
}
