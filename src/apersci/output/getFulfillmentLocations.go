package output

import (
	"apersci/soap"
	"io"
)

func GetFulfillmentLocationsRequest(w io.Writer, v soap.OrderRequest) error {
	return templates.ExecuteTemplate(w, "request-getFulfillerLocations.xml", v)
}

func GetFulfillmentLocationsResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-getFulfillerLocations.xml", v)
}
