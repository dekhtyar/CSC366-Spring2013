"use strict";

angular.module('apersci', ['apersci.services', 'apersci.controllers', 'apersci.filters', 'apersci.directives'])
.config(['$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode(true);
}])
.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {templateUrl: '/assets/partials/home.html', controller: 'HomeCtrl'});
    $routeProvider.when('/operation/:op', {templateUrl: '/assets/partials/op.html', controller: 'OpCtrl'});
    $routeProvider.otherwise({redirectTo: '/'});
}])
.value('escape', function(text) {
    return text.replace(/\&/g, '&amp;').replace(/\</g, '&lt;').replace(/\>/g, '&gt;').replace(/"/g, '&quot;');
})
.value('operations', {
    adjustInventory: {
        AdjustRequest: {
            FulfillerID:        { __label:'Fulfiller ID',         __placeholder:'positiveInteger', __value:'' },
            ExternalLocationID: { __label:'External Location ID', __placeholder:'string',          __value:'' },
            Items: [{
                items: {
                    PartNumber: { __label:'Part Number', __placeholder:'string', __value:'' },
                    UPC:        { __label:'UPC',         __placeholder:'string', __value:'' },
                    BinID:      { __label:'Bin ID',      __placeholder:'int',    __value:'' },
                    Quantity:   { __label:'Quantity',    __placeholder:'int',    __value:'' }
                }
            }]
        }
    },
    allocateInventory: {
        allocateInventory: {
            request: {
                FulfillerID: { __label:'Fulfiller ID', __placeholder:'positiveInteger', __value:'' },
                FulfillerLocationCatalog: {
                    ManufacturerCatalog: {
                        ManufacturerID: { __label:'Manufacturer ID', __placeholder:'positiveInteger', __value:'' },
                        CatalogID:      { __label:'Catalog ID',      __placeholder:'positiveInteger', __value:'' }
                    },
                    ExternalLocationID: { __label:'External Location ID', __placeholder:'string', __value:'' }
                },
                Items: [{
                    items: {
                        PartNumber:         { __label:'Part Number',          __placeholder:'string',          __value:'' },
                        UPC:                { __label:'UPC',                  __placeholder:'string',          __value:'' },
                        Quantity:           { __label:'Quantity',             __placeholder:'int',             __value:'' },
                        OrderID:            { __label:'Order ID',             __placeholder:'positiveInteger', __value:'' },
                        OrderItemID:        { __label:'Order Item ID',        __placeholder:'positiveInteger', __value:'' },
                        ShipmentID:         { __label:'Shipment ID',          __placeholder:'positiveInteger', __value:'' },
                        ExternalLocationID: { __label:'External Location ID', __placeholder:'string',          __value:'' }
                    }
                }]
            }
        }
    },
    createBin: {
        createBin: {
            request: {
                FulfillerID:        { __label:'Fulfiller ID',         __placeholder:'positiveInteger', __value:'' },
                BinID:              { __label:'Bin ID',               __placeholder:'positiveInteger', __value:'' },
                ExternalLocationID: { __label:'External Location ID', __placeholder:'positiveInteger',          __value:'' },
                BinType:            { __label:'Bin Type',             __placeholder:'string',          __value:'' },
                BinStatus:          { __label:'Bin Status',           __placeholder:'string',          __value:'' },
                Name:               { __label:'Bin Name',             __placeholder:'string',          __value:'' }
            }
        }
    },
    createFulfiller: {
        createFulfiller: {
            request: {
                FulfillerID: { __label:'Fulfiller ID',   __placeholder:'positiveInteger', __value:'' },
                Name:        { __label:'Fulfiller Name', __placeholder:'string',          __value:'' }
            }
        }
    },
    createFulfillmentLocation: {
        createFulfillmentLocation: {
            request: {
                FulfillerID:        { __label:'Fulfiller ID',         __placeholder:'positiveInteger', __value:'' },
                RetailerLocationID: { __label:'Internal Location ID', __placeholder:'positiveInteger', __value:'' },
                ExternalLocationID: { __label:'External Location ID', __placeholder:'string',          __value:'' },
                LocationName:       { __label:'Location Name',        __placeholder:'string',          __value:'' },
                LocationType:       { __label:'Location Type',        __placeholder:'string',          __value:'' },
                Latitude:           { __label:'Latitude',             __placeholder:'double',          __value:'' },
                Longitude:          { __label:'Longitude',            __placeholder:'double',          __value:'' },
                Status:             { __label:'Status',               __placeholder:'1 or 2',          __value:'' },
                CountryCode:        { __label:'Country Code',         __placeholder:'string',          __value:'' }
            }
        }
    },
    deallocateInventory: {
        deallocateInventory: {
            request: {
                FulfillerID: { __label:'Fulfiller ID', __placeholder:'positiveInteger', __value:'' },
                FulfillerLocationCatalog: {
                    ManufacturerCatalog: {
                        ManufacturerID: { __label:'Manufacturer ID', __placeholder:'positiveInteger', __value:'' },
                        CatalogID:      { __label:'Catalog ID',      __placeholder:'positiveInteger', __value:'' }
                    },
                    ExternalLocationID: { __label:'External Location ID', __placeholder:'string', __value:'' }
                },
                Items: [{
                    items: {
                        PartNumber:         { __label:'Part Number',          __placeholder:'string',          __value:'' },
                        UPC:                { __label:'UPC',                  __placeholder:'string',          __value:'' },
                        Quantity:           { __label:'Quantity',             __placeholder:'int',             __value:'' },
                        OrderID:            { __label:'Order ID',             __placeholder:'positiveInteger', __value:'' },
                        OrderItemID:        { __label:'Order Item ID',        __placeholder:'positiveInteger', __value:'' },
                        ShipmentID:         { __label:'Shipment ID',          __placeholder:'positiveInteger', __value:'' },
                        ExternalLocationID: { __label:'External Location ID', __placeholder:'string',          __value:'' }
                    }
                }]
            }
        }
    },
    fulfillInventory: {
        fulfillInventory: {
            request: {
                FulfillerID: { __label:'Fulfiller ID', __placeholder:'positiveInteger', __value:'' },
                FulfillerLocationCatalog: {
                    ManufacturerCatalog: {
                        ManufacturerID: { __label:'Manufacturer ID', __placeholder:'positiveInteger', __value:'' },
                        CatalogID:      { __label:'Catalog ID',      __placeholder:'positiveInteger', __value:'' }
                    },
                    ExternalLocationID: { __label:'External Location ID', __placeholder:'string', __value:'' }
                },
                Items: [{
                    items: {
                        PartNumber:         { __label:'Part Number',          __placeholder:'string',          __value:'' },
                        UPC:                { __label:'UPC',                  __placeholder:'string',          __value:'' },
                        Quantity:           { __label:'Quantity',             __placeholder:'int',             __value:'' },
                        OrderID:            { __label:'Order ID',             __placeholder:'positiveInteger', __value:'' },
                        OrderItemID:        { __label:'Order Item ID',        __placeholder:'positiveInteger', __value:'' },
                        ShipmentID:         { __label:'Shipment ID',          __placeholder:'positiveInteger', __value:'' },
                        ExternalLocationID: { __label:'External Location ID', __placeholder:'string',          __value:'' }
                    }
                }]
            }
        }
    },
    getBinStatuses: {
        getBinStatuses: null
    },
    getBinTypes: {
        getBinTypes: null
    },
    getBins: {
        getBins: {
            request: {
                FulfillerID:        { __label:'Fulfiller ID',         __placeholder:'positiveInteger', __value:'' },
                ExternalLocationID: { __label:'External Location ID', __placeholder:'string',          __value:'' },
                SearchTerm:         { __label:'Search Term',          __placeholder:'string',          __value:'' },
                NumResults:         { __label:'Num Results',          __placeholder:'positiveInteger', __value:'' },
                ResultsStart:       { __label:'Results Start',        __placeholder:'positiveInteger', __value:'' }
            }
        }
    },
    getFulfillerStatus: {
        getFulfillerStatus: {
            fulfillerID: { _label:'Fulfiller ID', __placeholder:'positiveInteger', __value:'' }
        }
    },
    getFulfillmentLocationTypes: {
        getFulfillmentLocationTypes: null
    },
    getFulfillmentLocations: {
        getFulfillmentLocations: {
            request: {
                FulfillerID: { __label:'Fulfiller ID', __placeholder:'positiveInteger', __value:'' },
                Catalog: {
                    ManufacturerID: { __label:'Manufacturer ID', __placeholder:'positiveInteger', __value:'' },
                    CatalogID:      { __label:'Catalog ID',      __placeholder:'positiveInteger', __value:'' }
                },
                MaxLocations: { __label:'Max Locations', __placeholder:'positiveInteger', __value:'' },
                Location: {
                    Unit:        { __label:'Unit',         __placeholder:'string',          __value:'MILES' },
                    Radius:      { __label:'Radius',       __placeholder:'positiveInteger', __value:'' },
                    PostalCode:  { __label:'Postal Code',  __placeholder:'string',          __value:'' },
                    Latitude:    { __label:'Latitude',     __placeholder:'double',          __value:'' },
                    Longitude:   { __label:'Longitude',    __placeholder:'double',          __value:'' },
                    CountryCode: { __label:'Country Code', __placeholder:'string',          __value:'' }
                }
            }
        }
    },
    getInventory: {
        getInventory: {
            request: {
                FulfillerID: { __label:'Fulfiller ID', __placeholder:'positiveInteger', __value:'' },
                Catalog: {
                    ManufacturerID: { __label:'Manufacturer ID', __placeholder:'positiveInteger', __value:'' },
                    CatalogID:      { __label:'Catalog ID',      __placeholder:'positiveInteger', __value:'' }
                },
                Quantities: {
                    items: [{
                        PartNumber: { __label:'Part Number', __placeholder:'string', __value:'' },
                        UPC:        { __label:'UPC',         __placeholder:'string', __value:'' },
                        Quantity:   { __label:'Quantity',    __placeholder:'int',    __value:'' }
                    }]
                },
                LocationIDs: [{
                    ExternalLocationID: { __label:'External Location ID', __placeholder:'string', __value:'' }
                }],
                Location: {
                    Unit:        { __label:'Unit',         __placeholder:'string',          __value:'MILES' },
                    Radius:      { __label:'Radius',       __placeholder:'positiveInteger', __value:'' },
                    PostalCode:  { __label:'Postal Code',  __placeholder:'string',          __value:'' },
                    Latitude:    { __label:'Latitude',     __placeholder:'double',          __value:'' },
                    Longitude:   { __label:'Longitude',    __placeholder:'double',          __value:'' },
                    CountryCode: { __label:'Country Code', __placeholder:'string',          __value:'' }
                },
                Type:  { __label:'Inventory Request Type', __placeholder:'string', __value:'' },
                Limit: { __label:'Limit',                  __placeholder:'int',    __value:'10000' },
                IgnoreSafetyStock: { __label:'Ignore Safety Stock', __placeholder:'boolean', __value:'false' },
                IncludeNegativeInventory: { __label:'Include Negative Inventory', __placeholder:'boolean', __value:'false' },
                OrderByLTD: { __label:'Order By LTD', __placeholder:'boolean', __value:'' }
            }
        }
    },
    refreshInventory: {
        RefreshRequest: {
            FulfillerID: { __label:'Fulfiller ID', __placeholder:'positiveInteger', __value:'' },
            ExternalLocationID: { __label:'External Location ID', __placeholder:'string', __value:'' },
            Items: [{
                items: {
                    PartNumber:  { __label:'Part Number',  __placeholder:'string', __value:'' },
                    UPC:         { __label:'UPC',          __placeholder:'string', __value:'' },
                    BinID:       { __label:'Bin ID',       __placeholder:'int',    __value:'' },
                    Quantity:    { __label:'Quantity',     __placeholder:'int',    __value:'' },
                    LTD:         { __label:'LTD',          __placeholder:'double', __value:'' },
                    SafetyStock: { __label:'Safety Stock', __placeholder:'int',    __value:'' },
                }
            }]
        }
    }
});
