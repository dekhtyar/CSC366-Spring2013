/**
 * InventoryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class InventoryRequest  implements java.io.Serializable {
    private org.apache.axis.types.PositiveInteger fulfillerID;

    private com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog catalog;

    private com.shopatron.api.coexprivate.core.v4.ItemQuantity[] quantities;

    private java.lang.String[] locationIDs;

    private com.shopatron.api.coexprivate.core.v4.RequestLocation location;

    private com.shopatron.api.coexprivate.core.v4.InventoryRequestType type;

    private int limit;

    private java.lang.Boolean ignoreSafetyStock;

    private java.lang.Boolean includeNegativeInventory;

    private boolean orderByLTD;

    public InventoryRequest() {
    }

    public InventoryRequest(
           org.apache.axis.types.PositiveInteger fulfillerID,
           com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog catalog,
           com.shopatron.api.coexprivate.core.v4.ItemQuantity[] quantities,
           java.lang.String[] locationIDs,
           com.shopatron.api.coexprivate.core.v4.RequestLocation location,
           com.shopatron.api.coexprivate.core.v4.InventoryRequestType type,
           int limit,
           java.lang.Boolean ignoreSafetyStock,
           java.lang.Boolean includeNegativeInventory,
           boolean orderByLTD) {
           this.fulfillerID = fulfillerID;
           this.catalog = catalog;
           this.quantities = quantities; // How many of each item we are looking for
           this.locationIDs = locationIDs; // The locations to search
           this.location = location; // Where the user request is coming from
           this.type = type;
           this.limit = limit; // limit numer locations returned
           this.ignoreSafetyStock = ignoreSafetyStock;
           this.includeNegativeInventory = includeNegativeInventory;
           this.orderByLTD = orderByLTD;
    }


    /**
     * Gets the fulfillerID value for this InventoryRequest.
     * 
     * @return fulfillerID
     */
    public org.apache.axis.types.PositiveInteger getFulfillerID() {
        return fulfillerID;
    }


    /**
     * Sets the fulfillerID value for this InventoryRequest.
     * 
     * @param fulfillerID
     */
    public void setFulfillerID(org.apache.axis.types.PositiveInteger fulfillerID) {
        this.fulfillerID = fulfillerID;
    }


    /**
     * Gets the catalog value for this InventoryRequest.
     * 
     * @return catalog
     */
    public com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog getCatalog() {
        return catalog;
    }


    /**
     * Sets the catalog value for this InventoryRequest.
     * 
     * @param catalog
     */
    public void setCatalog(com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog catalog) {
        this.catalog = catalog;
    }


    /**
     * Gets the quantities value for this InventoryRequest.
     * 
     * @return quantities
     */
    public com.shopatron.api.coexprivate.core.v4.ItemQuantity[] getQuantities() {
        return quantities;
    }


    /**
     * Sets the quantities value for this InventoryRequest.
     * 
     * @param quantities
     */
    public void setQuantities(com.shopatron.api.coexprivate.core.v4.ItemQuantity[] quantities) {
        this.quantities = quantities;
    }


    /**
     * Gets the locationIDs value for this InventoryRequest.
     * 
     * @return locationIDs
     */
    public java.lang.String[] getLocationIDs() {
        return locationIDs;
    }


    /**
     * Sets the locationIDs value for this InventoryRequest.
     * 
     * @param locationIDs
     */
    public void setLocationIDs(java.lang.String[] locationIDs) {
        this.locationIDs = locationIDs;
    }


    /**
     * Gets the location value for this InventoryRequest.
     * 
     * @return location
     */
    public com.shopatron.api.coexprivate.core.v4.RequestLocation getLocation() {
        return location;
    }


    /**
     * Sets the location value for this InventoryRequest.
     * 
     * @param location
     */
    public void setLocation(com.shopatron.api.coexprivate.core.v4.RequestLocation location) {
        this.location = location;
    }


    /**
     * Gets the type value for this InventoryRequest.
     * 
     * @return type
     */
    public com.shopatron.api.coexprivate.core.v4.InventoryRequestType getType() {
        return type;
    }


    /**
     * Sets the type value for this InventoryRequest.
     * 
     * @param type
     */
    public void setType(com.shopatron.api.coexprivate.core.v4.InventoryRequestType type) {
        this.type = type;
    }


    /**
     * Gets the limit value for this InventoryRequest.
     * 
     * @return limit
     */
    public int getLimit() {
        return limit;
    }


    /**
     * Sets the limit value for this InventoryRequest.
     * 
     * @param limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }


    /**
     * Gets the ignoreSafetyStock value for this InventoryRequest.
     * 
     * @return ignoreSafetyStock
     */
    public java.lang.Boolean getIgnoreSafetyStock() {
        return ignoreSafetyStock;
    }


    /**
     * Sets the ignoreSafetyStock value for this InventoryRequest.
     * 
     * @param ignoreSafetyStock
     */
    public void setIgnoreSafetyStock(java.lang.Boolean ignoreSafetyStock) {
        this.ignoreSafetyStock = ignoreSafetyStock;
    }


    /**
     * Gets the includeNegativeInventory value for this InventoryRequest.
     * 
     * @return includeNegativeInventory
     */
    public java.lang.Boolean getIncludeNegativeInventory() {
        return includeNegativeInventory;
    }


    /**
     * Sets the includeNegativeInventory value for this InventoryRequest.
     * 
     * @param includeNegativeInventory
     */
    public void setIncludeNegativeInventory(java.lang.Boolean includeNegativeInventory) {
        this.includeNegativeInventory = includeNegativeInventory;
    }


    /**
     * Gets the orderByLTD value for this InventoryRequest.
     * 
     * @return orderByLTD
     */
    public boolean isOrderByLTD() {
        return orderByLTD;
    }


    /**
     * Sets the orderByLTD value for this InventoryRequest.
     * 
     * @param orderByLTD
     */
    public void setOrderByLTD(boolean orderByLTD) {
        this.orderByLTD = orderByLTD;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InventoryRequest)) return false;
        InventoryRequest other = (InventoryRequest) obj;
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
            ((this.quantities==null && other.getQuantities()==null) || 
             (this.quantities!=null &&
              java.util.Arrays.equals(this.quantities, other.getQuantities()))) &&
            ((this.locationIDs==null && other.getLocationIDs()==null) || 
             (this.locationIDs!=null &&
              java.util.Arrays.equals(this.locationIDs, other.getLocationIDs()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            this.limit == other.getLimit() &&
            ((this.ignoreSafetyStock==null && other.getIgnoreSafetyStock()==null) || 
             (this.ignoreSafetyStock!=null &&
              this.ignoreSafetyStock.equals(other.getIgnoreSafetyStock()))) &&
            ((this.includeNegativeInventory==null && other.getIncludeNegativeInventory()==null) || 
             (this.includeNegativeInventory!=null &&
              this.includeNegativeInventory.equals(other.getIncludeNegativeInventory()))) &&
            this.orderByLTD == other.isOrderByLTD();
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
        if (getQuantities() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQuantities());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQuantities(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLocationIDs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLocationIDs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLocationIDs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        _hashCode += getLimit();
        if (getIgnoreSafetyStock() != null) {
            _hashCode += getIgnoreSafetyStock().hashCode();
        }
        if (getIncludeNegativeInventory() != null) {
            _hashCode += getIncludeNegativeInventory().hashCode();
        }
        _hashCode += (isOrderByLTD() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InventoryRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "InventoryRequest"));
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
        elemField.setFieldName("quantities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Quantities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ItemQuantity"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "items"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationIDs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "LocationIDs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ExternalLocationID"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "RequestLocation"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "InventoryRequestType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Limit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ignoreSafetyStock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "IgnoreSafetyStock"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("includeNegativeInventory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "IncludeNegativeInventory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderByLTD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "OrderByLTD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
