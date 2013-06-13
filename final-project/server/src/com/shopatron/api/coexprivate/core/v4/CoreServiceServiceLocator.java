/**
 * CoreServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shopatron.api.coexprivate.core.v4;

public class CoreServiceServiceLocator extends org.apache.axis.client.Service implements com.shopatron.api.coexprivate.core.v4.CoreServiceService {

    public CoreServiceServiceLocator() {
    }


    public CoreServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CoreServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CoreService
    private java.lang.String CoreService_address = "http://localhost/inventoryService/";

    public java.lang.String getCoreServiceAddress() {
        return CoreService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CoreServiceWSDDServiceName = "CoreService";

    public java.lang.String getCoreServiceWSDDServiceName() {
        return CoreServiceWSDDServiceName;
    }

    public void setCoreServiceWSDDServiceName(java.lang.String name) {
        CoreServiceWSDDServiceName = name;
    }

    public com.shopatron.api.coexprivate.core.v4.CoreService getCoreService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CoreService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCoreService(endpoint);
    }

    public com.shopatron.api.coexprivate.core.v4.CoreService getCoreService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.shopatron.api.coexprivate.core.v4.CoreServiceSoapBindingStub _stub = new com.shopatron.api.coexprivate.core.v4.CoreServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCoreServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCoreServiceEndpointAddress(java.lang.String address) {
        CoreService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.shopatron.api.coexprivate.core.v4.CoreService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.shopatron.api.coexprivate.core.v4.CoreServiceSoapBindingStub _stub = new com.shopatron.api.coexprivate.core.v4.CoreServiceSoapBindingStub(new java.net.URL(CoreService_address), this);
                _stub.setPortName(getCoreServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CoreService".equals(inputPortName)) {
            return getCoreService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "CoreServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://v4.core.coexprivate.api.shopatron.com", "CoreService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CoreService".equals(portName)) {
            setCoreServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
