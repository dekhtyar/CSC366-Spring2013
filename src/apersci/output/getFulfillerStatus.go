package output

import (
	"io"
)

func GetFulfillerStatusRequest(w io.Writer, v uint) error {
	return templates.ExecuteTemplate(w, "request-getFulfillerStatus.xml", v)
}

func GetFulfillerStatusResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-getFulfillerStatus.xml", v)
}
