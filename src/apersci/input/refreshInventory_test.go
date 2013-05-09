package input

import (
	"testing"
)

const testRequestInput = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:RefreshRequest>
         <v4:FulfillerID>1</v4:FulfillerID>
         <v4:LocationName>LocationName</v4:LocationName>
         <v4:Items>
            <!--Zero or more repetitions:-->
            <v4:items>
               <v4:PartNumber>PartNumber1</v4:PartNumber>
               <v4:UPC>UPC1</v4:UPC>
               <v4:BinID>2</v4:BinID>
               <v4:Quantity>3</v4:Quantity>
               <v4:LTD>4.4</v4:LTD>
               <v4:SafetyStock>5</v4:SafetyStock>
            </v4:items>
            <v4:items>
               <v4:PartNumber>PartNumber2</v4:PartNumber>
               <v4:UPC>UPC2</v4:UPC>
               <v4:BinID>6</v4:BinID>
               <v4:Quantity>7</v4:Quantity>
               <v4:LTD>8.8</v4:LTD>
               <v4:SafetyStock>9</v4:SafetyStock>
            </v4:items>
         </v4:Items>
      </v4:RefreshRequest>
   </soapenv:Body>
</soapenv:Envelope>
`

const testResponseInput = `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:RefreshResponse>?</v4:RefreshResponse>
   </soapenv:Body>
</soapenv:Envelope>
`

func TestRefreshInventoryRequest(t *testing.T) {
	if r, err := RefreshInventoryRequest([]byte(testRequestInput)); err != nil {
		t.Error(err)
	} else {
		t.Log(r)
	}
}

func TestRefreshInventoryResponse(t *testing.T) {
	if r, err := RefreshInventoryResponse([]byte(testResponseInput)); err != nil {
		t.Error(err)
	} else {
		t.Log(r)
	}
}
