package input

import (
	"apersci/soap"
	"io"
)

func GetItemLocationsByFulfillerRequest(r io.Reader) (soap.InventorySearchRequest, error) {
	var v soap.GetItemLocationsByFulfiller
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func GetItemLocationsByFulfillerResponse(r io.Reader) ([]soap.InventorySearchResponse, error) {
	var v soap.GetItemLocationsByFulfillerResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
