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

func GetInventory(url string, v soap.InventoryRequest) (r []soap.InventoryReturn, err error) {
	var b bytes.Buffer

	err = output.GetInventoryRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/getInventory/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	r, err = input.GetInventoryResponse(resp.Body)
	return
}

func GetFulfillerStatus(url string, v uint) (r string, err error) {
	var b bytes.Buffer

	err = output.GetFulfillerStatusRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/getFulfillerStatus/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	r, err = input.GetFulfillerStatusResponse(resp.Body)
	return
}


func GetFulfillmentLocations(url string, v soap.OrderRequest) (r soap.FulfillmentLocationsReturn, err error) {
	var b bytes.Buffer

	err = output.GetFulfillmentLocationsRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/getFulfillmentLocations/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	r, err = input.GetFulfillmentLocationsResponse(resp.Body)
	return
}

func GetFulfillmentLocationsTypes(url string, v soap.FulfillmentLocation) (r []string, err error) {
	var b bytes.Buffer

	err = output.GetFulfillmentLocationTypesRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/getFulfillmentLocationTypes/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	r, err = input.GetFulfillmentLocationTypesResponse(resp.Body)
	return
}

func GetBinStatuses(url string) (r []string, err error) {
	var b bytes.Buffer

	resp, err := http.Post(url+"/getBinStatuses/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	r, err = input.GetBinStatusesResponse(resp.Body)
	return
}

func AllocateInventory(url string, v soap.UpdateRequest) (err error) {
	var b bytes.Buffer

	err = output.AllocateInventoryRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/allocateInventory/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	err = input.AllocateInventoryResponse(resp.Body)
	return
}

func DeallocateInventory(url string, v soap.UpdateRequest) (err error) {
	var b bytes.Buffer

	err = output.DeallocateInventoryRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/deallocateInventory/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	err = input.DeallocateInventoryResponse(resp.Body)
	return
}

func FulfillInventory(url string, v soap.UpdateRequest) (err error) {
	var b bytes.Buffer

	err = output.FulfillInventoryRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/fulfillInventory/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	err = input.FulfillInventoryResponse(resp.Body)
	return
}

func AdjustInventory(url string, v soap.AdjustRequest) (r string, err error) {
	var b bytes.Buffer

	err = output.AdjustInventoryRequest(&b, v)
	if err != nil {
		return
	}

	resp, err := http.Post(url+"/adjustInventory/", "application/soap+xml", &b)
	if err != nil {
		return
	}

	r, err = input.AdjustInventoryResponse(resp.Body)
	return
}

