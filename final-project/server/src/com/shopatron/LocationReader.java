package com.shopatron;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationReader implements DbReader<Location> {
	public Location read(ResultSet results) throws SQLException {
		Location loc = new Location();
		
		loc.setExternalID(results.getString("ExternalID"));
		loc.setFulfillerID(results.getInt("fulfillerID"));
		loc.setInternalID(results.getInt("InternalID"));
		loc.setLatitude(results.getDouble("Latitude"));
		loc.setLongitude(results.getDouble("Longitude"));
		loc.setName(results.getString("Name"));
		loc.setSafetyStock(results.getInt("SafetyStock"));
		loc.setStatus(results.getString("Status"));
		loc.setStoreType(results.getString("StoreType"));
		
		return loc;
	}
	
}
