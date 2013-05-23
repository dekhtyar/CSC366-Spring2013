package soap

import "encoding/xml"

type CreateBin struct {
	XMLName xml.Name `xml:"createBin"`
	Request Bin      `xml:"request"`
}

type Bin struct {
	FulfillerID         uint
	BinID               uint
	FulfillerLocationID uint
	BinType             string
	BinStatus           string
	Name                string
}

type CreateBinResponse struct {
	XMLName         xml.Name `xml:"createBinResponse"`
	CreateBinReturn string   `xml:"createBinReturn"`
}

type RefreshItem struct {
	XMLName     xml.Name `xml:"items"`
	PartNumber  string
	UPC         string
	BinID       string //BinID really bin name
	Quantity    int
	LTD         float64
	SafetyStock int
}

type RefreshRequest struct {
	XMLName      xml.Name `xml:"RefreshRequest"`
	FulfillerID  uint
	LocationName uint          //LocationName is really location id
	Items        []RefreshItem `xml:">items"`
}

type RefreshResponse struct {
	XMLName xml.Name `xml:"RefreshResponse"`
	Return  string   `xml:",chardata"`
}

type CreateFulfiller struct {
	XMLName xml.Name         `xml:"createFulfiller"`
	Request FulfillerRequest `xml:"request"`
}

type FulfillerRequest struct {
	FulfillerID uint
	Name        string
}

type CreateFulfillerResponse struct {
	XMLName               xml.Name `xml:"createFulfillerResponse"`
	CreateFulfillerReturn string   `xml:"createFulfillerReturn"`
}

type CreateFulfillmentLocation struct {
	XMLName xml.Name            `xml:"createFulfillmentLocation"`
	Request FulfillmentLocation `xml:"request"`
}

type FulfillmentLocation struct {
	FulfillerID        uint
	RetailerLocationID uint
	ExternalLocationID string
	LocationName       string
	LocationType       string
	Latitude           float64
	Longitude          float64
	Status             string
	CountryCode        string
}

type CreateFulfillmentLocationResponse struct {
	XMLName                         xml.Name `xml:"createFulfillmentLocationResponse"`
	CreateFulfillmentLocationReturn string   `xml:"createFulfillmentLocationReturn"`
}

type AllocateInventory struct {
	XMLName xml.Name      `xml:"allocateInventory"`
	Request UpdateRequest `xml:"request"`
}

type UpdateRequest struct {
	FulfillerID              uint
	FulfillerLocationCatalog FulfillmentLocationCatalog
	Items                    []UpdateItem `xml:">items"`
}

type FulfillmentLocationCatalog struct {
	ManufacturerID      uint `xml:"ManufacturerCatalog>ManufacturerID"`
	CatalogID           uint `xml:"ManufacturerCatalog>CatalogID"`
	FulfillerLocationID uint
}

type UpdateItem struct {
	PartNumber          string
	UPC                 string
	Quantity            int
	FulfillerLocationID uint
}

type DeallocateInventory struct {
	XMLName xml.Name      `xml:"deallocateInventory"`
	Request UpdateRequest `xml:"request"`
}

type FulfillInventory struct {
	XMLName xml.Name      `xml:"fulfillInventory"`
	Request UpdateRequest `xml:"request"`
}
