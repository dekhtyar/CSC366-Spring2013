package com.shopatron.api.coexprivate.core.v4;

public class Distances {
   String ExtFulfillerID;
   int FulfillerID;
   String name;
   double latitude;
   double longitude;
   int status;
   int safety;
   int catID;
   int manID;
   String SKU;
   String UPC;
   int LTD;
   int onhand;
   int countrycode;
   double distance;

   public Distances (String eID, int fID, String name, double lat, 
         double lon, int status, int safety, int catID, int manID,
         String SKU, String UPC, int LTD, int onhand, int countrycode, double dist) 
   {
      ExtFulfillerID = eID;
      FulfillerID = fID;
      this.name = name;
      latitude = lat;
      longitude = lon;
      this.status = status;
      this.safety = safety;
      this.catID = catID;
      this.manID = manID;
      this.SKU = SKU;
      this.UPC = UPC;
      this.LTD = LTD;
      this.onhand = onhand;
      this.countrycode = countrycode;
      distance = dist;
   }

   public Distances(String eID, int fID, double lat, double lon, double dist) 
   {
      ExtFulfillerID = eID;
      FulfillerID = fID;
      latitude = lat;
      longitude = lon;
      distance = dist;

   }
}
