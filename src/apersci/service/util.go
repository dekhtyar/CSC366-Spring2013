package main

import (
	"database/sql"
)

func readIntAndCloseRows(rows *sql.Rows) (count int, err error) {
	rows.Next()
	err = rows.Scan(&count)
	if err != nil {
		return
	}

	err = rows.Close()
	if err != nil {
		return
	}

	return
}

func readIntSliceAndCloseRows(rows *sql.Rows) (values []int, err error) {
	for rows.Next() {
		var value int
		err = rows.Scan(&value)
		if err != nil {
			return
		}
		values = append(values, value)
	}
	err = rows.Err()
	if err != nil {
		return
	}

	err = rows.Close()
	if err != nil {
		return
	}

	return
}
