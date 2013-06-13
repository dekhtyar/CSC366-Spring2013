package com.shopatron;

public class Location {
	public int FulfillerID;
	public int InternalID;
	public String ExternalID;
	public String Name;
	public String StoreType;
    public double Latitude;
    public double Longitude;
    public String Status;
    public int SafetyStock;
    
    public Location() {
    	
    }
    
	public int getFulfillerID() {
		return FulfillerID;
	}
	public void setFulfillerID(int fulfillerID) {
		FulfillerID = fulfillerID;
	}
	public int getInternalID() {
		return InternalID;
	}
	public void setInternalID(int internalID) {
		InternalID = internalID;
	}
	public String getExternalID() {
		return ExternalID;
	}
	public void setExternalID(String externalID) {
		ExternalID = externalID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getStoreType() {
		return StoreType;
	}
	public void setStoreType(String storeType) {
		StoreType = storeType;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public int getSafetyStock() {
		return SafetyStock;
	}
	public void setSafetyStock(int safetyStock) {
		SafetyStock = safetyStock;
	}
    
    
}
