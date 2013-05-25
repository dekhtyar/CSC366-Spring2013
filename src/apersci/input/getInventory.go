package input

import (
	"apersci/soap"
	"io"
)

func GetInventoryRequest(r io.Reader) (soap.InventoryRequest, error) {
	var v soap.GetInventory
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func GetInventoryResponse(r io.Reader) (string, error) {
	var v soap.GetInventoryResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
