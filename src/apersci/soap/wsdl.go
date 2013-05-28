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

type GetFulfillerStatus struct {
	XMLName     xml.Name `xml:"getFulfillerStatus"`
	FulfillerID uint
}

type GetFulfillerStatusResponse struct {
	XMLName xml.Name `xml:"getFulfillerStatusResponse"`
	Return  string   `xml:"getFulfillerStatusReturn"`
}

type AdjustRequest struct {
	XMLName      xml.Name `xml:"AdjustRequest"`
	FulfillerID  uint
	LocationName string
	Items        []AdjustItem `xml:">items"`
}

type AdjustItem struct {
	PartNumber string
	UPC        string
	BinID      uint
	Quantity   int
}

type AdjustResponse struct {
	XMLName xml.Name `xml:"AdjustResponse"`
	Return  string   `xml:",chardata"`
}

type GetInventory struct {
	XMLName xml.Name         `xml:"getInventory"`
	Request InventoryRequest `xml:"request"`
}

type InventoryRequest struct {
	FulfillerID              uint
	ManufacturerID           uint            `xml:"Catalog>ManufacturerID"`
	CatalogID                uint            `xml:"Catalog>CatalogID"`
	Quantities               []InventoryItem `xml:">items"`
	LocationNames            []string        `xml:">LocationNames"`
	Location                 Location
	Type                     string
	Limit                    int
	IgnoreSafetyStock        bool
	IncludeNegativeInventory bool
	OrderByLTD               bool
}

type InventoryItem struct {
	PartNumber string
	UPC        string
	Quantity   int
}

type Location struct {
	Unit        string
	Radius      float64
	PostalCode  string
	Latitude    float64
	Longitude   float64
	CountryCode string
}

type GetInventoryResponse struct {
	XMLName xml.Name        `xml:"getInventoryResponse"`
	Return  InventoryReturn `xml:"getInventoryReturn"`
}

type InventoryReturn struct {
	LocationName   string
	CatalogID      uint
	ManufacturerID uint
	OnHand         int
	Available      int
	PartNumber     string
	UPC            string
	LTD            float64
	SafetyStock    int
	CountryCode    string
	Distance       float64
}

type GetFulfillerLocations struct {
	XMLName xml.Name     `xml:"getFulfillmentLocations"`
	Request OrderRequest `xml:"request"`
}

type OrderRequest struct {
	FulfillerID    uint
	ManufacturerID uint `xml:"Catalog>ManufacturerID"`
	CatalogID      uint `xml:"Catalog>CatalogID"`
	Location       Location
	MaxLocations   uint
}

type GetFulfillerLocationsResponse struct {
	XMLName xml.Name                   `xml:"getFulfillmentLocationsResponse"`
	Return  FulfillmentLocationsReturn `xml:"getFulfillmentLocationsReturn"`
}

type FulfillmentLocationsReturn struct {
	FulfillerID         uint
	FulfillerLocationID uint
}

type GetFulfillerLocationTypesResponse struct {
	XMLName xml.Name `xml:"getFulfillmentLocationTypesResponse"`
	Return  []string `xml:"getFulfillmentLocationTypesReturn>LocationType"`
}

type GetItemLocationsByFulfiller struct {
	XMLName xml.Name               `xml:"getItemLocationsByFulfiller"`
	Request InventorySearchRequest `xml:"request"`
}

type InventorySearchRequest struct {
	FulfillerIDs []uint `xml:">items"`
	LocationID   uint
	PostalCode   string
	PartNumber   string
	UPC          string
}

type GetItemLocationsByFulfillerResponse struct {
	XMLName xml.Name                  `xml:"getItemLocationsByFulfillerResponse"`
	Return  []InventorySearchResponse `xml:"getItemLocationsByFulfillerReturn"`
}

type InventorySearchResponse struct {
	FulfillerID         uint
	FulfillerLocationID uint
	LocationName        string
	PartNumber          string
	UPC                 string
	Available           int
	OnHand              int
	Allocated           int
	Distance            float64
}

type GetBins struct {
	XMLName xml.Name   `xml:"getBins"`
	Request BinRequest `xml:"request"`
}

type BinRequest struct {
	FulfillerID         uint
	FulfillerLocationID uint
	SearchTerm          string
	NumResults          uint
	ResultsStart        uint
}

type GetBinsResponse struct {
	XMLName xml.Name   `xml:"getBinsResponse"`
	Return  BinsReturn `xml:"getBinsReturn"`
}

type BinsReturn struct {
	Bins []Bin `xml:">items"`
}

type GetBinTypesResponse struct {
	XMLName xml.Name `xml:"getBinTypesResponse"`
	Return  []string `xml:"getBinTypesReturn>BinType"`
}

type GetBinStatusesResponse struct {
	XMLName xml.Name `xml:"getBinStatusesResponse"`
	Return  []string `xml:"getBinStatusesReturn>BinStatus"`
}
