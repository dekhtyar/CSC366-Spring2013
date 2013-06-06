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

var url = "http://db2.thepicard.org:8125"

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
	req.ExternalLocationID = "600"
	//req.LocationID = 54802
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
			assertString(t, "ExternalLocationID mismatch", bin.ExternalLocationID, req.ExternalLocationID)
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

func TestAllocateInventory(t *testing.T) {
	var req soap.UpdateRequest

	req.FulfillerID = 69170

	var item soap.UpdateItem

	item.PartNumber = "8888063910"
	item.UPC = "8888063910"
	item.Quantity = 1
	item.ExternalLocationID = "440006"
	//item.LocationID = 50565
	req.Items = append(req.Items, item)

	/* TODO uncomment 
	err := AllocateInventory(url, req)
	if err != nil {
		t.Fatal("Error occurred during AllocateInventory: \n" + err.Error())
	}*/

	// TODO assert the result programatically using GetInventory()
}

func TestDeallocateInventory(t *testing.T) {
	var req soap.UpdateRequest

	req.FulfillerID = 69170

	var item soap.UpdateItem

	item.PartNumber = "8888063910"
	item.UPC = "8888063910"
	item.Quantity = 1
	item.ExternalLocationID = "440006"
	//item.LocationID = 50565
	req.Items = append(req.Items, item)

	/* TODO uncomment
	err := DeallocateInventory(url, req)
	if err != nil {
		t.Fatal("Error occurred during DeallocateInventory: \n" + err.Error())
	}*/

	// TODO assert the result programatically using GetInventory()
}

func TestFulfillInventory(t *testing.T) {
	var req soap.UpdateRequest

	req.FulfillerID = 69170

	var item soap.UpdateItem

	item.PartNumber = "8888063910"
	item.UPC = "8888063910"
	item.Quantity = 1
	item.ExternalLocationID = "440006"
	//item.LocationID = 50565
	req.Items = append(req.Items, item)

	/* TODO uncomment
	err := FulfillInventory(url, req)
	if err != nil {
		t.Fatal("Error occurred during FulfillInventory: \n" + err.Error())
	}*/

	// TODO assert the result programatically using GetInventory()
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

