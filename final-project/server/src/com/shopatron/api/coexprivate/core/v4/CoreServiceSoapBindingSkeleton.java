/**
 * CoreServiceSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.axis.types.PositiveInteger;

public class CoreServiceSoapBindingSkeleton
		implements
			CoreService,
			org.apache.axis.wsdl.Skeleton {
	private CoreService impl;
	private static Map _myOperations = new Hashtable();
	private static Collection _myOperationsList = new ArrayList();

	/**
	 * Returns List of OperationDesc objects with this name
	 */
	public static List getOperationDescByName(String methodName) {
		return (List) _myOperations.get(methodName);
	}

	/**
	 * Returns Collection of OperationDescs
	 */
	public static Collection getOperationDescs() {
		return _myOperationsList;
	}

	static {
		org.apache.axis.description.OperationDesc _oper;
		org.apache.axis.description.ParameterDesc[] _params;
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"CreateFulfiller"), CreateFulfiller.class, false, false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"createFulfiller", _params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"createFulfillerReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "int"));
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"createFulfiller"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("createFulfiller") == null) {
			_myOperations.put("createFulfiller", new ArrayList());
		}
		((List) _myOperations.get("createFulfiller")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"fulfillerID"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "int"), int.class,
				false, false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"getFulfillerStatus", _params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"getFulfillerStatusReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "int"));
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"getFulfillerStatus"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("getFulfillerStatus") == null) {
			_myOperations.put("getFulfillerStatus", new ArrayList());
		}
		((List) _myOperations.get("getFulfillerStatus")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"CreateFulfillmentLocation"),
				CreateFulfillmentLocation.class, false, false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"createFulfillmentLocation", _params,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"createFulfillmentLocationReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "int"));
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"createFulfillmentLocation"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("createFulfillmentLocation") == null) {
			_myOperations.put("createFulfillmentLocation", new ArrayList());
		}
		((List) _myOperations.get("createFulfillmentLocation")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"OrderRequest"), OrderRequest.class, false, false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"getFulfillmentLocations", _params,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"getFulfillmentLocationsReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://v4.core.coexprivate.api.shopatron.com",
				"AssignmentResponse"));
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"getFulfillmentLocations"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("getFulfillmentLocations") == null) {
			_myOperations.put("getFulfillmentLocations", new ArrayList());
		}
		((List) _myOperations.get("getFulfillmentLocations")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{};
		_oper = new org.apache.axis.description.OperationDesc(
				"getFulfillmentLocationTypes", _params,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"getFulfillmentLocationTypesReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://v4.core.coexprivate.api.shopatron.com",
				"FulfillmentLocationType"));
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"getFulfillmentLocationTypes"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("getFulfillmentLocationTypes") == null) {
			_myOperations.put("getFulfillmentLocationTypes", new ArrayList());
		}
		((List) _myOperations.get("getFulfillmentLocationTypes")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"AllocateInventory"), AllocateInventory.class, false,
				false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"allocateInventory", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"allocateInventory"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("allocateInventory") == null) {
			_myOperations.put("allocateInventory", new ArrayList());
		}
		((List) _myOperations.get("allocateInventory")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"DeallocateInventory"), DeallocateInventory.class,
				false, false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"deallocateInventory", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"deallocateInventory"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("deallocateInventory") == null) {
			_myOperations.put("deallocateInventory", new ArrayList());
		}
		((List) _myOperations.get("deallocateInventory")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"FulfillInventory"), FulfillInventory.class, false,
				false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"fulfillInventory", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"fulfillInventory"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("fulfillInventory") == null) {
			_myOperations.put("fulfillInventory", new ArrayList());
		}
		((List) _myOperations.get("fulfillInventory")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"CreateBin"), CreateBin.class, false, false),};
		_oper = new org.apache.axis.description.OperationDesc("createBin",
				_params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"createBinReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "positiveInteger"));
		_oper.setElementQName(new javax.xml.namespace.QName("", "createBin"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("createBin") == null) {
			_myOperations.put("createBin", new ArrayList());
		}
		((List) _myOperations.get("createBin")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"GetBins"), GetBins.class, false, false),};
		_oper = new org.apache.axis.description.OperationDesc("getBins",
				_params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"getBinsReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://v4.core.coexprivate.api.shopatron.com", "BinResponse"));
		_oper.setElementQName(new javax.xml.namespace.QName("", "getBins"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("getBins") == null) {
			_myOperations.put("getBins", new ArrayList());
		}
		((List) _myOperations.get("getBins")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{};
		_oper = new org.apache.axis.description.OperationDesc("getBinTypes",
				_params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"getBinTypesReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://v4.core.coexprivate.api.shopatron.com", "BinType"));
		_oper.setElementQName(new javax.xml.namespace.QName("", "getBinTypes"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("getBinTypes") == null) {
			_myOperations.put("getBinTypes", new ArrayList());
		}
		((List) _myOperations.get("getBinTypes")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{};
		_oper = new org.apache.axis.description.OperationDesc("getBinStatuses",
				_params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"getBinStatusesReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://v4.core.coexprivate.api.shopatron.com", "BinStatus"));
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"getBinStatuses"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("getBinStatuses") == null) {
			_myOperations.put("getBinStatuses", new ArrayList());
		}
		((List) _myOperations.get("getBinStatuses")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"request"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"GetInventory"), GetInventory.class, false,
				false),};
		_oper = new org.apache.axis.description.OperationDesc("getInventory",
				_params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"getInventoryReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://v4.core.coexprivate.api.shopatron.com",
				"InventoryResponse"));
		_oper.setElementQName(new javax.xml.namespace.QName("", "getInventory"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("getInventory") == null) {
			_myOperations.put("getInventory", new ArrayList());
		}
		((List) _myOperations.get("getInventory")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{
				new org.apache.axis.description.ParameterDesc(
						new javax.xml.namespace.QName(
								"http://v4.core.coexprivate.api.shopatron.com",
								"FulfillerID"),
						org.apache.axis.description.ParameterDesc.IN,
						new javax.xml.namespace.QName(
								"http://www.w3.org/2001/XMLSchema",
								"positiveInteger"), PositiveInteger.class,
						false, false),
				new org.apache.axis.description.ParameterDesc(
						new javax.xml.namespace.QName(
								"http://v4.core.coexprivate.api.shopatron.com",
								"ExternalLocationID"),
						org.apache.axis.description.ParameterDesc.IN,
						new javax.xml.namespace.QName(
								"http://www.w3.org/2001/XMLSchema", "string"),
						String.class, false, false),
				new org.apache.axis.description.ParameterDesc(
						new javax.xml.namespace.QName(
								"http://v4.core.coexprivate.api.shopatron.com",
								"Items"),
						org.apache.axis.description.ParameterDesc.IN,
						new javax.xml.namespace.QName(
								"http://v4.core.coexprivate.api.shopatron.com",
								"ArrayOf_impl_AdjustItem"), AdjustItem[].class,
						false, false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"adjustInventory", _params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"AdjustResponse"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"adjustInventory"));
		_myOperationsList.add(_oper);
		if (_myOperations.get("adjustInventory") == null) {
			_myOperations.put("adjustInventory", new ArrayList());
		}
		((List) _myOperations.get("adjustInventory")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"RefreshRequest"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						">RefreshRequest"), RefreshRequest.class, false, false),};
		_oper = new org.apache.axis.description.OperationDesc(
				"refreshInventory", _params, new javax.xml.namespace.QName(
						"http://v4.core.coexprivate.api.shopatron.com",
						"RefreshResponse"));
		_oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		_oper.setElementQName(new javax.xml.namespace.QName("",
				"refreshInventory"));
		_myOperationsList.add(_oper);
		if (_myOperations.get("refreshInventory") == null) {
			_myOperations.put("refreshInventory", new ArrayList());
		}
		((List) _myOperations.get("refreshInventory")).add(_oper);
	}

	public CoreServiceSoapBindingSkeleton() {
		this.impl = new CoreServiceSoapBindingImpl();
	}

	public CoreServiceSoapBindingSkeleton(CoreService impl) {
		this.impl = impl;
	}
	public int createFulfiller(CreateFulfiller request) throws RemoteException {
		int ret = impl.createFulfiller(request);
		return ret;
	}

	public int getFulfillerStatus(int fulfillerID) throws RemoteException {
		int ret = impl.getFulfillerStatus(fulfillerID);
		return ret;
	}

	public int createFulfillmentLocation(CreateFulfillmentLocation request)
			throws RemoteException {
		int ret = impl.createFulfillmentLocation(request);
		return ret;
	}

	public AssignmentResponse[] getFulfillmentLocations(OrderRequest request)
			throws RemoteException {
		AssignmentResponse[] ret = impl.getFulfillmentLocations(request);
		return ret;
	}

	public FulfillmentLocationType[] getFulfillmentLocationTypes()
			throws RemoteException {
		FulfillmentLocationType[] ret = impl.getFulfillmentLocationTypes();
		return ret;
	}

	public void allocateInventory(AllocateInventory request)
			throws RemoteException {
		impl.allocateInventory(request);
	}

	public void deallocateInventory(DeallocateInventory request)
			throws RemoteException {
		impl.deallocateInventory(request);
	}

	public void fulfillInventory(FulfillInventory request)
			throws RemoteException {
		impl.fulfillInventory(request);
	}

	public PositiveInteger createBin(CreateBin request) throws RemoteException {
		PositiveInteger ret = impl.createBin(request);
		return ret;
	}

	public BinResponse getBins(GetBins request) throws RemoteException {
		BinResponse ret = impl.getBins(request);
		return ret;
	}

	public BinType[] getBinTypes() throws RemoteException {
		BinType[] ret = impl.getBinTypes();
		return ret;
	}

	public BinStatus[] getBinStatuses() throws RemoteException {
		BinStatus[] ret = impl.getBinStatuses();
		return ret;
	}

	public InventoryResponse[] getInventory(GetInventory request)
			throws RemoteException {
		InventoryResponse[] ret = impl.getInventory(request);
		return ret;
	}

	public String adjustInventory(PositiveInteger fulfillerID,
			String externalLocationID, AdjustItem[] items)
			throws RemoteException {
		String ret = impl.adjustInventory(fulfillerID, externalLocationID,
				items);
		return ret;
	}

	public String refreshInventory(RefreshRequest parameters)
			throws RemoteException {
		String ret = impl.refreshInventory(parameters);
		return ret;
	}

}
