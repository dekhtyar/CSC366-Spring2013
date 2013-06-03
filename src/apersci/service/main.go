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

type httpOperationHandler func(w http.ResponseWriter, r *http.Request) (err error)

func getDBConnection() (*sql.DB, error) {
	return sql.Open(dbConnType, dbConnInfo)
}

func getDBTransaction() (tx *sql.Tx, err error) {
	conn, err := getDBConnection()
	if err != nil {
		return
	}

	tx, err = conn.Begin()
	return
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

func execFile(fileName string) httpOperationHandler {
	fileContents, err := getFileContents(fileName)
	if err != nil {
		panic(fmt.Sprintf("error reading SQL file (%s): %s\n", fileName, err.Error()))
	}
	return func(w http.ResponseWriter, r *http.Request) (err error) {
		conn, err := getDBConnection()
		if err != nil {
			return
		}
		defer conn.Close()

		_, err = conn.Exec(fileContents)
		if err != nil {
			return
		}
		return
	}
}

func onlyPostAndCORS(h httpOperationHandler) http.HandlerFunc {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if r.Method != "POST" {
			http.Error(w, http.StatusText(http.StatusMethodNotAllowed), http.StatusMethodNotAllowed)
		} else {
			w.Header().Set("Access-Control-Allow-Origin", "*")
			err := h(w, r)
			if err != nil {
				http.Error(w, err.Error(), http.StatusInternalServerError)
			}
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
	http.HandleFunc("/getFulfillerStatus/", onlyPostAndCORS(getFulfillerStatus))
	http.HandleFunc("/getFulfillmentLocations/", onlyPostAndCORS(getFulfillmentLocations))
	http.HandleFunc("/getFulfillmentLocationTypes/", onlyPostAndCORS(getFulfillmentLocationTypes))
	http.HandleFunc("/allocateInventory/", onlyPostAndCORS(allocateInventory))
	http.HandleFunc("/deallocateInventory/", onlyPostAndCORS(deallocateInventory))
	http.HandleFunc("/fulfillInventory/", onlyPostAndCORS(fulfillInventory))
	http.HandleFunc("/getBins/", onlyPostAndCORS(getBins))
	http.HandleFunc("/getBinStatuses/", onlyPostAndCORS(getBinStatuses))
	http.HandleFunc("/getInventory/", onlyPostAndCORS(getInventory))
	http.HandleFunc("/adjustInventory/", onlyPostAndCORS(adjustInventory))
	http.HandleFunc("/createDatabase/", onlyPostAndCORS(execFile("sql/DB-setup.sql")))
	http.HandleFunc("/clearDatabase/", onlyPostAndCORS(execFile("sql/DB-clear.sql")))
	http.HandleFunc("/destroyDatabase/", onlyPostAndCORS(execFile("sql/DB-cleanup.sql")))
	http.ListenAndServe(":8080", nil)
}
