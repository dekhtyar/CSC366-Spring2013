package main

import (
	"bytes"
	"database/sql"
	"fmt"
	_ "github.com/bmizerany/pq"
	"net/http"
	"os"
)

func getFileContents(fileName string) (contents string, err error) {
	var b bytes.Buffer
	f, err := os.Open(fileName)
	if err != nil {
		return
	}
	_, err = b.ReadFrom(f)
	if err != nil {
		return
	}
	contents = b.String()
	return
}

func execFile(fileName string) func(http.ResponseWriter, *http.Request) {
	fileContents, err := getFileContents(fileName)
	if err != nil {
		panic(fmt.Sprintf("error reading SQL file (%s): %s\n", fileName, err.Error()))
	}
	return func(w http.ResponseWriter, r *http.Request) {
		conn, err := sql.Open("postgres", "dbname=db2 password=db2")
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
		defer conn.Close()

		result, err := conn.Exec(fileContents)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
	}
}

func main() {
	http.HandleFunc("/createBin/", createBin)
	http.HandleFunc("/createFulfiller/", createFulfiller)
	http.HandleFunc("/createFulfillmentLocation/", createFulfillmentLocation)
	http.HandleFunc("/refreshInventory/", refreshInventory)
	http.ListenAndServe(":8080", nil)
}
