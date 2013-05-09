package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func createFulfiller(w http.ResponseWriter, r *http.Request) {
	data, err := input.FulfillerRequest(r.Body)
	fmt.Println(err)
	fmt.Println(data)
	output.CreateFulfillerResponse(w, "BLAHHHH!")
}
