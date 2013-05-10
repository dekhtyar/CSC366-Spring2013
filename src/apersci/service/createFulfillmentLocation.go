package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func createFulfillmentLocation(w http.ResponseWriter, r *http.Request) {
	data, err := input.CreateFulfillmentLocationRequest(r.Body)
	fmt.Println(err)
	fmt.Println(data)
	output.CreateFulfillmentLocationResponse(w, "lalalala")
}
