package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func refreshInventory(w http.ResponseWriter, r *http.Request) {
	data, err := input.RefreshRequest(r.Body)
	fmt.Println(err)
	fmt.Println(data)
	output.RefreshResponse(w, ":-D")
}
