package client

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"bytes"
	"net/http"
)

func CreateBin(url string, v soap.Bin) (r string, err error) {
	var b bytes.Buffer

	err = output.CreateBinRequest(&b, v)
	if err != nil {
		return
	}
	resp, err := http.Post(url+"/createBin/", "application/soap+xml", &b)
	if err != nil {
		return
	}
	r, err = input.CreateBinResponse(resp.Body)
	return
}

func CreateFulfiller(url string, v soap.FulfillerRequest) (r string, err error) {
	var b bytes.Buffer

	err = output.CreateFulfillerRequest(&b, v)
	if err != nil {
		return
	}
	resp, err := http.Post(url+"/createFulfiller/", "application/soap+xml", &b)
	if err != nil {
		return
	}
	r, err = input.CreateFulfillerResponse(resp.Body)
	return
}

func CreateFulfillmentLocation(url string, v soap.FulfillmentLocation) (r string, err error) {
	var b bytes.Buffer

	err = output.CreateFulfillmentLocationRequest(&b, v)
	if err != nil {
		return
	}
	resp, err := http.Post(url+"/createFulfillmentLocation/", "application/soap+xml", &b)
	if err != nil {
		return
	}
	r, err = input.CreateFulfillmentLocationResponse(resp.Body)
	return
}

func RefreshInventory(url string, v soap.RefreshRequest) (r string, err error) {
	var b bytes.Buffer

	err = output.RefreshInventoryRequest(&b, v)
	if err != nil {
		return
	}
	resp, err := http.Post(url+"/refreshInventory/", "application/soap+xml", &b)
	if err != nil {
		return
	}
	r, err = input.RefreshInventoryResponse(resp.Body)
	return
}


func GetBinTypes(url string) (r []string, err error) {
	var b bytes.Buffer

	resp, err := http.Post(url+"/getBinTypes/", "application/soap+xml", &b)
	if err != nil {
		return
	}
	r, err = input.GetBinTypesResponse(resp.Body)
	return
}

func GetBins(url string, v soap.BinRequest) (r soap.BinsReturn, err error) {
	var b bytes.Buffer

	err = output.GetBinsRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/getBins/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	r, err = input.GetBinsResponse(resp.Body)
	return
}


