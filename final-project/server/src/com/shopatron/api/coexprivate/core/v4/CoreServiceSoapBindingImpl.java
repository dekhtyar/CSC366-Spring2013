/**
 * CoreServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

import java.rmi.RemoteException;

import org.apache.axis.types.PositiveInteger;

import com.shopatron.UseCases;

public class CoreServiceSoapBindingImpl implements CoreService {
	@Override
	public int createFulfiller(CreateFulfiller request) throws RemoteException {
		return -3;
	}

	@Override
	public int getFulfillerStatus(int fulfillerID) throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int createFulfillmentLocation(CreateFulfillmentLocation request)
			throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public AssignmentResponse[] getFulfillmentLocations(OrderRequest request)
			throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public FulfillmentLocationType[] getFulfillmentLocationTypes()
			throws RemoteException {
		return UseCases.getFulfillmentLocationTypes();
	}

	@Override
	public void allocateInventory(AllocateInventory request) throws RemoteException {
		UseCases.allocateInventory(request.getRequest());
	}

	@Override
	public void deallocateInventory(DeallocateInventory request)
			throws RemoteException {
		UseCases.deallocateInventory(request.getRequest());
	}

	@Override
	public void fulfillInventory(FulfillInventory request) throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PositiveInteger createBin(CreateBin request) throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BinResponse getBins(GetBins request) throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BinType[] getBinTypes() throws RemoteException {
		return UseCases.getBinTypes();
	}

	@Override
	public BinStatus[] getBinStatuses() throws RemoteException {
		return UseCases.getBinStatuses();
	}

	@Override
	public InventoryResponse[] getInventory(GetInventory request)
			throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String adjustInventory(PositiveInteger fulfillerID,
			String externalLocationID, AdjustItem[] items)
			throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String refreshInventory(RefreshRequest parameters)
			throws RemoteException {
		throw new UnsupportedOperationException();
	}
}
