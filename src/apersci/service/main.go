package main

import (
	"bytes"
	"database/sql"
	"fmt"
	_ "github.com/bmizerany/pq"
	"net/http"
	"os"
	"runtime"
)

var dbConnType = "postgres"
var dbConnInfo = "user=db2 database=db2 password=db2"

func getDBConnection() (*sql.DB, error) {
	return sql.Open(dbConnType, dbConnInfo)
}

func writeStatusInternalServerError(w http.ResponseWriter, err error) {
	http.Error(w, err.Error(), http.StatusInternalServerError)
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

func onlyPostAndCORS(h http.HandlerFunc) http.HandlerFunc {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if r.Method != "POST" {
			http.Error(w, http.StatusText(http.StatusMethodNotAllowed), http.StatusMethodNotAllowed)
		} else {
			w.Header().Set("Access-Control-Allow-Origin", "*")
			h(w, r)
		}
	})
}

func main() {
	runtime.GOMAXPROCS(runtime.NumCPU())
	http.HandleFunc("/createBin/", onlyPostAndCORS(createBin))
	http.HandleFunc("/createFulfiller/", onlyPostAndCORS(createFulfiller))
	http.HandleFunc("/createFulfillmentLocation/", onlyPostAndCORS(createFulfillmentLocation))
	http.HandleFunc("/refreshInventory/", onlyPostAndCORS(refreshInventory))
	http.HandleFunc("/getBinTypes/", onlyPostAndCORS(getBinTypes))
	http.HandleFunc("/createDatabase/", onlyPostAndCORS(execFile("sql/DB-setup.sql")))
	http.HandleFunc("/clearDatabase/", onlyPostAndCORS(execFile("sql/DB-clear.sql")))
	http.HandleFunc("/destroyDatabase/", onlyPostAndCORS(execFile("sql/DB-cleanup.sql")))
	http.ListenAndServe(":8080", nil)
}
