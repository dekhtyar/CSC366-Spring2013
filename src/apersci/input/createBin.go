package input

import (
	"apersci/soap"
)

// Request:
//
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//    <soapenv:Header/>
//    <soapenv:Body>
//       <v4:createBin>
//          <v4:request>
//             <v4:FulfillerID>?</v4:FulfillerID>
//             <v4:BinID>?</v4:BinID>
//             <v4:FulfillerLocationID>?</v4:FulfillerLocationID>
//             <v4:BinType>?</v4:BinType>
//             <v4:BinStatus>?</v4:BinStatus>
//             <v4:Name>?</v4:Name>
//          </v4:request>
//       </v4:createBin>
//    </soapenv:Body>
// </soapenv:Envelope>
//
// Response:
//
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//    <soapenv:Header/>
//    <soapenv:Body>
//       <v4:createBinResponse>
//          <v4:createBinReturn>?</v4:createBinReturn>
//       </v4:createBinResponse>
//    </soapenv:Body>
// </soapenv:Envelope

func CreateBinRequest(data []byte) (soap.Bin, error) {
	var r soap.CreateBin
	err := soap.Unmarshal(data, &r)
	return r.Request, err
}

func CreateBinResponse(data []byte) (string, error) {
	var r soap.CreateBinResponse
	err := soap.Unmarshal(data, &r)
	return r.CreateBinReturn, err
}
