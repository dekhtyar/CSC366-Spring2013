package input

import (
	"apersci/soap"
	"io"
)

// Request:
//<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//   <soapenv:Header/>
//   <soapenv:Body>
//		 <v4:allocateInventory>
//			 <v4:request>
//				 <v4:FulfillerID>?</v4:FulfillerID>
//				 <v4:FulfillerLocationCatalog>
//					<v4:ManufacturerCatalog>
//						<v4:ManufacturerID>?</v4:ManufacturerID>
//						<v4:CatalogID>?</v4:CatalogID>
//					</v4:ManufacturerCatalog>
//					<v4:FulfillerLocationID>?</v4:FulfillerLocationID>
//				  </v4:FulfillerLocationCatalog>
//				<v4:Items>
//					<!--Zero or more repetitions:-->
//					<v4:items>
//						<v4:PartNumber>?</v4:PartNumber>
//						<v4:UPC>?</v4:UPC>
//						<v4:Quantity>?</v4:Quantity>
//						<v4:FulfillerLocationID>?</v4:FulfillerLocationID>
//					</v4:items>
//				</v4:Items>
//			</v4:request>
//		</v4:allocateInventory>
//	</soapenv:Body>
//</soapenv:Envelope>
//
// Response:
//<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//	<soapenv:Header/>
//	<soapenv:Body>
//		<v4:allocateInventoryResponse/>
//	</soapenv:Body>
//</soapenv:Envelope>

func CreateAllocateInventoryRequest(r io.Reader) (soap.UpdateRequest, error) {
	var v soap.AllocateInventory
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func CreateAllocateInventoryResponse(r io.Reader) error {
	return nil
}
