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
    
    private static int num = 0;
    
    public class RefreshItem {
        String partNumber;
        String UPC;
        Integer BinID;
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

                if(fulfillerLocationCatalog[0] != null) {
                   ps.setInt(3, (Integer)fulfillerLocationCatalog[0][0]);
                   ps.setInt(3, (Integer)fulfillerLocationCatalog[0][1]);
                }
                
                ResultSet r = ps.executeQuery();
                int binId;
                String externalLocationId;
                
                if(!r.next()) {
                    System.out.println("Product does not exist and cannot be allocated");
                    continue;
                }
                
                binId = r.getInt(1);
                externalLocationId = r.getString(2);

                PreparedStatement ps2 = conn.prepareStatement(update);
                ps2.setInt(1, quantity);
                
                if(charCount > 3) {
                    ps2.setInt(2, quantity);
                }
                
                ps2.setInt(charCount-1, binId);
                ps2.setString(charCount, externalLocationId);
 
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
        String check =
        "SELECT b.Id, lp.LocationProductId " +
        "FROM StoreBin b, ContainedInBin cb, Product p, LocationProduct lp, " +
         "RetailerProduct rp" +
        "WHERE b.Id = cb.BinId AND cb.LocationProductId = lp.Id " +
         "AND lp.RetailerProductId = rp.Id AND rp.SKU = ? AND rp.UPC = p.UPC " +
         "AND cb.OnHand - cb.Allocated - lp.SafeStockLimit >= ? " +
         ((fulfillerLocationCatalog[0] == null)? "" :
         "AND p.ManufacturerId = ? AND p.CatalogId = ? ") +
        "ORDER BY c.OnHand";
        
        String allocate = "UPDATE ContainedInBin " +
                          "SET Allocated = Allocated + ? " +
                          "WHERE BinId = ? AND LocationProductId = ?";
        
        updateInventory(check, allocate, fulfillerId, fulfillerLocationCatalog, items);
    }
    
    public void deallocateInventory(int fulfillerId, Object[][] fulfillerLocationCatalog, Object[][] items) {
        String check =
        "SELECT b.Id, lp.LocationProductId " +
        "FROM StoreBin b, ContainedInBin c, Product p, LocationProduct lp, " +
         "RetailerProduct rp " +
        "WHERE b.Id = c.BinId AND c.LocationProductId = lp.Id " +
         "AND lp.RetailerProductId = rp.Id AND rp.SKU = ? AND rp.UPC = p.UPC " +
         "AND c.Allocated >= ? " +
         ((fulfillerLocationCatalog[0] == null)? "" :
         "AND p.ManufacturerId = ? AND p.CatalogId = ? ") +
        "ORDER BY c.Allocated";
        
        String deallocate = "UPDATE ContainedInBin " +
                            "SET Allocated = Allocated - ? " +
                            "WHERE BinId = ? AND LocationProductId = ?";
        
        updateInventory(check, deallocate, fulfillerId, fulfillerLocationCatalog, items);
    }
    
    public void fulfillInventory(int fulfillerId, Object[][] fulfillerLocationCatalog, Object[][] items) {
        String check =
        "SELECT b.Id, lp.LocationProductId " +
        "FROM StoreBin b, ContainedInBin c, LocationProduct lp, RetailerProduct rp " +
        "WHERE b.Id = c.BinId AND c.LocationProductId = lp.Id " +
         "AND lp.RetailerProductId = rp.Id AND rp.SKU = ? AND rp.UPC = p.UPC " +
         "AND c.OnHand - c.Allocated - lp.SafeStockLimit >= ? " +
         ((fulfillerLocationCatalog[0] == null)? "" :
         "AND p.ManufacturerId = ? AND p.CatalogId = ? ") +
        "ORDER BY c.OnHand, c.Allocated";
        
        String fulfill = "UPDATE ContainedInBin " +
                         "SET OnHand = OnHand - ?, Allocated = Allocated - ? " +
                         "WHERE BinId = ? AND LocationProductId = ?";
        
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
        createBin (fulfillerId, null, externalLocationId, "General",
                   "Pickable", "Default");
        
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
    
    public int createBin (int fulfillerId, Integer binId, String externalLocationId, String binType, String binStatus, String binName)
    {
        if(fulfillerId < 0 || (binId != null && binId < 0)) {
            return -1;
        }
        
        if(setUpConnection() == false)
            return -1;
        
        try {
            
            String sql = "INSERT INTO StoreBin VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            if(binId == null) {
                ps.setString(1, null);
            }
            else {
                ps.setInt(1, binId);
            }
           
            ps.setInt(2, fulfillerId); 
            ps.setString(3, externalLocationId);
            ps.setString(4, binStatus);
            ps.setString(5, binType);
            ps.setString(6, binName);
            
            ps.executeUpdate();
            
            if(binId == null) {
                ResultSet r = ps.getGeneratedKeys();
                
                if(r != null && r.next()) {
                    binId = r.getInt(1);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return -1;
        }
        
        closeConnection();
        
        return binId;
    }
    
    public ArrayList<Object[]> getBins (int fulfillerId, String externalLocationId, String searchTerm, int numResults, int resultsStart)
    {
        ArrayList<Object[]> bins = new ArrayList<Object[]>();
        
        if(numResults < 0 || resultsStart < 0) {
            return bins;
        }
        
        if(setUpConnection() == false)
            return bins;
        
        try {
            String query = "SELECT Id, ExternalFulfillerLocationId, Type, Status, Name " +
            "FROM StoreBin " +
            "WHERE FulfillerId = ? AND ExternalFulfillerLocationId = ?";

            if(searchTerm != null && searchTerm.length() > 0) {
                query += " AND Name = '" + searchTerm + "'";
            }
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, fulfillerId);
            ps.setString(2, externalLocationId);

            ResultSet r = ps.executeQuery();
            boolean hasNext = r.next();
            int ndx = 0;
            
            while(hasNext && ndx < resultsStart + numResults) {
                Object[] returnObj = {fulfillerId, r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5)};
                
                if(ndx >= resultsStart) {
                    bins.add(returnObj);
                }
                
                hasNext = r.next();
                ndx++;
            }
        }
        catch (Exception e) {
            return bins;
        }
        
        closeConnection();
        
        return bins;
    }
    
    public ArrayList<String> getBinAttributes(String query, int fulfillerId)
    {
        ArrayList<String> binTypes = new ArrayList<String>();
        
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
                binTypes.add(r.getString(1));
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
    
    public ArrayList<String> getBinTypes(int fulfillerId)
    {
        String query = "SELECT b.Type " +
        "FROM StoreBin b, Location l " +
        "WHERE l.FulfillerId = ? AND " +
        "b.ExternalFulfillerLocationId = l.ExternalFulfillerLocationId";
        
        return getBinAttributes(query, fulfillerId);
    }
    
    public ArrayList<String> getBinStatuses(int fulfillerId)
    {
        String query = "SELECT b.Status " +
        "FROM StoreBin b, Location l " +
        "WHERE l.FulfillerId = ? AND " +
        "b.ExternalFulfillerLocationId = l.ExternalFulfillerLocationId";
        
        return getBinAttributes(query, fulfillerId);
    }
   
   public int getRPId(int FulfillerId, String UPC, String SKU)
   {
        PreparedStatement s0;
        ResultSet id;
        String find = "SELECT R.Id FROM RetailerProduct R " +
        "WHERE R.FulfillerId = " + FulfillerId + " AND R.UPC = '" + UPC + "' AND R.SKU = '" + SKU + "'";
		
        try {
	        s0 = conn.prepareStatement(find);
	        id = s0.executeQuery();
		
	        if (id.first()) { 
	            return id.getInt(1);
	        }
	        return 0;
	    }
	    catch (Exception e) {
	        System.out.println("getting RetailerProduct Id");
	    }
		
	    return 0;
   }
   
   public int getLPId(int InternalFulfillerId, double LTD, int SafetyStock, String UPC, String SKU, int fulfiller)
   {
        PreparedStatement s0;
        ResultSet id;
        ResultSet fulfillerId;
      //  int fId = -1;
        int rpId = -1;
        
      /*  String getRP = "SELECT R.FulfillerId " +
        "FROM Retailer R, LocationProduct LP, RetailerProduct RP, Location L " +
        "WHERE LP.RetailerProductId = RP.Id AND RP.UPC = '" + UPC + "' AND RP.FulfillerId = L.FulfillerId " +
        "AND L.InternalFulfillerLocationId = " + InternalFulfillerId + " AND L.FulfillerId = R.FulfillerId"; */
        
      /*  String getRP = "SELECT R.FulfillerId " +
	     "FROM LocationProduct LP, RetailerProduct RP,Retailer R, Location L " +
		 "where LP.RetailerProductId = RP.Id AND RP.FulfillerId = R.FulfillerId " +
		 "AND R.FulfillerId = L.FulfillerId"; */
		
        String find = "SELECT L.Id FROM LocationProduct L " +
        "WHERE L.RetailerProductId = ? AND L.InternalFulfillerLocationId = " + InternalFulfillerId; //+ " AND L.LTD = " + 
       // LTD + " AND L.SafeStockLimit = " + SafetyStock;
        
        try {
          //  s0 = conn.prepareStatement(getRP);
          //  fulfillerId = s0.executeQuery();

          //  if (fulfillerId.first())
          //      fId = fulfillerId.getInt(1);

            rpId = getRPId(fulfiller, UPC, SKU);

            s0 = conn.prepareStatement(find);
            s0.setInt(1, rpId);
            id = s0.executeQuery();
		
            if (id.first())
                return id.getInt(1);
            return -1;
        }
        catch (Exception e) {
            System.out.println("getting LocationProduct Id: " + e);
        }
		
        return -1;
   }
   
   public int updateProduct(String UPC, int internalFulfillerLocationId)
   {
        PreparedStatement s0;
		PreparedStatement s1;
		PreparedStatement s2;
		int catId = 0;
		int manuId = 0;
		String searchUPC = "";
		ResultSet catHolder;
		ResultSet manuIdHolder;
		ResultSet upcHolder;
		
	//	System.out.println("updating Product");
		
		String catalog = "SELECT C.CatalogId " +
		 "FROM CatalogServedByLocation C " +
		 "WHERE C.InternalFulfillerLocationId = ?";
		String manufacturer = "SELECT C.ManufacturerId " +
		 "FROM CatalogServedByLocation C " +
		 "WHERE C.InternalFulfillerLocationId = ?";
		String findUPC = "SELECT P.UPC " +
		 "FROM Product P " +
		 "WHERE P.UPC = '" + UPC + "'";
		
		try {
            s0 = conn.prepareStatement(catalog);
            s0.setInt(1, internalFulfillerLocationId);
            catHolder = s0.executeQuery();
			
			s1 = conn.prepareStatement(manufacturer);
			s1.setInt(1, internalFulfillerLocationId);
			manuIdHolder = s1.executeQuery();
			
			s2 = conn.prepareStatement(findUPC);
			upcHolder = s2.executeQuery();
						
			if (catHolder.first())
			    catId = catHolder.getInt(1);
			if (manuIdHolder.first())
			    manuId = manuIdHolder.getInt(1);
			if (upcHolder.first())
			    searchUPC = upcHolder.getString(1);
		}
		catch (Exception e) {
		    System.out.println("updateProduct: " + e);
		}
		
		if (!searchUPC.equals(null) && searchUPC.length() > 0) { // upc exists. use update
		    String sql = "UPDATE Product P " +
			 "SET P.CatalogId = ? AND P.ManufacturerId = ? " +
			 "WHERE P.UPC = '" + UPC + "'";
			 
			try {
			    PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, catId);
				ps.setInt(2, manuId);
				ps.executeUpdate();
			}
			catch (Exception e) {
			    System.out.println("update into product: " + e);
			}
		}
		else { // this product isn't there yet. use insert
		    String Product = "INSERT INTO Product (UPC, ManufacturerId, CatalogId) VALUES(?, ?, ?)";
			
			try {
			    s0 = conn.prepareStatement(Product);
			    s0.setString(1, UPC);
			    s0.setInt(2, manuId);
			    s0.setInt(3, catId);
			    s0.executeUpdate();
			}
			catch (Exception e) {
			    System.out.println("insert into Product: " + e);
			}
		}
		return 1;
   }
   
   public int updateRetailerProduct(String UPC, String SKU, int FulfillerId)
   {
		PreparedStatement s0;
		PreparedStatement s1;
		int fulfiller = -1;
		String searchUPC = "";
		ResultSet FulfillerHolder;
		ResultSet upcHolder;
	   
	   // System.out.println("updating Retailer Product");
		String findUPC = "SELECT RP.UPC " +
	     "FROM RetailerProduct RP " +
		 "where RP.UPC = '" + UPC + "'";
		String getfulfiller = "SELECT P.FulfillerId " +
		 "FROM RetailerProduct P " +
		 "WHERE P.UPC = '" + UPC + "' AND P.FulfillerId = " + FulfillerId;
		
		try {
	        s0 = conn.prepareStatement(findUPC);
	        upcHolder = s0.executeQuery();
		   
		    if (upcHolder.first())
		        searchUPC = upcHolder.getString(1);
				
		    s1 = conn.prepareStatement(getfulfiller);
		    FulfillerHolder = s1.executeQuery();
		   
		    if (FulfillerHolder.first())
		        fulfiller = FulfillerHolder.getInt(1);
	    }
		catch (Exception e) {
		    System.out.println("updateRetailerProduct: " + e);
		}
				
		if (FulfillerId == fulfiller || (!searchUPC.equals(null) && searchUPC.length() > 0)) {
		    String sql = "UPDATE RetailerProduct RP " +
			 "SET RP.SKU = ? " +
			 "WHERE RP.UPC = '" + UPC + "' AND RP.FulfillerId = " + FulfillerId;
			 
			try {
			    PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, SKU);
				ps.executeUpdate();
			}
			catch (Exception e) {
			    System.out.println("update into retailerProduct: " + e);
			}
		}
		else {
		    String RP = "INSERT INTO RetailerProduct (FulfillerId, UPC, SKU) VALUES(?, ?, ?)";
			
			try {
			    s0 = conn.prepareStatement(RP);
			    s0.setInt(1, FulfillerId);
			    s0.setString(2, UPC);
			    s0.setString(3, SKU);
			    s0.executeUpdate();
			}
			catch (Exception e) {
			    System.out.println("insert into retailerProduct: " + e);
			}
		}
		return 1;
   
   }
   
   public int updateLocationProduct(int internalFulfillerLocationId, double ltd, int safetyStock, String UPC, String SKU, int fulfiller)
   {
      PreparedStatement s0;
		PreparedStatement s1;
		//int FulfillerId = -1;
		int rpId = -1;
		int id = -1;
		ResultSet FulfillerHolder;
		
		//System.out.println("updating LocationProduct");
		
		//String getfulfiller = "SELECT R.FulfillerId " +
	   //  "FROM LocationProduct LP, RetailerProduct RP,Retailer R, Location L " +
		// "where LP.RetailerProductId = RP.Id AND RP.FulfillerId = R.FulfillerId " +
		// "AND R.FulfillerId = L.FulfillerId";
		 
	/*	try {
            s0 = conn.prepareStatement(getfulfiller);
	        FulfillerHolder = s0.executeQuery();
			
			if (FulfillerHolder.first())
		        FulfillerId = FulfillerHolder.getInt(1);
		}
		catch (Exception e) {
		    System.out.println("updateLocationProduct: " + e);
		}*/
		
		rpId = getRPId(fulfiller, UPC, SKU);
		id = getLPId(internalFulfillerLocationId, ltd, safetyStock, UPC, SKU, fulfiller);
		
		if (id != -1) { // this entry already exists. use update
		    String sql = "UPDATE LocationProduct LP " +
			 "SET LP.LTD = ? AND LP.SafeStockLimit = ? AND LP.RetailerProductId = ? " +
			 "WHERE LP.RetailerProductId = " + rpId + " AND LP.Id = " + id;
			 
			try {
			    s1 = conn.prepareStatement(sql);
			    s1.setDouble(1, ltd);
			    s1.setInt(2, safetyStock);
			    s1.setInt(3, rpId);
			
			    s1.executeUpdate();
			}
			catch (Exception e) {
			    System.out.println("update locationProduct: " + e);
			}
		}
		else { // insert
		    String LP = "INSERT INTO LocationProduct (InternalFulfillerLocationId, RetailerProductId, " +
			 "LTD, SafeStockLimit) VALUES(?, ?, ?, ?)";
			 
			try {
			    s1 = conn.prepareStatement(LP);
			    s1.setInt(1, internalFulfillerLocationId);
			    s1.setInt(2, rpId);
			    s1.setDouble(3, ltd);
			    s1.setInt(4, safetyStock);
				
				s1.executeUpdate();
			}
			catch (Exception e) {
			    System.out.println("insert into locationProduct: " + e);
			}
		}
				
		return 1;
		
   }
   
   public int updateContainedInBin(int binId, int onHand, int internalFulfillerLocationId, double ltd, 
    int safetyStock, String UPC, String SKU, int fulfiller, String externalLocId)
   {
		PreparedStatement s0;
		PreparedStatement s1;
		int lpId = -1;
		int id = -1;
      int binNum = -1;
      int match = -1;
		ResultSet idHolder;
      ResultSet checkHolder;
		
		//System.out.println("updating ContainedInBin");
		
		lpId = getLPId(internalFulfillerLocationId, ltd, safetyStock, UPC, SKU, fulfiller);
		
		String bin = "SELECT S.Id " +
		 "FROM StoreBin S " +
		 "WHERE S.FulfillerId = " + fulfiller + " AND S.ExternalFulfillerLocationId = '" + externalLocId + "'";
       
      String check = "SELECT C.BinId FROM ContainedInBin C " +
       " WHERE C.BinId = ? AND C.LocationProductId = ?";
		
		try {
		    s0 = conn.prepareStatement(bin);
		    idHolder = s0.executeQuery();
			
		    if (idHolder.first())
		        id = idHolder.getInt(1);
		}
		catch (Exception e) {
		    System.out.println("updateContainedInBin: " + e);
		}

      // check if this exists in CIB already
		if (id != -1 && lpId != -1) { 
          try {
             s0 = conn.prepareStatement(check);
             s0.setInt(1, id);
             s0.setInt(2, lpId);
             checkHolder = s0.executeQuery();
             
             if (checkHolder.first())
                binNum = checkHolder.getInt(1);
             if (binNum == id)
                match = 1;
             else
                match = 0;
          }
          catch (Exception e) {
             System.out.println("check failed in ContainedInBin: " + e);
          }
      }
      
      if (match == 1) { // use update
		    String sql = "UPDATE ContainedInBin CB " +
	             "SET CB.OnHand = ? " +
		     "WHERE CB.BinId = " + binId + " AND CB.LocationProductId = " + lpId;
	             		 
		    try {
			    s1 = conn.prepareStatement(sql);
			    s1.setInt(1, onHand);
				
			    s1.executeUpdate();
		    }
		    catch (Exception e) {
			    System.out.println("update CIB: " + e);
		    }
		}
		else { // use insert
		    String CIB = "INSERT INTO ContainedInBin (BinId, LocationProductId, OnHand, Allocated) VALUES(?, ?, ?, ?)";
			
		    try {
			    s1 = conn.prepareStatement(CIB);
		            s1.setInt(1, binId);
			    s1.setInt(2, lpId);
			    s1.setInt(3, onHand);
			    s1.setInt(4, 0);
			    s1.executeUpdate();
		    }
		    catch (Exception e) {
             // use default bin
             useDefaultBin(internalFulfillerLocationId, onHand, lpId, fulfiller, externalLocId);
		    }		
		}
		
		return 1;
		
   }

   public int useDefaultBin(int internalFulfillerLocationId, int onHand, int lpId, int fulfiller, String externalId)
   {
       PreparedStatement s0;
       ResultSet idHolder;
       ResultSet bin;
       ResultSet defaultBin;
       int binId = -1;
       int defaultId = 0;

       // Add the extra variable
       String find = "SELECT S.Id FROM StoreBin S " +
       "WHERE S. Name = 'Default' AND S.FulfillerId = " + fulfiller + " AND S.ExternalFulfillerLocationId = " + externalId;

       String search = "SELECT CB.BinId FROM ContainedInBin CB " +
        "WHERE CB.BinId = ? AND CB.LocationProductId = " + lpId;

       /* default bin is binId = 0 */
       String sql = "UPDATE ContainedInBin CB " +
        "SET CB.OnHand = " + onHand + " AND CB.Allocated = 0 " +
        "WHERE CB.BinId = ?"; 

       String sql2 = "INSERT INTO ContainedInBin CB (BinId, LocationProductId, OnHand, Allocated) VALUES(?, ?, ?, ?)";

       try {
          s0 = conn.prepareStatement(find);
          defaultBin = s0.executeQuery();

          if (defaultBin.first())
             defaultId = defaultBin.getInt(1);

          s0 = conn.prepareStatement(search);
          s0.setInt(1, defaultId);
          bin = s0.executeQuery();

          if (bin.first())
             binId = bin.getInt(1);

          if (binId == defaultId) {// exists in CB, update
             s0 = conn.prepareStatement(sql);
             s0.setInt(1, binId);
             s0.executeUpdate();
          }
          else {
             s0 = conn.prepareStatement(sql2);
             s0.setInt(1, defaultId);
             s0.setInt(2, lpId);
             s0.setInt(3, onHand);
             s0.setInt(4, 0);
             s0.executeUpdate();
          }

       }
       catch (Exception e) {
          System.out.println("useDefaultBin: " + e);
       }
	   return 1;
   }
   
   public int refreshInventory(int fulfillerId, String externalLocId, String SKU, String UPC,
      int binId, int onhand, double ltd, int safetyStock)
   {
        int i = 0;
        int internalFulfillerLocationId = -1;
        PreparedStatement s0;
        ResultSet holder;
        
        String sql = "SELECT L.InternalFulfillerLocationId FROM Location L " +
         "WHERE L.FulfillerId = " + fulfillerId + " AND L.ExternalFulfillerLocationId = '" + externalLocId + "'";

        setUpConnection();
        RefreshItem[] item = new RefreshItem[1];
        item[0] = new RefreshItem(SKU, UPC, binId, onhand, ltd, safetyStock);
        
        try {
           s0 = conn.prepareStatement(sql);
           holder = s0.executeQuery();
           
           if (holder.first())
              internalFulfillerLocationId = holder.getInt(1);
        }
        catch (Exception e) {
           System.out.println("Couldn't find InternalFulfillerLocationId" + e);
        }
		
       // for (i = 0; i < item.length; i++) {
            updateProduct(item[i].UPC, internalFulfillerLocationId);
            updateRetailerProduct(item[i].UPC, item[i].partNumber, fulfillerId);
            updateLocationProduct(internalFulfillerLocationId, item[i].LTD, item[i].SafetyStock, item[i].UPC, item[i].partNumber,
             fulfillerId);
            updateContainedInBin(item[i].BinID, item[i].Quantity, internalFulfillerLocationId, item[i].LTD, item[i].SafetyStock, 
             item[i].UPC, item[i].partNumber, fulfillerId, externalLocId);
//	      }

      System.out.println("num: " + num);
      num++;

      closeConnection();
      return 1;
   }
   
   public int adjustInventory (int internalFulfillerLocationId, int binId, int onhand, double ltd, int safetyStock, int adjust) {
        
		if (onhand+adjust > safetyStock) {
			System.out.println("Adjusting larger than safety stock");
		}
		try {
            //		updateContainedInBin(binId, onhand+adjust, internalFulfillerLocationId, ltd, safetyStock);
			System.out.println("Adjusted " +binId);
		}
		catch (Exception e) {
			System.out.println("adjustInventory: " +e);
			return -1;
		}
        
		return 1;
        
	}
    
   public ArrayList<Object[]> getAllInventory(int fulfillerId,
    int[] manCatalog, Object[][] quantities, String[] locationIds,
    Object[] location, String type, int limit, Boolean ignoreSafetyStock,
    Boolean includeNegativeInventory, boolean orderByLtd) {

      ArrayList<Object[]> inventory = new ArrayList<Object[]>();
      ArrayList<String> locations = new ArrayList<String>();
      ArrayList<String> locations2 = new ArrayList<String>();
      ArrayList<String> locations3 = new ArrayList<String>();
      String checkAvailable = " AND cb.OnHand - cb.Allocated" +
      ((ignoreSafetyStock == null || !ignoreSafetyStock)? " - lp.SafeStockLimit" : "") + " >= ? ";
      String sql =
       "FROM Location l, Catalog c, CatalogServedByLocation cl, " +
        "ContainedInBin cb, LocationProduct lp, " +
        "RetailerProduct rp, Product p " +
       "WHERE l.InternalFulfillerLocationId = cl.InternalFulfillerLocationId " +
        "AND cl.CatalogId = c.CatalogId AND p.UPC = rp.UPC " +
        "AND l.InternalFulfillerLocationId = lp.InternalFulfillerLocationId " +
        "AND rp.Id = lp.RetailerProductId AND lp.Id = cb.LocationProductId " +
        "AND l.FulfillerId = ? AND c.ManufacturerId = ? AND c.CatalogId = ? " +
        //"AND p.ManufacturerId = c.ManufacturerId AND p.CatalogId = c.CatalogId " +
        "AND rp.SKU = ? AND rp.UPC = ?" +
        ((includeNegativeInventory != null && includeNegativeInventory)? "" : checkAvailable);
      String loc = " AND (";
      String sql1;
      String sql2;
      int num;  
        
      for(int ndx = 0; locationIds != null && ndx < locationIds.length; ndx++) {
         loc += " l.ExternalFulfillerLocationId = ?";
            
         if(ndx + 1 < locationIds.length) {
            loc += " OR";
         }
      }
        
      if(locationIds != null && locationIds.length > 0) {
         sql += loc + ")";
      }
        
      loc = sql + " AND (";
        
        
      /*if(orderByLtd) {
         sql += " ORDER BY lp.LTD";
      }*/
        
      sql1 = "SELECT l.ExternalFulfillerLocationId " + sql;
        
      for(int ndx = 0; ndx < quantities.length; ndx++) {
         num = 6;
         try {
            PreparedStatement ps = conn.prepareStatement(sql1);
                
            ps.setInt(1, fulfillerId);
            ps.setInt(2, manCatalog[0]);
            ps.setInt(3, manCatalog[1]);
            ps.setString(4, quantities[ndx][0].toString());
            ps.setString(5, quantities[ndx][1].toString());
            
            if(!(includeNegativeInventory != null && includeNegativeInventory)) {
               ps.setInt(num++, (new Integer(quantities[ndx][2].toString())).intValue());
            }

            for(int i = 0; i < locationIds.length; i++) {
               ps.setString(num + i, locationIds[i]);
            }
            
            System.out.println(ps.toString());
            ResultSet r = ps.executeQuery();
            boolean hasNext = r.next();
            locations3.clear();

            while(hasNext) {
               String l = r.getString(1);
               //System.out.println("ndx: " + ndx + "   l: " + l);

               if(ndx > 0) {
                  locations3.add(l);
               }
               else {
                  locations2.add(l);
               }
               hasNext = r.next();
            }
                
            for(int i = 0; i < locations3.size(); i++) {
               String l = locations3.get(i);

               //System.out.println("locations2.size()" + locations2.size());

               if(locations2.contains(l)) {
                  locations.add(l);
                  //System.out.println("Adding to locations");
               }
               /*else {
                  System.out.println("l: " + l + " is not in locations2");
               }*/
            }

            if(ndx > 0) { 
               locations2 = new ArrayList<String>();
               locations2.addAll(locations);
            }
         }
         catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
         }
      }

      loc = " AND (";      

      /*System.out.println("locations.size(): " + locations.size());
      System.out.println("locations2.size(): " + locations2.size());
      System.out.println("locations3.size(): " + locations3.size());*/
      for(int i = 0; i < locations.size(); i++) {
         loc += " l.ExternalFulfillerLocationId = ?";
            
         if(i + 1 < locations.size()) {
            loc += " OR";
         }
      }
        
      sql2 = "SELECT l.ExternalFulfillerLocationId, c.CatalogId, " +
              "c.ManufacturerId, cb.OnHand, cb.OnHand - cb.Allocated, " +
              "rp.SKU, rp.UPC, lp.LTD, lp.SafeStockLimit " + sql;
        
      if(locations.size() > 0) {
         sql2 += loc + ")";
      }
        
      if(orderByLtd) {
         sql2 += " ORDER BY lp.LTD";
      }
      
      for(int ndx = 0; ndx < quantities.length; ndx++) {
         num = 6; 
         try {
            PreparedStatement ps = conn.prepareStatement(sql2);
                
            ps.setInt(1, fulfillerId);
            ps.setInt(2, manCatalog[0]);
            ps.setInt(3, manCatalog[1]);
            ps.setString(4, quantities[ndx][0].toString());
            ps.setString(5, quantities[ndx][1].toString());
            
            if(!(includeNegativeInventory != null && includeNegativeInventory)) {
               ps.setInt(num++, (new Integer(quantities[ndx][2].toString())).intValue());
            } 

            for(int i = 0; i < locations.size(); i++) {
               ps.setString(num + i, locations.get(i));
            }
            
            //System.out.println(ps.toString());    
            ResultSet r = ps.executeQuery();
            boolean hasNext = r.next();
            int count = 0;
            while(hasNext && count <= limit) {
               Object[] returnObj = {r.getString(1), r.getInt(2), r.getInt(3),
                                     r.getInt(4), r.getInt(5), r.getString(6),
                                     r.getString(7), r.getDouble(8),
                                     r.getInt(9), 0, 0};
                    
               inventory.add(returnObj);
                  
               hasNext = r.next();
               count++;
            }
         }
         catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
         }
            
      }

      return inventory;
   }
    
   public ArrayList<Object[]> getSomeInventory(int fulfillerId,
    int[] manCatalog, Object[][] quantities, String[] locationIds,
    Object[] location, String type, int limit, Boolean ignoreSafetyStock,
    Boolean includeNegativeInventory, boolean orderByLtd) {

      ArrayList<Object[]> inventory = new ArrayList<Object[]>();
      String checkAvailable = " AND cb.OnHand - cb.Allocated" +
                              ((ignoreSafetyStock == null || !ignoreSafetyStock)? " - lp.SafeStockLimit" : "") + " >= ? ";
      String sql =
       "SELECT l.ExternalFulfillerLocationId, c.CatalogId, " +
        "c.ManufacturerId, cb.OnHand, cb.OnHand - cb.Allocated, " +
        "rp.SKU, rp.UPC, lp.LTD, lp.SafeStockLimit " +
        "FROM Location l, Catalog c, CatalogServedByLocation cl, " +
        "ContainedInBin cb, LocationProduct lp, " +
        "RetailerProduct rp, Product p " +
       "WHERE l.InternalFulfillerLocationId = cl.InternalFulfillerLocationId " +
        "AND cl.CatalogId = c.CatalogId AND p.UPC = rp.UPC " +
        "AND l.InternalFulfillerLocationId = lp.InternalFulfillerLocationId " +
        "AND rp.Id = lp.RetailerProductId AND lp.Id = cb.LocationProductId " +
        "AND l.FulfillerId = ? AND c.ManufacturerId = ? AND c.CatalogId = ? " +
        //"AND p.ManufacturerId = c.ManufacturerId AND p.CatalogId = c.CatalogId " +
        "AND rp.SKU = ? AND rp.UPC = ?" +
        ((includeNegativeInventory != null && includeNegativeInventory)? "" : checkAvailable);
      String loc = " AND (";
          
      for(int ndx = 0; locationIds != null && ndx < locationIds.length; ndx++) {
         loc += " l.ExternalFulfillerLocationId = ?";
            
         if(ndx + 1 < locationIds.length) {
            loc += " OR";
         }
      }
        
      if(locationIds != null && locationIds.length > 0) {
         sql += loc + ")";
      }
        
      if(orderByLtd) {
         sql += " ORDER BY lp.LTD";
      }
        
      for(int ndx = 0; ndx < quantities.length; ndx++) {
            
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
                
            ps.setInt(1, fulfillerId);
            ps.setInt(2, manCatalog[0]);
            ps.setInt(3, manCatalog[1]);
            ps.setString(4, quantities[ndx][0].toString());
            ps.setString(5, quantities[ndx][1].toString());
            

            if(includeNegativeInventory != null && includeNegativeInventory) {
               ;
            }    
            else if(type.equals("ANY")) {
               ps.setInt(6, 1);
            }
            else {
               ps.setInt(6, (new Integer(quantities[ndx][2].toString())).intValue());
            }
                
            for(int i = 0; i < locationIds.length; i++) {
                ps.setString(7 + i, locationIds[i]);
            }
                
            System.out.println(ps.toString());
            ResultSet r = ps.executeQuery();
            boolean hasNext = r.next();
            int count = 0;
                
            while(hasNext && count <= limit) {
               Object[] returnObj = {r.getString(1), r.getInt(2), r.getInt(3),
                                     r.getInt(4), r.getInt(5), r.getString(6),
                                     r.getString(7), r.getDouble(8),
                                     r.getInt(9), 0, 0};
                    
               inventory.add(returnObj);
                    
               hasNext = r.next();
               count++;
            }
         }
         catch(Exception e) {
            e.printStackTrace();
         }
      }
        
      return inventory;
   }
    
   public ArrayList<Object[]> getInventory(int fulfillerId,
    int[] manCatalog, Object[][] quantities, String[] locationIds,
    Object[] location, String type, int limit, Boolean ignoreSafetyStock,
    Boolean includeNegativeInventory, boolean orderByLtd) {
        
      ArrayList<Object[]> inventory = new ArrayList<Object[]>();
        
      if(!setUpConnection()) {
         return inventory;
      }
        
      if(type.equals("ALL") || type.equals("ALL_STORES")) {
         inventory =  getAllInventory(fulfillerId, manCatalog, quantities,
          locationIds, location, type, limit, ignoreSafetyStock,
          includeNegativeInventory, orderByLtd);
      }
      else if(type.equals("PARTIAL") || type.equals("ANY")) {
         inventory = getSomeInventory(fulfillerId, manCatalog, quantities,
          locationIds, location, type, limit, ignoreSafetyStock,
          includeNegativeInventory, orderByLtd);
      }
        
      closeConnection();
        
      return inventory;
   }
    
    public double getDistance(String unit, double lat1, double lon1, double lat2, double lon2) {
        int kmRadius = 6371; // Radius of the earth in km
        int miRadius = 3959; // Radius of the earth in mi
        double distance = 0;
        
        /*Double lat1 = Double.parseDouble(args[0]);
         Double lon1 = Double.parseDouble(args[1]);
         Double lat2 = Double.parseDouble(args[2]);
         Double lon2 = Double.parseDouble(args[3]);*/
        double latDistance = toRad(lat2-lat1);
        double lonDistance = toRad(lon2-lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
        Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        if(unit.equals("MILES"))
            distance = miRadius * c;
        else if(unit.equals("KM"))
            distance = kmRadius * c;
        
        //System.out.println("The distance between two lat and long is::" + distance);
        
        return distance;
    }
    
    public double toRad(double value) {
        return value * Math.PI / 180;
    }
}
