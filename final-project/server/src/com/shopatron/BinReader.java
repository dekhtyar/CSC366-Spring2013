package com.shopatron;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.shopatron.api.coexprivate.core.v4.Bin;


public class BinReader implements DbReader<Bin> {
	public Bin read(ResultSet results) throws SQLException {
		Bin bin = new Bin();
		bin.setExternalLocationID(results.getString("LocationID"));
		bin.setBinStatus(results.getString("BinStatus"));
		bin.setBinType(results.getString("BinType"));
		bin.setName(results.getString("Name"));
		
		return bin;
	}
}
