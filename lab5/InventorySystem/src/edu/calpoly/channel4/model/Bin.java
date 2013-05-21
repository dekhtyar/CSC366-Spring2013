package edu.calpoly.channel4.model;

/**
 * Bin is the WSDL representation of a Bin.
 */
public class Bin {

   private int fulfillerID;
   private int binID;
   private int intFulfillerLocationID;
   private String binType;
   private String binStatus;
   private String name;
   
   public Bin(int fulfillerID, int fulfillerLocationID,
               final String binType, final String binStatus, final String name) {
   
       this.fulfillerID = fulfillerID;
       this.intFulfillerLocationID = fulfillerLocationID;
       this.binType = binType;
       this.binStatus = binStatus;
       this.name = name;
   }

   public int getFulfillerID() {
      return this.fulfillerID;
   }
   
   public int getBinID() {
      return this.binID;
   }
   
   public int getFulfillerLocationID() {
      return this.intFulfillerLocationID;
   }
   
   public String getBinType() {
      return this.binType;
   }
   
   public String getBinStatus() {
      return this.binStatus;
   }
   
   public String getName() {
      return this.name;
   }

}
