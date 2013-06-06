package main

import (
	"fmt"
	"net/http"
	"runtime"
)

func noIndex(h http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		url := r.URL.Path
		if url[len(url)-1] == '/' {
			http.NotFound(w, r)
		} else {
			h.ServeHTTP(w, r)
		}
	})
}

func serveDirAs(dir, as string) http.Handler {
	if as[0] != '/' {
		as = "/" + as
	}
	return noIndex(http.StripPrefix(as, http.FileServer(http.Dir(dir))))
}

func serveIndex(w http.ResponseWriter, r *http.Request) {
	http.ServeFile(w, r, "index.html")
}

func main() {
	runtime.GOMAXPROCS(runtime.NumCPU())

	http.Handle("/assets/", serveDirAs(".", "/assets"))
	http.HandleFunc("/", serveIndex)
	err := http.ListenAndServe(":9000", nil)
	if err != nil {
		fmt.Println(err)
	}
}
