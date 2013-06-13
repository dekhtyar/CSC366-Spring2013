/**
 * GetBinsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class GetBinsResponse  implements java.io.Serializable {
    private com.shopatron.api.coexprivate.core.v4.BinResponse getBinsReturn;

    public GetBinsResponse() {
    }

    public GetBinsResponse(
           com.shopatron.api.coexprivate.core.v4.BinResponse getBinsReturn) {
           this.getBinsReturn = getBinsReturn;
    }


    /**
     * Gets the getBinsReturn value for this GetBinsResponse.
     * 
     * @return getBinsReturn
     */
    public com.shopatron.api.coexprivate.core.v4.BinResponse getGetBinsReturn() {
        return getBinsReturn;
    }


    /**
     * Sets the getBinsReturn value for this GetBinsResponse.
     * 
     * @param getBinsReturn
     */
    public void setGetBinsReturn(com.shopatron.api.coexprivate.core.v4.BinResponse getBinsReturn) {
        this.getBinsReturn = getBinsReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetBinsResponse)) return false;
        GetBinsResponse other = (GetBinsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getBinsReturn==null && other.getGetBinsReturn()==null) || 
             (this.getBinsReturn!=null &&
              this.getBinsReturn.equals(other.getGetBinsReturn())));
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
        if (getGetBinsReturn() != null) {
            _hashCode += getGetBinsReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetBinsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", ">getBinsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getBinsReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "getBinsReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "BinResponse"));
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
