package input

import (
	"apersci/soap"
	"io"
)

func GetBinRequest(r io.Reader) (soap.BinRequest, error) {
	var v soap.GetBins
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func GetBinResponse(r io.Reader) (soap.BinsReturn, error) {
	var v soap.GetBinsResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
