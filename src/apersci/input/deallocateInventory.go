package input

import (
	"apersci/soap"
	"io"
)

func CreateDeallocateInventoryRequest(r io.Reader) (soap.UpdateRequest, error) {
	var v soap.DeallocateInventory
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func CreateDeallocateInventoryResponse(r io.Reader) error {
	return nil
}
