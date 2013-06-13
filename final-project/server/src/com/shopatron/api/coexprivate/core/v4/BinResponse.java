/**
 * BinResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class BinResponse  implements java.io.Serializable {
    private com.shopatron.api.coexprivate.core.v4.Bin[] bins;

    private org.apache.axis.types.PositiveInteger resultCount;

    public BinResponse() {
    }

    public BinResponse(
           com.shopatron.api.coexprivate.core.v4.Bin[] bins,
           org.apache.axis.types.PositiveInteger resultCount) {
           this.bins = bins;
           this.resultCount = resultCount;
    }


    /**
     * Gets the bins value for this BinResponse.
     * 
     * @return bins
     */
    public com.shopatron.api.coexprivate.core.v4.Bin[] getBins() {
        return bins;
    }


    /**
     * Sets the bins value for this BinResponse.
     * 
     * @param bins
     */
    public void setBins(com.shopatron.api.coexprivate.core.v4.Bin[] bins) {
        this.bins = bins;
    }


    /**
     * Gets the resultCount value for this BinResponse.
     * 
     * @return resultCount
     */
    public org.apache.axis.types.PositiveInteger getResultCount() {
        return resultCount;
    }


    /**
     * Sets the resultCount value for this BinResponse.
     * 
     * @param resultCount
     */
    public void setResultCount(org.apache.axis.types.PositiveInteger resultCount) {
        this.resultCount = resultCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BinResponse)) return false;
        BinResponse other = (BinResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bins==null && other.getBins()==null) || 
             (this.bins!=null &&
              java.util.Arrays.equals(this.bins, other.getBins()))) &&
            ((this.resultCount==null && other.getResultCount()==null) || 
             (this.resultCount!=null &&
              this.resultCount.equals(other.getResultCount())));
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
        if (getBins() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBins());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBins(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResultCount() != null) {
            _hashCode += getResultCount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BinResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "BinResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Bins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Bin"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "items"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ResultCount"));
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
