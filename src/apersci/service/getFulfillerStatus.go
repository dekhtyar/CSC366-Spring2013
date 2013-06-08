package main

import (
	"apersci/input"
	"apersci/output"
	"fmt"
	"net/http"
)

func getFulfillerStatus(w http.ResponseWriter, r *http.Request) (err error) {
	l, err := input.GetFulfillerStatusRequest(r.Body)
	if err != nil {
		return
	}

	conn, err := getDBConnection()
	if err != nil {
		return
	}
	defer conn.Close()

	tx, err := getDBTransaction()
	if err != nil {
		return
	}
	defer tx.Rollback()

	rows, err := conn.Query("SELECT COUNT(*) FROM Locations WHERE fulfillerId = $1 AND status = 'active'",
		l)
	if err != nil {
		return
	}
	fmt.Println(l)

	count, err := readIntAndCloseRows(rows)
	if err != nil {
		return
	}

	var str string

	if count > 0 {
		str = "active"
	} else {
		str = "inactive"
	}
	fmt.Println(count)
	fmt.Println(str)

	err = output.GetFulfillerStatusResponse(w, str)
	if err != nil {
		return
	}

	err = tx.Commit()
	if err != nil {
		return
	}

	return
}
