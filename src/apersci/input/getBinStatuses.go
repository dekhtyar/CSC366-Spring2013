package input

import (
	"apersci/soap"
	"io"
)

func GetBinStatusesRequest(r io.Reader) error {
	return nil
}

func GetBinStatusesResponse(r io.Reader) ([]string, error) {
	var v soap.GetBinStatusesResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
