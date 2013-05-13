package main

import (
	"bytes"
	"database/sql"
	"fmt"
	_ "github.com/bmizerany/pq"
	"net/http"
	"os"
)

var dbConnType = "postgres"
var dbConnInfo = "user=db2 database=db2 password=db2"

func getDBConnection() (*sql.DB, error) {
	return sql.Open(dbConnType, dbConnInfo)
}

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

func execFile(fileName string) http.HandlerFunc {
	fileContents, err := getFileContents(fileName)
	if err != nil {
		panic(fmt.Sprintf("error reading SQL file (%s): %s\n", fileName, err.Error()))
	}
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		conn, err := getDBConnection()
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
		defer conn.Close()

		_, err = conn.Exec(fileContents)
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
	})
}

func onlyPost(h http.HandlerFunc) http.HandlerFunc {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if r.Method != "POST" {
			http.Error(w, http.StatusText(http.StatusMethodNotAllowed), http.StatusMethodNotAllowed)
		} else {
			h(w, r)
		}
	})
}

func main() {
	http.HandleFunc("/createBin/", onlyPost(createBin))
	http.HandleFunc("/createFulfiller/", onlyPost(createFulfiller))
	http.HandleFunc("/createFulfillmentLocation/", onlyPost(createFulfillmentLocation))
	http.HandleFunc("/refreshInventory/", onlyPost(refreshInventory))
	http.HandleFunc("/createDatabase/", onlyPost(execFile("sql/DB-setup.sql")))
	http.HandleFunc("/clearDatabase/", onlyPost(execFile("sql/DB-clear.sql")))
	http.HandleFunc("/destroyDatabase/", onlyPost(execFile("sql/DB-cleanup.sql")))
	http.ListenAndServe(":8080", nil)
}
