package output

import (
	"apersci/soap"
	"io"
)

func AdjustInventoryRequest(w io.Writer, v soap.AdjustRequest) error {
	return templates.ExecuteTemplate(w, "request-adjustInventory.xml", v)
}

func AdjustResponse(w io.Writer, v string) error {
	return templates.ExecuteTemplate(w, "response-adjustInventory.xml", v)
}
