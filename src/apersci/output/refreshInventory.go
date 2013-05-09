package output

import (
	"apersci/soap"
	"io"
)

// Request:
//
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//    <soapenv:Header/>
//    <soapenv:Body>
//       <v4:RefreshRequest>
//          <v4:FulfillerID>?</v4:FulfillerID>
//          <v4:LocationName>?</v4:LocationName>
//          <v4:Items>
//             <!--Zero or more repetitions:-->
//             <v4:items>
//                <v4:PartNumber>?</v4:PartNumber>
//                <v4:UPC>?</v4:UPC>
//                <v4:BinID>?</v4:BinID>
//                <v4:Quantity>?</v4:Quantity>
//                <v4:LTD>?</v4:LTD>
//                <v4:SafetyStock>?</v4:SafetyStock>
//             </v4:items>
//          </v4:Items>
//       </v4:RefreshRequest>
//    </soapenv:Body>
// </soapenv:Envelope>
//
// Response:
//
// <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
//    <soapenv:Header/>
//    <soapenv:Body>
//       <v4:RefreshResponse>?</v4:RefreshResponse>
//    </soapenv:Body>
// </soapenv:Envelope>

func RefreshInventoryRequest(w io.Writer, r soap.RefreshRequest) error {
	return templates.ExecuteTemplate(w, "request-refreshRequest.xml", r)
}

func RefreshInventoryResponse(w io.Writer, r string) error {
	return templates.ExecuteTemplate(w, "response-refreshRequest.xml", r)
}
