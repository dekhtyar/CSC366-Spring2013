package com.shopatron;

import org.apache.axis.types.PositiveInteger;

import com.shopatron.api.coexprivate.core.v4.AdjustItem;
import com.shopatron.api.coexprivate.core.v4.AssignmentResponse;
import com.shopatron.api.coexprivate.core.v4.Bin;
import com.shopatron.api.coexprivate.core.v4.BinRequest;
import com.shopatron.api.coexprivate.core.v4.BinResponse;
import com.shopatron.api.coexprivate.core.v4.FulfillmentLocation;
import com.shopatron.api.coexprivate.core.v4.FulfillmentLocationCatalog;
import com.shopatron.api.coexprivate.core.v4.FulfillmentLocationStatus;
import com.shopatron.api.coexprivate.core.v4.InventoryRequest;
import com.shopatron.api.coexprivate.core.v4.InventoryRequestType;
import com.shopatron.api.coexprivate.core.v4.InventoryResponse;
import com.shopatron.api.coexprivate.core.v4.ItemQuantity;
import com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog;
import com.shopatron.api.coexprivate.core.v4.OrderRequest;
import com.shopatron.api.coexprivate.core.v4.RequestLocation;
import com.shopatron.api.coexprivate.core.v4.UpdateItem;
import com.shopatron.api.coexprivate.core.v4.UpdateRequest;

public class Main {
	public static void main(String[] args) {
		System.out.println("MY CODE IS COMPILING");
		
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
//			//Database.executeSQL("CREATE TABLE Manufacturer ( 		   MfcID   INT, 		   PRIMARY KEY (MfcID) 		); 		CREATE TABLE Catalog ( 		   MfcID        INT, 		   CatalogID    INT, 		   PRIMARY KEY (MfcID, CatalogID), 		   FOREIGN KEY (MfcID) 		        REFERENCES Manufacturer (MfcID) 		); 		CREATE TABLE Item ( 		    UPC           INT PRIMARY KEY, 		    MfcID         INT, 		    CatalogID     INT, 		    FOREIGN KEY (MfcID, CatalogID) 		        REFERENCES Catalog (MfcID, CatalogID) 		); 		CREATE TABLE Fulfiller ( 		    FulfillerID INT PRIMARY KEY 		); 		CREATE TABLE Location ( 		    FulfillerID         INT, 		    InternalID          INT, 		    ExternalID          VARCHAR(20), 		    Name                VARCHAR(60), 		    StoreType           VARCHAR(60), 		    Latitude            FLOAT, 		    Longitude           FLOAT, 		    Status              VARCHAR(15), 		    SafetyStock         INT,  		    PRIMARY KEY (InternalID), 		    FOREIGN KEY (FulfillerID) 		        REFERENCES Fulfiller (FulfillerID) 		); 		CREATE TABLE Bin ( 		    LocationID  INT, 		    Name        VARCHAR(25), 		    BinType     VARCHAR(20), 		    BinStatus   VARCHAR(20), 		    PRIMARY KEY (LocationID, Name), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location (InternalID) 		); 		CREATE TABLE Seller ( 		    MfcID       INT, 		    CatalogID   INT, 		    LocationID  INT, 		    PRIMARY KEY (MfcID, CatalogID, LocationID), 		    FOREIGN KEY (MfcID, CatalogID) 		        REFERENCES Catalog (MfcID, CatalogID), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location(LocationID) 		); 		CREATE TABLE FulfillerItem ( 		    FulfillerID INT, 		    SKU         INT, 		    UPC         INT, 		    PRIMARY KEY (FulfillerID, SKU), 		    FOREIGN KEY (FulfillerID) 		        REFERENCES Fulfiller (FulfillerID), 		    FOREIGN KEY (UPC) 		        REFERENCES Item (UPC) 		); 		CREATE TABLE Stock ( 		    LocationID  INT, 		    FulfillerID INT, 		    SKU         INT, 		    Quantity    INT, 		    Safety      INT, 		    LTD         INT, 		    PRIMARY KEY (LocationID, SKU), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location (LocationID), 		    FOREIGN KEY (FulfillerID, SKU) 		        REFERENCES FulfillerItem (FulfillerID, SKU) 		);");
//			//Database.executeSQL("CREATE TABLE Manufacturer ( 		   MfcID   INT, 		   PRIMARY KEY (MfcID) 		) 		CREATE TABLE Catalog ( 		   MfcID        INT, 		   CatalogID    INT, 		   PRIMARY KEY (MfcID, CatalogID), 		   FOREIGN KEY (MfcID) 		        REFERENCES Manufacturer (MfcID) 		) 		CREATE TABLE Item ( 		    UPC           INT PRIMARY KEY, 		    MfcID         INT, 		    CatalogID     INT, 		    FOREIGN KEY (MfcID, CatalogID) 		        REFERENCES Catalog (MfcID, CatalogID) 		) 		CREATE TABLE Fulfiller ( 		    FulfillerID INT PRIMARY KEY 		) 		CREATE TABLE Location ( 		    FulfillerID         INT, 		    InternalID          INT, 		    ExternalID          VARCHAR(20), 		    Name                VARCHAR(60), 		    StoreType           VARCHAR(60), 		    Latitude            FLOAT, 		    Longitude           FLOAT, 		    Status              VARCHAR(15), 		    SafetyStock         INT,  		    PRIMARY KEY (InternalID), 		    FOREIGN KEY (FulfillerID) 		        REFERENCES Fulfiller (FulfillerID) 		) 		CREATE TABLE Bin ( 		    LocationID  INT, 		    Name        VARCHAR(25), 		    BinType     VARCHAR(20), 		    BinStatus   VARCHAR(20), 		    PRIMARY KEY (LocationID, Name), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location (InternalID) 		) 		CREATE TABLE Seller ( 		    MfcID       INT, 		    CatalogID   INT, 		    LocationID  INT, 		    PRIMARY KEY (MfcID, CatalogID, LocationID), 		    FOREIGN KEY (MfcID, CatalogID) 		        REFERENCES Catalog (MfcID, CatalogID), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location(LocationID) 		) 		CREATE TABLE FulfillerItem ( 		    FulfillerID INT, 		    SKU         INT, 		    UPC         INT, 		    PRIMARY KEY (FulfillerID, SKU), 		    FOREIGN KEY (FulfillerID) 		        REFERENCES Fulfiller (FulfillerID), 		    FOREIGN KEY (UPC) 		        REFERENCES Item (UPC) 		) 		CREATE TABLE Stock ( 		    LocationID  INT, 		    FulfillerID INT, 		    SKU         INT, 		    Quantity    INT, 		    Safety      INT, 		    LTD         INT, 		    PRIMARY KEY (LocationID, SKU), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location (LocationID), 		    FOREIGN KEY (FulfillerID, SKU) 		        REFERENCES FulfillerItem (FulfillerID, SKU) 		)");
//
//			Database.executeSQL("CREATE TABLE Manufacturer ( 		   MfcID   INT, 		   PRIMARY KEY (MfcID) 		)"); 		
//			System.out.println("---CREATED 1");
//			Database.executeSQL("CREATE TABLE Catalog ( 		   MfcID        INT, 		   CatalogID    INT, 		   PRIMARY KEY (MfcID, CatalogID), 		   FOREIGN KEY (MfcID) 		        REFERENCES Manufacturer (MfcID) 		)");
//			System.out.println("---CREATED 2");
//			Database.executeSQL("CREATE TABLE Item ( 		    UPC           INT PRIMARY KEY, 		    MfcID         INT, 		    CatalogID     INT, 		    FOREIGN KEY (MfcID, CatalogID) 		        REFERENCES Catalog (MfcID, CatalogID) 		)");
//			System.out.println("---CREATED 3");
//			Database.executeSQL("CREATE TABLE Fulfiller ( 		    FulfillerID INT PRIMARY KEY 		)");
//			System.out.println("---CREATED 4");
//			Database.executeSQL("CREATE TABLE Location ( 		    FulfillerID         INT, 		    InternalID          INT, 		    ExternalID          VARCHAR(20), 		    Name                VARCHAR(60), 		    StoreType           VARCHAR(60), 		    Latitude            FLOAT, 		    Longitude           FLOAT, 		    Status              VARCHAR(15), 		    SafetyStock         INT,  		    PRIMARY KEY (InternalID), 		    FOREIGN KEY (FulfillerID) 		        REFERENCES Fulfiller (FulfillerID) 		)");
//			System.out.println("---CREATED 5");
//			Database.executeSQL("CREATE TABLE Bin ( 		    LocationID  INT, 		    Name        VARCHAR(25), 		    BinType     VARCHAR(20), 		    BinStatus   VARCHAR(20), 		    PRIMARY KEY (LocationID, Name), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location 		)");
//			System.out.println("---CREATED 6");
//			Database.executeSQL("CREATE TABLE Seller ( 		    MfcID       INT, 		    CatalogID   INT, 		    LocationID  INT, 		    PRIMARY KEY (MfcID, CatalogID, LocationID), 		    FOREIGN KEY (MfcID, CatalogID) 		        REFERENCES Catalog (MfcID, CatalogID), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location 		)");	
//			System.out.println("---CREATED 7");
//			Database.executeSQL("CREATE TABLE FulfillerItem ( 		    FulfillerID INT, 		    SKU         INT, 		    UPC         INT, 		    PRIMARY KEY (FulfillerID, SKU), 		    FOREIGN KEY (FulfillerID) 		        REFERENCES Fulfiller (FulfillerID), 		    FOREIGN KEY (UPC) 		        REFERENCES Item (UPC) 		)");	
//			System.out.println("---CREATED 8");
//			Database.executeSQL("CREATE TABLE Stock ( 		    LocationID  INT, 		    FulfillerID INT, 		    SKU         INT, 		    Quantity    INT, 		    Safety      INT, 		    LTD         INT, 		    PRIMARY KEY (LocationID, SKU), 		    FOREIGN KEY (LocationID) 		        REFERENCES Location, 		    FOREIGN KEY (FulfillerID, SKU) 		        REFERENCES FulfillerItem (FulfillerID, SKU) 		)");
//			System.out.println("---CREATED 9");
//			Database.executeSQL("CREATE TABLE OnHand (     LocationID  INT,     Name        VARCHAR(25),     FulfillerID INT,     SKU         INT,     Quantity    INT,     Allocated   INT,     PRIMARY KEY (LocationID, Name, SKU),     FOREIGN KEY (LocationID, Name)         REFERENCES Bin (LocationID, Name),     FOREIGN KEY (FulfillerID, SKU)         REFERENCES FulfillerItem (FulfillerID, SKU) )");
//			Database.executeSQL("CREATE TABLE OnHandOrder (     OrderID     INT,     LocationID  INT,     Name        VARCHAR(25),     FulfillerID INT,     SKU         INT,     CreatedOn   DATE,     Quantity    INT,     PRIMARY KEY (OrderID, LocationID, Name, FulfillerID, SKU),     FOREIGN KEY (LocationID, Name)         REFERENCES Bin (LocationID, Name),     FOREIGN KEY (LocationID, Name, SKU)         REFERENCES OnHand (LocationID, Name, SKU) )");
//		
//			
//			System.out.println("----TABLE CREATED");
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		try {
//			UpdateRequest request = new UpdateRequest();
//			FulfillmentLocationCatalog flc = new FulfillmentLocationCatalog();
//			flc.setExternalLocationID("1");
//			
//			request.setFulfillerID(new PositiveInteger("1"));
//			request.setFulfillerLocationCatalog(flc);
			
//			UpdateItem[] items = new UpdateItem[1];
//			UpdateItem item = new UpdateItem();
//			item.setQuantity(1);
//			item.setPartNumber("1");
//			item.setUPC("1");
//			item.setExternalLocationID("1");
//			item.setOrderID(new PositiveInteger("6"));
			
//			AdjustItem[] items = new AdjustItem[1];
//			AdjustItem item = new AdjustItem();
//			item.setQuantity(100);
//			item.setPartNumber("1");
//			
//			items[0] = item;
//			
//			UseCases.adjustInventory(new PositiveInteger("1"), "1", items);
			
			//System.out.println("Result: " + result);
			
//			FulfillmentLocation request = new FulfillmentLocation();
//			
//			FulfillmentLocationStatus status = new FulfillmentLocationStatus(1);
//			
//			request.setLocationType("i hate this type");
//			
//			
//			
//			request.setLongitude(1.1);
//			request.setLatitude(1.2);
//			request.setCountryCode("Iceland");
//			request.setExternalLocationID("1");
//			request.setFulfillerID(new PositiveInteger("1"));
//			request.setLocationName("reykjavik");
//			request.setRetailerLocationID(new PositiveInteger("2"));
//			request.setStatus(status);
//			
//			
//			int result = UseCases.createFulfillmentLocation(request);
//			System.out.println("Result: " + result);
			
//			Bin request = new Bin();
//			request.setBinStatus("1");
//			request.setBinType("1");
//			request.setExternalLocationID("1");
//			request.setFulfillerID(new PositiveInteger("1"));
//			request.setName("my bin name");
//			
//			UseCases.createBin(request);
			
//			BinRequest request = new BinRequest();
//			request.setNumResults(new PositiveInteger("2"));
//			request.setSearchTerm("");
//			request.setExternalLocationID("1");
//			request.setResultsStart(new PositiveInteger("1"));
//			request.setFulfillerID(new PositiveInteger("1"));
//			
//			
//			BinResponse[] response = UseCases.getBins(request);
//			for (BinResponse br : response) {
//				Bin[] bins = br.getBins();
//				for (Bin bin : bins) {
//					System.out.println("bin name: " + bin.getName());
//				}
//				System.out.println("--------------------------------");
//			}
			
//			OrderRequest request = new OrderRequest();
//			
//			RequestLocation location = new RequestLocation();
//			location.setLatitude(40d);
//			location.setLongitude(-73d);
//			location.setRadius(new PositiveInteger("100"));
//			
//			request.setFulfillerID(new PositiveInteger("48590"));
//			request.setLocation(location);
//			
//			AssignmentResponse[] response = UseCases.getFulfillmentLocations(request);
//			for (AssignmentResponse res : response) {
//				System.out.println("location lat: " + res.getExternalLocationID());
			//}
			
			InventoryRequest request = new InventoryRequest();
			ItemQuantity iq = new ItemQuantity();
			iq.setPartNumber("1");
			iq.setQuantity(5);
			iq.setUPC("1");
			ItemQuantity[] items = new ItemQuantity[]{iq};
			ManufacturerCatalog mc = new ManufacturerCatalog();
			mc.setManufacturerID(new PositiveInteger("1"));
			mc.setCatalogID(new PositiveInteger("1"));
			
			
			request.setFulfillerID(new PositiveInteger("1"));
			request.setCatalog(mc);
			request.setQuantities(items);
			request.setLocationIDs(new String[]{"1"});
			request.setType(InventoryRequestType.ALL_STORES);
			request.setLimit(100);
			request.setIgnoreSafetyStock(false);
			request.setIncludeNegativeInventory(true);
			request.setOrderByLTD(true);
			
			InventoryResponse[] res = UseCases.getInventory(request);
			res =  res;
			
			
//			UpdateItem item = new UpdateItem();
//			item.setQuantity(30);
//			item.setPartNumber("1");
//			item.setUPC("1");
//			item.setExternalLocationID("1");
//			item.setOrderID(new PositiveInteger("14"));
//			UpdateItem[] items = new UpdateItem[]{item};
//			
//			ManufacturerCatalog mc = new ManufacturerCatalog();
//			mc.setManufacturerID(new PositiveInteger("1"));
//			mc.setCatalogID(new PositiveInteger("1"));
//			
//			FulfillmentLocationCatalog catalog = new FulfillmentLocationCatalog();
//			catalog.setExternalLocationID("1");
//			catalog.setManufacturerCatalog(mc);
//			
//			UpdateRequest request = new UpdateRequest();
//
//			request.setFulfillerID(new PositiveInteger("1"));
//			request.setFulfillerLocationCatalog(catalog);
//			request.setItems(items);
//			
//			//UseCases.allocateInventory(request);
//			UseCases.deallocateInventory(request);
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}




