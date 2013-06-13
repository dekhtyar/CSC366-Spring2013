package main

import (
	"database/sql"
	"errors"
)

func readIntAndCloseRows(rows *sql.Rows, str string) (count int, err error) {
	if !rows.Next() {
		err = errors.New("Unable to read " + str + ".")
		return
	}

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

func readUintSliceAndCloseRows(rows *sql.Rows) (values []uint, err error) {
	for rows.Next() {
		var value uint
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
