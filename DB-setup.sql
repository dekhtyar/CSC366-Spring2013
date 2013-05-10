--Team Ross
-----------
--Ian McBride
--Ross McKelvie
--Matt Tondreau
--Matt Schirle
--Riley Lew

create table Catalogs (
  catalogId VARCHAR(11) NOT NULL,
  manufacturerId VARCHAR(11) NOT NULL,
  constraint catalogs_pk PRIMARY KEY (catalogId, manufacturerId)
);

create table Fulfillers (
  fulfillerId VARCHAR(11) NOT NULL,
  name VARCHAR(32) NOT NULL,
  constraint fulfillers_pk PRIMARY KEY (fulfillerId)
);

-- locationType is uint in WSDL

create table Locations (
  externalLocationId VARCHAR(11) NOT NULL,
  internalLocationId VARCHAR(11) NOT NULL,
  fulfillerId VARCHAR(11) NOT NULL,
  locationType VARCHAR(50),
  latitude decimal(9,6),
  longitude decimal(9,6),
  status VARCHAR(50),
  safetyStockLimitDefault VARCHAR(10),
  constraint locations_pk PRIMARY KEY (internalLocationId),
  constraint locations_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers (fulfillerId)
);

create table Products (
  upc VARCHAR(11) NOT NULL,
  catalogId VARCHAR(11) NOT NULL,
  manufacturerId VARCHAR(11) NOT NULL,
  name VARCHAR(50),
  constraint products_pk PRIMARY KEY (upc),
  constraint products_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs (catalogId, manufacturerId)
);

create table Bins (
  internalLocationId VARCHAR(11) NOT NULL,
  fulfillerId VARCHAR(11),
  binName VARCHAR(50) NOT NULL,
  binType VARCHAR(50),
  status VARCHAR(10),
  constraint bins_pk PRIMARY KEY (internalLocationId, binName),
  constraint bins_fk FOREIGN KEY (internalLocationId) REFERENCES Locations (internalLocationId)
);

create table LocationOffersCatalogs (
  catalogId VARCHAR(11) NOT NULL,
  manufacturerId VARCHAR(11) NOT NULL,
  internalLocationId VARCHAR(11) NOT NULL,
  fulfillerId VARCHAR(11),
  constraint loc_catalog_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs (catalogId, manufacturerId),
  constraint loc_location_fk FOREIGN KEY (internalLocationId) REFERENCES Locations (internalLocationId),
  constraint loc_pk PRIMARY KEY (catalogId, manufacturerId, internalLocationId)
);

create table FulfillerCarriesProducts (
  fulfillerId VARCHAR(11) NOT NULL,
  productUpc VARCHAR(11) NOT NULL,
  sku VARCHAR(11) NOT NULL,
  constraint fcp_fulfiller_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers (fulfillerId),
  constraint fcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
  constraint fcp_pk PRIMARY KEY (fulfillerId, productUpc)
);

create table BinContainsProducts (
  productUpc VARCHAR(11) NOT NULL,
  binName VARCHAR(11) NOT NULL,
  internalLocationId VARCHAR(11) NOT NULL,
  allocated VARCHAR(6) DEFAULT '0',
  onHand VARCHAR(6),
  fulfillerId VARCHAR(11),
  constraint bcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
  constraint bcp_binname_fk FOREIGN KEY (internalLocationId, binName) REFERENCES Bins (internalLocationId, binName),
  constraint bcp_location_fk FOREIGN KEY (internalLocationId) REFERENCES Locations (internalLocationId),
  constraint bcp_pk PRIMARY KEY (productUpc, internalLocationId, binName)
);

create table LocationSellsProducts (
  productUpc VARCHAR(10) NOT NULL,
  internalLocationId VARCHAR(10) NOT NULL,
  ltd VARCHAR(10),
  storeSku VARCHAR(10),
  safetyStock VARCHAR(10),
  constraint lsp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
  constraint lsp_location_fk FOREIGN KEY (internalLocationId) REFERENCES Locations (internalLocationId),
  constraint lsp_pk PRIMARY KEY (productUpc, internalLocationId)
);
