package output

import (
	"apersci/soap"
	"bytes"
	"encoding/xml"
	"testing"
)

var createBinRequestTests = []struct {
	Input  soap.Bin
	Output string
}{
	{
		Input: soap.Bin{42, 43, 44, "abc", "def", "ghi"},
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:createBin>
         <v4:request>
            <v4:FulfillerID>42</v4:FulfillerID>
            <v4:BinID>43</v4:BinID>
            <v4:FulfillerLocationID>44</v4:FulfillerLocationID>
            <v4:BinType>abc</v4:BinType>
            <v4:BinStatus>def</v4:BinStatus>
            <v4:Name>ghi</v4:Name>
         </v4:request>
      </v4:createBin>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

var createBinResponseTests = []struct {
	Input  string
	Output string
}{
	{
		Input: "abc",
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:createBinResponse>
          <v4:createBinReturn>abc</v4:createBinReturn>
      </v4:createBinResponse>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

var createFulfillerRequestTests = []struct {
	Input  soap.FulfillerRequest
	Output string
}{
	{
		Input: soap.FulfillerRequest{42, "abc"},
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:createFulfiller>
         <v4:request>
            <v4:FulfillerID>42</v4:FulfillerID>
            <v4:Name>abc</v4:Name>
         </v4:request>
      </v4:createFulfiller>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

var createFulfillerResponseTests = []struct {
	Input  string
	Output string
}{
	{
		Input: "abc",
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:createFulfillerResponse>
          <v4:createFulfillerReturn>abc</v4:createFulfillerReturn>
      </v4:createFulfillerResponse>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

var createFulfillmentLocationRequestTests = []struct {
	Input  soap.FulfillmentLocation
	Output string
}{
	{
		Input: soap.FulfillmentLocation{42, 43, "abc", "def", "ghi", 1.234, 5.678, 45, "JK"},
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:createFulfillmentLocation>
         <v4:request>
            <v4:FulfillerID>42</v4:FulfillerID>
            <v4:RetailerLocationID>43</v4:RetailerLocationID>
            <v4:ExternalLocationID>abc</v4:ExternalLocationID>
            <v4:LocationName>def</v4:LocationName>
            <v4:LocationType>ghi</v4:LocationType>
            <v4:Latitude>1.234</v4:Latitude>
            <v4:Longitude>5.678</v4:Longitude>
            <v4:Status>45</v4:Status>
            <v4:CountryCode>JK</v4:CountryCode>
         </v4:request>
      </v4:createFulfillmentLocation>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

var createFulfillmentLocationResponseTests = []struct {
	Input  string
	Output string
}{
	{
		Input: "abc",
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:createFulfillmentLocationResponse>
          <v4:createFulfillmentLocationReturn>abc</v4:createFulfillmentLocationReturn>
      </v4:createFulfillmentLocationResponse>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

var refreshInventoryRequestTests = []struct {
	Input  soap.RefreshRequest
	Output string
}{
	{
		Input: soap.RefreshRequest{xml.Name{}, 12, "ab", []soap.RefreshItem{
			{xml.Name{}, "cd", "ef", 34, 56, 7.8, 90},
			{xml.Name{}, "cd", "ef", 34, 56, 7.8, 90}}},
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:RefreshRequest>
         <v4:FulfillerID>12</v4:FulfillerID>
         <v4:LocationName>ab</v4:LocationName>
         <v4:Items> 
            <v4:items>
               <v4:PartNumber>cd</v4:PartNumber>
               <v4:UPC>ef</v4:UPC>
               <v4:BinID>34</v4:BinID>
               <v4:Quantity>56</v4:Quantity>
               <v4:LTD>7.8</v4:LTD>
               <v4:SafetyStock>90</v4:SafetyStock>
            </v4:items> 
            <v4:items>
               <v4:PartNumber>cd</v4:PartNumber>
               <v4:UPC>ef</v4:UPC>
               <v4:BinID>34</v4:BinID>
               <v4:Quantity>56</v4:Quantity>
               <v4:LTD>7.8</v4:LTD>
               <v4:SafetyStock>90</v4:SafetyStock>
            </v4:items> 
         </v4:Items>
      </v4:RefreshRequest>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

var refreshInventoryResponseTests = []struct {
	Input  string
	Output string
}{
	{
		Input: "abc",
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
       <v4:RefreshResponse></v4:RefreshResponse>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

func assertString(t *testing.T, expOutput string, actOutput string) {
	if expOutput != actOutput {
		t.Errorf("Expected output did not match the actual output.\n" +
			"Expected:\n" + expOutput + "\nActual:\n" + actOutput)
	}
}

// TODO merge code in functions below?

func TestCreateBinRequest(t *testing.T) {
	for _, strct := range createBinRequestTests {
		input := strct.Input
		var buff bytes.Buffer

		err := CreateBinRequest(&buff, input)
		if err != nil {
			t.Errorf(err.Error())
		}

		actOutput := buff.String()
		assertString(t, strct.Output, actOutput)
	}
}

func TestCreateBinResponse(t *testing.T) {
	for _, strct := range createBinResponseTests {
		input := strct.Input
		var buff bytes.Buffer

		err := CreateBinResponse(&buff, input)
		if err != nil {
			t.Errorf(err.Error())
		}

		actOutput := buff.String()
		assertString(t, strct.Output, actOutput)
	}
}

func TestCreateFulfillerRequest(t *testing.T) {
	for _, strct := range createFulfillerRequestTests {
		input := strct.Input
		var buff bytes.Buffer

		err := CreateFulfillerRequest(&buff, input)
		if err != nil {
			t.Errorf(err.Error())
		}

		actOutput := buff.String()
		assertString(t, strct.Output, actOutput)
	}
}

func TestCreateFulfillerResponse(t *testing.T) {
	for _, strct := range createFulfillerResponseTests {
		input := strct.Input
		var buff bytes.Buffer

		err := CreateFulfillerResponse(&buff, input)
		if err != nil {
			t.Errorf(err.Error())
		}

		actOutput := buff.String()
		assertString(t, strct.Output, actOutput)
	}
}

func TestCreateFulfillmentLocationRequest(t *testing.T) {
	for _, strct := range createFulfillmentLocationRequestTests {
		input := strct.Input
		var buff bytes.Buffer

		err := CreateFulfillmentLocationRequest(&buff, input)
		if err != nil {
			t.Errorf(err.Error())
		}

		actOutput := buff.String()
		assertString(t, strct.Output, actOutput)
	}
}

func TestCreateFulfillmentLocationResponse(t *testing.T) {
	for _, strct := range createFulfillmentLocationResponseTests {
		input := strct.Input
		var buff bytes.Buffer

		err := CreateFulfillmentLocationResponse(&buff, input)
		if err != nil {
			t.Errorf(err.Error())
		}

		actOutput := buff.String()
		assertString(t, strct.Output, actOutput)
	}
}

func TestRefreshInventoryRequest(t *testing.T) {
	for _, strct := range refreshInventoryRequestTests {
		input := strct.Input
		var buff bytes.Buffer

		err := RefreshInventoryRequest(&buff, input)
		if err != nil {
			t.Errorf(err.Error())
		}

		actOutput := buff.String()
		assertString(t, strct.Output, actOutput)
	}
}

func TestRefreshInventoryResponse(t *testing.T) {
	for _, strct := range refreshInventoryResponseTests {
		input := strct.Input
		var buff bytes.Buffer

		err := RefreshInventoryResponse(&buff, input)
		if err != nil {
			t.Errorf(err.Error())
		}

		actOutput := buff.String()
		assertString(t, strct.Output, actOutput)
	}
}
