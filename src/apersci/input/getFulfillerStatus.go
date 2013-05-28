package input

import (
	"apersci/soap"
	"io"
)

func GetFulfillerStatusRequest(r io.Reader) (uint, error) {
	var v soap.GetFulfillerStatus
	err := soap.Unmarshal(r, &v)
	return v.FulfillerID, err
}

func GetFulfillerStatusResponse(r io.Reader) (string, error) {
	var v soap.GetFulfillerStatusResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
