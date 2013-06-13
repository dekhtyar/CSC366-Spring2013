/**
 * FulfillmentLocationCatalog.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class FulfillmentLocationCatalog  implements java.io.Serializable {
    private com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog manufacturerCatalog;

    private java.lang.String externalLocationID;

    public FulfillmentLocationCatalog() {
    }

    public FulfillmentLocationCatalog(
           com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog manufacturerCatalog,
           java.lang.String externalLocationID) {
           this.manufacturerCatalog = manufacturerCatalog;
           this.externalLocationID = externalLocationID;
    }


    /**
     * Gets the manufacturerCatalog value for this FulfillmentLocationCatalog.
     * 
     * @return manufacturerCatalog
     */
    public com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog getManufacturerCatalog() {
        return manufacturerCatalog;
    }


    /**
     * Sets the manufacturerCatalog value for this FulfillmentLocationCatalog.
     * 
     * @param manufacturerCatalog
     */
    public void setManufacturerCatalog(com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog manufacturerCatalog) {
        this.manufacturerCatalog = manufacturerCatalog;
    }


    /**
     * Gets the externalLocationID value for this FulfillmentLocationCatalog.
     * 
     * @return externalLocationID
     */
    public java.lang.String getExternalLocationID() {
        return externalLocationID;
    }


    /**
     * Sets the externalLocationID value for this FulfillmentLocationCatalog.
     * 
     * @param externalLocationID
     */
    public void setExternalLocationID(java.lang.String externalLocationID) {
        this.externalLocationID = externalLocationID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FulfillmentLocationCatalog)) return false;
        FulfillmentLocationCatalog other = (FulfillmentLocationCatalog) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.manufacturerCatalog==null && other.getManufacturerCatalog()==null) || 
             (this.manufacturerCatalog!=null &&
              this.manufacturerCatalog.equals(other.getManufacturerCatalog()))) &&
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
        if (getManufacturerCatalog() != null) {
            _hashCode += getManufacturerCatalog().hashCode();
        }
        if (getExternalLocationID() != null) {
            _hashCode += getExternalLocationID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FulfillmentLocationCatalog.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "FulfillmentLocationCatalog"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manufacturerCatalog");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ManufacturerCatalog"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ManufacturerCatalog"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalLocationID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ExternalLocationID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
