package com.shopatron;

import java.sql.SQLException;

public class OnHandOrder {
	public int orderID;
	public int locationID;
	public String name;
	public int fulfillerID;
	public int SKU;
	public String createdOn; //TODO: note, might need this to be of date type
	public int quantity;
	
	public OnHandOrder() {
		
	}
	
	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
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

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void writeToDB() throws SQLException {
		Database.executeSQL("UPDATE OnHandOrder "
				+ "SET OrderID = ?,"
				+ "Quantity = ?"
				+ "WHERE FulfillerID = ? " 
				+ "AND ExternalID = ? "
				+ "AND SKU = ?"
				+ "AND Name = ?", 
					orderID, 
					quantity,
					fulfillerID,
					locationID, 
					SKU,
					name);
	}
	
	public void delete() throws SQLException {
		Database.executeSQL("DELETE FROM OnHandOrder "
				+ "WHERE FulfillerID = ? " 
				+ "AND LocationID = ? "
				+ "AND SKU = ? "
				+ "AND Name = ?",
				fulfillerID,
				locationID, 
				SKU,
				name); 
	}
}