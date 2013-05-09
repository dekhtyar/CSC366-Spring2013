package input

import (
	"apersci/soap"
)

// Request:
//
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//    <soapenv:Header/>
//    <soapenv:Body>
//       <v4:createFulfiller>
//          <v4:request>
//             <v4:FulfillerID>?</v4:FulfillerID>
//             <v4:Name>?</v4:Name>
//          </v4:request>
//       </v4:createFulfiller>
//    </soapenv:Body>
// </soapenv:Envelope>
//
// Response:
//
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//    <soapenv:Header/>
//    <soapenv:Body>
//       <v4:createFulfillerResponse>
//          <v4:createFulfillerReturn>?</v4:createFulfillerReturn>
//       </v4:createFulfillerResponse>
//    </soapenv:Body>
// </soapenv:Envelope>

func CreateFulfillerRequest(data []byte) (soap.FulfillerRequest, error) {
	var r soap.CreateFulfiller
	err := soap.Unmarshal(data, &r)
	return r.Request, err
}

func CreateFulfillerResponse(data []byte) (string, error) {
	var r soap.CreateFulfillerResponse
	err := soap.Unmarshal(data, &r)
	return r.CreateFulfillerReturn, err
}
