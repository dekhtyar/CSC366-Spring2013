package input

import (
	"apersci/soap"
	"io"
)

// Request:
//
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//    <soapenv:Header/>
//    <soapenv:Body>
//       <v4:createFulfillmentLocation>
//          <v4:request>
//             <v4:FulfillerID>?</v4:FulfillerID>
//             <v4:RetailerLocationID>?</v4:RetailerLocationID>
//             <v4:ExternalLocationID>?</v4:ExternalLocationID>
//             <v4:LocationName>?</v4:LocationName>
//             <v4:LocationType>?</v4:LocationType>
//             <v4:Latitude>?</v4:Latitude>
//             <v4:Longitude>?</v4:Longitude>
//             <v4:Status>?</v4:Status>
//             <v4:CountryCode>?</v4:CountryCode>
//          </v4:request>
//       </v4:createFulfillmentLocation>
//    </soapenv:Body>
// </soapenv:Envelope>
//
// Response:
//
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//    <soapenv:Header/>
//    <soapenv:Body>
//       <v4:createFulfillmentLocationResponse>
//          <v4:createFulfillmentLocationReturn>?</v4:createFulfillmentLocationReturn>
//       </v4:createFulfillmentLocationResponse>
//    </soapenv:Body>
// </soapenv:Envelope>

func CreateFulfillmentLocationRequest(r io.Reader) (soap.FulfillmentLocation, error) {
	var v soap.CreateFulfillmentLocation
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func CreateFulfillmentLocationResponse(r io.Reader) (string, error) {
	var v soap.CreateFulfillmentLocationResponse
	err := soap.Unmarshal(r, &v)
	return v.CreateFulfillmentLocationReturn, err
}
