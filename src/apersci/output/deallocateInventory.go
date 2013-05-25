package output

import (
	"apersci/soap"
	"io"
)

func DeallocateInventoryRequest(w io.Writer, v soap.UpdateRequest) error {
	return templates.ExecuteTemplate(w, "request-deallocateInventory.xml", v)
}

func DeallocateInventoryResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-deallocateInventory.xml", v)
}
