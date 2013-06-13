
/**
 * CoreServiceServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.shopatron.api.coexprivate.core.v4;
    /**
     *  CoreServiceServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface CoreServiceServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param getInventory
         */

        
                public com.shopatron.api.coexprivate.core.v4.GetInventoryResponse getInventory
                (
                  com.shopatron.api.coexprivate.core.v4.GetInventory getInventory
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createFulfillmentLocation
         */

        
                public com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse createFulfillmentLocation
                (
                  com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation createFulfillmentLocation
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param refreshRequest
         */

        
                public com.shopatron.api.coexprivate.core.v4.RefreshResponse refreshInventory
                (
                  com.shopatron.api.coexprivate.core.v4.RefreshRequest refreshRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getBins
         */

        
                public com.shopatron.api.coexprivate.core.v4.GetBinsResponse getBins
                (
                  com.shopatron.api.coexprivate.core.v4.GetBins getBins
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * Request to create a single fulfiller which will later contain one or more locations
                                    * @param createFulfiller
         */

        
                public com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse createFulfiller
                (
                  com.shopatron.api.coexprivate.core.v4.CreateFulfiller createFulfiller
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getFulfillmentLocationTypes
         */

        
                public com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse getFulfillmentLocationTypes
                (
                  com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes getFulfillmentLocationTypes
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param fulfillInventory
         */

        
                public com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse fulfillInventory
                (
                  com.shopatron.api.coexprivate.core.v4.FulfillInventory fulfillInventory
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * Data retrieval to check a fulfiller's status
                                    * @param getFulfillerStatus
         */

        
                public com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse getFulfillerStatus
                (
                  com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus getFulfillerStatus
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param allocateInventory
         */

        
                public com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse allocateInventory
                (
                  com.shopatron.api.coexprivate.core.v4.AllocateInventory allocateInventory
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createBin
         */

        
                public com.shopatron.api.coexprivate.core.v4.CreateBinResponse createBin
                (
                  com.shopatron.api.coexprivate.core.v4.CreateBin createBin
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getFulfillmentLocations
         */

        
                public com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse getFulfillmentLocations
                (
                  com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations getFulfillmentLocations
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getBinStatuses
         */

        
                public com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse getBinStatuses
                (
                  com.shopatron.api.coexprivate.core.v4.GetBinStatuses getBinStatuses
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param adjustRequest
         */

        
                public com.shopatron.api.coexprivate.core.v4.AdjustResponse adjustInventory
                (
                  com.shopatron.api.coexprivate.core.v4.AdjustRequest adjustRequest
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getBinTypes
         */

        
                public com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse getBinTypes
                (
                  com.shopatron.api.coexprivate.core.v4.GetBinTypes getBinTypes
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param deallocateInventory
         */

        
                public com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse deallocateInventory
                (
                  com.shopatron.api.coexprivate.core.v4.DeallocateInventory deallocateInventory
                 )
            ;
        
         }
    