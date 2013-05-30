package output

import "html/template"

var templates *template.Template

var templateNames = [...]string{
	"request-adjustInventory.xml",
	"request-allocateInventory.xml",
	"request-createBin.xml",
	"request-createFulfiller.xml",
	"request-createFulfillmentLocation.xml",
	"request-deallocateInventory.xml",
	"request-fulfillInventory.xml",
	"request-getBinStatuses.xml",
	"request-getBins.xml",
	"request-getBinTypes.xml",
	"request-getFulfillerLocations.xml",
	"request-getFulfillerLocationTypes.xml",
	"request-getFulfillerStatus.xml",
	"request-getInventory.xml",
	"request-getItemLocationsByFulfiller.xml",
	"request-refreshInventory.xml",
	"response-adjustInventory.xml",
	"response-allocateInventory.xml",
	"response-createBin.xml",
	"response-createFulfiller.xml",
	"response-createFulfillmentLocation.xml",
	"response-deallocateInventory.xml",
	"response-fulfillInventory.xml",
	"response-getBinStatuses.xml",
	"response-getBins.xml",
	"response-getBinTypes.xml",
	"response-getFulfillerLocations.xml",
	"response-getFulfillerLocationTypes.xml",
	"response-getFulfillerStatus.xml",
	"response-getInventory.xml",
	"response-getItemLocationsByFulfiller.xml",
	"response-refreshInventory.xml",
}

func init() {
	templateDir := "src/apersci/output/templates/" // TODO configurable
	templatePaths := make([]string, len(templateNames))
	for i, name := range templateNames {
		templatePaths[i] = templateDir + name
	}
	templates = template.Must(template.ParseFiles(templatePaths...))
}
