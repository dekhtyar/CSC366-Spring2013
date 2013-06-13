package com.shopatron;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.shopatron.api.coexprivate.core.v4.Bin;


public class OnHandOrderReader implements DbReader<OnHandOrder> {
	public OnHandOrder read(ResultSet results) throws SQLException {
		OnHandOrder order = new OnHandOrder();
		order.setOrderID(results.getInt("OrderId"));
		order.setLocationID(results.getInt("LocationID"));
		order.setFulfillerID(results.getInt("FulfillerID"));
		order.setQuantity(results.getInt("Quantity"));
		order.setSKU(results.getInt("SKU"));
		order.setName(results.getString("Name"));
		
		return order;
	}
}

