package input

import (
	"apersci/soap"
	"io"
)

func GetBinsRequest(r io.Reader) (soap.BinRequest, error) {
	var v soap.GetBins
	err := soap.Unmarshal(r, &v)
	return v.Request, err
}

func GetBinsResponse(r io.Reader) (soap.BinsReturn, error) {
	var v soap.GetBinsResponse
	err := soap.Unmarshal(r, &v)
	return v.Return, err
}
