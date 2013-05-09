--Team Ross
-----------
--Ian McBride
--Ross McKelvie
--Matt Tondreau
--Matt Schirle
--Riley Lew

create table Catalogs (
  catalogId VARCHAR2(10) NOT NULL,
  manufacturerId VARCHAR2(10) NOT NULL,
  constraint catalogs_pk PRIMARY KEY (catalogId, manufacturerId)
);

create table Fulfillers (
  fulfillerId VARCHAR2(10) NOT NULL,
  name VARCHAR2(32) NOT NULL,
  constraint fulfillers_pk PRIMARY KEY (fulfillerId)
);

-- locationType is uint in WSDL

create table Locations (
  externalLocationID VARCHAR2(10) NOT NULL,  
  internalLocationID VARCHAR2(32) NOT NULL,
  fulfillerId VARCHAR2(10) NOT NULL,
  locationType VARCHAR2(50),
  latitude decimal(9,6),
  longitude decimal(9,6),
  status VARCHAR2(50),
  safetyStockLimitDefault VARCHAR2(10),
  constraint locations_pk PRIMARY KEY (internalLocationId),
  constraint locations_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers
  
);

create table Products (
  upc VARCHAR2(10) NOT NULL,
  catalogId VARCHAR2(10) NOT NULL,
  manufacturerId VARCHAR2(10) NOT NULL,
  name VARCHAR2(50),
  constraint products_pk PRIMARY KEY (upc),
  constraint products_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs
);

create table Bins (
  locationId VARCHAR2(10) NOT NULL,
  fulfillerId VARCHAR2(10) NOT NULL,
  name VARCHAR2(50) NOT NULL,
  binType VARCHAR2(50),
  status VARCHAR2(10),
  constraint bins_pk PRIMARY KEY (locationId, fulfillerId, name),
  constraint bins_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations
);

create table LocationOffersCatalogs (
  catalogId VARCHAR2(10) NOT NULL,
  manufacturerId VARCHAR2(10) NOT NULL,
  locationId VARCHAR2(10) NOT NULL,
  fulfillerId VARCHAR2(10) NOT NULL,
  constraint loc_catalog_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs,
  constraint loc_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations,
  constraint loc_pk PRIMARY KEY (catalogId, manufacturerId, locationId, fulfillerId)
);

create table FulfillerCarriesProducts (
  fulfillerId VARCHAR2(10) NOT NULL,
  productUpc VARCHAR2(10) NOT NULL,
  sku VARCHAR2(10) NOT NULL,
  constraint fcp_fulfiller_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers,
  constraint fcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products(upc),
  constraint fcp_pk PRIMARY KEY (fulfillerId, productUpc)
);

create table BinContainsProducts (
  productUpc VARCHAR2(10) NOT NULL,
  binName VARCHAR2(10) NOT NULL,
  fulfillerId VARCHAR2(10) NOT NULL,
  locationId VARCHAR2(10) NOT NULL,
  allocated VARCHAR2(6),
  onHand VARCHAR2(6),
  constraint bcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products(upc),
  constraint bcp_binname_fk FOREIGN KEY (locationId, fulfillerId, binName) REFERENCES Bins(locationId, fulfillerId, name),
  constraint bcp_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations,
  constraint bcp_pk PRIMARY KEY (productUpc, locationId, fulfillerId, binName)
);

create table LocationSellsProducts (
  productUpc VARCHAR2(10) NOT NULL,
  fulfillerId VARCHAR2(10) NOT NULL,
  locationId VARCHAR2(10) NOT NULL,
  ltd VARCHAR2(10),
  storeSku VARCHAR2(10),
  safetyStock VARCHAR2(10),
  constraint lsp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products(upc),
  constraint lsp_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations,
  constraint lsp_pk PRIMARY KEY (productUpc, locationId, fulfillerId)
);
