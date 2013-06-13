
/**
 * CoreServiceServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.shopatron.api.coexprivate.core.v4;

    /**
     *  CoreServiceServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class CoreServiceServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public CoreServiceServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public CoreServiceServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getInventory method
            * override this method for handling normal response from getInventory operation
            */
           public void receiveResultgetInventory(
                    com.shopatron.api.coexprivate.core.v4.GetInventoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getInventory operation
           */
            public void receiveErrorgetInventory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createFulfillmentLocation method
            * override this method for handling normal response from createFulfillmentLocation operation
            */
           public void receiveResultcreateFulfillmentLocation(
                    com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createFulfillmentLocation operation
           */
            public void receiveErrorcreateFulfillmentLocation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for refreshInventory method
            * override this method for handling normal response from refreshInventory operation
            */
           public void receiveResultrefreshInventory(
                    com.shopatron.api.coexprivate.core.v4.RefreshResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from refreshInventory operation
           */
            public void receiveErrorrefreshInventory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getBins method
            * override this method for handling normal response from getBins operation
            */
           public void receiveResultgetBins(
                    com.shopatron.api.coexprivate.core.v4.GetBinsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getBins operation
           */
            public void receiveErrorgetBins(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createFulfiller method
            * override this method for handling normal response from createFulfiller operation
            */
           public void receiveResultcreateFulfiller(
                    com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createFulfiller operation
           */
            public void receiveErrorcreateFulfiller(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFulfillmentLocationTypes method
            * override this method for handling normal response from getFulfillmentLocationTypes operation
            */
           public void receiveResultgetFulfillmentLocationTypes(
                    com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFulfillmentLocationTypes operation
           */
            public void receiveErrorgetFulfillmentLocationTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fulfillInventory method
            * override this method for handling normal response from fulfillInventory operation
            */
           public void receiveResultfulfillInventory(
                    com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fulfillInventory operation
           */
            public void receiveErrorfulfillInventory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFulfillerStatus method
            * override this method for handling normal response from getFulfillerStatus operation
            */
           public void receiveResultgetFulfillerStatus(
                    com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFulfillerStatus operation
           */
            public void receiveErrorgetFulfillerStatus(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for allocateInventory method
            * override this method for handling normal response from allocateInventory operation
            */
           public void receiveResultallocateInventory(
                    com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from allocateInventory operation
           */
            public void receiveErrorallocateInventory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createBin method
            * override this method for handling normal response from createBin operation
            */
           public void receiveResultcreateBin(
                    com.shopatron.api.coexprivate.core.v4.CreateBinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createBin operation
           */
            public void receiveErrorcreateBin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFulfillmentLocations method
            * override this method for handling normal response from getFulfillmentLocations operation
            */
           public void receiveResultgetFulfillmentLocations(
                    com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFulfillmentLocations operation
           */
            public void receiveErrorgetFulfillmentLocations(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getBinStatuses method
            * override this method for handling normal response from getBinStatuses operation
            */
           public void receiveResultgetBinStatuses(
                    com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getBinStatuses operation
           */
            public void receiveErrorgetBinStatuses(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for adjustInventory method
            * override this method for handling normal response from adjustInventory operation
            */
           public void receiveResultadjustInventory(
                    com.shopatron.api.coexprivate.core.v4.AdjustResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from adjustInventory operation
           */
            public void receiveErroradjustInventory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getBinTypes method
            * override this method for handling normal response from getBinTypes operation
            */
           public void receiveResultgetBinTypes(
                    com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getBinTypes operation
           */
            public void receiveErrorgetBinTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deallocateInventory method
            * override this method for handling normal response from deallocateInventory operation
            */
           public void receiveResultdeallocateInventory(
                    com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deallocateInventory operation
           */
            public void receiveErrordeallocateInventory(java.lang.Exception e) {
            }
                


    }
    