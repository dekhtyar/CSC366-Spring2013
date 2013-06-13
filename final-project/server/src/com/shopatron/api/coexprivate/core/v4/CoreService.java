/**
 * CoreService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.apache.axis.types.PositiveInteger;

public interface CoreService extends Remote {

    /**
     * Request to create a single fulfiller which will later contain
     * one or more locations
     */
    public int createFulfiller(CreateFulfiller request) throws RemoteException;

    /**
     * Data retrieval to check a fulfiller's status
     */
    public int getFulfillerStatus(int fulfillerID) throws RemoteException;
    public int createFulfillmentLocation(CreateFulfillmentLocation request) throws RemoteException;
    public AssignmentResponse[] getFulfillmentLocations(OrderRequest request) throws RemoteException;
    public FulfillmentLocationType[] getFulfillmentLocationTypes() throws RemoteException;
    public void allocateInventory(AllocateInventory request) throws RemoteException;
    public void deallocateInventory(DeallocateInventory request) throws RemoteException;
    public void fulfillInventory(FulfillInventory request) throws RemoteException;
    public PositiveInteger createBin(CreateBin request) throws RemoteException;
    public BinResponse getBins(GetBins request) throws RemoteException;
    public BinType[] getBinTypes() throws RemoteException;
    public BinStatus[] getBinStatuses() throws RemoteException;
    public InventoryResponse[] getInventory(GetInventory request) throws RemoteException;
    public String adjustInventory(PositiveInteger fulfillerID, String externalLocationID, AdjustItem[] items) throws RemoteException;
    public String refreshInventory(RefreshRequest parameters) throws RemoteException;
}
