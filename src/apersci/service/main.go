package main

import (
	"net/http"
)

func main() {
	http.HandleFunc("/createBin/", createBin)
	http.ListenAndServe(":8080", nil)
}
