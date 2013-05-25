package output

import (
	"apersci/soap"
	"io"
)

func GetBinTypeRequest(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "request-getBinTypes.xml", v)
}

func GetBinTypeResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-getBinTypes.xml", v)
}
