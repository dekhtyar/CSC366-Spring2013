/**
 * BinRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class BinRequest  implements java.io.Serializable {
    private org.apache.axis.types.PositiveInteger fulfillerID;

    private java.lang.String externalLocationID;

    private java.lang.String searchTerm;

    private org.apache.axis.types.PositiveInteger numResults;

    private org.apache.axis.types.PositiveInteger resultsStart;

    public BinRequest() {
    }

    public BinRequest(
           org.apache.axis.types.PositiveInteger fulfillerID,
           java.lang.String externalLocationID,
           java.lang.String searchTerm,
           org.apache.axis.types.PositiveInteger numResults,
           org.apache.axis.types.PositiveInteger resultsStart) {
           this.fulfillerID = fulfillerID;
           this.externalLocationID = externalLocationID;
           this.searchTerm = searchTerm;
           this.numResults = numResults;
           this.resultsStart = resultsStart;
    }


    /**
     * Gets the fulfillerID value for this BinRequest.
     * 
     * @return fulfillerID
     */
    public org.apache.axis.types.PositiveInteger getFulfillerID() {
        return fulfillerID;
    }


    /**
     * Sets the fulfillerID value for this BinRequest.
     * 
     * @param fulfillerID
     */
    public void setFulfillerID(org.apache.axis.types.PositiveInteger fulfillerID) {
        this.fulfillerID = fulfillerID;
    }


    /**
     * Gets the externalLocationID value for this BinRequest.
     * 
     * @return externalLocationID
     */
    public java.lang.String getExternalLocationID() {
        return externalLocationID;
    }


    /**
     * Sets the externalLocationID value for this BinRequest.
     * 
     * @param externalLocationID
     */
    public void setExternalLocationID(java.lang.String externalLocationID) {
        this.externalLocationID = externalLocationID;
    }


    /**
     * Gets the searchTerm value for this BinRequest.
     * 
     * @return searchTerm
     */
    public java.lang.String getSearchTerm() {
        return searchTerm;
    }


    /**
     * Sets the searchTerm value for this BinRequest.
     * 
     * @param searchTerm
     */
    public void setSearchTerm(java.lang.String searchTerm) {
        this.searchTerm = searchTerm;
    }


    /**
     * Gets the numResults value for this BinRequest.
     * 
     * @return numResults
     */
    public org.apache.axis.types.PositiveInteger getNumResults() {
        return numResults;
    }


    /**
     * Sets the numResults value for this BinRequest.
     * 
     * @param numResults
     */
    public void setNumResults(org.apache.axis.types.PositiveInteger numResults) {
        this.numResults = numResults;
    }


    /**
     * Gets the resultsStart value for this BinRequest.
     * 
     * @return resultsStart
     */
    public org.apache.axis.types.PositiveInteger getResultsStart() {
        return resultsStart;
    }


    /**
     * Sets the resultsStart value for this BinRequest.
     * 
     * @param resultsStart
     */
    public void setResultsStart(org.apache.axis.types.PositiveInteger resultsStart) {
        this.resultsStart = resultsStart;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BinRequest)) return false;
        BinRequest other = (BinRequest) obj;
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
            ((this.searchTerm==null && other.getSearchTerm()==null) || 
             (this.searchTerm!=null &&
              this.searchTerm.equals(other.getSearchTerm()))) &&
            ((this.numResults==null && other.getNumResults()==null) || 
             (this.numResults!=null &&
              this.numResults.equals(other.getNumResults()))) &&
            ((this.resultsStart==null && other.getResultsStart()==null) || 
             (this.resultsStart!=null &&
              this.resultsStart.equals(other.getResultsStart())));
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
        if (getSearchTerm() != null) {
            _hashCode += getSearchTerm().hashCode();
        }
        if (getNumResults() != null) {
            _hashCode += getNumResults().hashCode();
        }
        if (getResultsStart() != null) {
            _hashCode += getResultsStart().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BinRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "BinRequest"));
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
        elemField.setFieldName("searchTerm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "SearchTerm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "NumResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultsStart");
        elemField.setXmlName(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "ResultsStart"));
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
