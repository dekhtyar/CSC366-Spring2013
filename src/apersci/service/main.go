package main

import (
	_ "github.com/bmizerany/pq"
	"net/http"
)

func main() {
	http.HandleFunc("/createBin/", createBin)
	http.HandleFunc("/createFulfiller/", createFulfiller)
	http.HandleFunc("/createFulfillmentLocation/", createFulfillmentLocation)
	http.HandleFunc("/refreshInventory/", refreshInventory)
	http.ListenAndServe(":8080", nil)
}
