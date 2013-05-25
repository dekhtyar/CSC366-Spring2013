package output

import (
	"apersci/soap"
	"io"
)

func GetItemLocationsByFulfillerRequest(w io.Writer, v soap.InventorySearchRequest) error {
	return templates.ExecuteTemplate(w, "request-getItemLocationsByFulfiller.xml", v)
}

func GetFulfillmentLocationTypesResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-getItemLocationsByFulfiller.xml", v)
}
