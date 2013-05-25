package input

import (
	"apersci/soap"
	"io"
)

func CreateFulfillInventoryRequest(r io.Reader) (soap.UpdateRequest, error) {
	var v soap.FulfillInventory
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func CreateFulfillInventoryResponse(r io.Reader) (string, error) {
	return nil
}
