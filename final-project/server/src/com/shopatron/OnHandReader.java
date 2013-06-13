package com.shopatron;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OnHandReader implements DbReader<OnHand> {

	@Override
	public OnHand read(ResultSet results) throws SQLException {
		OnHand hand = new OnHand();
		
		hand.setAllocated(results.getInt("Allocated"));
		hand.setFulfillerID(results.getInt("FulfillerID"));
		hand.setLocationID(results.getInt("LocationID"));
		hand.setName(results.getString("Name"));
		hand.setQuantity(results.getInt("Quantity"));
		hand.setSKU(results.getInt("SKU"));
		hand.setExternalID(results.getInt("ExternalID"));
		
		
		return hand;
	}

}
