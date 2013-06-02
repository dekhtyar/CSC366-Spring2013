package output

import (
	"apersci/soap"
	"io"
)

func FulfillInventoryRequest(w io.Writer, v soap.UpdateRequest) error {
	return templates.ExecuteTemplate(w, "request-fulfillInventory.xml", v)
}

func FulfillInventoryResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-fulfillInventory.xml", v)
}
