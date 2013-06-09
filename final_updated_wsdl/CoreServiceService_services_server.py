##################################################
# CoreServiceService_services_server.py
#      Generated by ZSI.generate.wsdl2dispatch.ServiceModuleWriter
#
##################################################

from CoreServiceService_services import *
from ZSI.ServiceContainer import ServiceSOAPBinding

class CoreServiceService(ServiceSOAPBinding):
    soapAction = {}
    root = {}
    _wsdl = """<?xml version=\"1.0\" ?>
<!-- edited with XMLSpy v2013 sp1 (http://www.altova.com) by Dave Cumberland (Shopatron) --><wsdl:definitions targetNamespace=\"http://v4.core.coexprivate.api.shopatron.com\" xmlns:impl=\"http://v4.core.coexprivate.api.shopatron.com\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:wsdlsoap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">
	<wsdl:types>
		<schema elementFormDefault=\"qualified\" targetNamespace=\"http://v4.core.coexprivate.api.shopatron.com\" xmlns=\"http://www.w3.org/2001/XMLSchema\">
			<!-- header -->
			<!-- function -->
			<element name=\"createFulfiller\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:FulfillerRequest\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"createFulfillerResponse\">
				<complexType>
					<sequence>
						<element name=\"createFulfillerReturn\" type=\"xsd:int\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getFulfillerStatus\">
				<complexType>
					<sequence>
						<element name=\"fulfillerID\" type=\"xsd:int\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getFulfillerStatusResponse\">
				<complexType>
					<sequence>
						<element name=\"getFulfillerStatusReturn\" type=\"xsd:int\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"createFulfillmentLocation\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:FulfillmentLocation\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"createFulfillmentLocationResponse\">
				<complexType>
					<sequence>
						<element name=\"createFulfillmentLocationReturn\" type=\"xsd:int\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getFulfillmentLocations\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:OrderRequest\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getFulfillmentLocationsResponse\">
				<complexType>
					<sequence>
						<element maxOccurs=\"unbounded\" name=\"getFulfillmentLocationsReturn\" type=\"impl:AssignmentResponse\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getFulfillmentLocationTypes\">
				<complexType/>
			</element>
			<element name=\"getFulfillmentLocationTypesResponse\">
				<complexType>
					<sequence>
						<element maxOccurs=\"unbounded\" name=\"getFulfillmentLocationTypesReturn\" type=\"impl:FulfillmentLocationType\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"allocateInventory\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:UpdateRequest\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"allocateInventoryResponse\">
				<complexType/>
			</element>
			<element name=\"deallocateInventory\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:UpdateRequest\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"deallocateInventoryResponse\">
				<complexType/>
			</element>
			<element name=\"fulfillInventory\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:UpdateRequest\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"fulfillInventoryResponse\">
				<complexType/>
			</element>
			<element name=\"createBin\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:Bin\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"createBinResponse\">
				<complexType>
					<sequence>
						<element name=\"createBinReturn\" type=\"xsd:positiveInteger\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getBins\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:BinRequest\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getBinsResponse\">
				<complexType>
					<sequence>
						<element name=\"getBinsReturn\" type=\"impl:BinResponse\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getBinTypes\">
				<complexType/>
			</element>
			<element name=\"getBinTypesResponse\">
				<complexType>
					<sequence>
						<element maxOccurs=\"unbounded\" name=\"getBinTypesReturn\" type=\"impl:BinType\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getBinStatuses\">
				<complexType/>
			</element>
			<element name=\"getBinStatusesResponse\">
				<complexType>
					<sequence>
						<element maxOccurs=\"unbounded\" name=\"getBinStatusesReturn\" type=\"impl:BinStatus\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getInventory\">
				<complexType>
					<sequence>
						<element name=\"request\" type=\"impl:InventoryRequest\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"getInventoryResponse\">
				<complexType>
					<sequence>
						<element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"getInventoryReturn\" type=\"impl:InventoryResponse\"/>
					</sequence>
				</complexType>
			</element>
			<!-- Models -->
			<complexType name=\"AssignmentResponse\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"ExternalLocationID\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"Bin\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"BinID\" nillable=\"true\" type=\"xsd:positiveInteger\"/>
					<element name=\"ExternalLocationID\" type=\"xsd:string\"/>
					<element name=\"BinType\" type=\"xsd:string\"/>
					<element name=\"BinStatus\" type=\"xsd:string\"/>
					<element name=\"Name\" nillable=\"true\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"BinRequest\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"ExternalLocationID\" type=\"xsd:string\"/>
					<element name=\"SearchTerm\" nillable=\"true\" type=\"xsd:string\"/>
					<element name=\"NumResults\" nillable=\"true\" type=\"xsd:positiveInteger\"/>
					<element name=\"ResultsStart\" nillable=\"true\" type=\"xsd:positiveInteger\"/> <!-- pagination -->
				</sequence>
			</complexType>
			<complexType name=\"BinResponse\">
				<sequence>
					<element name=\"Bins\" nillable=\"false\" type=\"impl:ArrayOf_impl_Bin\"/>
					<element name=\"ResultCount\" nillable=\"false\" type=\"xsd:positiveInteger\"/>
				</sequence>
			</complexType>
			<complexType name=\"BinType\">
				<sequence>
					<element name=\"BinType\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"BinStatus\">
				<sequence>
					<element name=\"BinStatus\" nillable=\"false\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"FulfillerRequest\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"Name\" nillable=\"true\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"FulfillmentLocation\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"RetailerLocationID\" nillable=\"true\" type=\"xsd:positiveInteger\"/> <!-- internal location ID -->
					<element name=\"ExternalLocationID\" nillable=\"true\" type=\"xsd:string\"/>
					<element name=\"LocationName\" nillable=\"true\" type=\"xsd:string\"/>
					<element name=\"LocationType\" type=\"xsd:string\"/> <!-- see FulfillmentLocationIdentifierType -->
					<element name=\"Latitude\" type=\"xsd:double\"/>
					<element name=\"Longitude\" type=\"xsd:double\"/>
					<element name=\"Status\">
						<simpleType>
							<restriction base=\"xsd:int\">
								<enumeration value=\"1\">
									<annotation>
										<documentation>Location Active</documentation>
									</annotation>
								</enumeration>
								<enumeration value=\"2\">
									<annotation>
										<documentation>Location Not Active</documentation>
									</annotation>
								</enumeration>
							</restriction>
						</simpleType>
					</element>
					<element name=\"CountryCode\" nillable=\"true\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"FulfillmentLocationCatalog\">
				<sequence>
					<element name=\"ManufacturerCatalog\" nillable=\"true\" type=\"impl:ManufacturerCatalog\"/>
					<element name=\"ExternalLocationID\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<simpleType name=\"FulfillmentLocationIdentifierType\"> <!-- not used but documents FulfillmentLocation > Type -->
				<annotation>
					<documentation/>
				</annotation>
				<restriction base=\"xsd:string\">
					<enumeration value=\"FULFILLER\"/>
					<enumeration value=\"RETAILER\"/>
					<enumeration value=\"MANUFACTURER\"/>
				</restriction>
			</simpleType>
			<complexType name=\"FulfillmentLocationType\">
				<sequence>
					<element name=\"LocationType\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"InventorySearchRequest\">
				<sequence>
					<element name=\"FulfillerIDs\" type=\"impl:ArrayOf_xsd_FulfillerID\"/>
					<element name=\"LocationID\" nillable=\"true\" type=\"xsd:positiveInteger\"/> <!-- internal location ID -->
					<element name=\"PostalCode\" nillable=\"true\" type=\"xsd:string\"/>
					<element name=\"PartNumber\" type=\"xsd:string\"/> <!-- SKU, identical to the now removed LocationUPC -->
					<element name=\"UPC\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"InventorySearchResponse\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"ExternalLocationID\" type=\"xsd:string\"/>
					<element name=\"LocationName\" type=\"xsd:string\"/>
					<element name=\"PartNumber\" type=\"xsd:string\"/>
					<element name=\"UPC\" type=\"xsd:string\"/>
					<element name=\"Available\" type=\"xsd:int\"/>
					<element name=\"OnHand\" type=\"xsd:int\"/>
					<element name=\"Allocated\" type=\"xsd:int\"/>
					<element name=\"Distance\" type=\"xsd:double\"/>
				</sequence>
			</complexType>
			<complexType name=\"ItemQuantity\">
				<sequence>
					<element name=\"PartNumber\" type=\"xsd:string\"/>
					<element name=\"UPC\" type=\"xsd:string\"/>
					<element name=\"Quantity\" type=\"xsd:int\"/>
				</sequence>
			</complexType>
			<complexType name=\"ManufacturerCatalog\">
				<sequence>
					<element name=\"ManufacturerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"CatalogID\" type=\"xsd:positiveInteger\"/>
				</sequence>
			</complexType>
			<complexType name=\"OrderRequest\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"Catalog\" type=\"impl:ManufacturerCatalog\"/>
					<element name=\"Location\" nillable=\"true\" type=\"impl:RequestLocation\"/>
					<element name=\"MaxLocations\" nillable=\"true\" type=\"xsd:positiveInteger\"/>
				</sequence>
			</complexType>
			<complexType name=\"RequestLocation\">
				<sequence>
					<element default=\"MILES\" name=\"Unit\" nillable=\"true\" type=\"xsd:string\"/> <!-- see UnitType -->
					<element name=\"Radius\" nillable=\"true\" type=\"xsd:positiveInteger\"/>
					<element name=\"PostalCode\" nillable=\"true\" type=\"xsd:string\"/>
					<element name=\"Latitude\" nillable=\"true\" type=\"xsd:double\"/>
					<element name=\"Longitude\" nillable=\"true\" type=\"xsd:double\"/>
					<element name=\"CountryCode\" nillable=\"true\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"UpdateItem\">
				<sequence>
					<element name=\"PartNumber\" type=\"xsd:string\"/>
					<element name=\"UPC\" type=\"xsd:string\"/>
					<element name=\"Quantity\" type=\"xsd:int\"/>
					<!-- these next three items are 100% optional, and may be used for LTD calculation -->
					<element name=\"OrderID\" type=\"xsd:positiveInteger\"/> <!-- for a single Order -->
					<element name=\"OrderItemID\" type=\"xsd:positiveInteger\"/> <!-- for a single item in an Order, still globally unique -->
					<element name=\"ShipmentID\" type=\"xsd:positiveInteger\"/> <!-- group of Order Items -->
					<element name=\"ExternalLocationID\" nillable=\"true\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<complexType name=\"UpdateRequest\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"FulfillerLocationCatalog\" type=\"impl:FulfillmentLocationCatalog\"/>
					<element name=\"Items\" type=\"impl:ArrayOf_impl_UpdateItem\"/>
				</sequence>
			</complexType>
			<complexType name=\"RefreshItem\">
				<sequence>
					<element name=\"PartNumber\" type=\"xsd:string\"/>
					<element name=\"UPC\" type=\"xsd:string\"/>
					<element name=\"BinID\" type=\"xsd:int\"/>
					<element name=\"Quantity\" type=\"xsd:int\"/>
					<element name=\"LTD\" type=\"xsd:double\"/>
					<element name=\"SafetyStock\" type=\"xsd:int\"/>
				</sequence>
			</complexType>
			<complexType name=\"InventoryRequest\">
				<sequence>
					<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
					<element name=\"Catalog\" nillable=\"false\" type=\"impl:ManufacturerCatalog\"/>
					<element name=\"Quantities\" nillable=\"false\" type=\"impl:ArrayOf_impl_ItemQuantity\"/>
					<element name=\"LocationIDs\" nillable=\"true\" type=\"impl:ArrayOfLocationIDs\"/>
					<element name=\"Location\" nillable=\"true\" type=\"impl:RequestLocation\"/>
					<element minOccurs=\"1\" name=\"Type\" nillable=\"false\" type=\"impl:InventoryRequestType\"/>
					<element default=\"10000\" name=\"Limit\" type=\"xsd:int\"/> <!-- limit refers to the maximum amount of responses to send -->
					<element default=\"false\" name=\"IgnoreSafetyStock\" nillable=\"true\" type=\"xsd:boolean\"/>
					<element default=\"false\" name=\"IncludeNegativeInventory\" nillable=\"true\" type=\"xsd:boolean\"/>
					<element name=\"OrderByLTD\" type=\"boolean\"/>
				</sequence>
			</complexType>
			<complexType name=\"InventoryResponse\">
				<sequence>
					<element name=\"LocationName\" type=\"xsd:string\"/>
					<element name=\"CatalogID\" type=\"xsd:int\"/>
					<element name=\"ManufacturerID\" type=\"xsd:int\"/>
					<element name=\"OnHand\" type=\"xsd:int\"/>
					<element name=\"Available\" type=\"xsd:int\"/>
					<element name=\"PartNumber\" type=\"xsd:string\"/>
					<element name=\"UPC\" type=\"xsd:string\"/>
					<element name=\"LTD\" type=\"xsd:double\"/>
					<element name=\"SafetyStock\" type=\"xsd:int\"/>
					<element name=\"CountryCode\" type=\"xsd:string\"/>
					<element name=\"Distance\" type=\"xsd:double\"/>
				</sequence>
			</complexType>
			<simpleType name=\"InventoryRequestType\">
				<annotation>
					<documentation/>
				</annotation>
				<restriction base=\"xsd:string\">
					<enumeration value=\"ALL\"/>
					<enumeration value=\"PARTIAL\"/>
					<enumeration value=\"ANY\"/>
					<enumeration value=\"ALL_STORES\"/>
				</restriction>
			</simpleType>
			<!-- Arrays -->
			<complexType name=\"ArrayOf_xsd_FulfillerID\">
				<sequence>
					<element maxOccurs=\"unbounded\" minOccurs=\"1\" name=\"items\" type=\"xsd:positiveInteger\"/>
				</sequence>
			</complexType>
			<complexType name=\"ArrayOf_impl_ItemQuantity\">
				<sequence>
					<element maxOccurs=\"unbounded\" minOccurs=\"1\" name=\"items\" type=\"impl:ItemQuantity\"/>
				</sequence>
			</complexType>
			<complexType name=\"ArrayOf_impl_UpdateItem\">
				<sequence>
					<element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"items\" type=\"impl:UpdateItem\"/>
				</sequence>
			</complexType>
			<complexType name=\"ArrayOf_impl_Bin\">
				<sequence>
					<element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"items\" type=\"impl:Bin\"/>
				</sequence>
			</complexType>
			<complexType name=\"ArrayOf_impl_RefreshItem\">
				<sequence>
					<element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"items\" type=\"impl:RefreshItem\"/>
				</sequence>
			</complexType>
			<complexType name=\"ArrayOfLocationIDs\">
				<sequence>
					<element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"ExternalLocationID\" type=\"xsd:string\"/>
				</sequence>
			</complexType>
			<!--Inventory intake-->
			<!-- elements -->
			<element name=\"AdjustRequest\">
				<complexType>
					<sequence>
						<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
						<element name=\"ExternalLocationID\" type=\"xsd:string\"/>
						<element name=\"Items\" nillable=\"true\" type=\"impl:ArrayOf_impl_AdjustItem\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"AdjustResponse\" type=\"xsd:string\"/>
			<element name=\"RefreshRequest\">
				<complexType>
					<sequence>
						<element name=\"FulfillerID\" type=\"xsd:positiveInteger\"/>
						<element name=\"ExternalLocationID\" type=\"xsd:string\"/>
						<element name=\"Items\" nillable=\"true\" type=\"impl:ArrayOf_impl_RefreshItem\"/>
					</sequence>
				</complexType>
			</element>
			<element name=\"RefreshResponse\" type=\"xsd:string\"/>
			<!-- types -->
			<simpleType name=\"UnitType\"> <!-- not used but documents RequestLocation > Unit -->
				<annotation>
					<documentation/>
				</annotation>
				<restriction base=\"xsd:string\">
					<enumeration value=\"MILES\"/>
					<enumeration value=\"KM\"/>
				</restriction>
			</simpleType>
			<complexType name=\"AdjustItem\">
				<sequence>
					<element name=\"PartNumber\" type=\"xsd:string\"/>
					<element name=\"UPC\" type=\"xsd:string\"/>
					<element name=\"BinID\" type=\"xsd:int\"/>
					<element name=\"Quantity\" type=\"xsd:int\"/>
				</sequence>
			</complexType>
			<!-- arrays -->
			<complexType name=\"ArrayOf_impl_AdjustItem\">
				<sequence>
					<element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"items\" type=\"impl:AdjustItem\"/>
				</sequence>
			</complexType>
			<!--Inventory intake complete-->
		</schema>
	</wsdl:types>
	<wsdl:message name=\"createFulfillerRequest\">
		<wsdl:part element=\"impl:createFulfiller\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"createFulfillerResponse\">
		<wsdl:part element=\"impl:createFulfillerResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getFulfillerStatusRequest\">
		<wsdl:part element=\"impl:getFulfillerStatus\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getFulfillerStatusResponse\">
		<wsdl:part element=\"impl:getFulfillerStatusResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"createFulfillmentLocationRequest\">
		<wsdl:part element=\"impl:createFulfillmentLocation\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"createFulfillmentLocationResponse\">
		<wsdl:part element=\"impl:createFulfillmentLocationResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getFulfillmentLocationsResponse\">
		<wsdl:part element=\"impl:getFulfillmentLocationsResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getFulfillmentLocationsRequest\">
		<wsdl:part element=\"impl:getFulfillmentLocations\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getFulfillmentLocationTypesResponse\">
		<wsdl:part element=\"impl:getFulfillmentLocationTypesResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getFulfillmentLocationTypesRequest\">
		<wsdl:part element=\"impl:getFulfillmentLocationTypes\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"allocateInventoryRequest\">
		<wsdl:part element=\"impl:allocateInventory\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"allocateInventoryResponse\">
		<wsdl:part element=\"impl:allocateInventoryResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"deallocateInventoryRequest\">
		<wsdl:part element=\"impl:deallocateInventory\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"deallocateInventoryResponse\">
		<wsdl:part element=\"impl:deallocateInventoryResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"fulfillInventoryRequest\">
		<wsdl:part element=\"impl:fulfillInventory\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"fulfillInventoryResponse\">
		<wsdl:part element=\"impl:fulfillInventoryResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"createBinRequest\">
		<wsdl:part element=\"impl:createBin\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"createBinResponse\">
		<wsdl:part element=\"impl:createBinResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getBinsRequest\">
		<wsdl:part element=\"impl:getBins\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getBinsResponse\">
		<wsdl:part element=\"impl:getBinsResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getBinTypesRequest\">
		<wsdl:part element=\"impl:getBinTypes\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getBinTypesResponse\">
		<wsdl:part element=\"impl:getBinTypesResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getBinStatusesRequest\">
		<wsdl:part element=\"impl:getBinStatuses\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getBinStatusesResponse\">
		<wsdl:part element=\"impl:getBinStatusesResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getInventoryRequest\">
		<wsdl:part element=\"impl:getInventory\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"getInventoryResponse\">
		<wsdl:part element=\"impl:getInventoryResponse\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"AdjustInventorySoapIn\">
		<wsdl:part element=\"impl:AdjustRequest\" name=\"parameter\"/>
	</wsdl:message>
	<wsdl:message name=\"AdjustInventorySoapOut\">
		<wsdl:part element=\"impl:AdjustResponse\" name=\"parameter\"/>
	</wsdl:message>
	<wsdl:message name=\"RefreshInventorySoapIn\">
		<wsdl:part element=\"impl:RefreshRequest\" name=\"parameters\"/>
	</wsdl:message>
	<wsdl:message name=\"RefreshInventorySoapOut\">
		<wsdl:part element=\"impl:RefreshResponse\" name=\"parameter\"/>
	</wsdl:message>
	<wsdl:portType name=\"CoreService\">
		<wsdl:operation name=\"createFulfiller\">
			<wsdl:documentation>Request to create a single fulfiller which will later contain one or more locations</wsdl:documentation>
			<wsdl:input message=\"impl:createFulfillerRequest\" name=\"createFulfillerRequest\"/>
			<wsdl:output message=\"impl:createFulfillerResponse\" name=\"createFulfillerResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"getFulfillerStatus\">
			<wsdl:documentation>Data retrieval to check a fulfiller's status</wsdl:documentation>
			<wsdl:input message=\"impl:getFulfillerStatusRequest\" name=\"getFulfillerStatusRequest\"/>
			<wsdl:output message=\"impl:getFulfillerStatusResponse\" name=\"getFulfillerStatusResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"createFulfillmentLocation\">
			<wsdl:input message=\"impl:createFulfillmentLocationRequest\" name=\"createFulfillmentLocationRequest\"/>
			<wsdl:output message=\"impl:createFulfillmentLocationResponse\" name=\"createFulfillmentLocationResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"getFulfillmentLocations\">
			<wsdl:input message=\"impl:getFulfillmentLocationsRequest\" name=\"getFulfillmentLocationsRequest\"/>
			<wsdl:output message=\"impl:getFulfillmentLocationsResponse\" name=\"getFulfillmentLocationsResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"getFulfillmentLocationTypes\">
			<wsdl:input message=\"impl:getFulfillmentLocationTypesRequest\" name=\"getFulfillmentLocationTypesRequest\"/>
			<wsdl:output message=\"impl:getFulfillmentLocationTypesResponse\" name=\"getFulfillmentLocationTypesResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"allocateInventory\">
			<wsdl:input message=\"impl:allocateInventoryRequest\" name=\"allocateInventoryRequest\"/>
			<wsdl:output message=\"impl:allocateInventoryResponse\" name=\"allocateInventoryResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"deallocateInventory\">
			<wsdl:input message=\"impl:deallocateInventoryRequest\" name=\"deallocateInventoryRequest\"/>
			<wsdl:output message=\"impl:deallocateInventoryResponse\" name=\"deallocateInventoryResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"fulfillInventory\">
			<wsdl:input message=\"impl:fulfillInventoryRequest\" name=\"fulfillInventoryRequest\"/>
			<wsdl:output message=\"impl:fulfillInventoryResponse\" name=\"fulfillInventoryResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"createBin\">
			<wsdl:input message=\"impl:createBinRequest\" name=\"createBinRequest\"/>
			<wsdl:output message=\"impl:createBinResponse\" name=\"createBinResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"getBins\">
			<wsdl:input message=\"impl:getBinsRequest\" name=\"getBinsRequest\"/>
			<wsdl:output message=\"impl:getBinsResponse\" name=\"getBinsResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"getBinTypes\">
			<wsdl:input message=\"impl:getBinTypesRequest\" name=\"getBinTypesRequest\"/>
			<wsdl:output message=\"impl:getBinTypesResponse\" name=\"getBinTypesResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"getBinStatuses\">
			<wsdl:input message=\"impl:getBinStatusesRequest\" name=\"getBinStatusesRequest\"/>
			<wsdl:output message=\"impl:getBinStatusesResponse\" name=\"getBinStatusesResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"getInventory\">
			<wsdl:input message=\"impl:getInventoryRequest\" name=\"getInventoryRequest\"/>
			<wsdl:output message=\"impl:getInventoryResponse\" name=\"getInventoryResponse\"/>
		</wsdl:operation>
		<wsdl:operation name=\"adjustInventory\">
			<wsdl:input message=\"impl:AdjustInventorySoapIn\"/>
			<wsdl:output message=\"impl:AdjustInventorySoapOut\"/>
		</wsdl:operation>
		<wsdl:operation name=\"refreshInventory\">
			<wsdl:input message=\"impl:RefreshInventorySoapIn\" name=\"refreshInventoryRequest2\"/>
			<wsdl:output message=\"impl:RefreshInventorySoapOut\" name=\"refreshInventoryResponse2\"/>
		</wsdl:operation>
	</wsdl:portType>
	<wsdl:binding name=\"CoreServiceSoapBinding\" type=\"impl:CoreService\">
		<wsdlsoap:binding style=\"document\" transport=\"http://schemas.xmlsoap.org/soap/http\"/>
		<wsdl:operation name=\"createFulfiller\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"createFulfillerRequest\">
				<wsdlsoap:header message=\"impl:createFulfillerRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"createFulfillerResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"getFulfillerStatus\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"getFulfillerStatusRequest\">
				<wsdlsoap:header message=\"impl:getFulfillerStatusRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"getFulfillerStatusResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"createFulfillmentLocation\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"createFulfillmentLocationRequest\">
				<wsdlsoap:header message=\"impl:createFulfillmentLocationRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"createFulfillmentLocationResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"getFulfillmentLocations\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"getFulfillmentLocationsRequest\">
				<wsdlsoap:header message=\"impl:getFulfillmentLocationsRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"getFulfillmentLocationsResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"getFulfillmentLocationTypes\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"getFulfillmentLocationTypesRequest\">
				<wsdlsoap:header message=\"impl:getFulfillmentLocationTypesRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"getFulfillmentLocationTypesResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"allocateInventory\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"allocateInventoryRequest\">
				<wsdlsoap:header message=\"impl:allocateInventoryRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"allocateInventoryResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"deallocateInventory\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"deallocateInventoryRequest\">
				<wsdlsoap:header message=\"impl:deallocateInventoryRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"deallocateInventoryResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"fulfillInventory\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"fulfillInventoryRequest\">
				<wsdlsoap:header message=\"impl:fulfillInventoryRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"fulfillInventoryResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"createBin\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"createBinRequest\">
				<wsdlsoap:header message=\"impl:createBinRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"createBinResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"getBins\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"getBinsRequest\">
				<wsdlsoap:header message=\"impl:getBinsRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"getBinsResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"getBinTypes\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"getBinTypesRequest\">
				<wsdlsoap:header message=\"impl:getBinTypesRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"getBinTypesResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"getBinStatuses\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"getBinStatusesRequest\">
				<wsdlsoap:header message=\"impl:getBinStatusesRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"getBinStatusesResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"getInventory\">
			<wsdlsoap:operation soapAction=\"\"/>
			<wsdl:input name=\"getInventoryRequest\">
				<wsdlsoap:header message=\"impl:getInventoryRequest\" use=\"literal\"/>
				<wsdlsoap:body parts=\"parameters\" use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"getInventoryResponse\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"adjustInventory\">
			<wsdlsoap:operation soapAction=\"urn:#NewOperation\"/>
			<wsdl:input>
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:input>
			<wsdl:output>
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
		<wsdl:operation name=\"refreshInventory\">
			<wsdlsoap:operation soapAction=\"urn:#NewOperation\"/>
			<wsdl:input name=\"refreshInventoryRequest2\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:input>
			<wsdl:output name=\"refreshInventoryResponse2\">
				<wsdlsoap:body use=\"literal\"/>
			</wsdl:output>
		</wsdl:operation>
	</wsdl:binding>
	<wsdl:service name=\"CoreServiceService\">
		<wsdl:port binding=\"impl:CoreServiceSoapBinding\" name=\"CoreService\">
			<wsdlsoap:address location=\"http://localhost/inventoryService/\"/>
		</wsdl:port>
	</wsdl:service>
	<!-- Services -->
	<!-- Soap Binding -->
</wsdl:definitions>"""

    def __init__(self, post='/inventoryService/', **kw):
        ServiceSOAPBinding.__init__(self, post)

    def soap_createFulfiller(self, ps):
        self.request = ps.Parse(createFulfillerRequest.typecode)
        return createFulfillerResponse()

    soapAction[''] = 'soap_createFulfiller'
    root[(createFulfillerRequest.typecode.nspname,createFulfillerRequest.typecode.pname)] = 'soap_createFulfiller'

    def soap_getFulfillerStatus(self, ps):
        self.request = ps.Parse(getFulfillerStatusRequest.typecode)
        return getFulfillerStatusResponse()

    soapAction[''] = 'soap_getFulfillerStatus'
    root[(getFulfillerStatusRequest.typecode.nspname,getFulfillerStatusRequest.typecode.pname)] = 'soap_getFulfillerStatus'

    def soap_createFulfillmentLocation(self, ps):
        self.request = ps.Parse(createFulfillmentLocationRequest.typecode)
        return createFulfillmentLocationResponse()

    soapAction[''] = 'soap_createFulfillmentLocation'
    root[(createFulfillmentLocationRequest.typecode.nspname,createFulfillmentLocationRequest.typecode.pname)] = 'soap_createFulfillmentLocation'

    def soap_getFulfillmentLocations(self, ps):
        self.request = ps.Parse(getFulfillmentLocationsRequest.typecode)
        return getFulfillmentLocationsResponse()

    soapAction[''] = 'soap_getFulfillmentLocations'
    root[(getFulfillmentLocationsRequest.typecode.nspname,getFulfillmentLocationsRequest.typecode.pname)] = 'soap_getFulfillmentLocations'

    def soap_getFulfillmentLocationTypes(self, ps):
        self.request = ps.Parse(getFulfillmentLocationTypesRequest.typecode)
        return getFulfillmentLocationTypesResponse()

    soapAction[''] = 'soap_getFulfillmentLocationTypes'
    root[(getFulfillmentLocationTypesRequest.typecode.nspname,getFulfillmentLocationTypesRequest.typecode.pname)] = 'soap_getFulfillmentLocationTypes'

    def soap_allocateInventory(self, ps):
        self.request = ps.Parse(allocateInventoryRequest.typecode)
        return allocateInventoryResponse()

    soapAction[''] = 'soap_allocateInventory'
    root[(allocateInventoryRequest.typecode.nspname,allocateInventoryRequest.typecode.pname)] = 'soap_allocateInventory'

    def soap_deallocateInventory(self, ps):
        self.request = ps.Parse(deallocateInventoryRequest.typecode)
        return deallocateInventoryResponse()

    soapAction[''] = 'soap_deallocateInventory'
    root[(deallocateInventoryRequest.typecode.nspname,deallocateInventoryRequest.typecode.pname)] = 'soap_deallocateInventory'

    def soap_fulfillInventory(self, ps):
        self.request = ps.Parse(fulfillInventoryRequest.typecode)
        return fulfillInventoryResponse()

    soapAction[''] = 'soap_fulfillInventory'
    root[(fulfillInventoryRequest.typecode.nspname,fulfillInventoryRequest.typecode.pname)] = 'soap_fulfillInventory'

    def soap_createBin(self, ps):
        self.request = ps.Parse(createBinRequest.typecode)
        return createBinResponse()

    soapAction[''] = 'soap_createBin'
    root[(createBinRequest.typecode.nspname,createBinRequest.typecode.pname)] = 'soap_createBin'

    def soap_getBins(self, ps):
        self.request = ps.Parse(getBinsRequest.typecode)
        return getBinsResponse()

    soapAction[''] = 'soap_getBins'
    root[(getBinsRequest.typecode.nspname,getBinsRequest.typecode.pname)] = 'soap_getBins'

    def soap_getBinTypes(self, ps):
        self.request = ps.Parse(getBinTypesRequest.typecode)
        return getBinTypesResponse()

    soapAction[''] = 'soap_getBinTypes'
    root[(getBinTypesRequest.typecode.nspname,getBinTypesRequest.typecode.pname)] = 'soap_getBinTypes'

    def soap_getBinStatuses(self, ps):
        self.request = ps.Parse(getBinStatusesRequest.typecode)
        return getBinStatusesResponse()

    soapAction[''] = 'soap_getBinStatuses'
    root[(getBinStatusesRequest.typecode.nspname,getBinStatusesRequest.typecode.pname)] = 'soap_getBinStatuses'

    def soap_getInventory(self, ps):
        self.request = ps.Parse(getInventoryRequest.typecode)
        return getInventoryResponse()

    soapAction[''] = 'soap_getInventory'
    root[(getInventoryRequest.typecode.nspname,getInventoryRequest.typecode.pname)] = 'soap_getInventory'

    def soap_adjustInventory(self, ps):
        self.request = ps.Parse(AdjustInventorySoapIn.typecode)
        return AdjustInventorySoapOut()

    soapAction['urn:#NewOperation'] = 'soap_adjustInventory'
    root[(AdjustInventorySoapIn.typecode.nspname,AdjustInventorySoapIn.typecode.pname)] = 'soap_adjustInventory'

    def soap_refreshInventory(self, ps):
        self.request = ps.Parse(RefreshInventorySoapIn.typecode)
        return RefreshInventorySoapOut()

    soapAction['urn:#NewOperation'] = 'soap_refreshInventory'
    root[(RefreshInventorySoapIn.typecode.nspname,RefreshInventorySoapIn.typecode.pname)] = 'soap_refreshInventory'

