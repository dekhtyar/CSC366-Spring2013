package input

import (
	"apersci/soap"
	"io"
)

func GetBinTypesRequest(r io.Reader) error {
	return nil
}

func GetBinTypesResponse(r io.Reader) ([]string, error) {
	var v soap.GetBinTypesResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
