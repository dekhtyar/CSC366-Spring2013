package output

import (
	"apersci/soap"
	"io"
)

func GetFulfillmentLocationTypesRequest(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "request-createFulfillmentLocation.xml", v)
}

func GetFulfillmentLocationTypesResponse(w io.Writer, v soap.GetFulfillerLocationTypesResponse) error {
	return templates.ExecuteTemplate(w, "response-getFulfillmentLocation.xml", v)
}
