package input

import (
	"apersci/soap"
	"io"
)

func AdjustInventoryRequest(r io.Reader) (v soap.AdjustRequest, err error) {
	err = soap.Unmarshal(r, &v)
	return
}

func AdjustInventoryResponse(r io.Reader) (string, error) {
	var v soap.AdjustResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
