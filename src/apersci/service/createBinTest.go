package main

import (
	"apersci/soap"
	"database/sql"
	"fmt"
	_ "github.com/bmizerany/pq"
)

/*func createBin(w http.ResponseWriter, r *http.Request) {
	data, err := input.CreateBinRequest(r.Body)
	fmt.Println(err)
	fmt.Println(data)
	output.CreateBinResponse(w, "Hi everyone!")
}*/

func dbConn(b soap.Bin) error {
	conn, err := sql.Open("postgres", "dbname=cait password=cait")
	if err != nil {
		return err
	}
	defer conn.Close()

	res, err := conn.Exec("INSERT INTO Bins VALUES(" + b.BinID + "," + b.FulfillerLocationID + "," + b.Name + "," + b.BinType + "," + b.BinStatus + ")")
	if err != nil {
		return err
	}

	/*rows, err := conn.Query("SELECT * FROM cait")
	var i uint

	for rows.Next() {
		err = rows.Scan(&i)
		if err != nil {
			return err
		}
		fmt.Println(i)
	}*/
	return nil
}

func main() {

}
