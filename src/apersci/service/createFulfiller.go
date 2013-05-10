package main

import (
	"apersci/input"
	"apersci/output"
	"apersci/soap"
	"fmt"
	"net/http"
)

func createFulfiller(w http.ResponseWriter, r *http.Request) {
	data, err := input.CreateFulfillerRequest(r.Body)
	fmt.Println(err)
	fmt.Println(data)
	output.CreateFulfillerResponse(w, "BLAHHHH!")
}

func dbConn(f soap.FulfillerRequest) error {
	conn, err := sql.Open("postgres", "dbname=cait password=cait")
	if err != nil {
		return err
	}
	defer conn.Close()

	res, err := conn.Exec("INSERT INTO Fulfillers VALUES(" + f.FulfillerID + "," + f.Name + ")")
	if err != nil {
		return err
	}

	return nil
}
