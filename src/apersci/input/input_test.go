package input

import (
	"apersci/soap"
	"bytes"
	"encoding/xml"
	"testing"
)

var createBinRequestTests = []struct {
	Input  string
	Output soap.Bin
}{
	{
		Output: soap.Bin{42, 43, 44, "abc", "def", "ghi"},
		Input: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
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
		Output: "abc",
		Input: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
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
	Output soap.FulfillerRequest
	Input  string
}{
	{
		Output: soap.FulfillerRequest{42, "abc"},
		Input: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
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
	Output string
	Input  string
}{
	{
		Output: "abc",
		Input: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
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
	Output soap.FulfillmentLocation
	Input  string
}{
	{
		Output: soap.FulfillmentLocation{42, 43, "abc", "def", "ghi", 1.234, 5.678, 45, "JK"},
		Input: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
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
	Output string
	Input  string
}{
	{
		Output: "abc",
		Input: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
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
	Output soap.RefreshRequest
	Input  string
}{
	{
		Output: soap.RefreshRequest{xml.Name{}, 12, "ab", []soap.RefreshItem{
			{xml.Name{}, "DIFFERENT", "ef", 34, 56, 7.8, 90},
			{xml.Name{}, "cd", "ef", 34, 56, 7.8, 90}}},
		Input: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:RefreshRequest>
         <v4:FulfillerID>12</v4:FulfillerID>
         <v4:LocationName>ab</v4:LocationName>
         <v4:Items> 
            <v4:items>
               <v4:PartNumber>DIFFERENT</v4:PartNumber>
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
	Output string
	Input  string
}{
	{
		Output: "abc",
		Input: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
       <v4:RefreshResponse>abc</v4:RefreshResponse>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

func assertString(t *testing.T, actOutput string, expOutput string) {
	if expOutput != actOutput {
		t.Errorf("Expected output did not match the actual output.\n" +
			"Expected:\n" + expOutput + "\nActual:\n" + actOutput)
	}
}

func TestCreateBinRequest(t *testing.T) {
	for _, strct := range createBinRequestTests {
		r := bytes.NewReader([]byte(strct.Input))
		value, err := CreateBinRequest(r)
		if err != nil {
			t.Errorf(err.Error())
		}

		if value != strct.Output {
			t.Errorf("Bin Request returned an unexpected bin.\n")
		}
	}
}

func TestCreateBinResponse(t *testing.T) {
	for _, strct := range createBinResponseTests {
		r := bytes.NewReader([]byte(strct.Input))
		value, err := CreateBinResponse(r)
		if err != nil {
			t.Errorf(err.Error())
		}

		assertString(t, value, strct.Output)
	}
}

func TestCreateFulfillerRequest(t *testing.T) {
	for _, strct := range createFulfillerRequestTests {
		r := bytes.NewReader([]byte(strct.Input))
		value, err := CreateFulfillerRequest(r)
		if err != nil {
			t.Errorf(err.Error())
		}

		if value != strct.Output {
			t.Errorf("Create fulfiller request returned an unexpected fulfiller.\n")
		}
	}
}

func TestCreateFulfillerResponse(t *testing.T) {
	for _, strct := range createFulfillerResponseTests {
		r := bytes.NewReader([]byte(strct.Input))
		value, err := CreateFulfillerResponse(r)
		if err != nil {
			t.Errorf(err.Error())
		}

		assertString(t, value, strct.Output)
	}
}

func TestCreateFulfillmentLocationRequest(t *testing.T) {
	for _, strct := range createFulfillmentLocationRequestTests {
		r := bytes.NewReader([]byte(strct.Input))
		value, err := CreateFulfillmentLocationRequest(r)
		if err != nil {
			t.Errorf(err.Error())
		}

		if value != strct.Output {
			t.Errorf("Create fulfillment location return an unexpected location.\n")
		}
	}
}

func TestCreateFulfillmentLocationResponse(t *testing.T) {
	for _, strct := range createFulfillmentLocationResponseTests {
		r := bytes.NewReader([]byte(strct.Input))
		value, err := CreateFulfillmentLocationResponse(r)
		if err != nil {
			t.Errorf(err.Error())
		}

		assertString(t, value, strct.Output)
	}
}

func TestRefreshInventoryRequest(t *testing.T) {
	for _, strct := range refreshInventoryRequestTests {
		r := bytes.NewReader([]byte(strct.Input))
		request, err := RefreshInventoryRequest(r)
		if err != nil {
			t.Errorf(err.Error())
		}

		for i, item := range request.Items {
			actItem := strct.Output.Items[i]
			if item.PartNumber != actItem.PartNumber ||
				item.UPC != actItem.UPC ||
				item.BinID != actItem.BinID ||
				item.Quantity != actItem.Quantity ||
				item.LTD != actItem.LTD ||
				item.SafetyStock != actItem.SafetyStock {
				t.Errorf("Refresh Inventory returned an unexpected item.\n")
			}
		}
	}
}

func TestRefreshInventoryResponse(t *testing.T) {
	for _, strct := range refreshInventoryResponseTests {
		r := bytes.NewReader([]byte(strct.Input))
		value, err := RefreshInventoryResponse(r)
		if err != nil {
			t.Errorf(err.Error())
		}

		assertString(t, value, strct.Output)
	}
}
