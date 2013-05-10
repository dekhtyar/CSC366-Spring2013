package output

import (
	"testing"
//	"log"
//	"bytes"
)

const buffer_size = 4096

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
		actOutput []byte := make([]byte, 0, buffer_size)
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


		actOutput []byte := make([]byte, 0, buffer_size)
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

