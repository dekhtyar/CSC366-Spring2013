package output

import (
	"apersci/soap"
	"io"
)

func GetBinsRequest(w io.Writer, v soap.BinRequest) error {
	return templates.ExecuteTemplate(w, "request-getBins.xml", v)
}

func CreateBinResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-getBins.xml", v)
}
