/**
 * RefreshRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class RefreshRequest  implements java.io.Serializable {
    private org.apache.axis.types.PositiveInteger fulfillerID;

    private java.lang.String externalLocationID;

    private com.shopatron.api.coexprivate.core.v4.RefreshItem[] items;

    public RefreshRequest() {
    }

    public RefreshRequest(
           org.apache.axis.types.PositiveInteger fulfillerID,
           java.lang.String externalLocationID,
           com.shopatron.api.coexprivate.core.v4.RefreshItem[] items) {
           this.fulfillerID = fulfillerID;
           this.externalLocationID = externalLocationID;
           this.items = items;
    }


    /**
     * Gets the fulfillerID value for this RefreshRequest.
     * 
     * @return fulfillerID
     */
    public org.apache.axis.types.PositiveInteger getFulfillerID() {
        return fulfillerID;
    }


    /**
     * Sets the fulfillerID value for this RefreshRequest.
     * 
     * @param fulfillerID
     */
    public void setFulfillerID(org.apache.axis.types.PositiveInteger fulfillerID) {
        this.fulfillerID = fulfillerID;
    }


    /**
     * Gets the externalLocationID value for this RefreshRequest.
     * 
     * @return externalLocationID
     */
    public java.lang.String getExternalLocationID() {
        return externalLocationID;
    }


    /**
     * Sets the externalLocationID value for this RefreshRequest.
     * 
     * @param externalLocationID
     */
    public void setExternalLocationID(java.lang.String externalLocationID) {
        this.externalLocationID = externalLocationID;
    }


    /**
     * Gets the items value for this RefreshRequest.
     * 
     * @return items
     */
    public com.shopatron.api.coexprivate.core.v4.RefreshItem[] getItems() {
        return items;
    }


    /**
     * Sets the items value for this RefreshRequest.
     * 
     * @param items
     */
    public void setItems(com.shopatron.api.coexprivate.core.v4.RefreshItem[] items) {
        this.items = items;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RefreshRequest)) return false;
        RefreshRequest other = (RefreshRequest) obj;
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
            ((this.externalLocationID==null && other.getExternalLocationID()==null) || 
             (this.externalLocationID!=null &&
              this.externalLocationID.equals(other.getExternalLocationID()))) &&
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              java.util.Arrays.equals(this.items, other.getItems())));
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
        if (getExternalLocationID() != null) {
            _hashCode += getExternalLocationID().hashCode();
        }
        if (getItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RefreshRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", ">RefreshRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fulfillerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "FulfillerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalLocationID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ExternalLocationID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("items");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Items"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "RefreshItem"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "items"));
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
