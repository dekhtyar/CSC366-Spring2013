
/**
 * InventoryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.shopatron.api.coexprivate.core.v4;
            

            /**
            *  InventoryRequest bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class InventoryRequest
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InventoryRequest
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
                        * field for Catalog
                        */

                        
                                    protected com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog localCatalog ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog
                           */
                           public  com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog getCatalog(){
                               return localCatalog;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Catalog
                               */
                               public void setCatalog(com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog param){
                            
                                            this.localCatalog=param;
                                    

                               }
                            

                        /**
                        * field for Quantities
                        */

                        
                                    protected com.shopatron.api.coexprivate.core.v4.ArrayOf_impl_ItemQuantity localQuantities ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.shopatron.api.coexprivate.core.v4.ArrayOf_impl_ItemQuantity
                           */
                           public  com.shopatron.api.coexprivate.core.v4.ArrayOf_impl_ItemQuantity getQuantities(){
                               return localQuantities;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Quantities
                               */
                               public void setQuantities(com.shopatron.api.coexprivate.core.v4.ArrayOf_impl_ItemQuantity param){
                            
                                            this.localQuantities=param;
                                    

                               }
                            

                        /**
                        * field for LocationIDs
                        */

                        
                                    protected com.shopatron.api.coexprivate.core.v4.ArrayOfLocationIDs localLocationIDs ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.shopatron.api.coexprivate.core.v4.ArrayOfLocationIDs
                           */
                           public  com.shopatron.api.coexprivate.core.v4.ArrayOfLocationIDs getLocationIDs(){
                               return localLocationIDs;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LocationIDs
                               */
                               public void setLocationIDs(com.shopatron.api.coexprivate.core.v4.ArrayOfLocationIDs param){
                            
                                            this.localLocationIDs=param;
                                    

                               }
                            

                        /**
                        * field for Location
                        */

                        
                                    protected com.shopatron.api.coexprivate.core.v4.RequestLocation localLocation ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.shopatron.api.coexprivate.core.v4.RequestLocation
                           */
                           public  com.shopatron.api.coexprivate.core.v4.RequestLocation getLocation(){
                               return localLocation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Location
                               */
                               public void setLocation(com.shopatron.api.coexprivate.core.v4.RequestLocation param){
                            
                                            this.localLocation=param;
                                    

                               }
                            

                        /**
                        * field for Type
                        */

                        
                                    protected com.shopatron.api.coexprivate.core.v4.InventoryRequestType localType ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.shopatron.api.coexprivate.core.v4.InventoryRequestType
                           */
                           public  com.shopatron.api.coexprivate.core.v4.InventoryRequestType getType(){
                               return localType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Type
                               */
                               public void setType(com.shopatron.api.coexprivate.core.v4.InventoryRequestType param){
                            
                                            this.localType=param;
                                    

                               }
                            

                        /**
                        * field for Limit
                        */

                        
                                    protected int localLimit =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt("10000");
                                

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getLimit(){
                               return localLimit;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Limit
                               */
                               public void setLimit(int param){
                            
                                            this.localLimit=param;
                                    

                               }
                            

                        /**
                        * field for IgnoreSafetyStock
                        */

                        
                                    protected boolean localIgnoreSafetyStock =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("false");
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIgnoreSafetyStock(){
                               return localIgnoreSafetyStock;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IgnoreSafetyStock
                               */
                               public void setIgnoreSafetyStock(boolean param){
                            
                                            this.localIgnoreSafetyStock=param;
                                    

                               }
                            

                        /**
                        * field for IncludeNegativeInventory
                        */

                        
                                    protected boolean localIncludeNegativeInventory =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("false");
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIncludeNegativeInventory(){
                               return localIncludeNegativeInventory;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IncludeNegativeInventory
                               */
                               public void setIncludeNegativeInventory(boolean param){
                            
                                            this.localIncludeNegativeInventory=param;
                                    

                               }
                            

                        /**
                        * field for OrderByLTD
                        */

                        
                                    protected boolean localOrderByLTD ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getOrderByLTD(){
                               return localOrderByLTD;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OrderByLTD
                               */
                               public void setOrderByLTD(boolean param){
                            
                                            this.localOrderByLTD=param;
                                    

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
                           namespacePrefix+":InventoryRequest",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InventoryRequest",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "FulfillerID", xmlWriter);
                                    System.out.println("right before local fulfillerId processing");
                             

                                          if (localFulfillerID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("FulfillerID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFulfillerID));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                            if (localCatalog==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Catalog cannot be null!!");
                                            }
                                           localCatalog.serialize(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Catalog"),
                                               xmlWriter);
                                        
                                            if (localQuantities==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Quantities cannot be null!!");
                                            }
                                           localQuantities.serialize(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Quantities"),
                                               xmlWriter);
                                        
                                    if (localLocationIDs==null){

                                        writeStartElement(null, "http://v4.core.coexprivate.api.shopatron.com", "LocationIDs", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localLocationIDs.serialize(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","LocationIDs"),
                                        xmlWriter);
                                    }
                                
                                    if (localLocation==null){

                                        writeStartElement(null, "http://v4.core.coexprivate.api.shopatron.com", "Location", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localLocation.serialize(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Location"),
                                        xmlWriter);
                                    }
                                
                                            if (localType==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Type cannot be null!!");
                                            }
                                           localType.serialize(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Type"),
                                               xmlWriter);
                                        
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "Limit", xmlWriter);
                             
                                               if (localLimit==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("Limit cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLimit));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "IgnoreSafetyStock", xmlWriter);
                             
                                               if (false) {
                                           
                                                         writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIgnoreSafetyStock));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "IncludeNegativeInventory", xmlWriter);
                             
                                               if (false) {
                                           
                                                         writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIncludeNegativeInventory));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://v4.core.coexprivate.api.shopatron.com";
                                    writeStartElement(null, namespace, "OrderByLTD", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("OrderByLTD cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderByLTD));
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
                                                                      "Catalog"));
                            
                            
                                    if (localCatalog==null){
                                         throw new org.apache.axis2.databinding.ADBException("Catalog cannot be null!!");
                                    }
                                    elementList.add(localCatalog);
                                
                            elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "Quantities"));
                            
                            
                                    if (localQuantities==null){
                                         throw new org.apache.axis2.databinding.ADBException("Quantities cannot be null!!");
                                    }
                                    elementList.add(localQuantities);
                                
                            elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "LocationIDs"));
                            
                            
                                    elementList.add(localLocationIDs==null?null:
                                    localLocationIDs);
                                
                            elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "Location"));
                            
                            
                                    elementList.add(localLocation==null?null:
                                    localLocation);
                                
                            elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "Type"));
                            
                            
                                    if (localType==null){
                                         throw new org.apache.axis2.databinding.ADBException("Type cannot be null!!");
                                    }
                                    elementList.add(localType);
                                
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "Limit"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLimit));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "IgnoreSafetyStock"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIgnoreSafetyStock));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "IncludeNegativeInventory"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIncludeNegativeInventory));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                                      "OrderByLTD"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderByLTD));
                            

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
        public static InventoryRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InventoryRequest object =
                new InventoryRequest();

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
                    
                            if (!"InventoryRequest".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InventoryRequest)com.shopatron.api.coexprivate.core.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    System.out.println("reached the parse method");
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","FulfillerID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"FulfillerID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFulfillerID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToPositiveInteger(content));
                                              System.out.println("setting the fulfillerId");
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Catalog").equals(reader.getName())){
                                    	System.out.println("setting the manucata");
                                                object.setCatalog(com.shopatron.api.coexprivate.core.v4.ManufacturerCatalog.Factory.parse(reader));
                                                System.out.println("out of manucata");
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement thinking " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Quantities").equals(reader.getName())){
                                    	System.out.println("setting Quantities");
                                                object.setQuantities(com.shopatron.api.coexprivate.core.v4.ArrayOf_impl_ItemQuantity.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","LocationIDs").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setLocationIDs(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setLocationIDs(com.shopatron.api.coexprivate.core.v4.ArrayOfLocationIDs.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Location").equals(reader.getName())){
                                    	System.out.println("going into Request Location factory parse");
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setLocation(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setLocation(com.shopatron.api.coexprivate.core.v4.RequestLocation.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Type").equals(reader.getName())){
                                    		System.out.println("going into inventoryrequest type");
                                                object.setType(com.shopatron.api.coexprivate.core.v4.InventoryRequestType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","Limit").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Limit" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    System.out.println("limits");
                                    
                                              object.setLimit(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","IgnoreSafetyStock").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                    System.out.println("ignoring safety stock");
                                              object.setIgnoreSafetyStock(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                            
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","IncludeNegativeInventory").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    System.out.println("negative inventory");
                                    
                                              object.setIncludeNegativeInventory(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                            
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com","OrderByLTD").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"OrderByLTD" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    System.out.println("setting the order by LTD");
                                              object.setOrderByLTD(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
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
           
    