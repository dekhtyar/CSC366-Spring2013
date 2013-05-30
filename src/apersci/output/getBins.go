package output

import (
	"apersci/soap"
	"io"
)

func GetBinsRequest(w io.Writer, v soap.BinRequest) error {
	return templates.ExecuteTemplate(w, "request-getBins.xml", v)
}

func GetBinResponse(w io.Writer, v soap.GetBinsResponse) error {
	return templates.ExecuteTemplate(w, "response-getBins.xml", v)
}
