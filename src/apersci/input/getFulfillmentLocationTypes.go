package input

import (
	"apersci/soap"
	"io"
)

func GetFulfillmentLocationTypesRequest(r io.Reader) error {
	return nil
}

func GetFulfillmentLocationTypesResponse(r io.Reader) ([]string, error) {
	var v soap.GetFulfillerLocationTypesResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
