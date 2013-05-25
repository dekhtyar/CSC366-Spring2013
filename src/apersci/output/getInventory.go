package output

import (
	"apersci/soap"
	"io"
)

func GetInventoryRequest(w io.Writer, v soap.InventoryRequest) error {
	return templates.ExecuteTemplate(w, "request-getInventory.xml", v)
}

func GetInventoryResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-getInventory.xml", v)
}
