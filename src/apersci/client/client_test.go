package client

import (
	"testing"
)

var url = "http://db2.thepicard.org:8125"

func TestGetBinTypes(t *testing.T) {
	r, err := GetBinTypes(url)
	if err != nil {
		t.Errorf("Error occurred during GetBinTypes: \n" + err.Error())
		return
	}

	if len(r) != 1 {
		t.Errorf("Expected only 1 output, found %d.\n", len(r))
		return
	}

	if r[0] != "General" {
		t.Errorf("Expected 'General' as the bin type, found '" + r[0] + "'.\n")
		return
	}
}


