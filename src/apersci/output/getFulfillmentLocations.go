package output

import (
	"apersci/soap"
	"io"
)

func GetFulfillmentLocationsRequest(w io.Writer, v soap.OrderRequest) error {
	return templates.ExecuteTemplate(w, "request-getFulfillerLocations.xml", v)
}

func GetFulfillmentLocationsResponse(w io.Writer, v []soap.FulfillmentLocationsReturn) error {
	return templates.ExecuteTemplate(w, "response-getFulfillerLocations.xml", v)
}
