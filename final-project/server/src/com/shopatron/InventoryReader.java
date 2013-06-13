package com.shopatron;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryReader implements DbReader<Inventory>{
	public Inventory read(ResultSet results) throws SQLException {
		Inventory inv = new Inventory();
		
		inv.setCatalogID(results.getInt("CatalogID"));
		inv.setLocationName(results.getString("Name"));
		inv.setManufacturerID(results.getInt("MfcID"));
		inv.setQuantity(results.getInt("Quantity"));
		inv.setSku(results.getString("SKU"));
		inv.setSafetyStock(results.getInt("Safety"));
		inv.setUpc(results.getString("UPC"));
		inv.setLtd(results.getDouble("LTD"));
		
		return null;
	}
}
