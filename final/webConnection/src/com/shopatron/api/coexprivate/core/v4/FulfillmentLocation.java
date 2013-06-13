
/**
 * FulfillmentLocation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.shopatron.api.coexprivate.core.v4;
            

            /**
            *  FulfillmentLocation bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class FulfillmentLocation
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = FulfillmentLocation
                Namespace URI = http://v4.core.coexprivate.api.shopatron.com
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for FulfillerID
                        */

                        
                                    protected org.apache.axis2.databinding.types.PositiveInteger localFulfillerID ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.PositiveInteger
                           */
                           public  org.apache.axis2.databinding.types.PositiveInteger getFulfillerID(){
                               return localFulfillerID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FulfillerID
                               */
                               public void setFulfillerID(org.apache.axis2.databinding.types.PositiveInteger param){
                            
                                            this.localFulfillerID=param;
                                    

                               }
                            

                        /**
                        * field for RetailerLocationID
                        */

                        
                                    protected org.apache.axis2.databinding.types.PositiveInteger localRetailerLocationID ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.PositiveInteger
                           */
                           public  org.apache.axis2.databinding.types.PositiveInteger getRetailerLocationID(){
                               return localRetailerLocationID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RetailerLocationID
                               */
                               public void setRetailerLocationID(org.apache.axis2.databinding.types.PositiveInteger param){
                            
                                            this.localRetailerLocationID=param;
                                    

                               }
                            

                        /**
                        * field for ExternalLocationID
                        */

                        
                                    protected java.lang.String localExternalLocationID ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getExternalLocationID(){
                               return localExternalLocationID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExternalLocationID
                               */
                               public void setExternalLocationID(java.lang.String param){
                            
                                            this.localExternalLocationID=param;
                                    

                               }
                            

                        /**
                        * field for LocationName
                        */

                        
                                    protected java.lang.String localLocationName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLocationName(){
                               return localLocationName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LocationName
                               */
                               public void setLocationName(java.lang.String param){
                            
                                            this.localLocationName=param;
                                    

                               }
                            

                        /**
                        * field for LocationType
                        */

                        
                                    protected java.lang.String localLocationType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLocationType(){
                               return localLocationType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LocationType
                               */
                               public void setLocationType(java.lang.String param){
                            
                                            this.localLocationType=param;
                                    

                               }
                            

                        /**
                        * field for Latitude
                        */

                        
                                    protected double localLatitude ;
                                

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getLatitude(){
                               return localLatitude;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Latitude
                               */
                               public void setLatitude(double param){
                            
                                            this.localLatitude=param;
                                    

                               }
                            

                        /**
                        * field for Longitude
                        */

                        
                                    protected double localLongitude ;
                                

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getLongitude(){
                               return localLongitude;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Longitude
                               */
                               public void setLongitude(double param){
                            
                                            this.localLongitude=param;
                                    

                               }
                            

                        /**
                        * field for Status
                        */

                        
                                    protected com.shopatron.api.coexprivate.core.v4.Status_type1 localStatus ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.shopatron.api.coexprivate.core.v4.Status_type1
                           */
                           public  com.shopatron.api.coexprivate.core.v4.Status_type1 getStatus(){
                               return localStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Status
                               */
                               public void setStatus(com.shopatron.api.coexprivate.core.v4.Status_type1 param){
                            
                                            this.localStatus=param;
                                    

                               }
                            

                        /**
                        * field for CountryCode
                        */

                        
                                    protected java.lang.String localCountryCode ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCountryCode(){
                               return localCountryCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CountryCode
                               */
                               public void setCountryCode(java.lang.String param){
                            
                                            this.localCountryCode=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://v4.core.coexprivate.api.shopatron.com");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":FulfillmentLocation",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "FulfillmentLocation",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "FulfillerID", xmlWriter);
                             

                                          if (localFulfillerID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("FulfillerID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFulfillerID));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "RetailerLocationID", xmlWriter);
                             

                                          if (localRetailerLocationID==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRetailerLocationID));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "ExternalLocationID", xmlWriter);
                             

                                          if (localExternalLocationID==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localExternalLocationID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "LocationName", xmlWriter);
                             

                                          if (localLocationName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocationName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "LocationType", xmlWriter);
                             

                                          if (localLocationType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("LocationType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocationType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "Latitude", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localLatitude)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("Latitude cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLatitude));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "Longitude", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localLongitude)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("Longitude cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLongitude));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                            if (localStatus==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Status cannot be null!!");
                                            }
                                           localStatus.serialize(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Status"),
                                               xmlWriter);
                                        
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "CountryCode", xmlWriter);
                             

                                          if (localCountryCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCountryCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://v4.core.coexprivate.api.shopatron.com")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "FulfillerID"));
                                 
                                        if (localFulfillerID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFulfillerID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("FulfillerID cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "RetailerLocationID"));
                                 
                                         elementList.add(localRetailerLocationID==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRetailerLocationID));
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "ExternalLocationID"));
                                 
                                         elementList.add(localExternalLocationID==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalLocationID));
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "LocationName"));
                                 
                                         elementList.add(localLocationName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocationName));
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "LocationType"));
                                 
                                        if (localLocationType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocationType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("LocationType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "Latitude"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLatitude));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "Longitude"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLongitude));
                            
                            elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "Status"));
                            
                            
                                    if (localStatus==null){
                                         throw new org.apache.axis2.databinding.ADBException("Status cannot be null!!");
                                    }
                                    elementList.add(localStatus);
                                
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "CountryCode"));
                                 
                                         elementList.add(localCountryCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryCode));
                                    

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FulfillmentLocation parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FulfillmentLocation object =
                new FulfillmentLocation();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"FulfillmentLocation".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (FulfillmentLocation)com.shopatron.api.coexprivate.core.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","FulfillerID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"FulfillerID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFulfillerID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToPositiveInteger(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","RetailerLocationID").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRetailerLocationID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToPositiveInteger(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","ExternalLocationID").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setExternalLocationID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","LocationName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocationName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","LocationType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"LocationType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocationType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Latitude").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Latitude" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLatitude(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Longitude").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Longitude" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLongitude(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Status").equals(reader.getName())){
                                
                                                object.setStatus(com.shopatron.api.coexprivate.core.v4.Status_type1.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","CountryCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCountryCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                              
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    