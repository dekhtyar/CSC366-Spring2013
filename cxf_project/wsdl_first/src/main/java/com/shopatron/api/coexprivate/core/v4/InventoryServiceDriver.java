
package com.shopatron.api.coexprivate.core.v4;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.*;
import java.math.BigInteger;
import java.sql.SQLException;

public final class InventoryServiceDriver {

    private InventoryServiceDriver() {
    }

    private static CoreService port;
    
    public static void main(String args[]) throws java.lang.Exception {
        port = new InventoryServiceImpl();  
        
        createFulfiller();
        createFulfillmentLocation();
        createBin();
        
        getFulfillmentLocations();
        getBins();
        
        getFulfillmentLocationTypes();
        getBinStatuses();
        getBinTypes();
        
        getFulfillerStatus();
        
        getInventory();
        refreshInventory();
        adjustInventory();
        
        fulfillInventory();
        
        allocateInventory();
        deallocateInventory();
        
        System.exit(0);
    }
    
    private static void createFulfillmentLocation() {
        printOperationStart();
        System.out.println("Invoking createFulfillmentLocation...\n");
        
        FulfillmentLocation locationRequest = new FulfillmentLocation();
        locationRequest.setExternalLocationID("6666");
        locationRequest.setFulfillerID(Utils.bigInt(666));
        locationRequest.setLatitude(3.0);
        locationRequest.setLongitude(5.6);
        locationRequest.setLocationName("SLO Mountain Air Sports");
        locationRequest.setLocationType("General");
        locationRequest.setRetailerLocationID(Utils.bigInt(3));
        locationRequest.setStatus(1);

        CreateFulfillmentLocation locationRequestWrap = new CreateFulfillmentLocation();
        locationRequestWrap.setRequest(locationRequest);

        printFulfillmentLocation(locationRequest);
        
        CreateFulfillmentLocationHelp.createLocation(locationRequestWrap);
        
        /* TODO actually find if it was created successfully. */
        System.out.println();
        System.out.println("Location created successfully.");
        
        printOperationEnd();
    }
    
    private static void createFulfiller() {
        printOperationStart();
        System.out.println("Invoking createFulfiller...");
        com.shopatron.api.coexprivate.core.v4.FulfillerRequest _createFulfiller_request = null;
        int _createFulfiller__return = port.createFulfiller(_createFulfiller_request);
        System.out.println("Unsupported Operation. We have no need for creating Fulfillers separate from Locations.");
        printOperationEnd();
    }
    
    private static void getInventory() {
        printOperationStart();
        System.out.println("Invoking getInventory...\n");
        
        InventoryRequest getInvReq = new InventoryRequest();
        ManufacturerCatalog manuCat = new ManufacturerCatalog();
        RequestLocation reqLoc = new RequestLocation();
        ArrayOfLocationIDs locIDs = new ArrayOfLocationIDs();
        ArrayOfImplItemQuantity itemQ = new ArrayOfImplItemQuantity();
        ItemQuantity item = new ItemQuantity();
        ItemQuantity item2 = new ItemQuantity();
        InventoryRequestType reqType = null;

        manuCat.setCatalogID(Utils.bigInt(0));
        manuCat.setManufacturerID(Utils.bigInt(10636));
        reqLoc.setCountryCode("1");
        reqLoc.setLatitude(42.1);
        reqLoc.setLongitude(-73.7);
        reqLoc.setPostalCode("07101");
        reqLoc.setRadius(Utils.bigInt(15));
        reqLoc.setUnit("MILES");
        locIDs.getExternalLocationID().add("101");
        locIDs.getExternalLocationID().add("102");
        locIDs.getExternalLocationID().add("600");
        item.setPartNumber("53044953");
        item.setQuantity(1);
        item.setUPC("53044953");
        itemQ.getItems().add(item);
        item2.setPartNumber("200033103");
        item2.setQuantity(1);
        item2.setUPC("200033103");
        itemQ.getItems().add(item2);

        getInvReq.setCatalog(manuCat);
        getInvReq.setFulfillerID(Utils.bigInt(48590));
        getInvReq.setIgnoreSafetyStock(true);
        getInvReq.setIncludeNegativeInventory(false);
        getInvReq.setLimit(1000);
        getInvReq.setLocation(reqLoc);
        getInvReq.setLocationIDs(locIDs);
        getInvReq.setOrderByLTD(false);
        getInvReq.setQuantities(itemQ);
        getInvReq.setType(reqType.fromValue("ANY"));

        List<InventoryResponse> getInvRet = port.getInventory(getInvReq);
        for(InventoryResponse invRes : getInvRet)
        {
           System.out.println("Name: " + invRes.getLocationName() + " " +
                              "CatalogID: " + invRes.getCatalogID() + " " + 
                              "ManufacturerID: " + invRes.getManufacturerID() + " " +
                              "OnHand: " + invRes.getOnHand() + " " +
                              "Available: " + invRes.getAvailable() + " " +
                              "Part Number: " + invRes.getPartNumber() + " " +
                              "UPC: " + invRes.getUPC() + " " +
                              "LTD: " + invRes.getLTD() + " " +
                              "SafetyStock: " + invRes.getSafetyStock() + " " +
                              "CountryCode: " + invRes.getCountryCode() + " " +
                              "Distance: " + invRes.getDistance());
        }

        System.out.println();
        System.out.println("----- Get Inventory (By Locations) -----");
        System.out.println();
        
        doLocationInventoryRequest1();

        printOperationEnd();
    }
    
    private static void refreshInventory() {
        printOperationStart();
        System.out.println("Invoking refreshInventory...\n");
        RefreshRequest refreshInventoryRequest = new RefreshRequest();
        RefreshItem item = new RefreshItem();
        RefreshItem item2 = new RefreshItem();
        ArrayOfImplRefreshItem items = new ArrayOfImplRefreshItem();

        /* Existing product */
        item.setBinID(199);
        item.setLTD(2.0);
        item.setPartNumber("53044953");
        item.setQuantity(7);
        item.setSafetyStock(1);
        item.setUPC("53044953");

        /* product that does not exist */
        item2.setBinID(199);
        item2.setLTD(2.0);
        item2.setPartNumber("1111111");
        item2.setQuantity(5);
        item2.setSafetyStock(1);
        item2.setUPC("1111111");

        items.getItems().add(item);
        items.getItems().add(item2);

        refreshInventoryRequest.setExternalLocationID("600");
        refreshInventoryRequest.setFulfillerID(Utils.bigInt(48590));
        refreshInventoryRequest.setItems(items);
        
        printRefreshRequest(refreshInventoryRequest);

        String refreshInventoryReturn = port.refreshInventory(refreshInventoryRequest);
        System.out.println(refreshInventoryReturn);

        printOperationEnd();
    }
    
    private static void getFulfillmentLocationTypes() {
        printOperationStart();
        System.out.println("Invoking getFulfillmentLocationTypes...\n");
        java.util.List<FulfillmentLocationType> locTypes = port.getFulfillmentLocationTypes();
        System.out.println("Location Types:");
        
        for (FulfillmentLocationType locType : locTypes) {
           System.out.println(locType.getLocationType());
        }
        printOperationEnd();

    }
    
    private static void getBins() {
        printOperationStart();
        System.out.println("Invoking getBins...\n");
        BinRequest getBinsRequest = new BinRequest();
        
        getBinsRequest.setExternalLocationID("600");
        getBinsRequest.setFulfillerID(Utils.bigInt(48590));
        getBinsRequest.setNumResults(Utils.bigInt(5));
        getBinsRequest.setResultsStart(Utils.bigInt(0));
        
        printBinsRequest(getBinsRequest);
        System.out.println();
        BinResponse getBinsReturn = port.getBins(getBinsRequest);

        for (Bin bin : getBinsReturn.getBins().getItems()) {
            System.out.println("Name: " + bin.getName() + " BinID: " + bin.getBinID());
        }        
    
        printOperationEnd();
    }
    
    private static void createBin() {
        printOperationStart();
        System.out.println("Invoking createBin...");
        Bin bin = new Bin();
        bin.setBinID(BigInteger.valueOf(77));
        bin.setBinStatus("Pickable");
        bin.setBinType("General");
        bin.setExternalLocationID("600");
        bin.setFulfillerID(BigInteger.valueOf(48590));
        bin.setName("Channel 4 Bin");

        System.out.println();
        printBin(bin);
        System.out.println();

        java.math.BigInteger status = port.createBin(bin);
        if (status.intValue() >= 0) {
           System.out.println("Bin created succesfully.");
        }
        else {
           System.out.println("Failed to create Bin.");
        }
    
        printOperationEnd();
    }
    
    private static void adjustInventory() {
        printOperationStart();
        System.out.println("Invoking adjustInventory...\n");
        AdjustRequest adjustInventoryRequest = new AdjustRequest();
        AdjustItem item = new AdjustItem();
        ArrayOfImplAdjustItem items = new ArrayOfImplAdjustItem();	
          
        item.setBinID(199);
        item.setPartNumber("DefaultPart");
        item.setQuantity(4);
        item.setUPC("53044953");
        items.getItems().add(item);

        adjustInventoryRequest.setExternalLocationID("600");
        adjustInventoryRequest.setFulfillerID(Utils.bigInt(48590));
        adjustInventoryRequest.setItems(items);
			  
        String adjustInventoryReturn = port.adjustInventory(adjustInventoryRequest);
        System.out.print(adjustInventoryReturn);

        printOperationEnd();
    }
    
    private static void getBinStatuses() { 
        printOperationStart();
        System.out.println("Invoking getBinStatuses...");
        java.util.List<BinStatus> binStatuses = port.getBinStatuses();
        System.out.println("Bin Statuses:");
        
        for (BinStatus binStatus : binStatuses) {
           System.out.println(binStatus.getBinStatus());
        }
        
        printOperationEnd();
    }
    
    private static void getFulfillerStatus() { 
        printOperationStart();
        System.out.println("Invoking getFulfillerStatus...");

        System.out.println("FulfillerID: 48590");
        int status = port.getFulfillerStatus(48590);
        
        if(status == 1) {
           System.out.println("Active.");
        }
        else {
           System.out.println("Inactive.");
        }
        
        printOperationEnd();
    }
    
    private static void fulfillInventory() { 
        printOperationStart();
        System.out.println("Invoking fulfillInventory...");

        try {
            Utils.doUpdateQuery("Update Holds set Allocation = 100, onHand = 100 where BinId = " + 199 + " and SKU = '" + 53044953 + "';");
        }
        catch(SQLException e) {
            System.err.println("Failed to update Holds");
            System.err.println(e.getMessage());
        }
        
        UpdateRequest fulfillRequest = new UpdateRequest();

        ArrayOfImplUpdateItem items = new ArrayOfImplUpdateItem();

        FulfillmentLocationCatalog cat = new FulfillmentLocationCatalog();
        cat.setExternalLocationID("600");

        UpdateItem item1 = new UpdateItem();
        item1.setUPC("53044953");
        item1.setPartNumber("53044953");
        item1.setExternalLocationID("600");
        item1.setQuantity(1);

        items.getItems().add(item1);

        fulfillRequest.setItems(items);
        fulfillRequest.setFulfillerID(Utils.bigInt(48590));
        fulfillRequest.setFulfillerLocationCatalog(cat);
        FulfillInventoryHelp.fulfillInventory(fulfillRequest);
        
        printOperationEnd();
    }
    
    private static void allocateInventory() { 
        printOperationStart();
        System.out.println("Invoking allocateInventory...");
        
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setFulfillerID(BigInteger.valueOf(48590));
        FulfillmentLocationCatalog cat = new FulfillmentLocationCatalog();
        cat.setExternalLocationID("600");
        ManufacturerCatalog manCat = new ManufacturerCatalog();
        manCat.setCatalogID(BigInteger.valueOf(10636));
        manCat.setManufacturerID(BigInteger.valueOf(0));
        cat.setManufacturerCatalog(manCat);
        updateRequest.setFulfillerLocationCatalog(cat);
        updateRequest.setItems(new ArrayOfImplUpdateItem());
        List<UpdateItem> updateItems = updateRequest.getItems().getItems();
        UpdateItem updateItem = new UpdateItem();
        updateItem.setExternalLocationID("600");
        updateItem.setPartNumber("200237407");
        updateItem.setUPC("200237407");
        updateItem.setQuantity(1);
        updateItems.add(updateItem);
        
        port.allocateInventory(updateRequest);
    
        printOperationEnd();
    } 
    
    private static void deallocateInventory() {
        printOperationStart();
        System.out.println("Invoking deallocateInventory...");
        System.out.println();
        
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setFulfillerID(BigInteger.valueOf(48590));
        FulfillmentLocationCatalog cat = new FulfillmentLocationCatalog();
        cat.setExternalLocationID("600");
        ManufacturerCatalog manCat = new ManufacturerCatalog();
        manCat.setCatalogID(BigInteger.valueOf(10636));
        manCat.setManufacturerID(BigInteger.valueOf(0));
        cat.setManufacturerCatalog(manCat);
        updateRequest.setFulfillerLocationCatalog(cat);
        updateRequest.setItems(new ArrayOfImplUpdateItem());
        List<UpdateItem> updateItems = updateRequest.getItems().getItems();
        UpdateItem updateItem = new UpdateItem();
        updateItem.setExternalLocationID("600");
        updateItem.setPartNumber("200237407");
        updateItem.setUPC("200237407");
        updateItem.setQuantity(-2);
        updateItems.add(updateItem);
        
        port.deallocateInventory(updateRequest);
        
        printOperationEnd();
    }
    
    private static void getFulfillmentLocations() {
        printOperationStart();
        System.out.println("Invoking getFulfillmentLocations...");
        OrderRequest request = new OrderRequest();
        RequestLocation reqLoc = new RequestLocation();
        ManufacturerCatalog manCat = new ManufacturerCatalog();

        request.setFulfillerID(Utils.bigInt(91771));
        manCat.setCatalogID(Utils.bigInt(2));
        manCat.setManufacturerID(Utils.bigInt(1392));
        request.setCatalog(manCat);
        reqLoc.setCountryCode("1");
        reqLoc.setLatitude(36.7);
        reqLoc.setLongitude(-76.07);
        reqLoc.setPostalCode("93405");
        reqLoc.setRadius(Utils.bigInt(10000));
        reqLoc.setUnit("MILES");
        request.setLocation(reqLoc);
        request.setMaxLocations(Utils.bigInt(10));

        List<AssignmentResponse> getFulfillmentLoc = port.getFulfillmentLocations(request);

        for(AssignmentResponse loc : getFulfillmentLoc)
        {
           System.out.println("FulfillerID: " + loc.getFulfillerID() + " ExtFulfillerID: " + 
                 loc.getExternalLocationID());
        }
        
        printOperationEnd();
        
    }

    private static void getBinTypes() {
        printOperationStart();
        System.out.println("Invoking getBinTypes...");
        List<BinType> binTypes = port.getBinTypes();
        
        System.out.println("Bin types: ");
        for (BinType binType : binTypes){
           System.out.println(binType.getBinType());
        }

        printOperationEnd();
    }
    
    
    private static void doLocationInventoryRequest1(){
       System.out.println();
       System.out.println("-- Test 1 --");
       System.out.println();
      
       InventoryRequest request = new InventoryRequest();
      
       ManufacturerCatalog manCat = new ManufacturerCatalog();
       manCat.setCatalogID(Utils.bigInt(0));
       manCat.setManufacturerID(Utils.bigInt(10636));
       
       ArrayOfLocationIDs locations = new ArrayOfLocationIDs();
       locations.getExternalLocationID().add("600");
       
       ArrayOfImplItemQuantity items = new ArrayOfImplItemQuantity();
       
       ItemQuantity item1 = new ItemQuantity();
       item1.setQuantity(1);
       item1.setUPC("53044953");
       items.getItems().add(item1);
       
       ItemQuantity item2 = new ItemQuantity();
       item2.setQuantity(2);
       item2.setUPC("10056100");
       items.getItems().add(item2);
       
       ItemQuantity item3 = new ItemQuantity();
       item3.setQuantity(1);
       item3.setUPC("10056100");
       items.getItems().add(item3);
       
       
       request.setCatalog(manCat);
       request.setFulfillerID(Utils.bigInt(48590));
       request.setIgnoreSafetyStock(false);
       request.setIncludeNegativeInventory(false);
       request.setOrderByLTD(false);
       request.setLocationIDs(locations);
       request.setQuantities(items);

       printLocationInventoryRequest(request);
       
       GetInventoryByLocations lookup1 = new GetInventoryByLocations(request);
       List<InventoryResponse> getInvRet = lookup1.getResponses();
       
       System.out.println();
      
      if (getInvRet.size() > 0){
          System.out.println("-- Items found: --");
          for(InventoryResponse invRes : getInvRet)
          {
            System.out.println("Name: " + invRes.getLocationName() + " " +
                              "\nCatalogID: " + invRes.getCatalogID() + " " + 
                              "\nManufacturerID: " + invRes.getManufacturerID() + " " +
                              "\nOnHand: " + invRes.getOnHand() + " " +
                              "\nAvailable: " + invRes.getAvailable() + " " +
                              "\nUPC: " + invRes.getUPC() + " " +
                              "\nLTD: " + invRes.getLTD() + " " +
                              "\nSafetyStock: " + invRes.getSafetyStock() + " ");
            System.out.println();
                              
         }
      }
         
    }
    
    private static void doLocationInventoryRequest2(){
       System.out.println();
       System.out.println("-- Test 2 --");
       System.out.println();
      
       InventoryRequest request = new InventoryRequest();
      
       ManufacturerCatalog manCat = new ManufacturerCatalog();
       manCat.setCatalogID(Utils.bigInt(0));
       manCat.setManufacturerID(Utils.bigInt(10636));
       
       ArrayOfLocationIDs locations = new ArrayOfLocationIDs();
       locations.getExternalLocationID().add("600");
       
       ArrayOfImplItemQuantity items = new ArrayOfImplItemQuantity();
       
       ItemQuantity item1 = new ItemQuantity();
       item1.setQuantity(1);
       item1.setUPC("53044953");
       items.getItems().add(item1);
       
       ItemQuantity item2 = new ItemQuantity();
       item2.setQuantity(2);
       item2.setUPC("10056100");
       items.getItems().add(item2);
       
       request.setCatalog(manCat);
       request.setFulfillerID(Utils.bigInt(48590));
       request.setIgnoreSafetyStock(false);
       request.setIncludeNegativeInventory(false);
       request.setOrderByLTD(false);
       request.setLocationIDs(locations);
       request.setQuantities(items);

       printLocationInventoryRequest(request);
       
       GetInventoryByLocations lookup1 = new GetInventoryByLocations(request);
       List<InventoryResponse> getInvRet = lookup1.getResponses();
       
       System.out.println();
      
      if (getInvRet.size() > 0){
          System.out.println("-- Items found: --");
          for(InventoryResponse invRes : getInvRet)
          {
            System.out.println("Name: " + invRes.getLocationName() + " " +
                              "\nCatalogID: " + invRes.getCatalogID() + " " + 
                              "\nManufacturerID: " + invRes.getManufacturerID() + " " +
                              "\nOnHand: " + invRes.getOnHand() + " " +
                              "\nAvailable: " + invRes.getAvailable() + " " +
                              "\nUPC: " + invRes.getUPC() + " " +
                              "\nLTD: " + invRes.getLTD() + " " +
                              "\nSafetyStock: " + invRes.getSafetyStock() + " ");
            System.out.println();
                              
         }
      }
         
    }
    
    private static void printLocationInventoryRequest(InventoryRequest request){
       System.out.println("ManufacturerID: " + request.getCatalog().getManufacturerID());
       System.out.println("CatalogID: " + request.getCatalog().getCatalogID());
       System.out.println("FulfillerID: " + request.getFulfillerID());
       System.out.println("Ignore Safety Stock: " + request.isIgnoreSafetyStock());
       System.out.println("Include Negative Inventory: " + request.isIncludeNegativeInventory());
       System.out.println("Order By LTD: " + request.isOrderByLTD());
       System.out.print("Restricitng to locations: ");
       
       for (String location : request.getLocationIDs().getExternalLocationID()){
          System.out.print(location + ",  ");
       }
       System.out.println();
       System.out.println("Looking for items: ");
       
       for (ItemQuantity item : request.getQuantities().getItems()){
          System.out.println("UPC: " + item.getUPC() + " Quantity: " + item.getQuantity());
       }
    }

    private static void printBin(Bin bin) {
        System.out.println("Bin ID: " + bin.getBinID());
        System.out.println("Bin Status: " + bin.getBinStatus());
        System.out.println("Bin Type: " + bin.getBinType());
        System.out.println("Ext Loc ID: " + bin.getExternalLocationID());
        System.out.println("Fulfiller ID: " + bin.getFulfillerID());
        System.out.println("Name: " + bin.getName());
    }
    
    private static void printFulfillmentLocation(FulfillmentLocation loc) {
        System.out.println("Fulfiller ID: " + loc.getFulfillerID());
        System.out.println("Ext Loc ID: " + loc.getExternalLocationID());
        System.out.println("Status: " + loc.getStatus());
        System.out.println("Type: " + loc.getLocationType());
        System.out.println("Lat" + loc.getLatitude());
        System.out.println("Long: " + loc.getLongitude());
        System.out.println("Name: " + loc.getLocationName());
    }
    
    private static void printRefreshRequest(RefreshRequest request) {
        System.out.println("Fulfiller ID: " + request.getFulfillerID());
        System.out.println("Ext Loc ID: " + request.getExternalLocationID());
       
        System.out.println("Items:");
        for (RefreshItem item : request.getItems().getItems()) {
            System.out.println("\tUPC: " + item.getUPC());
            System.out.println("\tSKU: " + item.getPartNumber());
            System.out.println("\tOnHand: " + item.getQuantity());
            System.out.println("\tLTD: " + item.getLTD());
            System.out.println("\tSafetyStock: " + item.getSafetyStock());
            System.out.println();
        }
    }
    
    private static void printBinsRequest(BinRequest request) {
        System.out.println("Fulfiller ID: " + request.getFulfillerID());
        System.out.println("Ext Loc ID: " + request.getExternalLocationID());
        System.out.println("Num Results: " + request.getNumResults());
        System.out.println("Results Start: " + request.getResultsStart());
    }

    private static void printOperationStart() {
       System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    private static void printOperationEnd() {
       System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
       System.out.println();
    }
}
