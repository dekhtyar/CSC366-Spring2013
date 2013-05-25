package output

import (
	"apersci/soap"
	"io"
)

func GetBinStatusesRequest(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "request-getBinStatuses.xml", v)
}

func GetBinStatusesResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-getBinStatuses.xml", v)
}
