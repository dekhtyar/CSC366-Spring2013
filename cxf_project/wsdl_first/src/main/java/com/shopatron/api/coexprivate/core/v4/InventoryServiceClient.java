
package com.shopatron.api.coexprivate.core.v4;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

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

/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-04T10:16:15.490-07:00
 * Generated source version: 2.7.5
 * 
 */
public final class InventoryServiceClient {

    private static final QName SERVICE_NAME = new QName("http://v4.core.coexprivate.api.shopatron.com", "CoreServiceService");

    private InventoryServiceClient() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = CoreServiceService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        CoreServiceService ss = new CoreServiceService(wsdlURL, SERVICE_NAME);
        CoreService port = ss.getCoreService();  
        
        {
        System.out.println("Invoking createFulfillmentLocation...");
        com.shopatron.api.coexprivate.core.v4.FulfillmentLocation _createFulfillmentLocation_request = null;
        int _createFulfillmentLocation__return = port.createFulfillmentLocation(_createFulfillmentLocation_request);
        System.out.println("createFulfillmentLocation.result=" + _createFulfillmentLocation__return);


        }
        {
        System.out.println("Invoking createFulfiller...");
        com.shopatron.api.coexprivate.core.v4.FulfillerRequest _createFulfiller_request = null;
        int _createFulfiller__return = port.createFulfiller(_createFulfiller_request);
        System.out.println("createFulfiller.result=" + _createFulfiller__return);


        }
        {
        System.out.println("Invoking getInventory...");
        com.shopatron.api.coexprivate.core.v4.InventoryRequest _getInventory_request = null;
        java.util.List<com.shopatron.api.coexprivate.core.v4.InventoryResponse> _getInventory__return = port.getInventory(_getInventory_request);
        System.out.println("getInventory.result=" + _getInventory__return);


        }
        {
        System.out.println("Skipping refreshInventory...");
       /* com.shopatron.api.coexprivate.core.v4.RefreshRequest _refreshInventory_parameters = null;
        java.lang.String _refreshInventory__return = port.refreshInventory(_refreshInventory_parameters);
        System.out.println("refreshInventory.result=" + _refreshInventory__return);
       */

        }
        {
        System.out.println("Invoking getFulfillmentLocationTypes...");
        java.util.List<com.shopatron.api.coexprivate.core.v4.FulfillmentLocationType> _getFulfillmentLocationTypes__return = port.getFulfillmentLocationTypes();
        System.out.println("getFulfillmentLocationTypes.result=" + _getFulfillmentLocationTypes__return);


        }
        {
        System.out.println("Invoking getBins...");
        com.shopatron.api.coexprivate.core.v4.BinRequest _getBins_request = null;
        com.shopatron.api.coexprivate.core.v4.BinResponse _getBins__return = port.getBins(_getBins_request);
        System.out.println("getBins.result=" + _getBins__return);


        }
        {
        System.out.println("Invoking createBin...");
        com.shopatron.api.coexprivate.core.v4.Bin _createBin_request = null;
        java.math.BigInteger _createBin__return = port.createBin(_createBin_request);
        System.out.println("createBin.result=" + _createBin__return);


        }
        {
        System.out.println("Invoking adjustInventory...");
        com.shopatron.api.coexprivate.core.v4.AdjustRequest _adjustInventory_parameter = null;
        java.lang.String _adjustInventory__return = port.adjustInventory(_adjustInventory_parameter);
        System.out.println("adjustInventory.result=" + _adjustInventory__return);


        }
        {
        System.out.println("Invoking deallocateInventory...");
        com.shopatron.api.coexprivate.core.v4.UpdateRequest _deallocateInventory_request = null;
        port.deallocateInventory(_deallocateInventory_request);


        }
        {
        System.out.println("Invoking getBinStatuses...");
        java.util.List<com.shopatron.api.coexprivate.core.v4.BinStatus> _getBinStatuses__return = port.getBinStatuses();
        System.out.println("getBinStatuses.result=" + _getBinStatuses__return);


        }
        {
        System.out.println("Invoking getFulfillerStatus...");
        int _getFulfillerStatus_fulfillerID = 0;
        int _getFulfillerStatus__return = port.getFulfillerStatus(_getFulfillerStatus_fulfillerID);
        System.out.println("getFulfillerStatus.result=" + _getFulfillerStatus__return);


        }
        {
        System.out.println("Invoking fulfillInventory...");
        com.shopatron.api.coexprivate.core.v4.UpdateRequest _fulfillInventory_request = null;
        port.fulfillInventory(_fulfillInventory_request);


        }
        {
        System.out.println("Skipping allocateInventory...");
       /* com.shopatron.api.coexprivate.core.v4.UpdateRequest _allocateInventory_request = null;
        port.allocateInventory(_allocateInventory_request);
       */

        }
        {
        System.out.println("Invoking getFulfillmentLocations...");
        com.shopatron.api.coexprivate.core.v4.OrderRequest _getFulfillmentLocations_request = null;
        java.util.List<com.shopatron.api.coexprivate.core.v4.AssignmentResponse> _getFulfillmentLocations__return = port.getFulfillmentLocations(_getFulfillmentLocations_request);
        System.out.println("getFulfillmentLocations.result=" + _getFulfillmentLocations__return);


        }
        {
        System.out.println("Invoking getBinTypes...");
        java.util.List<com.shopatron.api.coexprivate.core.v4.BinType> _getBinTypes__return = port.getBinTypes();
        System.out.println("getBinTypes.result=" + _getBinTypes__return);


        }

        System.exit(0);
    }

}
