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

func CreateFulfillerRequest(r io.Reader) (soap.FulfillerRequest, error) {
	var v soap.CreateFulfiller
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func CreateFulfillerResponse(r io.Reader) (string, error) {
	var v soap.CreateFulfillerResponse
	err := soap.Unmarshal(r, &v)
	return v.CreateFulfillerReturn, err
}
