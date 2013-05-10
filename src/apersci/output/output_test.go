package output

import (
	"apersci/soap"
	"testing"

//	"log"
//	"bytes"
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

var createFulfillmentLocationTests = []struct {
	Input  soap.FulfillmentLocation
	Output string
}{
	{
		Input: soap.FulfillmentLocation{42, 43, 44, "abc", "def", "ghi", 1.234, 5.678, 45, "JK"},
		Output: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v4="http://v4.core.coexprivate.api.shopatron.com">
   <soapenv:Header/>
   <soapenv:Body>
      <v4:createFulfillmentLocation>
         <v4:request>
            <v4:FulfillerID>42</v4:FulfillerID>
            <v4:RetailerLocationID>43</v4:RetailerLocationID>
            <v4:ExternalLocationID>44</v4:ExternalLocationID>
            <v4:LocationName>abc</v4:LocationName>
            <v4:LocationType>def</v4:LocationType>
            <v4:Latitude>ghi</v4:Latitude>
            <v4:Longitude>1.234</v4:Longitude>
            <v4:Status>5.678</v4:Status>
            <v4:CountryCode>JK</v4:CountryCode>
         </v4:request>
      </v4:createFulfillmentLocation>
   </soapenv:Body>
</soapenv:Envelope>
`},
}

const bufferSize = 4096

/*

// output (structs -> xml)
func CreateBinRequest(w io.Writer, v soap.Bin) error {
	return templates.ExecuteTemplate(w, "request-createBin.xml", v)
}

func CreateBinResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-createBin.xml", v)
}

*/

func errorMismatch(expOutput string, actOutput string) {
	t.ErrorF("Expected output did not match the actual output.\n" +
		"Expected:\n" + expOutput + "\nActual:\n" + actOutput)
}

// TODO merge code in functions below

func TestCreateBinRequests(t *testing.T) {
	for _, strct := range createBinRequestTests {
		input := strct.Input
		actOutput := make([]byte, 0, bufferSize)
		w := bytes.NewBuffer(actOutput)

		err := CreateBinRequest(w, input)
		if err != nil {
			t.ErrorF(err.Error())
		}

		if expOutput := strct.Output; expOutput != string(actOutput) {
			errorMismatch(expOutput, actOutput)
		}
	}
}

func TestCreateBinResponse(t *testing.T) {
	for _, strct := range createBinResponseTests {
		input := strct.Input

		actOutput := make([]byte, 0, bufferSize)
		w := bytes.NewBuffer(actOutput)

		err := CreateBinRequest(w, input)
		if err != nil {
			t.ErrorF(err.Error())
		}

		if expOutput := strct.Output; expOutput != string(actOutput) {
			errorMismatch(expOutput, actOutput)
		}
	}
}

/*
func createBin() &soap.Bin {
	var bin = &soap.Bin
	bin.FulfillerID = 123
	bin.BinID = 456
	bin.FulfillerLocationID = 789
	bin.BinType = "MyBinType"
	bin.BinStatus = "MyBinStatus"
	bin.Name = "MyName"
	return bin
}
*/
