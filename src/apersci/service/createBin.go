package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func createBin(w http.ResponseWriter, r *http.Request) {
	data, err := input.CreateBinRequest(r.Body)
	fmt.Println(err)
	fmt.Println(data)
	output.CreateBinResponse(w, "Hi everyone!")
}
