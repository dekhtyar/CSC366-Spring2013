package com.shopatron.api.coexprivate.core.v4;

import java.sql.*;
import java.math.*;
import java.util.*;

public class getFulfillmentLocationsHelper {
   public static List<AssignmentResponse> getLocations (OrderRequest ordReq)
   {
      List<AssignmentResponse> assignResList = new ArrayList<AssignmentResponse>();
      ArrayList<Distances> distList = new ArrayList<Distances>();
      Connection conn = DbConnect.getConnection();
      BigInteger catID = ordReq.getCatalog().getCatalogID();
      BigInteger manID = ordReq.getCatalog().getManufacturerID();
      BigInteger fulfillerID = ordReq.getFulfillerID();
      BigInteger limit = ordReq.getMaxLocations();
      String countrycode = ordReq.getLocation().getCountryCode();
      Double latitude = ordReq.getLocation().getLatitude();
      Double longitude = ordReq.getLocation().getLongitude();
      String zipcode = ordReq.getLocation().getPostalCode();
      BigInteger radius = ordReq.getLocation().getRadius();
      String unit = ordReq.getLocation().getUnit();
      int count = 0;

      distList = getList(conn, catID, manID, fulfillerID, distList);
   
      for(Distances dist : distList)
      {
         if(zipcode == null)
         {
            dist.distance = getInventoryDistance.getDistanceLatLon(latitude, longitude, dist.latitude, dist.longitude, unit);
         }
         else
         {
            dist.distance = getInventoryDistance.getDistanceZip(zipcode, dist.latitude, dist.longitude, unit);
         }
      }

      for(Distances dist : distList)
      {
         AssignmentResponse assignRes = new AssignmentResponse();

         if(count == limit.intValue())
         {
            break;
         }
         if(dist.distance <= (double)radius.intValue())
         {
            assignRes.setFulfillerID(Utils.bigInt(dist.FulfillerID));
            assignRes.setExternalLocationID(dist.ExtFulfillerID);
            assignResList.add(assignRes);
            count++;
         }
      }

      return assignResList;
   }

   public static ArrayList<Distances> getList(Connection conn, BigInteger catID,
         BigInteger manID, BigInteger fulfillerID, ArrayList<Distances> distList)
   {
      String query = "SELECT l.ExtFulFillerID, l.FulfillerID, l.Latitude, l.Longitude " +
                     "FROM Locations l, Serves s " +
                     "WHERE s.ExtfulfillerID = l.ExtFulfillerID AND " +
                     "s.FulfillerID = l.FulfillerID AND l.fulfillerID = " + fulfillerID +
                     " AND s.CatID = " + catID + " AND s.ManID = "  + manID;

      try {
         Statement s = conn.createStatement();
         ResultSet result = s.executeQuery(query);
         while(result.next()) {
            distList.add(new Distances(result.getString(1), result.getInt(2),
                     result.getDouble(3), result.getDouble(4), 0.0));
         }
      } catch (Exception e) { e.printStackTrace(); }

      return distList;
   }
}
