/**
 * InventoryRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class InventoryRequestType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected InventoryRequestType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ALL = "ALL";
    public static final java.lang.String _PARTIAL = "PARTIAL";
    public static final java.lang.String _ANY = "ANY";
    public static final java.lang.String _ALL_STORES = "ALL_STORES";
    public static final InventoryRequestType ALL = new InventoryRequestType(_ALL);
    public static final InventoryRequestType PARTIAL = new InventoryRequestType(_PARTIAL);
    public static final InventoryRequestType ANY = new InventoryRequestType(_ANY);
    public static final InventoryRequestType ALL_STORES = new InventoryRequestType(_ALL_STORES);
    public java.lang.String getValue() { return _value_;}
    public static InventoryRequestType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        InventoryRequestType enumeration = (InventoryRequestType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static InventoryRequestType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InventoryRequestType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "InventoryRequestType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
