package output

import (
	"apersci/soap"
	"io"
)

func AllocateInventoryRequest(w io.Writer, v soap.UpdateRequest) error {
	return templates.ExecuteTemplate(w, "request-allocateInventory.xml", v)
}

func AllocateInventoryResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-allocateInventory.xml", v)
}
