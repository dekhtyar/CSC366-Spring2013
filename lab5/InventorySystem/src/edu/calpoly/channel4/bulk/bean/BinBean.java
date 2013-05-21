package edu.calpoly.channel4.bulk.bean;

/**
 * BinBean is a class representing a record in the bins CSV file.
 * A BinBean's fields are a direct mapping of the columns in the CSV file.
 */
public class BinBean {

   private String extFulfillerLocId;
   private int intFulfillerLocId;
   private String name;
   private String type;
   private String status;

   public String toString() {

      String separator = " | ";
      StringBuilder builder = new StringBuilder();

      builder.append("[");
      builder.append("name: ").append(name).append(separator);
      builder.append("extFulfillerLocId: ").append(extFulfillerLocId).append(separator);
      builder.append("intFulfillerLocId: ").append(intFulfillerLocId).append(separator);
      builder.append("status: ").append(status).append(separator);
      builder.append("type: ").append(type);
      builder.append("]");

      return builder.toString();
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

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
