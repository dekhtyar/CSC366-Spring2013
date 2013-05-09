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
	BinID       int
	Quantity    int
	LTD         float64
	SafetyStock int
}

type RefreshRequest struct {
	XMLName      xml.Name `xml:"RefreshRequest"`
	FulfillerID  uint
	LocationName string
	Items        []RefreshItem `xml:">items"`
}

type RefreshResponse string

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
	Status             int
}

type CreateFulfillmentLocationResponse struct {
	XMLName                         xml.Name `xml:"createFulfillmentLocationResponse"`
	CreateFulfillmentLocationReturn string   `xml:"createFulfillmentLocationReturn"`
}
