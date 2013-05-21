package edu.calpoly.channel4.bulk.bean;

/**
 * LocationBean is a class representing a record in the locations CSV file.
 * A LocationBean's fields are a direct mapping of the columns in the CSV file.
 */
public class LocationBean {

   private String name;
   private int fulfillerId;
   private String extFulfillerLocId;
   private int intFulfillerLocId;
   private String desc;
   private double latitude;
   private double longitude;
   private int status;
   private int safetyStock;
   private int manufacturerId;
   private int catalogId;

   public String toString() {

      String separator = " | ";
      StringBuilder builder = new StringBuilder();

      builder.append("[");
      builder.append("name: ").append(name).append(separator);
      builder.append("fulfillerId: ").append(fulfillerId).append(separator);
      builder.append("extFulfillerLocId: ").append(extFulfillerLocId).append(separator);
      builder.append("intFulfillerLocId: ").append(intFulfillerLocId).append(separator);
      builder.append("desc: ").append(desc).append(separator);
      builder.append("latitude: ").append(latitude).append(separator);
      builder.append("longitude: ").append(longitude).append(separator);
      builder.append("status: ").append(status).append(separator);
      builder.append("safetyStock: ").append(safetyStock).append(separator);
      builder.append("manId: ").append(manufacturerId).append(separator);
      builder.append("catId: ").append(catalogId);
      builder.append("]");

      return builder.toString();
   }
   
   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @param name
    *           the name to set
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * @return the fulfillerId
    */
   public int getFulfillerId() {
      return fulfillerId;
   }

   /**
    * @param fulfillerId
    *           the fulfillerId to set
    */
   public void setFulfillerId(int fulfillerId) {
      this.fulfillerId = fulfillerId;
   }

   /**
    * @return the extFulfillerLocId
    */
   public String getExtFulfillerLocId() {
      return extFulfillerLocId;
   }

   /**
    * @param extFulfillerLocId
    *           the extFulfillerLocId to set
    */
   public void setExtFulfillerLocId(String extFulfillerLocId) {
      this.extFulfillerLocId = extFulfillerLocId;
   }

   /**
    * @return the intFulfillerLocId
    */
   public int getIntFulfillerLocId() {
      return intFulfillerLocId;
   }

   /**
    * @param intFulfillerLocId
    *           the intFulfillerLocId to set
    */
   public void setIntFulfillerLocId(int intFulfillerLocId) {
      this.intFulfillerLocId = intFulfillerLocId;
   }

   /**
    * @return the desc
    */
   public String getDesc() {
      return desc;
   }

   /**
    * @param desc
    *           the desc to set
    */
   public void setDesc(String desc) {
      this.desc = desc;
   }

   /**
    * @return the latitude
    */
   public double getLatitude() {
      return latitude;
   }

   /**
    * @param latitude
    *           the latitude to set
    */
   public void setLatitude(double latitude) {
      this.latitude = latitude;
   }

   /**
    * @return the longitude
    */
   public double getLongitude() {
      return longitude;
   }

   /**
    * @param longitude
    *           the longitude to set
    */
   public void setLongitude(double longitude) {
      this.longitude = longitude;
   }

   /**
    * @return the status
    */
   public int getStatus() {
      return status;
   }

   /**
    * @param status
    *           the status to set
    */
   public void setStatus(int status) {
      this.status = status;
   }

   /**
    * @return the safetyStock
    */
   public int getSafetyStock() {
      return safetyStock;
   }

   /**
    * @param safetyStock
    *           the safetyStock to set
    */
   public void setSafetyStock(int safetyStock) {
      this.safetyStock = safetyStock;
   }

   /**
    * @return the manufacturerId
    */
   public int getManufacturerId() {
      return manufacturerId;
   }

   /**
    * @param manufacturerId
    *           the manufacturerId to set
    */
   public void setManufacturerId(int manufacturerId) {
      this.manufacturerId = manufacturerId;
   }

   /**
    * @return the catalogId
    */
   public int getCatalogId() {
      return catalogId;
   }

   /**
    * @param catalogId
    *           the catalogId to set
    */
   public void setCatalogId(int catalogId) {
      this.catalogId = catalogId;
   }
}
