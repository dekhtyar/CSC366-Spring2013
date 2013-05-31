package client

import (
	"testing"
	"apersci/soap"
)

//***********************************//
//
// These tests assume that the database
// is initialized using the csv files.
//
//***********************************//

var url = "http://db2.thepicard.org:8101"

func TestGetBinTypes(t *testing.T) {
	r, err := GetBinTypes(url)
	if err != nil {
		t.Fatal("Error occurred during GetBinTypes: \n" + err.Error())
	}

	if len(r) != 1 {
		t.Fatal("Expected only 1 output, found %d.\n", len(r))
	}

	if r[0] != "General" {
		t.Fatal("Expected 'General' as the bin type, found '" + r[0] + "'.\n")
	}
}

func TestGetBins(t *testing.T) {
	var req soap.BinRequest

	req.FulfillerID = 48590
	req.FulfillerLocationID = 54802
	req.SearchTerm = "203"
	req.NumResults = 3
	req.ResultsStart = 2

	r, err := GetBins(url, req)
	if err != nil {
		t.Fatal("Error occurred during GetBins: \n" + err.Error())
	}

	assertInt(t, "ResultCount mismatch", r.ResultCount, int(req.NumResults))
	assertInt(t, "Number of bins mismatch", len(r.Bins), int(req.NumResults))

	validBinIDs := []uint{8739, 8741, 8742}
	validBinNames := []string{"1020301", "1020302", "1020303"}
	for _, bin := range r.Bins {
		found := false
		for i := range validBinIDs {
			if validBinIDs[i] == bin.BinID && validBinNames[i] == bin.Name {
				found = true
				break
			}
		}

		if !found {
			t.Errorf("Did not expect to find bin: Name=%s Id=%d\n", bin.Name, bin.BinID)
		} else {
			assertInt(t, "FulfillerID mismatch", int(bin.FulfillerID), int(req.FulfillerID))
			assertInt(t, "FulfillerLocationID mismatch", int(bin.FulfillerLocationID), int(req.FulfillerLocationID))
			assertString(t, "Type mismatch", bin.BinType, "General")
			assertString(t, "Status mismatch", bin.BinStatus, "Pickable")
		}
	}
}

func TestGetBinStatuses(t *testing.T) {
	r, err := GetBinStatuses(url)
	if err != nil {
		t.Fatal("Error occurred during GetBinStatuses: \n" + err.Error())
	}

	if len(r) != 1 {
		t.Fatal("Expected only 1 output, found %d.\n", len(r))
	}

	if r[0] != "Pickable" {
		t.Fatal("Expected 'Pickable' as the bin type, found '" + r[0] + "'.\n")
	}

}

func assertInt(t *testing.T, msg string, actual int, expected int) {
	if actual != expected {
		t.Fatalf("%s: found %d, expected %d.\n", msg, actual, expected)
	}
}

func assertString(t *testing.T, msg string, actual string, expected string) {
	if actual != expected {
		t.Fatalf("%s: found '%s', expected '%s'.\n", msg, actual, expected)
	}
}

