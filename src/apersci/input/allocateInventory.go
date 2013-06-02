package input

import (
	"apersci/soap"
	"io"
)

func AllocateInventoryRequest(r io.Reader) (soap.UpdateRequest, error) {
	var v soap.AllocateInventory
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func AllocateInventoryResponse(r io.Reader) error {
	return nil
}
