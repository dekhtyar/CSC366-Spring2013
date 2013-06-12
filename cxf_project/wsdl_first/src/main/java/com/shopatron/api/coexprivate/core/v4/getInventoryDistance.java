package com.shopatron.api.coexprivate.core.v4;

import java.sql.*;
import java.math.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

public class getInventoryDistance {
   public static List<InventoryResponse> listInventory(InventoryRequest invReq)
   {
      List<InventoryResponse> invResList = new ArrayList<InventoryResponse>();
      ArrayList<Distances> distList = new ArrayList<Distances>();
      Connection conn = DbConnect.getConnection();
      BigInteger catID = invReq.getCatalog().getCatalogID();
      BigInteger manID = invReq.getCatalog().getManufacturerID();
      BigInteger fulFillerID = invReq.getFulfillerID();
      List<String> extFulfillerID = invReq.getLocationIDs().getExternalLocationID();
      ArrayOfImplItemQuantity itemQ = invReq.getQuantities();
      Double latitude = invReq.getLocation().getLatitude();
      Double longitude = invReq.getLocation().getLongitude();
      String countrycode = invReq.getLocation().getCountryCode();
      BigInteger radius = invReq.getLocation().getRadius();
      String unit = invReq.getLocation().getUnit();
      String zipcode = invReq.getLocation().getPostalCode();
      int limit = invReq.getLimit();
      int count = 0, minCount = 1;
      String type = invReq.getType().value();
      boolean orderByLTD = invReq.isOrderByLTD();
      Boolean ignSafety = invReq.isIgnoreSafetyStock();
      Boolean ignNegativeInv = invReq.isIncludeNegativeInventory();

      /* Queries the databases for the tuples */
      for(String eID : extFulfillerID)
      {
         for(ItemQuantity item : itemQ.getItems())
         {
            if(!type.equals("ANY"))
            {
               minCount = item.getQuantity();
            }
            distList = getDistances(conn, distList, eID, catID, manID, item.getUPC(), item.getPartNumber(), minCount, fulFillerID);
         }
      }
      for(Distances dist : distList)
      {
         /* No zipcode provided use the provided latitude/longitude  to calculate distance*/
         if(zipcode == null)
         {
            dist.distance = getDistanceLatLon(latitude, longitude, dist.latitude, dist.longitude, unit);
         }
         /* Use the zipcode provided to calculate distance */
         else
         {
            dist.distance = getDistanceZip(zipcode, dist.latitude, dist.longitude, unit);
         }
         /* IgnoreSafetyStock flag is false therefore subtract safetystock from onhandi */
         if(ignSafety == false)
         {
            dist.onhand = dist.onhand - dist.safety;
         }
      }
      
      /* Sorts by LTD */
      if(orderByLTD == true)
      {
         Collections.sort(distList, new Comparator<Distances>() { public int compare(Distances d1, Distances d2) { return new Integer(d1.LTD).compareTo(new Integer(d2.LTD));}});
      }
      /* Sorts by on hand count */
      else
      {
         Collections.sort(distList, new Comparator<Distances>() { public int compare(Distances d1, Distances d2) { return new Integer(d1.onhand).compareTo(new Integer(d2.onhand))*(-1);}});
      }
      for(Distances dist : distList)
      {
         InventoryResponse invRes = new InventoryResponse();

         /* If distance is greater than the radius */
         if(dist.distance > (double)radius.intValue())
         {
            continue;
         }
         /* If IgnoreNegativeInventory is false then skip the ones with negative inventory */
         if(ignNegativeInv == false && dist.onhand < 0)
         {
            continue;
         }
         /* Reached the limit */
         if(count == limit)
         {
            break;
         }
         /* Check for the 'ALL' type? */
         if(type.toUpperCase().equals("ALL") || type.toUpperCase().equals("ALL_STORES"))
         {
            boolean hasAll = true;
            InventoryResponse tempRes;
            List<InventoryResponse> tempResList = new ArrayList<InventoryResponse>();

            for(ItemQuantity item: itemQ.getItems())
            {  
               if(item.getPartNumber() == dist.SKU && item.getUPC() == dist.UPC && item.getQuantity() >= dist.onhand)
               {  
                  tempRes = new InventoryResponse();
                  tempRes.setLocationName(dist.name);
                  tempRes.setCatalogID(dist.catID);
                  tempRes.setManufacturerID(dist.manID);
                  tempRes.setOnHand(dist.onhand);
                  tempRes.setAvailable(dist.status);
                  tempRes.setPartNumber(dist.SKU);
                  tempRes.setUPC(dist.UPC);
                  tempRes.setLTD((double)dist.LTD);
                  tempRes.setSafetyStock(dist.safety);
                  /* My zipcode cvs is only for US postal codes and US country code = 1 */
                  tempRes.setCountryCode("1");
                  tempRes.setDistance(dist.distance);
                  tempResList.add(tempRes);
                  count++;
               }
               else 
               {
                  hasAll = false;
                  break;
               }
            }

            // all of the items were available
            if (hasAll) {
               invResList.addAll(tempResList);
            }
         }
         
         /* Checks if a partial match appear
          * One set of UPC/SKU were able to be completed */
         else if(type.toUpperCase().compareTo("PARTIAL") == 0)
         {
            for(ItemQuantity item: itemQ.getItems())
            {
               if(item.getPartNumber() == dist.SKU && item.getUPC() == dist.UPC && item.getQuantity() >= dist.onhand)
               {
                  invRes.setLocationName(dist.name);
                  invRes.setCatalogID(dist.catID);
                  invRes.setManufacturerID(dist.manID);
                  invRes.setOnHand(dist.onhand);
                  invRes.setAvailable(dist.status);
                  invRes.setPartNumber(dist.SKU);
                  invRes.setUPC(dist.UPC);
                  invRes.setLTD((double)dist.LTD);
                  invRes.setSafetyStock(dist.safety);
                  /* My zipcode cvs is only for US postal codes and US country code = 1 */
                  invRes.setCountryCode("1");
                  invRes.setDistance(dist.distance);
                  invResList.add(invRes);
                  count++;
               }
            }
         }
         /* Display any amount */
         else
         {
            invRes.setLocationName(dist.name);
            invRes.setCatalogID(dist.catID);
            invRes.setManufacturerID(dist.manID);
            invRes.setOnHand(dist.onhand);
            invRes.setAvailable(dist.status);
            invRes.setPartNumber(dist.SKU);
            invRes.setUPC(dist.UPC);
            invRes.setLTD((double)dist.LTD);
            invRes.setSafetyStock(dist.safety);
            /* My zipcode cvs is only for US postal codes and US country code = 1 */
            invRes.setCountryCode("1");
            invRes.setDistance(dist.distance);
            invResList.add(invRes);
            count++;
         }
      }

      return invResList;
   }   
   
   public static ArrayList<Distances> getDistances(Connection conn, 
         ArrayList<Distances> distList, String extFulfillerID, BigInteger catID, BigInteger manID,
         String UPC, String SKU, int minCount, BigInteger fulFillerID) 
   {
      String query = "SELECT l.ExtFulfillerID, l.FulfillerID, l.Name, l.Latitude, l.Longitude, l.Status, " + 
                     "l.SafetyStock, c.CatID, c.ManID, st.SKU, st.UPC, st.LTD, h.Onhand " +
                     "FROM Locations l, Clients c, Serves s, Stocks st, Holds h " +
                     "WHERE s.ExtfulfillerID = l.ExtFulfillerID AND s.FulfillerID = l.FulfillerID AND " +
                     "s.CatID = c.CatID AND s.ManID = c.ManID AND st.ExtFulfillerID = l.ExtFulfillerID " +
                     "AND st.FulfillerID = l.FulfillerID AND l.SafetyStock = st.SafetyStock AND " +
                     "h.SKU = st.SKU AND h.UPC = st.UPC AND c.CatID = " + catID + " AND c.ManID = " + manID +
                     " AND l.ExtFulfillerID = \"" + extFulfillerID + "\" AND st.SKU = \"" + SKU + "\" AND " +
                     "st.UPC = \"" + UPC + "\" AND h.Onhand-h.Allocation >= " + minCount + " AND l.FulfillerID = " + fulFillerID +
                     " ORDER BY h.Onhand DESC";

      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(query);
         while(result.next()) {
            distList.add(new Distances(result.getString(1), result.getInt(2),
                     result.getString(3), result.getDouble(4), result.getDouble(5),
                     result.getInt(6), result.getInt(7), result.getInt(8), result.getInt(9),
                     result.getString(10), result.getString(11), result.getInt(12),result.getInt(13),
                     1, 0.0));
         } 
      } catch (Exception e) { e.printStackTrace(); }

      return distList;
   }

   public static double getDistanceZip(String zipcode, double lat, double lon, String unit) 
   {
      double distance = 0.0;

      try {
         for(ZipcodeBean zip : getZipcode("src/main/resources/zipcode.csv")) {
            if(zipcode.equals(zip.getZip()))
            {
               distance = getDistanceLatLon(Double.parseDouble(zip.getLat()), Double.parseDouble(zip.getLon()), lat, lon, unit);
               break;
            }
         }
      } catch (Exception e) { e.printStackTrace(); }
		
      return distance;
   }
	
   public static double getDistanceLatLon(double lat1, double lon1, double lat2, double lon2, String unit) 
   {
      int radius = 0;

      if(unit.toUpperCase().compareTo("MILES") == 0)
      {
         radius = 3961;
      }
      else
      {
         radius = 6373;
      }

      double diffLat = (lat2-lat1) * (Math.PI/180);
      double diffLon = (lon2-lon1) * (Math.PI/180);
      double a = (Math.pow(Math.sin(diffLat/2), 2.0) + Math.cos(lat1*(Math.PI/180)) * 
            Math.cos(lat2*(Math.PI/180)) * Math.pow(Math.sin(diffLon/2), 2.0));
      double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
      double d = radius * c;
		
      return d;
   }
	
   public static List<ZipcodeBean> getZipcode(String fileName) throws Exception 
   {
      ICsvBeanReader beanReader = null;
      ArrayList<ZipcodeBean> zips = new ArrayList<ZipcodeBean>();
      try {
         beanReader = new CsvBeanReader(new FileReader(fileName), CsvPreference.STANDARD_PREFERENCE);
         beanReader.getHeader(true);
			
         final String[] fieldMap = new String[] { "zip", "city", "state", "lat", "lon", "timezone", "dst" };
         final CellProcessor[] processors = getProcessors();
			
         ZipcodeBean zip;
			
         while((zip = beanReader.read(ZipcodeBean.class, fieldMap, processors)) != null) {
            zips.add(zip);
         }
      }
      finally {
         if(beanReader != null) {
            beanReader.close();
         }
      }
		
      return zips;
   }
	
   private static CellProcessor[] getProcessors() 
   {
      final CellProcessor[] processors = new CellProcessor[] {
         new NotNull(),
            new NotNull(),
            new NotNull(),
            new NotNull(),
            new NotNull(),
            new NotNull(),
            new NotNull(),
      };	
		
      return processors;
   }
}
