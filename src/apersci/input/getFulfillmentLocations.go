package input

import (
	"apersci/soap"
	"io"
)

func GetFulfillmentLocationsRequest(r io.Reader) (soap.OrderRequest, error) {
	var v soap.GetFulfillerLocations
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func GetFulfillmentLocationsResponse(r io.Reader) ([]soap.FulfillmentLocationsReturn, error) {
	var v soap.GetFulfillerLocationsResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
