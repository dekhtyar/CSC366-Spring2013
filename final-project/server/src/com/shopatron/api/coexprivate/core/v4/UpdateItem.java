/**
 * UpdateItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class UpdateItem  implements java.io.Serializable {
    private java.lang.String partNumber;

    private java.lang.String UPC;

    private int quantity;

    private org.apache.axis.types.PositiveInteger orderID;

    private org.apache.axis.types.PositiveInteger orderItemID;

    private org.apache.axis.types.PositiveInteger shipmentID;

    private java.lang.String externalLocationID;

    public UpdateItem() {
    }

    public UpdateItem(
           java.lang.String partNumber,
           java.lang.String UPC,
           int quantity,
           org.apache.axis.types.PositiveInteger orderID,
           org.apache.axis.types.PositiveInteger orderItemID,
           org.apache.axis.types.PositiveInteger shipmentID,
           java.lang.String externalLocationID) {
           this.partNumber = partNumber;
           this.UPC = UPC;
           this.quantity = quantity;
           this.orderID = orderID;
           this.orderItemID = orderItemID;
           this.shipmentID = shipmentID;
           this.externalLocationID = externalLocationID;
    }


    /**
     * Gets the partNumber value for this UpdateItem.
     * 
     * @return partNumber
     */
    public java.lang.String getPartNumber() {
        return partNumber;
    }


    /**
     * Sets the partNumber value for this UpdateItem.
     * 
     * @param partNumber
     */
    public void setPartNumber(java.lang.String partNumber) {
        this.partNumber = partNumber;
    }


    /**
     * Gets the UPC value for this UpdateItem.
     * 
     * @return UPC
     */
    public java.lang.String getUPC() {
        return UPC;
    }


    /**
     * Sets the UPC value for this UpdateItem.
     * 
     * @param UPC
     */
    public void setUPC(java.lang.String UPC) {
        this.UPC = UPC;
    }


    /**
     * Gets the quantity value for this UpdateItem.
     * 
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this UpdateItem.
     * 
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the orderID value for this UpdateItem.
     * 
     * @return orderID
     */
    public org.apache.axis.types.PositiveInteger getOrderID() {
        return orderID;
    }


    /**
     * Sets the orderID value for this UpdateItem.
     * 
     * @param orderID
     */
    public void setOrderID(org.apache.axis.types.PositiveInteger orderID) {
        this.orderID = orderID;
    }


    /**
     * Gets the orderItemID value for this UpdateItem.
     * 
     * @return orderItemID
     */
    public org.apache.axis.types.PositiveInteger getOrderItemID() {
        return orderItemID;
    }


    /**
     * Sets the orderItemID value for this UpdateItem.
     * 
     * @param orderItemID
     */
    public void setOrderItemID(org.apache.axis.types.PositiveInteger orderItemID) {
        this.orderItemID = orderItemID;
    }


    /**
     * Gets the shipmentID value for this UpdateItem.
     * 
     * @return shipmentID
     */
    public org.apache.axis.types.PositiveInteger getShipmentID() {
        return shipmentID;
    }


    /**
     * Sets the shipmentID value for this UpdateItem.
     * 
     * @param shipmentID
     */
    public void setShipmentID(org.apache.axis.types.PositiveInteger shipmentID) {
        this.shipmentID = shipmentID;
    }


    /**
     * Gets the externalLocationID value for this UpdateItem.
     * 
     * @return externalLocationID
     */
    public java.lang.String getExternalLocationID() {
        return externalLocationID;
    }


    /**
     * Sets the externalLocationID value for this UpdateItem.
     * 
     * @param externalLocationID
     */
    public void setExternalLocationID(java.lang.String externalLocationID) {
        this.externalLocationID = externalLocationID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UpdateItem)) return false;
        UpdateItem other = (UpdateItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.partNumber==null && other.getPartNumber()==null) || 
             (this.partNumber!=null &&
              this.partNumber.equals(other.getPartNumber()))) &&
            ((this.UPC==null && other.getUPC()==null) || 
             (this.UPC!=null &&
              this.UPC.equals(other.getUPC()))) &&
            this.quantity == other.getQuantity() &&
            ((this.orderID==null && other.getOrderID()==null) || 
             (this.orderID!=null &&
              this.orderID.equals(other.getOrderID()))) &&
            ((this.orderItemID==null && other.getOrderItemID()==null) || 
             (this.orderItemID!=null &&
              this.orderItemID.equals(other.getOrderItemID()))) &&
            ((this.shipmentID==null && other.getShipmentID()==null) || 
             (this.shipmentID!=null &&
              this.shipmentID.equals(other.getShipmentID()))) &&
            ((this.externalLocationID==null && other.getExternalLocationID()==null) || 
             (this.externalLocationID!=null &&
              this.externalLocationID.equals(other.getExternalLocationID())));
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
        if (getPartNumber() != null) {
            _hashCode += getPartNumber().hashCode();
        }
        if (getUPC() != null) {
            _hashCode += getUPC().hashCode();
        }
        _hashCode += getQuantity();
        if (getOrderID() != null) {
            _hashCode += getOrderID().hashCode();
        }
        if (getOrderItemID() != null) {
            _hashCode += getOrderItemID().hashCode();
        }
        if (getShipmentID() != null) {
            _hashCode += getShipmentID().hashCode();
        }
        if (getExternalLocationID() != null) {
            _hashCode += getExternalLocationID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "UpdateItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "OrderID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderItemID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "OrderItemID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ShipmentID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalLocationID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ExternalLocationID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
