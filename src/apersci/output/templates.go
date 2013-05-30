package output

import "html/template"

var templates *template.Template

var templateNames = [...]string{
	"request-createBin.xml",
	"request-createFulfiller.xml",
	"request-createFulfillmentLocation.xml",
	"request-refreshInventory.xml",
	"request-getBinTypes.xml",
	"response-createBin.xml",
	"response-createFulfiller.xml",
	"response-createFulfillmentLocation.xml",
	"response-refreshInventory.xml",
	"response-getBinTypes.xml",
}

func init() {
	templateDir := "src/apersci/output/templates/" // TODO configurable
	templatePaths := make([]string, len(templateNames))
	for i, name := range templateNames {
		templatePaths[i] = templateDir + name
	}
	templates = template.Must(template.ParseFiles(templatePaths...))
}
