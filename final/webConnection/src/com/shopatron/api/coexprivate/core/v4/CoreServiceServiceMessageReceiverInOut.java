
/**
 * CoreServiceServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.shopatron.api.coexprivate.core.v4;

        /**
        *  CoreServiceServiceMessageReceiverInOut message receiver
        */

        public class CoreServiceServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        CoreServiceServiceSkeletonInterface skel = (CoreServiceServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("getInventory".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.GetInventoryResponse getInventoryResponse31 = null;
	                        com.shopatron.api.coexprivate.core.v4.GetInventory wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.GetInventory)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.GetInventory.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getInventoryResponse31 =
                                                   
                                                   
                                                         skel.getInventory(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getInventoryResponse31, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "getInventory"));
                                    } else 

            if("createFulfillmentLocation".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse createFulfillmentLocationResponse33 = null;
	                        com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createFulfillmentLocationResponse33 =
                                                   
                                                   
                                                         skel.createFulfillmentLocation(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createFulfillmentLocationResponse33, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "createFulfillmentLocation"));
                                    } else 

            if("refreshInventory".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.RefreshResponse refreshResponse35 = null;
	                        com.shopatron.api.coexprivate.core.v4.RefreshRequest wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.RefreshRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.RefreshRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               refreshResponse35 =
                                                   
                                                   
                                                         skel.refreshInventory(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), refreshResponse35, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "refreshInventory"));
                                    } else 

            if("getBins".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.GetBinsResponse getBinsResponse37 = null;
	                        com.shopatron.api.coexprivate.core.v4.GetBins wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.GetBins)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.GetBins.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getBinsResponse37 =
                                                   
                                                   
                                                         skel.getBins(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getBinsResponse37, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "getBins"));
                                    } else 

            if("createFulfiller".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse createFulfillerResponse39 = null;
	                        com.shopatron.api.coexprivate.core.v4.CreateFulfiller wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.CreateFulfiller)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.CreateFulfiller.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createFulfillerResponse39 =
                                                   
                                                   
                                                         skel.createFulfiller(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createFulfillerResponse39, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "createFulfiller"));
                                    } else 

            if("getFulfillmentLocationTypes".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse getFulfillmentLocationTypesResponse41 = null;
	                        com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getFulfillmentLocationTypesResponse41 =
                                                   
                                                   
                                                         skel.getFulfillmentLocationTypes(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getFulfillmentLocationTypesResponse41, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "getFulfillmentLocationTypes"));
                                    } else 

            if("fulfillInventory".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse fulfillInventoryResponse43 = null;
	                        com.shopatron.api.coexprivate.core.v4.FulfillInventory wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.FulfillInventory)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.FulfillInventory.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               fulfillInventoryResponse43 =
                                                   
                                                   
                                                         skel.fulfillInventory(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), fulfillInventoryResponse43, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "fulfillInventory"));
                                    } else 

            if("getFulfillerStatus".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse getFulfillerStatusResponse45 = null;
	                        com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getFulfillerStatusResponse45 =
                                                   
                                                   
                                                         skel.getFulfillerStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getFulfillerStatusResponse45, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "getFulfillerStatus"));
                                    } else 

            if("allocateInventory".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse allocateInventoryResponse47 = null;
	                        com.shopatron.api.coexprivate.core.v4.AllocateInventory wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.AllocateInventory)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.AllocateInventory.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               allocateInventoryResponse47 =
                                                   
                                                   
                                                         skel.allocateInventory(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), allocateInventoryResponse47, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "allocateInventory"));
                                    } else 

            if("createBin".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.CreateBinResponse createBinResponse49 = null;
	                        com.shopatron.api.coexprivate.core.v4.CreateBin wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.CreateBin)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.CreateBin.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createBinResponse49 =
                                                   
                                                   
                                                         skel.createBin(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createBinResponse49, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "createBin"));
                                    } else 

            if("getFulfillmentLocations".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse getFulfillmentLocationsResponse51 = null;
	                        com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getFulfillmentLocationsResponse51 =
                                                   
                                                   
                                                         skel.getFulfillmentLocations(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getFulfillmentLocationsResponse51, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "getFulfillmentLocations"));
                                    } else 

            if("getBinStatuses".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse getBinStatusesResponse53 = null;
	                        com.shopatron.api.coexprivate.core.v4.GetBinStatuses wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.GetBinStatuses)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.GetBinStatuses.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getBinStatusesResponse53 =
                                                   
                                                   
                                                         skel.getBinStatuses(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getBinStatusesResponse53, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "getBinStatuses"));
                                    } else 

            if("adjustInventory".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.AdjustResponse adjustResponse55 = null;
	                        com.shopatron.api.coexprivate.core.v4.AdjustRequest wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.AdjustRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.AdjustRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               adjustResponse55 =
                                                   
                                                   
                                                         skel.adjustInventory(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), adjustResponse55, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "adjustInventory"));
                                    } else 

            if("getBinTypes".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse getBinTypesResponse57 = null;
	                        com.shopatron.api.coexprivate.core.v4.GetBinTypes wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.GetBinTypes)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.GetBinTypes.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getBinTypesResponse57 =
                                                   
                                                   
                                                         skel.getBinTypes(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getBinTypesResponse57, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "getBinTypes"));
                                    } else 

            if("deallocateInventory".equals(methodName)){
                
                com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse deallocateInventoryResponse59 = null;
	                        com.shopatron.api.coexprivate.core.v4.DeallocateInventory wrappedParam =
                                                             (com.shopatron.api.coexprivate.core.v4.DeallocateInventory)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.shopatron.api.coexprivate.core.v4.DeallocateInventory.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deallocateInventoryResponse59 =
                                                   
                                                   
                                                         skel.deallocateInventory(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deallocateInventoryResponse59, false, new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com",
                                                    "deallocateInventory"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetInventory param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetInventory.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetInventoryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetInventoryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.RefreshRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.RefreshRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.RefreshResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.RefreshResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetBins param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBins.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetBinsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBinsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.CreateFulfiller param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateFulfiller.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.FulfillInventory param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.FulfillInventory.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.AllocateInventory param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.AllocateInventory.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.CreateBin param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateBin.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.CreateBinResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateBinResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetBinStatuses param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBinStatuses.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.AdjustRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.AdjustRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.AdjustResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.AdjustResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetBinTypes param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBinTypes.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.DeallocateInventory param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.DeallocateInventory.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.GetInventoryResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetInventoryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.GetInventoryResponse wrapgetInventory(){
                                com.shopatron.api.coexprivate.core.v4.GetInventoryResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.GetInventoryResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse wrapcreateFulfillmentLocation(){
                                com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.RefreshResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.RefreshResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.RefreshResponse wraprefreshInventory(){
                                com.shopatron.api.coexprivate.core.v4.RefreshResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.RefreshResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.GetBinsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBinsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.GetBinsResponse wrapgetBins(){
                                com.shopatron.api.coexprivate.core.v4.GetBinsResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.GetBinsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse wrapcreateFulfiller(){
                                com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse wrapgetFulfillmentLocationTypes(){
                                com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse wrapfulfillInventory(){
                                com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse wrapgetFulfillerStatus(){
                                com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse wrapallocateInventory(){
                                com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.CreateBinResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.CreateBinResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.CreateBinResponse wrapcreateBin(){
                                com.shopatron.api.coexprivate.core.v4.CreateBinResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.CreateBinResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse wrapgetFulfillmentLocations(){
                                com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse wrapgetBinStatuses(){
                                com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.AdjustResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.AdjustResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.AdjustResponse wrapadjustInventory(){
                                com.shopatron.api.coexprivate.core.v4.AdjustResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.AdjustResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse wrapgetBinTypes(){
                                com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse wrapdeallocateInventory(){
                                com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse wrappedElement = new com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.shopatron.api.coexprivate.core.v4.GetInventory.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetInventory.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetInventoryResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetInventoryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocation.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.CreateFulfillmentLocationResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.RefreshRequest.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.RefreshRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.RefreshResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.RefreshResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetBins.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetBins.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetBinsResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetBinsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.CreateFulfiller.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.CreateFulfiller.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.CreateFulfillerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypes.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationTypesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.FulfillInventory.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.FulfillInventory.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.FulfillInventoryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetFulfillerStatus.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetFulfillerStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.AllocateInventory.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.AllocateInventory.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.AllocateInventoryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.CreateBin.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.CreateBin.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.CreateBinResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.CreateBinResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocations.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetFulfillmentLocationsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetBinStatuses.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetBinStatuses.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetBinStatusesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.AdjustRequest.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.AdjustRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.AdjustResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.AdjustResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetBinTypes.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetBinTypes.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.GetBinTypesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.DeallocateInventory.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.DeallocateInventory.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse.class.equals(type)){
                
                           return com.shopatron.api.coexprivate.core.v4.DeallocateInventoryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    