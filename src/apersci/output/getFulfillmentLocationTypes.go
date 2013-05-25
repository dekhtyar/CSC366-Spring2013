package output

import (
	"apersci/soap"
	"io"
)

func GetFulfillmentLocationTypesRequest(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "request-getFulfillerLocationTypes.xml", v)
}

func GetFulfillmentLocationTypesResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-getFulfillerLocationTypes.xml", v)
}
