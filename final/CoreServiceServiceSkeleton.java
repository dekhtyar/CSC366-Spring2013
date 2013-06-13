oreServiceServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.shopatron.api.coexprivate.core.v4;
    import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import org.apache.axis2.databinding.types.PositiveInteger;
    /**
     *  CoreServiceServiceSkeleton java skeleton for the axisService
     */

/* grap api.java and integrate the api class */
    public class CoreServiceServiceSkeleton implements CoreServiceServiceSkeletonInterface{

        private Connection conn;
        private ArrayList<Integer> ids = new ArrayList<Integer>();
        private ArrayList<Integer> mIds = new ArrayList<Integer>();
        private ArrayList<Integer> cIds = new ArrayList<Integer>();
        private ArrayList<Integer> intFulLocIds = new ArrayList<Integer>();
        api apiCaller = new api();
        
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
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param getInventory0 
             * @return getInventoryResponse1 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.GetInventoryResponse getInventory
                  (
                  com.shopatron.api.coexprivate.core.v4.GetInventory getInventory0
                  )
            {

                	 //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getInventory");
                	// System.out.println("In the beginning of the function");
                	 com.shopatron.api.coexprivate.core.v4.GetInventoryResponse valueHolder = new com.shopatron.api.coexprivate.core.v4.GetInventoryResponse();
                	 com.shopatron.api.coexprivate.core.v4.InventoryResponse[] valueArray = new com.shopatron.api.coexprivate.core.v4.InventoryResponse[1];
                	 com.shopatron.api.coexprivate.core.v4.InventoryResponse storeArray = new com.shopatron.api.coexprivate.core.v4.InventoryResponse();
                	 
                	// valueArray[0].
                	 int []catalog = new int[2];
                	 Object[][] itemQuant = new Object[getInventory0.localRequest.localQuantities.localItems.length][3];
                	 Object[] requestLocTrans = new Object[6];
                	 //String[] locId;
                	 int index = 0;
                	 int itemIndex = 0;
                	 com.shopatron.api.coexprivate.core.v4.InventoryResponse inventoryValues = new com.shopatron.api.coexprivate.core.v4.InventoryResponse();
                	 //valueHolder.localGetInventoryReturn[0].
                	 ArrayList<Object[]> helper = new ArrayList<Object[]>();
                	 Object intermediate;
                	 
                	 //valueHolder.
                	 
                	 try{
                	 //System.out.println(getInventory0.localRequest.localQuantities.localItems.length);
                	 while(getInventory0.localRequest.localQuantities.localItems.length != index)
                	 {
                		 
                		 itemQuant[index][itemIndex] = getInventory0.localRequest.localQuantities.localItems[index];
                		 itemIndex++;
                		 
                		 if(itemIndex == 3)
                		 {
                			 
                			 itemIndex = 0;
                			 index++;
                		 }
                		 
                	 }
                	 /* test to make sure you can let this be null */
                	// for(int i = 0; i<6;i++)
                	 //{
                	 	//System.out.println(getInventory0.localRequest.localLocation.localUnit);
                	 
                		 requestLocTrans[0] = getInventory0.localRequest.localLocation.localUnit;
                		 requestLocTrans[1] = getInventory0.localRequest.localLocation.localRadius;
                		 requestLocTrans[2] = getInventory0.localRequest.localLocation.localPostalCode;
                		 requestLocTrans[3] = getInventory0.localRequest.localLocation.localLongitude;
                		 requestLocTrans[4] = getInventory0.localRequest.localLocation.localLatitude;
                		 requestLocTrans[5] = getInventory0.localRequest.localLocation.localCountryCode;
                		 
                	 //}
                	 
                	 //while(getInventory0.localRequest.localLocation.)
                	 
                
                	 
                	 catalog[0] = 0;//getInventory0.localRequest.localCatalog.localCatalogID.intValue();
                	 catalog[1] = 0;//getInventory0.localRequest.localCatalog.localManufacturerID.intValue();
                	 
                	 
                	 
                	 helper = apiCaller.getInventory(getInventory0.localRequest.localFulfillerID.intValue(),catalog,
                     		itemQuant,
                     		getInventory0.localRequest.localLocationIDs.localExternalLocationID,
                     		requestLocTrans, // request Location type
                     		getInventory0.localRequest.localType.localInventoryRequestType, //inventory request type
                     		getInventory0.localRequest.localLimit,
                     		getInventory0.localRequest.localIgnoreSafetyStock,
                     		getInventory0.localRequest.localIncludeNegativeInventory,
                     		getInventory0.localRequest.localOrderByLTD); 
                	 
                	 
                	 //requestLocTrans[0] = "helper";
                	
           
                	 
                	 //helper.add(e)
                	 
                	 
                	// helper.add(requestLocTrans);
                	 requestLocTrans = helper.get(0);
                	 
                	 storeArray.setLocationName(requestLocTrans[0].toString()); //0
                	 storeArray.setCatalogID(0); // 1
                	 storeArray.setManufacturerID(0); //2
                	 
                	 System.out.println(requestLocTrans[3]);
                	 //storeArray
                	 storeArray.setOnHand((Integer)requestLocTrans[3]); //3
                	 
                	 
                	 storeArray.setAvailable((Integer)requestLocTrans[4]);
                	 storeArray.setPartNumber(requestLocTrans[5].toString());
                	 storeArray.setUPC(requestLocTrans[6].toString());
                	 storeArray.setLTD(new Double(requestLocTrans[7].toString()));
                	 storeArray.setSafetyStock((Integer)requestLocTrans[8]);
                	 storeArray.setCountryCode(requestLocTrans[9].toString());
                	 storeArray.setDistance(new Double(requestLocTrans[10].toString()));
                	 
                	 //valueArray.setGetInventoryReturn(storeArray)
                	valueArray[0] = storeArray;
                	 
                	// helper.add("name");
                	 //helper.add("");
                	 //valueArray = (InventoryResponse[]) helper.get(0);
                	 
                	 
//                	 intermediate = helper.get(0);
//                	 //inventoryValues.localLocationName = intermediate.toString();
//                	 inventoryValues.setLocationName(intermediate.toString());
//                	 //inventoryValues.localCatalogID = (Integer) (helper.get(1));
//                	 intermediate = helper.get(1);
//                	 //inventoryValues.localCatalogID = (Integer)intermediate;
//                	 inventoryValues.setCatalogID((Integer)intermediate);
//                	 
//                	 intermediate = helper.get(2);
//                	 //inventoryValues.localManufacturerID = (Integer)intermediate;
//                	 inventoryValues.setManufacturerID((Integer)intermediate);
//                	 
//                	 intermediate = helper.get(3);
//                	 //inventoryValues.localOnHand = (Integer)intermediate;
//                	 inventoryValues.setOnHand((Integer)intermediate);
//                	 
//                	 intermediate = helper.get(4);
//                	 //inventoryValues.localAvailable = (Integer)intermediate;
//                	 inventoryValues.setAvailable((Integer)intermediate);
//                	 
//                	 intermediate = helper.get(5);
//                	 //inventoryValues.localPartNumber = intermediate.toString();
//                	 inventoryValues.setPartNumber(intermediate.toString());
//                	 
//                	 intermediate = helper.get(6);
//                	// inventoryValues.localUPC = intermediate.toString();
//                	 inventoryValues.setUPC(intermediate.toString());
//                	 
//                	 intermediate = helper.get(7);
//                	// inventoryValues.localLTD = new Double(intermediate.toString());
//                	 inventoryValues.setLTD(new Double(intermediate.toString()));
//                	 
//                	 //safety stock int
//                	 
//                	 intermediate = helper.get(8);
//                	 //inventoryValues.localSafetyStock = (Integer)intermediate;
//                	 inventoryValues.setSafetyStock((Integer)intermediate);
//                	 
//                	 //country code strin
//                	 //distance double
//                	 
//                	 intermediate = helper.get(9);
//                	 //inventoryValues.localCountryCode = intermediate.toString();
//                	 inventoryValues.setCountryCode(intermediate.toString());
//                	 
//                	 intermediate = helper.get(10);
//                	 //inventoryValues.localDistance = new Double(intermediate.toString());
//                	 inventoryValues.setDistance(new Double(intermediate.toString()));
                	 }
                	 catch(Exception ex){
                		 ex.printStackTrace();
                	 }
                	 
                	 
                	 //valueHolder.localGetInventoryReturn[0] = helper.get(0);
                	 return valueHolder;
                	 
                	 

        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param createFulfillmentLocation2 
             * @return createFulfillmentLocationResponse3 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse createFulfillmentLocation
                  (
                  com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation createFulfillmentLocation2
                  )
            {
                	 
                //TODO : fill this with the necessary business logic
//                apiCaller.createFulfillmentLocation(locationName, fulfillerId, externalLocationId, internalFulfillerLocationId, description, latitude, longitude, status, safetyStockLimit, manufacturerId, catalogId)
               	 com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse response = new com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse();	 

               	 response.localCreateFulfillmentLocationReturn =  apiCaller.createFulfillmentLocation(createFulfillmentLocation2.localRequest.localFulfillerID.intValue(), 
               			 createFulfillmentLocation2.localRequest.localRetailerLocationID.intValue(),// internalFulfillerLocationId, 
               			 createFulfillmentLocation2.localRequest.localExternalLocationID,// externalLocationId, 
               			 createFulfillmentLocation2.localRequest.localLocationName,// locationName, 
               			 createFulfillmentLocation2.localRequest.localLocationType,// type, 
               			 createFulfillmentLocation2.localRequest.localLatitude,//latitude,
               			 createFulfillmentLocation2.localRequest.localLongitude,// longitude, 
               			 createFulfillmentLocation2.localRequest.localStatus.toString(),// status, 
               			 createFulfillmentLocation2.localRequest.localCountryCode);// countryCode)
               	 
               	 
               	 
           
                	 return response;
                	 
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param refreshRequest4 
             * @return refreshResponse5 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.RefreshResponse refreshInventory
                  (
                  com.shopatron.api.coexprivate.core.v4.RefreshRequest refreshRequest4
                  )
            {
                	 com.shopatron.api.coexprivate.core.v4.RefreshResponse response = new com.shopatron.api.coexprivate.core.v4.RefreshResponse();
                	 
                	 apiCaller.refreshInventory(refreshRequest4.localFulfillerID.intValue(), refreshRequest4.localExternalLocationID,
                			 refreshRequest4.localItems.localItems[0].localPartNumber, // SKU, 
                			 refreshRequest4.localItems.localItems[0].localUPC, // UPC, 
                			 refreshRequest4.localItems.localItems[0].localBinID, // binId, 
                			 refreshRequest4.localItems.localItems[0].localQuantity, // onhand, 
                			 refreshRequest4.localItems.localItems[0].localLTD, 
                			 refreshRequest4.localItems.localItems[0].localSafetyStock); //safetyStock)
                	 
                	 response.localRefreshResponse = "pass"; /* ask liz about this */ /*might return the SKU given or something, just pick a parameter */ 
                	 return response;
                //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#refreshInventory");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param getBins6 
             * @return getBinsResponse7 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.GetBinsResponse getBins
                  (
                  com.shopatron.api.coexprivate.core.v4.GetBins getBins6
                  )
            {
                	 ArrayList<Object[]> helper;
                	 Object[] intermediate = new Object[6];
                	 Object hoard;
                	 Object countRes = 0;
                	 int i = 0;
                	 PositiveInteger holder_temp; 
                	 
                	 com.shopatron.api.coexprivate.core.v4.GetBinsResponse recievedBins = new com.shopatron.api.coexprivate.core.v4.GetBinsResponse();
    
                	 com.shopatron.api.coexprivate.core.v4.BinResponse bins = new com.shopatron.api.coexprivate.core.v4.BinResponse();
                	 com.shopatron.api.coexprivate.core.v4.ArrayOf_impl_Bin values = new com.shopatron.api.coexprivate.core.v4.ArrayOf_impl_Bin();
                	 com.shopatron.api.coexprivate.core.v4.Bin[] bin_holder = new com.shopatron.api.coexprivate.core.v4.Bin[2];
                	 com.shopatron.api.coexprivate.core.v4.Bin bin_temp = new com.shopatron.api.coexprivate.core.v4.Bin();
                	 
                	 helper = apiCaller.getBins(getBins6.localRequest.localFulfillerID.intValue(),
                			 getBins6.localRequest.localExternalLocationID, 
                			 getBins6.localRequest.localSearchTerm, 
                			 getBins6.localRequest.localNumResults.intValue(),
                			 getBins6.localRequest.localResultsStart.intValue()); 
                	 
                	 
                	 
                	 countRes = helper.size();
                	 try{
                		 bin_holder = new com.shopatron.api.coexprivate.core.v4.Bin[helper.size()];
                		 bins.localResultCount = new PositiveInteger(countRes.toString());
                	 }
                	 catch (Exception ex){
                		 ex.printStackTrace();
                	 }
                	 
                	 while(helper.size() != i)
                	 {
                		 
                	 try{
                	 	/*intermediate[0] = 1;
                	 	intermediate[1] = 500;
                	 	intermediate[2] = "external";
                 	 	intermediate[3] = "name";
                	 	intermediate[4] = "Pickable";
                	 	intermediate[5] = "General"; */
                		 
                		 /*should push everything we need into intermediate */
                		 intermediate = helper.get(i);
                	 
                	 	holder_temp = new PositiveInteger(intermediate[1].toString());
              
                	 	bin_holder[i] = bin_temp;
                	 	bin_holder[i].setBinID(holder_temp);
                	 	bin_holder[i].localFulfillerID = new PositiveInteger(intermediate[0].toString()); //((PositiveInteger) intermediate[0]);
                	 	bin_holder[i].localExternalLocationID = ((String) intermediate[2]);
                	 	bin_holder[i].localBinType = ((String) intermediate[3]);
                	 	bin_holder[i].localBinStatus = ((String) intermediate[4]);
                	 	bin_holder[i].localName = ((String)intermediate[5]);
                	 	
                	 	values.setItems(bin_holder);

                	 	bins.setBins(values);
                	 	recievedBins.setGetBinsReturn(bins);
                	 }
                	 catch(Exception ex){
                		 ex.printStackTrace();
                	 }

                	 }
                	 
                	 
                	 
                return recievedBins;
                	 
        }
     
         
        /**
         * Auto generated method signature
         * Request to create a single fulfiller which will later contain one or more locations
                                     * @param createFulfiller8 
             * @return createFulfillerResponse9 
         */
        /* look into this */
                 public com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse createFulfiller
                  (
                  com.shopatron.api.coexprivate.core.v4.CreateFulfiller createFulfiller8
                  )
            {
                	 com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse helper = new com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse();
                //TODO : fill this with the necessary business logic
                	 createNewRetailer(createFulfiller8.localRequest.localFulfillerID.intValue(),createFulfiller8.localRequest.localName);
                	 helper.localCreateFulfillerReturn = createFulfiller8.localRequest.localFulfillerID.intValue();
                	 return helper;
                	
                //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#createFulfiller");
        }
     /*added in helper method from api.java */
                 public void createNewRetailer(int fulfillerId, String locationName)
                 {
                	 if(setUpConnection() == false)
                		 return;
                	 
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
                     if(closeConnection() == false)
                    	 return;
                 }
         
        /**
         * Auto generated method signature
         * 
                                     * @param getFulfillmentLocationTypes10 
             * @return getFulfillmentLocationTypesResponse11 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse getFulfillmentLocationTypes
                  (
                  com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes getFulfillmentLocationTypes10
                  )
            {
                	 com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse response = new com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse();
                	 ArrayList<String> helper = new ArrayList<String>();
                	 int i = 0;
                	 
                	 
                	 
                	 /* grab new api calls from git */
                	 //apiCaller.
                //TODO : fill this with the necessary business logic
                	 //apiCaller.
                	 //get
                	 helper = apiCaller.getFulfillmentLocationTypes();
                	 while(helper.size() != i)
                	 {
                		 response.localGetFulfillmentLocationTypesReturn[i].localLocationType = helper.get(i);
	 
                	 }
                	 
                	 
                	 return response;
                //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getFulfillmentLocationTypes");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param fulfillInventory12 
             * @return fulfillInventoryResponse13 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse fulfillInventory
                  (
                  com.shopatron.api.coexprivate.core.v4.FulfillInventory fulfillInventory12
                  )
            {
                	 //api happyAPI = new api();
                //TODO : fill this with the necessary business logic
                	 com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse response = new com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse();	 
                Object[][] locCatalog = new Object[0][0];
                Object[][] items = new Object[1][4];
                items[0][0] = (Object)fulfillInventory12.localRequest.localItems.localItems[0];
                items[0][1] = (Object)fulfillInventory12.localRequest.localItems.localItems[1];
                items[0][2] = (Object)fulfillInventory12.localRequest.localItems.localItems[2];
                items[0][3] = (Object)fulfillInventory12.localRequest.localItems.localItems[3];
                
                apiCaller.fulfillInventory(fulfillInventory12.localRequest.localFulfillerID.intValue(),locCatalog, items);
           
                return response;
        }
     
         
        /**
         * Auto generated method signature
         * Data retrieval to check a fulfiller's status
                                     * @param getFulfillerStatus14 
             * @return getFulfillerStatusResponse15 
         */

                 public com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse getFulfillerStatus
                  (
                  com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus getFulfillerStatus14
                  )
            {
                	 com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse response = new com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse();
                //TODO : fill this with the necessary business logic
                	 response.localGetFulfillerStatusReturn = 
                			 apiCaller.getFulfillerStatus(getFulfillerStatus14.localFulfillerID);
                	 
                	 
                	 //apiCaller.getFulfillmentLocations(getFulfillerStatus14.localFulfillerID, getFulfillerStatus14. manufacturerCatalog, requestLocation, maxLocations)
                	 /*ask about this */
                	 //apiCaller.getF
                	 return response;
               // throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getFulfillerStatus");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param allocateInventory16 
             * @return allocateInventoryResponse17 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse allocateInventory
                  (
                  com.shopatron.api.coexprivate.core.v4.AllocateInventory allocateInventory16
                  )
            {
                	 com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse response = new com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse();	 
                Object[][] locCatalog = new Object[0][0];
                Object[][] items = new Object[1][4];
                items[0][0] = (Object)allocateInventory16.localRequest.localItems.localItems[0];
                items[0][1] = (Object)allocateInventory16.localRequest.localItems.localItems[1];
                items[0][2] = (Object)allocateInventory16.localRequest.localItems.localItems[2];
                items[0][3] = (Object)allocateInventory16.localRequest.localItems.localItems[3];
                //fulfillInventory12.localRequest.localFulfillerLocationCatalog.
                //apiCaller.fulfillInventory(fulfillInventory12.localRequest.localFulfillerID, null,items);
                apiCaller.fulfillInventory(allocateInventory16.localRequest.localFulfillerID.intValue(),locCatalog, items);
                return response;
                //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#allocateInventory");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param createBin18 
             * @return createBinResponse19 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.CreateBinResponse createBin
                  (
                  com.shopatron.api.coexprivate.core.v4.CreateBin createBin18
                  )
            {
                	 com.shopatron.api.coexprivate.core.v4.CreateBinResponse response = new com.shopatron.api.coexprivate.core.v4.CreateBinResponse();
                	 Object createResponse = 0;
                	 //PositiveInteger helper = new PositiveInteger();
                	 
                	 /*last call will be handled tomorrow */
                //TODO : fill this with the necessary business logic
                	createResponse = apiCaller.createBin(createBin18.localRequest.localFulfillerID.intValue(), 
                			createBin18.localRequest.localBinID.intValue(), 
                			createBin18.localRequest.localExternalLocationID, createBin18.localRequest.localBinType, 
                			createBin18.localRequest.localBinStatus, createBin18.localRequest.localName);
                	response.localCreateBinReturn = new PositiveInteger(createResponse.toString()); //(PositiveInteger)(createResponse);
                	//createResponse;
                	
            
                	 
                	return response;
                //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#createBin");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param getFulfillmentLocations20 
             * @return getFulfillmentLocationsResponse21 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse getFulfillmentLocations
                  (
                  com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations getFulfillmentLocations20
                  )
            {
                	 com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse response = new com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse();
                	 Object[] manuHolder = new Object[2];
                	 Object[] locHolder = new Object[6];
                	 ArrayList<Object[]> holder = new ArrayList<Object[]>();
                	 Object[] holder_Object;
                	 int i = 0;
                	 
                	 manuHolder[0] = getFulfillmentLocations20.localRequest.localCatalog.localCatalogID;
                	 manuHolder[0] = getFulfillmentLocations20.localRequest.localCatalog.localManufacturerID;
                	 
                	 locHolder[0] = getFulfillmentLocations20.localRequest.localLocation.localCountryCode;
                	 locHolder[1] = getFulfillmentLocations20.localRequest.localLocation.localLatitude;
                	 locHolder[2] = getFulfillmentLocations20.localRequest.localLocation.localLongitude;
                	 locHolder[3] = getFulfillmentLocations20.localRequest.localLocation.localPostalCode;
                	 locHolder[4] = getFulfillmentLocations20.localRequest.localLocation.localRadius;
                	 locHolder[5] = getFulfillmentLocations20.localRequest.localLocation.localUnit;
                	 
                //TODO : fill this with the necessary business logic
                	 holder = apiCaller.getFulfillmentLocations(getFulfillmentLocations20.localRequest.localFulfillerID.intValue(), 
                			 manuHolder, locHolder, 
                			 getFulfillmentLocations20.localRequest.localMaxLocations.intValue());
                	 
                	 while(holder.size() != i)
                	 { /* fix this */
                		 holder_Object = holder.get(i);
                		 //response.addGetFulfillmentLocationsReturn(param)
                		 response.localGetFulfillmentLocationsReturn[i].localFulfillerID = (PositiveInteger) holder_Object[0];
                		 response.localGetFulfillmentLocationsReturn[i].localExternalLocationID = (String) holder_Object[1];
                		
                	 }
                	 
                	 return response;
                	 //response.localGetFulfillmentLocationsReturn[0].
                //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getFulfillmentLocations");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param getBinStatuses22 
             * @return getBinStatusesResponse23 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse getBinStatuses
                  (
                  com.shopatron.api.coexprivate.core.v4.GetBinStatuses getBinStatuses22
                  )
            {
                	 int i = 0;
                	 ArrayList<String> holder = new ArrayList<String>();

                	 com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse response = new com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse();
                	BinStatus nope = new BinStatus();
                	
                	 holder = apiCaller.getBinStatuses();

                	 try{
                		 while(holder.size() != i)
                		 {
                			 //nope.
                			 nope.localBinStatus = holder.get(i);
                			 response.addGetBinStatusesReturn(nope);
                			 i++;
      
                		 }
                		 
                	 }
                	 catch(Exception ex){
                		 ex.printStackTrace();
                	 }
                
                	 return response;
                	 
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param adjustRequest24 
             * @return adjustResponse25 
         */
        /* lauren is fixing it */
                 public com.shopatron.api.coexprivate.core.v4.AdjustResponse adjustInventory
                  (
                  com.shopatron.api.coexprivate.core.v4.AdjustRequest adjustRequest24
                  )
            {
                	 //tell whoever did adjust inventory to fix it according to what is online. 
                	 com.shopatron.api.coexprivate.core.v4.AdjustResponse response = new com.shopatron.api.coexprivate.core.v4.AdjustResponse();
                //TODO : fill this with the necessary business logic
                	/* response.localAdjustResponse = apiCaller.adjustInventory(adjustRequest24.localFulfillerID,
                			 adjustRequest24.localItems.localItems[0].localBinID,
                			 adjustRequest24.localItems.localItems[0].localQuantity, 
                			 adjustRequest24.localItems.localItems[0]., safetyStock, adjust)*/
                	 //apiCaller.adjustInventory(adjustRequest, binId, onhand, ltd, safetyStock, adjust)
                	 
                	 
                throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#adjustInventory");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param getBinTypes26 
             * @return getBinTypesResponse27 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse getBinTypes
                  (
                  com.shopatron.api.coexprivate.core.v4.GetBinTypes getBinTypes26
                  )
            {
                	 int i = 0;
                	 ArrayList<String> holder = new ArrayList<String>();
                	 com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse response = new com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse();
                	 BinType nope = new BinType();
                	 
                	 holder = apiCaller.getBinTypes();
                	 while(holder.size() != i)
                	 {
                		 nope.localBinType = holder.get(i);
                		 response.addGetBinTypesReturn(nope);
                		 i++;
                		 
                	 }
                	 return response;
                	// response.localGetBinTypesRetur
                //throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getBinTypes");
        }
     
         
        /**
         * Auto generated method signature
         * 
                                     * @param deallocateInventory28 
             * @return deallocateInventoryResponse29 
         */
        
                 public com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse deallocateInventory
                  (
                  com.shopatron.api.coexprivate.core.v4.DeallocateInventory deallocateInventory28
                  )
            {
                	 
                	 com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse response = new com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse();	 
                Object[][] locCatalog = new Object[0][0];
                Object[][] items = new Object[1][4];
                items[0][0] = (Object)deallocateInventory28.localRequest.localItems.localItems[0];
                items[0][1] = (Object)deallocateInventory28.localRequest.localItems.localItems[1];
                items[0][2] = (Object)deallocateInventory28.localRequest.localItems.localItems[2];
                items[0][3] = (Object)deallocateInventory28.localRequest.localItems.localItems[3];
               
                apiCaller.fulfillInventory(deallocateInventory28.localRequest.localFulfillerID.intValue(),locCatalog, items);
                return response;
          
        }
     
    }
    
