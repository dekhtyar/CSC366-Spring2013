package input

import (
	"apersci/soap"
	"io"
)

func DeallocateInventoryRequest(r io.Reader) (soap.UpdateRequest, error) {
	var v soap.DeallocateInventory
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func DeallocateInventoryResponse(r io.Reader) error {
	return nil
}
