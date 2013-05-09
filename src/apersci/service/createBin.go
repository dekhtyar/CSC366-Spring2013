package main

import (
	"apersci/input"
	"apersci/output"
	"net/http"
)

func createBin(w http.ResponseWriter, r *http.Request) {
	data, err := input.CreateBinRequest(r.Body)
	fmt.Println(data)
	ouput.CreateBinResponse(w, "Hi everyone!")
}
