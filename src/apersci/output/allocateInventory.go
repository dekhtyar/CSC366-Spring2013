package output

import (
	"apersci/soap"
	"io"
)

func CreateFulfillmentLocationRequest(w io.Writer, v soap.FulfillmentLocation) error {
	return templates.ExecuteTemplate(w, "request-allocateInventory.xml", v)
}

func CreateFulfillmentLocationResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-allocateInventory.xml", v)
}
