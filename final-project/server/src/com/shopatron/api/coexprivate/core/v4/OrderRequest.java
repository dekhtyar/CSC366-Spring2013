/**
 * OrderRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class OrderRequest  implements java.io.Serializable {
    private org.apache.axis.types.PositiveInteger fulfillerID;

    private com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog catalog;

    private com.shopatron.api.coexprivate.core.v4.RequestLocation location;

    private org.apache.axis.types.PositiveInteger maxLocations;

    public OrderRequest() {
    }

    public OrderRequest(
           org.apache.axis.types.PositiveInteger fulfillerID,
           com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog catalog,
           com.shopatron.api.coexprivate.core.v4.RequestLocation location,
           org.apache.axis.types.PositiveInteger maxLocations) {
           this.fulfillerID = fulfillerID;
           this.catalog = catalog;
           this.location = location;
           this.maxLocations = maxLocations;
    }


    /**
     * Gets the fulfillerID value for this OrderRequest.
     * 
     * @return fulfillerID
     */
    public org.apache.axis.types.PositiveInteger getFulfillerID() {
        return fulfillerID;
    }


    /**
     * Sets the fulfillerID value for this OrderRequest.
     * 
     * @param fulfillerID
     */
    public void setFulfillerID(org.apache.axis.types.PositiveInteger fulfillerID) {
        this.fulfillerID = fulfillerID;
    }


    /**
     * Gets the catalog value for this OrderRequest.
     * 
     * @return catalog
     */
    public com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog getCatalog() {
        return catalog;
    }


    /**
     * Sets the catalog value for this OrderRequest.
     * 
     * @param catalog
     */
    public void setCatalog(com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog catalog) {
        this.catalog = catalog;
    }


    /**
     * Gets the location value for this OrderRequest.
     * 
     * @return location
     */
    public com.shopatron.api.coexprivate.core.v4.RequestLocation getLocation() {
        return location;
    }


    /**
     * Sets the location value for this OrderRequest.
     * 
     * @param location
     */
    public void setLocation(com.shopatron.api.coexprivate.core.v4.RequestLocation location) {
        this.location = location;
    }


    /**
     * Gets the maxLocations value for this OrderRequest.
     * 
     * @return maxLocations
     */
    public org.apache.axis.types.PositiveInteger getMaxLocations() {
        return maxLocations;
    }


    /**
     * Sets the maxLocations value for this OrderRequest.
     * 
     * @param maxLocations
     */
    public void setMaxLocations(org.apache.axis.types.PositiveInteger maxLocations) {
        this.maxLocations = maxLocations;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderRequest)) return false;
        OrderRequest other = (OrderRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fulfillerID==null && other.getFulfillerID()==null) || 
             (this.fulfillerID!=null &&
              this.fulfillerID.equals(other.getFulfillerID()))) &&
            ((this.catalog==null && other.getCatalog()==null) || 
             (this.catalog!=null &&
              this.catalog.equals(other.getCatalog()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.maxLocations==null && other.getMaxLocations()==null) || 
             (this.maxLocations!=null &&
              this.maxLocations.equals(other.getMaxLocations())));
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
        if (getFulfillerID() != null) {
            _hashCode += getFulfillerID().hashCode();
        }
        if (getCatalog() != null) {
            _hashCode += getCatalog().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getMaxLocations() != null) {
            _hashCode += getMaxLocations().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "OrderRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fulfillerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "FulfillerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("catalog");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Catalog"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ManufacturerCatalog"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "RequestLocation"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxLocations");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "MaxLocations"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(true);
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
