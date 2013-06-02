package input

import (
	"apersci/soap"
	"io"
)

func FulfillInventoryRequest(r io.Reader) (soap.UpdateRequest, error) {
	var v soap.FulfillInventory
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func FulfillInventoryResponse(r io.Reader) error {
	return nil
}
