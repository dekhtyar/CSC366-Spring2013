



public class api {
	private Connection conn;


	public void createFulfiller ()
	{
		
		
		System.out.println("in createFulfiller!");
		
	}
	
	public void createFullfillmentLocation ()
	{
		
		System.out.println("in create Fulfillment Location");
		
		
	}
	
	public void createManufacturerCatalog ()
	{
		
		System.out.println("in createManufacturer Catalog");
		
	}
	
	public void createBin ()
	{
		if(fulfillerId < 0 || binId < 0 || fulfillerLocationId < 0) {
			return -1;
		}
		try {
         		String sql = "INSERT INTO StoreBin VALUES(NULL, ?, ?, ?, ?, ?)";
         		PreparedStatement ps = conn.prepareStatement(sql);
         		ps.setInt(1, fulfillerLocationId);
         		ps.setString(2, binStatus);
         		ps.setString(3, binType);
         		ps.setString(4, binName);
         		ps.setString(5, "");

         		ps.executeUpdate();
      		}
      		catch (Exception e) {
         		return -1;
      		}

      		try {
         		conn.close();
      		}
      		catch (Exception e)
      		{
         		;
      		}

      		return 1;
	}
	
	public void refreshInventory()
	{
		
		System.out.println("refreshInventory");
		
	}
	
	
	
}






