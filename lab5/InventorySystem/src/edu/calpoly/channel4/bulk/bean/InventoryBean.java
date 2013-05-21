package edu.calpoly.channel4.bulk.bean;

/**
 * InventoryBean is a class representing a record in the inventory CSV file.
 * An InventoryBean's fields are a direct mapping of the columns in the CSV file.
 */
public class InventoryBean {

   private String productName;
   private String SKU;
   private String UPC;
   private int safetyStock;
   private double LTD;
   private int manufacturerId;
   private int catalogId;
   private int onHand;
   private String binName;
   private String extFulfillerLocId;
   private int intFulfillerLocId;

   public String toString() {

      String separator = " | ";
      StringBuilder builder = new StringBuilder();

      builder.append("[");
      builder.append("product name: ").append(productName).append(separator);
      builder.append("SKU: ").append(SKU).append(separator);
      builder.append("UPC: ").append(UPC).append(separator);
      builder.append("safety stock: ").append(safetyStock).append(separator);
      builder.append("LTD: ").append(LTD).append(separator);
      builder.append("manId: ").append(manufacturerId).append(separator);
      builder.append("catId: ").append(catalogId).append(separator);
      builder.append("on hand: ").append(onHand).append(separator);
      builder.append("bin name: ").append(binName).append(separator);
      builder.append("extFulfillerLocId: ").append(extFulfillerLocId).append(separator);
      builder.append("intFulfillerLocId: ").append(intFulfillerLocId);
      builder.append("]");

      return builder.toString();
   }

   public String getProductName() {
      return productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   public String getSKU() {
      return SKU;
   }

   public void setSKU(String SKU) {
      this.SKU = SKU;
   }

   public String getUPC() {
      return UPC;
   }

   public void setUPC(String UPC) {
      this.UPC = UPC;
   }

   public int getSafetyStock() {
      return safetyStock;
   }

   public void setSafetyStock(int safetyStock) {
      this.safetyStock = safetyStock;
   }

   public double getLTD() {
      return LTD;
   }

   public void setLTD(double LTD) {
      this.LTD = LTD;
   }

   public int getManufacturerId() {
      return manufacturerId;
   }

   public void setManufacturerId(int manufacturerId) {
      this.manufacturerId = manufacturerId;
   }

   public int getCatalogId() {
      return catalogId;
   }

   public void setCatalogId(int catalogId) {
      this.catalogId = catalogId;
   }

   public int getOnHand() {
      return onHand;
   }

   public void setOnHand(int onHand) {
      this.onHand = onHand;
   }

   public String getBinName() {
      return binName;
   }

   public void setBinName(String binName) {
      this.binName = binName;
   }

   public String getExtFulfillerLocId() {
      return extFulfillerLocId;
   }

   public void setExtFulfillerLocId(String extFulfillerLocId) {
      this.extFulfillerLocId = extFulfillerLocId;
   }

   public int getIntFulfillerLocId() {
      return intFulfillerLocId;
   }

   public void setIntFulfillerLocId(int intFulfillerLocId) {
      this.intFulfillerLocId = intFulfillerLocId;
   }
}
