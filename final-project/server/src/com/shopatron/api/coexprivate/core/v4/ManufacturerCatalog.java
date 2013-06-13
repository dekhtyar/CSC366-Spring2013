/**
 * ManufacturerCatalog.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class ManufacturerCatalog  implements java.io.Serializable {
    private org.apache.axis.types.PositiveInteger manufacturerID;

    private org.apache.axis.types.PositiveInteger catalogID;

    public ManufacturerCatalog() {
    }

    public ManufacturerCatalog(
           org.apache.axis.types.PositiveInteger manufacturerID,
           org.apache.axis.types.PositiveInteger catalogID) {
           this.manufacturerID = manufacturerID;
           this.catalogID = catalogID;
    }


    /**
     * Gets the manufacturerID value for this ManufacturerCatalog.
     * 
     * @return manufacturerID
     */
    public org.apache.axis.types.PositiveInteger getManufacturerID() {
        return manufacturerID;
    }


    /**
     * Sets the manufacturerID value for this ManufacturerCatalog.
     * 
     * @param manufacturerID
     */
    public void setManufacturerID(org.apache.axis.types.PositiveInteger manufacturerID) {
        this.manufacturerID = manufacturerID;
    }


    /**
     * Gets the catalogID value for this ManufacturerCatalog.
     * 
     * @return catalogID
     */
    public org.apache.axis.types.PositiveInteger getCatalogID() {
        return catalogID;
    }


    /**
     * Sets the catalogID value for this ManufacturerCatalog.
     * 
     * @param catalogID
     */
    public void setCatalogID(org.apache.axis.types.PositiveInteger catalogID) {
        this.catalogID = catalogID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ManufacturerCatalog)) return false;
        ManufacturerCatalog other = (ManufacturerCatalog) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.manufacturerID==null && other.getManufacturerID()==null) || 
             (this.manufacturerID!=null &&
              this.manufacturerID.equals(other.getManufacturerID()))) &&
            ((this.catalogID==null && other.getCatalogID()==null) || 
             (this.catalogID!=null &&
              this.catalogID.equals(other.getCatalogID())));
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
        if (getManufacturerID() != null) {
            _hashCode += getManufacturerID().hashCode();
        }
        if (getCatalogID() != null) {
            _hashCode += getCatalogID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ManufacturerCatalog.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ManufacturerCatalog"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manufacturerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ManufacturerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("catalogID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "CatalogID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
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
