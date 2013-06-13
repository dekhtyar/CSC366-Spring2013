package com.shopatron;

import java.sql.SQLException;

public class OnHand {
	public int locationID;
	public String name;
	public int fulfillerID;
	public int SKU;
	public int quantity;
	public int allocated;
	public int externalID;
	
	public OnHand() {
		
	}
	
	public int getExternalID() {
		return externalID;
	}
	
	public void setExternalID(int externalID) {
		this.externalID = externalID;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFulfillerID() {
		return fulfillerID;
	}

	public void setFulfillerID(int fulfillerID) {
		this.fulfillerID = fulfillerID;
	}

	public int getSKU() {
		return SKU;
	}

	public void setSKU(int sKU) {
		SKU = sKU;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAllocated() {
		return allocated;
	}

	public void setAllocated(int allocated) {
		this.allocated = allocated;
	}
	
	public void writeToDB() throws SQLException {
		Database.executeSQL("UPDATE OnHand "
				+ "SET Allocated = ?,"
				+ "Quantity = ?"
				+ "WHERE FulfillerID = ? " 
				+ "AND LocationID = ? "
				+ "AND SKU = ?"
				+ "AND Name = ?", 
					allocated, 
					quantity,
					fulfillerID,
					locationID, 
					SKU,
					name);
	}
}
