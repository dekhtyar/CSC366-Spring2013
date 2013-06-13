/**
 * InventoryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class InventoryResponse  implements java.io.Serializable {
    private java.lang.String locationName;

    private int catalogID;

    private int manufacturerID;

    private int onHand;

    private int available;

    private java.lang.String partNumber;

    private java.lang.String UPC;

    private double LTD;

    private int safetyStock;

    private java.lang.String countryCode;

    private double distance;

    public InventoryResponse() {
    }

    public InventoryResponse(
           java.lang.String locationName,
           int catalogID,
           int manufacturerID,
           int onHand,
           int available,
           java.lang.String partNumber,
           java.lang.String UPC,
           double LTD,
           int safetyStock,
           java.lang.String countryCode,
           double distance) {
           this.locationName = locationName;
           this.catalogID = catalogID;
           this.manufacturerID = manufacturerID;
           this.onHand = onHand;
           this.available = available;
           this.partNumber = partNumber;
           this.UPC = UPC;
           this.LTD = LTD;
           this.safetyStock = safetyStock;
           this.countryCode = countryCode;
           this.distance = distance;
    }


    /**
     * Gets the locationName value for this InventoryResponse.
     * 
     * @return locationName
     */
    public java.lang.String getLocationName() {
        return locationName;
    }


    /**
     * Sets the locationName value for this InventoryResponse.
     * 
     * @param locationName
     */
    public void setLocationName(java.lang.String locationName) {
        this.locationName = locationName;
    }


    /**
     * Gets the catalogID value for this InventoryResponse.
     * 
     * @return catalogID
     */
    public int getCatalogID() {
        return catalogID;
    }


    /**
     * Sets the catalogID value for this InventoryResponse.
     * 
     * @param catalogID
     */
    public void setCatalogID(int catalogID) {
        this.catalogID = catalogID;
    }


    /**
     * Gets the manufacturerID value for this InventoryResponse.
     * 
     * @return manufacturerID
     */
    public int getManufacturerID() {
        return manufacturerID;
    }


    /**
     * Sets the manufacturerID value for this InventoryResponse.
     * 
     * @param manufacturerID
     */
    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }


    /**
     * Gets the onHand value for this InventoryResponse.
     * 
     * @return onHand
     */
    public int getOnHand() {
        return onHand;
    }


    /**
     * Sets the onHand value for this InventoryResponse.
     * 
     * @param onHand
     */
    public void setOnHand(int onHand) {
        this.onHand = onHand;
    }


    /**
     * Gets the available value for this InventoryResponse.
     * 
     * @return available
     */
    public int getAvailable() {
        return available;
    }


    /**
     * Sets the available value for this InventoryResponse.
     * 
     * @param available
     */
    public void setAvailable(int available) {
        this.available = available;
    }


    /**
     * Gets the partNumber value for this InventoryResponse.
     * 
     * @return partNumber
     */
    public java.lang.String getPartNumber() {
        return partNumber;
    }


    /**
     * Sets the partNumber value for this InventoryResponse.
     * 
     * @param partNumber
     */
    public void setPartNumber(java.lang.String partNumber) {
        this.partNumber = partNumber;
    }


    /**
     * Gets the UPC value for this InventoryResponse.
     * 
     * @return UPC
     */
    public java.lang.String getUPC() {
        return UPC;
    }


    /**
     * Sets the UPC value for this InventoryResponse.
     * 
     * @param UPC
     */
    public void setUPC(java.lang.String UPC) {
        this.UPC = UPC;
    }


    /**
     * Gets the LTD value for this InventoryResponse.
     * 
     * @return LTD
     */
    public double getLTD() {
        return LTD;
    }


    /**
     * Sets the LTD value for this InventoryResponse.
     * 
     * @param LTD
     */
    public void setLTD(double LTD) {
        this.LTD = LTD;
    }


    /**
     * Gets the safetyStock value for this InventoryResponse.
     * 
     * @return safetyStock
     */
    public int getSafetyStock() {
        return safetyStock;
    }


    /**
     * Sets the safetyStock value for this InventoryResponse.
     * 
     * @param safetyStock
     */
    public void setSafetyStock(int safetyStock) {
        this.safetyStock = safetyStock;
    }


    /**
     * Gets the countryCode value for this InventoryResponse.
     * 
     * @return countryCode
     */
    public java.lang.String getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this InventoryResponse.
     * 
     * @param countryCode
     */
    public void setCountryCode(java.lang.String countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the distance value for this InventoryResponse.
     * 
     * @return distance
     */
    public double getDistance() {
        return distance;
    }


    /**
     * Sets the distance value for this InventoryResponse.
     * 
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InventoryResponse)) return false;
        InventoryResponse other = (InventoryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.locationName==null && other.getLocationName()==null) || 
             (this.locationName!=null &&
              this.locationName.equals(other.getLocationName()))) &&
            this.catalogID == other.getCatalogID() &&
            this.manufacturerID == other.getManufacturerID() &&
            this.onHand == other.getOnHand() &&
            this.available == other.getAvailable() &&
            ((this.partNumber==null && other.getPartNumber()==null) || 
             (this.partNumber!=null &&
              this.partNumber.equals(other.getPartNumber()))) &&
            ((this.UPC==null && other.getUPC()==null) || 
             (this.UPC!=null &&
              this.UPC.equals(other.getUPC()))) &&
            this.LTD == other.getLTD() &&
            this.safetyStock == other.getSafetyStock() &&
            ((this.countryCode==null && other.getCountryCode()==null) || 
             (this.countryCode!=null &&
              this.countryCode.equals(other.getCountryCode()))) &&
            this.distance == other.getDistance();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getLocationName() != null) {
            _hashCode += getLocationName().hashCode();
        }
        _hashCode += getCatalogID();
        _hashCode += getManufacturerID();
        _hashCode += getOnHand();
        _hashCode += getAvailable();
        if (getPartNumber() != null) {
            _hashCode += getPartNumber().hashCode();
        }
        if (getUPC() != null) {
            _hashCode += getUPC().hashCode();
        }
        _hashCode += new Double(getLTD()).hashCode();
        _hashCode += getSafetyStock();
        if (getCountryCode() != null) {
            _hashCode += getCountryCode().hashCode();
        }
        _hashCode += new Double(getDistance()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InventoryResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "InventoryResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "LocationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("catalogID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "CatalogID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manufacturerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ManufacturerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onHand");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "OnHand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("available");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Available"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "PartNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UPC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "UPC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LTD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "LTD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("safetyStock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "SafetyStock"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "CountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Distance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
