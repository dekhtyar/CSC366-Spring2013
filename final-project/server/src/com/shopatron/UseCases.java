package com.shopatron;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.axis.types.PositiveInteger;

import com.shopatron.api.coexprivate.core.v4.AdjustItem;
import com.shopatron.api.coexprivate.core.v4.AssignmentResponse;
import com.shopatron.api.coexprivate.core.v4.Bin;
import com.shopatron.api.coexprivate.core.v4.BinRequest;
import com.shopatron.api.coexprivate.core.v4.BinResponse;
import com.shopatron.api.coexprivate.core.v4.BinStatus;
import com.shopatron.api.coexprivate.core.v4.BinType;
import com.shopatron.api.coexprivate.core.v4.FulfillerRequest;
import com.shopatron.api.coexprivate.core.v4.FulfillmentLocation;
import com.shopatron.api.coexprivate.core.v4.FulfillmentLocationType;
import com.shopatron.api.coexprivate.core.v4.InventoryRequest;
import com.shopatron.api.coexprivate.core.v4.InventoryResponse;
import com.shopatron.api.coexprivate.core.v4.ItemQuantity;
import com.shopatron.api.coexprivate.core.v4.OrderRequest;
import com.shopatron.api.coexprivate.core.v4.RequestLocation;
import com.shopatron.api.coexprivate.core.v4.UpdateItem;
import com.shopatron.api.coexprivate.core.v4.UpdateRequest;

public class UseCases {
	public static int createFulfiller(FulfillerRequest request)
			throws RemoteException {
		try {
			Database.executeSQL("INSERT INTO Fulfiller VALUES (?)", request
					.getFulfillerID().intValue());
			return request.getFulfillerID().intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			return -3;
		}
	}

	public static int getFulfillerStatus(int fulfillerID)
			throws RemoteException {
//		try {
//			return Database.executeIntQuery(
//					"SELECT Status FROM Fulfiller WHERE FulfillerID = ?",
//					fulfillerID);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return -3;
//		}
		
		return 1;
	}

	public static int createFulfillmentLocation(FulfillmentLocation request)
			throws RemoteException {
		try {
			int fulfillerId = request.getFulfillerID().intValue();
			int locationId = request.getRetailerLocationID().intValue();
			String extId = request.getExternalLocationID();
			String locationName = request.getLocationName();
			String locationType = request.getLocationType();
			String status = "" + request.getStatus().getValue();
			double lat = request.getLatitude();
			double lng = request.getLongitude();
			Database.executeSQL(
					"INSERT INTO Location VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)",
					fulfillerId, locationId, extId, locationName, locationType, 
					lat, lng, status);
			return request.getRetailerLocationID().intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			return -3;
		}
	}

	public static AssignmentResponse[] getFulfillmentLocations(
			OrderRequest request) throws RemoteException {
		AssignmentResponse response;
		double startLat, startLon;
		List<Location> locations = null;
		List<AssignmentResponse> responses = new ArrayList<AssignmentResponse>();
		
		if (request.getLocation() != null) {
			 startLat = request.getLocation().getLatitude();
			 startLon = request.getLocation().getLongitude();
		} else {
			 startLat = 0d;
			 startLon = 0d;
		}
		
		try {
			locations = Database.executeQuery(new LocationReader(),
					"SELECT l.ExternalID AS ExternalID, l.FulfillerID AS FulfillerID, l.InternalID AS InternalID, l.Latitude AS Latitude, l.Longitude AS Longitude, l.Name AS Name, l.SafetyStock AS SafetyStock, l.Status AS Status, l.StoreType AS StoreType "
				    + "FROM Location l WHERE l.FulfillerID = ?", request.getFulfillerID());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Location loc : locations) {
			double distance = 0;
			
			if(request.getMaxLocations() != null && responses.size() > request.getMaxLocations().intValue())
				break;
			
			if (request.getLocation() != null) {
				distance = computeDistance(loc.getLatitude(), loc.getLongitude(), startLat, startLon, request.getLocation().getUnit());
			}
			
			if(request.getLocation() == null || distance < request.getLocation().getRadius().intValue()) {
				response = new AssignmentResponse();
				response.setExternalLocationID(loc.getExternalID());
				response.setFulfillerID(new PositiveInteger("" + loc.getFulfillerID()));
				
				responses.add(response);
			}
		}

		return responses.toArray(new AssignmentResponse[0]);
	}
	
	private static double computeDistance(double locLat, double locLon, double startLat, double startLon, String unit) {		
		double radius = 6371; // radius of earth in Km

		double lat1 = Math.toRadians(startLat);
		double lat2 = Math.toRadians(locLat);
		double lon1 = Math.toRadians(startLon);
		double lon2 = Math.toRadians(locLon);
		
		double distance = Math.acos(Math.sin(lat1)*Math.sin(lat2) + 
                Math.cos(lat1)*Math.cos(lat2) *
                Math.cos(lon2-lon1)) * radius;
		
		if (unit != null && unit.toLowerCase().equals("miles")) {
			distance *= 0.621371;
		}
		
		return distance;
	}

	public static FulfillmentLocationType[] getFulfillmentLocationTypes()
			throws RemoteException {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<FulfillmentLocationType> locationTypes = new ArrayList<FulfillmentLocationType>();

		try {
			list = Database
					.executeStringSQL("SELECT DISTINCT StoreType FROM Location");
		} catch (Exception e) {

		}

		for (String type : list) {
			locationTypes.add(new FulfillmentLocationType(type));
		}

		return locationTypes.toArray(new FulfillmentLocationType[0]);
	}

	public static String adjustInventory(PositiveInteger fulfillerID,
			String locationName, AdjustItem[] items) throws RemoteException,
			SQLException {
		int locationId = Integer.parseInt(locationName), sku;

		for (AdjustItem item : items) {
			sku = Integer.parseInt(item.getPartNumber());
			Database.executeSQL(
					"UPDATE OnHand SET Quantity = ? WHERE LocationID = ? AND SKU = ?",
					item.getQuantity(), locationId, sku);
		}

		// TODO: What should be returned here?

		return "Success";
	}

	public static void allocateInventory(UpdateRequest request)
			throws RemoteException {

		try {
			String externalID = request.getFulfillerLocationCatalog()
					.getExternalLocationID();
			PositiveInteger fulfillerID = request.getFulfillerID();

			List<Bin> bins = Database.executeQuery(new BinReader(),
					"SELECT * FROM Bin");

			for (UpdateItem item : request.getItems()) {
				int quantityFound = 0;
				
				// Look through all the bins and ensure that we have enough in stock
				for(Bin bin : bins) {
					int extId= Integer.parseInt(item.getExternalLocationID());
					String myBinName = bin.getName();
					int partNum = Integer.parseInt(item.getPartNumber());
					OnHand onHand = bin.findOnHand(extId, myBinName, partNum);
					if (onHand == null) {
						continue;
					}
					quantityFound += onHand.getQuantity();
				}

				// If there is enough in stock, perform allocation to "shopping cart" (OnHandOrder)
				if (quantityFound >= item.getQuantity()) {

					int tempQuantity = item.getQuantity();
					
					// Retrieve items from bins. Decreasing quantity and increasing allocated as we go through
					for(Bin bin : bins) {		
						OnHand onHand = bin.findOnHand(Integer.parseInt(item.getExternalLocationID()), 
										bin.getName(), //name need to be part of primary key for onHand Table?
										Integer.parseInt(item.getPartNumber()));
						
						// Skip this bin if it doesnt have the item
						if (onHand == null)
							continue;
						
						// If the bin doesnt have the requested amount, take out all that is available
						if (onHand.quantity < tempQuantity) {
							tempQuantity -= onHand.quantity;
							onHand.allocated = onHand.quantity;
							onHand.quantity = 0;
							
					    // If the bin has enough to statisfy the requested amount, take all only as much as we need
						} else {
							onHand.quantity -= tempQuantity;
							onHand.allocated += tempQuantity;
							tempQuantity = 0;
						}
						
						onHand.writeToDB();
						
						Date today = new Date();
						
						Database.executeSQL("INSERT INTO OnHandOrder "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?) ", 
							item.getOrderID(),
							onHand.locationID,
							bin.getName(),
							fulfillerID, 
							item.getUPC(),
							new java.sql.Date(today.getTime()), 
							item.getQuantity());
						
						if (tempQuantity <= 0) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void deallocate(UpdateRequest request, boolean remove) throws RemoteException {
		try {
			String externalID = request.getFulfillerLocationCatalog()
					.getExternalLocationID();
			PositiveInteger fulfillerID = request.getFulfillerID();

			List<Bin> bins = Database.executeQuery(new BinReader(), 
													"SELECT * FROM Bin");
			List<OnHandOrder> orders = Database.executeQuery(new OnHandOrderReader(), 
															 "SELECT * FROM OnHandOrder");
			//Int: Item.locationId -> (Int:Item.SKU -> list:OnHandOrder)
			HashMap<Integer, HashMap<Integer, List<OnHandOrder>>> orderMap = new HashMap<Integer, HashMap<Integer, List<OnHandOrder>>>();
			HashMap<String, Bin> binMap = new HashMap<String, Bin>();
			
			// Create a map from bin names to their corresponding bins
			for(Bin bin : bins) {
				if(!binMap.containsKey(bin.getName())) {
					binMap.put(bin.getName(), bin);
				}
			}
			
			// Create a mapping of LocationId && SKU to an OnHand object
			for (OnHandOrder order : orders) {
				if(!orderMap.containsKey(order.getLocationID())) {
					orderMap.put(order.getLocationID(), new HashMap<Integer, List<OnHandOrder>>());
				}
				
				if(!orderMap.get(order.getLocationID()).containsKey(order.getSKU())) {
					orderMap.get(order.getLocationID()).put(order.getSKU(),new ArrayList<OnHandOrder>());
				}
				
				orderMap.get(order.getLocationID()).get(order.getSKU()).add(order);
			}
			
			// For each item we are removing quantities from OnHandOrder and putting back into OnHand
			for (UpdateItem item : request.getItems()) {
				int sku = Integer.parseInt(item.getPartNumber());
				int locationId = Integer.parseInt(item.getExternalLocationID());
				// Get all the OnHandOrder tuples that match the item we are putting "back on the shelves"
				List<OnHandOrder> moreOrders = null;
				
				try {
					moreOrders = orderMap.get(locationId).get(sku);
				} catch(Exception e) {
					System.out.println("OnHand is most likely empty");
					e.printStackTrace();
					return;
				}
				
				// For each OnHandOrder, find the bin it belongs to and update the quantities for the OnHand item
				for(OnHandOrder order : moreOrders) {
					Bin bin = binMap.get(order.getName());
					OnHand onHand = bin.findOnHand(Integer.parseInt(item.getExternalLocationID()), 
							bin.getName(),
							Integer.parseInt(item.getPartNumber()));
					
					// Strategy pattern! or something... 
					if(!remove) {
						onHand.quantity += order.getQuantity();
					}
					
					onHand.allocated -= order.getQuantity(); 
					
					// Update OnHand
					onHand.writeToDB();
					
					// Remove OnHandOrder tuple from DB
					order.delete();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * When an item can not be fulfilled from the current location and needs to
	 * be fulfilled at another location. Here the allocation count is decreased
	 * then the on-hand count is increased.
	 * 
	 * @param request
	 * @throws RemoteException
	 */
	public static void deallocateInventory(UpdateRequest request)
			throws RemoteException {
		
		// Put items back into the store (Person cancelled order)
		deallocate(request, false);
	}

	/**
	 * When an order is fulfilled we can remove the item from current location.
	 * Here the allocation count is decreased and the on-hand count is
	 * decreased.
	 * 
	 * @param request
	 * @throws RemoteException
	 */
	public static void fulfillInventory(UpdateRequest request)
			throws RemoteException {
		
		// Remove the items from the store (Person fulfilled order)
		deallocate(request, true);
	}

	public static PositiveInteger createBin(Bin request) throws RemoteException {
		PositiveInteger result;
		
		try {
		Database.executeSQL("INSERT INTO Bin VALUES(?, ?, ?, ?)", 
				request.getExternalLocationID(),
				request.getName(),
				request.getBinType(),
				request.getBinStatus()
				);	
		
			result = new PositiveInteger("1");
		}
		catch (Exception e) {
			e.printStackTrace();
			result = new PositiveInteger("-1");
		}
		
		return result;
	}

	public static BinResponse[] getBins(BinRequest request)
			throws RemoteException {
		ArrayList<BinResponse> bins = new ArrayList<BinResponse>();
		List<Bin> binList;

		try {			
			binList = Database.executeQuery(new BinReader(),
					"SELECT * FROM Bin WHERE LocationID = ?", 
					request.getExternalLocationID());
			
			BinResponse response = new BinResponse();
			
			// List of bins that will be put into a single BinResponse
			ArrayList<Bin> responseBins = new ArrayList<Bin>();
			
			// How many results (bins) to skip before adding bins to BinReponse(s)
			int startResult = request.getResultsStart().intValue()-1;
			
			// This breaks up the returned bins into chunks of size NumResults.
			// If 50 bins are returned and NumResults = 10, then 5 BinResponses will be created each with
			// 10 bins.
			for(Bin bin : binList) {
				// If the bin name doesnt match the searhc term, skip it
				if(request.getSearchTerm() == "" || request.getSearchTerm() == null || 
						bin.getName().toLowerCase().contains(request.getSearchTerm().toLowerCase())) {
					
					// If the size of responseBins has reached the NumResults threshhold, create a BinResponse 
					// and add all the bins from responseBins to the BinReponse. Then add the BinResponse to
					// the list of responses to return.
					if(responseBins.size() != 0 && responseBins.size()%request.getNumResults().intValue() == 0) {
						response.setBins(responseBins.toArray(new Bin[0]));
						response.setResultCount(new PositiveInteger("" + responseBins.size()));
						bins.add(response);
						
						// Create new BinResponse to populate and clear responseBins
						response = new BinResponse();
						responseBins.clear();
					}
					
					if(startResult <= 0) {
						responseBins.add(bin);
					}
					else {
						startResult--;
					}
				}
			}
			
			// If there are any leftover bins that didn't make it into a BinResponse (# bins < NumResults)
			// then create a BinResponse and partially fill it with the remaining bins.
			if (responseBins.size() > 0) {
				response.setBins(responseBins.toArray(new Bin[0]));
				response.setResultCount(new PositiveInteger("" + responseBins.size()));
				bins.add(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bins.toArray(new BinResponse[0]);
	}

	public static BinType[] getBinTypes() throws RemoteException {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<BinType> bintype = new ArrayList<BinType>();

		try {
			list = Database.executeGenericStringSQL(
					"SELECT DISTINCT BinType FROM Bin", "BinType");
		} catch (Exception e) {
			System.out.println("get bin types error");
		}

		for (String type : list) {
			bintype.add(new BinType(type));
		}

		return bintype.toArray(new BinType[0]);

	}

	public static BinStatus[] getBinStatuses() throws RemoteException {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<BinStatus> binstatus = new ArrayList<BinStatus>();

		try {
			list = Database.executeGenericStringSQL(
					"SELECT DISTINCT BinStatus FROM Bin", "BinStatus");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("get bin types error");
		}

		for (String type : list) {
			binstatus.add(new BinStatus(type));
		}

		return binstatus.toArray(new BinStatus[0]);
	}

	public static InventoryResponse[] getInventory(InventoryRequest request)
			throws RemoteException {
		List<InventoryResponse> responses = new ArrayList<InventoryResponse>();
		
		//List<Location> locs = Database.executeQuery(new LocationReader(), "SELECT * FROM Location");
		
		for(ItemQuantity item : request.getQuantities()) {
			try {
				List<Inventory> invs = null;
				
				try {
				invs = Database.executeQuery(new InventoryReader(), 
					"SELECT i.CatalogID AS CatalogID, l.Name AS Name, i.MfcID AS MfcID, h.Quantity AS Quantity, h.SKU AS SKU, s.Safety AS Safety, i.UPC AS UPC, s.LTD AS LTD "
					+ "FROM Location l, OnHand h, FulfillerItem f, Item i, Stock s "
					+ "WHERE h.FulfillerID = f.FulfillerID "
					+ "AND l.FulfillerID = h.FulfillerID "
					+ "AND l.FulfillerID = f.FulfillerID "
					+ "AND s.LocationID = l.InternalID "
					+ "AND s.FulfillerID = h.FulfillerID "
					+ "AND s.FulfillerID = f.FulfillerID "
					+ "AND s.SKU = h.SKU "
					+ "AND s.SKU = f.SKU "
					+ "AND h.SKU = f.SKU "
					+ "AND f.UPC = i.UPC "
					+ "AND i.MfcID = ? "
					+ "AND i.CatalogID = ? "
					+ "AND f.UPC = ? "
					+ "AND h.SKU = ? "
					+ "AND h.Quantity >= ? "
					+ (request.getLocationIDs().length > 0 ? "AND l.ExternalID = ?" : "AND l.ExternalID != ?"),
					request.getCatalog().getManufacturerID().intValue(),
					request.getCatalog().getCatalogID().intValue(),
					Integer.parseInt(item.getUPC()),
					Integer.parseInt(item.getPartNumber()), 
					item.getQuantity(),
					(request.getLocationIDs().length > 0 ? request.getLocationIDs()[0] : "DONT USE"));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			
				for (Inventory inv : invs) {
					InventoryResponse response = new InventoryResponse();
					RequestLocation loc = request.getLocation();
					double distance = 0d;
					
					if(loc.getLatitude() != null && loc.getLongitude() != null && loc.getRadius() != null) {
						 distance = computeDistance(loc.getLatitude(), 
								loc.getLongitude(), 
								inv.getLatitude(), inv.getLongitude(), 
								loc.getUnit());
						
						if(distance < loc.getRadius().intValue()) {
							continue;
						}
					}
					
					int available = inv.getQuantity() - inv.getAllocated();
					
					if(request.getIgnoreSafetyStock()) {
						available -= inv.getSafetyStock();
					}
					
					response.setAvailable(available);
					response.setCatalogID(inv.getCatalogID());
					response.setLocationName(inv.getLocationName());
					response.setManufacturerID(inv.getManufacturerID());
					response.setOnHand(inv.getQuantity());
					response.setPartNumber(inv.getSku());
					response.setSafetyStock(inv.getSafetyStock());
					response.setUPC(inv.getUpc());
					response.setDistance(distance);			
					response.setLTD(inv.getLtd());
					
					responses.add(response);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return responses.toArray(new InventoryResponse[0]);
	}
}
