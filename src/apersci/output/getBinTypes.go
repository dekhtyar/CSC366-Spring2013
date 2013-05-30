package output

import (
	"io"
	"apersci/soap"
)

func GetBinTypesRequest(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "request-getBinTypes.xml", v)
}

func GetBinTypesResponse(w io.Writer, v soap.GetBinTypesResponse) error {
	return templates.ExecuteTemplate(w, "response-getBinTypes.xml", v)
}
