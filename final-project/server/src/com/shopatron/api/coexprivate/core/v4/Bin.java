/**
 * Bin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

import java.sql.SQLException;
import java.util.List;

import com.shopatron.Database;
import com.shopatron.OnHand;
import com.shopatron.OnHandReader;

public class Bin  implements java.io.Serializable {
    private org.apache.axis.types.PositiveInteger fulfillerID;

    private org.apache.axis.types.PositiveInteger binID;

    private java.lang.String externalLocationID;

    private java.lang.String binType;

    private java.lang.String binStatus;

    private java.lang.String name;
    
    private int internalID;
    
    private List<OnHand> onHand = null;

    public Bin() {
    }

    public Bin(
           org.apache.axis.types.PositiveInteger fulfillerID,
           org.apache.axis.types.PositiveInteger binID,
           java.lang.String externalLocationID,
           java.lang.String binType,
           java.lang.String binStatus,
           java.lang.String name) {
           this.fulfillerID = fulfillerID;
           this.binID = binID;
           this.externalLocationID = externalLocationID;
           this.binType = binType;
           this.binStatus = binStatus;
           this.name = name;
    }
    
    public OnHand findOnHand (int locationID, String name, int SKU) {
		try {
			getBinContents();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	for (OnHand item : onHand) {
    		if(item.getLocationID() == locationID &&
    		   item.getName().equals(name) &&
    		   item.getSKU() == SKU)
    			return item;
    	}
    	
    	return null;
    }
    
    public List<OnHand> getBinContents() throws SQLException {
    	if(onHand == null) {
    		onHand = Database.executeQuery(new OnHandReader(),
    				  "SELECT * FROM OnHand WHERE LocationID = ? AND Name = ?",
    				  externalLocationID, name);
    	}
    	
    	return onHand;
    }
    
    public int getInternalID() {
        return internalID;
    }
    
    public void setInternalID(int id) {
        this.internalID = id;
    }


    /**
     * Gets the fulfillerID value for this Bin.
     * 
     * @return fulfillerID
     */
    public org.apache.axis.types.PositiveInteger getFulfillerID() {
        return fulfillerID;
    }


    /**
     * Sets the fulfillerID value for this Bin.
     * 
     * @param fulfillerID
     */
    public void setFulfillerID(org.apache.axis.types.PositiveInteger fulfillerID) {
        this.fulfillerID = fulfillerID;
    }


    /**
     * Gets the binID value for this Bin.
     * 
     * @return binID
     */
    public org.apache.axis.types.PositiveInteger getBinID() {
        return binID;
    }


    /**
     * Sets the binID value for this Bin.
     * 
     * @param binID
     */
    public void setBinID(org.apache.axis.types.PositiveInteger binID) {
        this.binID = binID;
    }


    /**
     * Gets the externalLocationID value for this Bin.
     * 
     * @return externalLocationID
     */
    public java.lang.String getExternalLocationID() {
        return externalLocationID;
    }


    /**
     * Sets the externalLocationID value for this Bin.
     * 
     * @param externalLocationID
     */
    public void setExternalLocationID(java.lang.String externalLocationID) {
        this.externalLocationID = externalLocationID;
    }


    /**
     * Gets the binType value for this Bin.
     * 
     * @return binType
     */
    public java.lang.String getBinType() {
        return binType;
    }


    /**
     * Sets the binType value for this Bin.
     * 
     * @param binType
     */
    public void setBinType(java.lang.String binType) {
        this.binType = binType;
    }


    /**
     * Gets the binStatus value for this Bin.
     * 
     * @return binStatus
     */
    public java.lang.String getBinStatus() {
        return binStatus;
    }


    /**
     * Sets the binStatus value for this Bin.
     * 
     * @param binStatus
     */
    public void setBinStatus(java.lang.String binStatus) {
        this.binStatus = binStatus;
    }


    /**
     * Gets the name value for this Bin.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Bin.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Bin)) return false;
        Bin other = (Bin) obj;
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
            ((this.binID==null && other.getBinID()==null) || 
             (this.binID!=null &&
              this.binID.equals(other.getBinID()))) &&
            ((this.externalLocationID==null && other.getExternalLocationID()==null) || 
             (this.externalLocationID!=null &&
              this.externalLocationID.equals(other.getExternalLocationID()))) &&
            ((this.binType==null && other.getBinType()==null) || 
             (this.binType!=null &&
              this.binType.equals(other.getBinType()))) &&
            ((this.binStatus==null && other.getBinStatus()==null) || 
             (this.binStatus!=null &&
              this.binStatus.equals(other.getBinStatus()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName())));
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
        if (getBinID() != null) {
            _hashCode += getBinID().hashCode();
        }
        if (getExternalLocationID() != null) {
            _hashCode += getExternalLocationID().hashCode();
        }
        if (getBinType() != null) {
            _hashCode += getBinType().hashCode();
        }
        if (getBinStatus() != null) {
            _hashCode += getBinStatus().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Bin.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Bin"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fulfillerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "FulfillerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("binID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "BinID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalLocationID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ExternalLocationID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("binType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "BinType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("binStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "BinStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "Name"));
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
