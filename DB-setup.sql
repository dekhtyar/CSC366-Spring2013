--Team Ross
-----------
--Ian McBride
--Ross McKelvie
--Matt Tondreau
--Matt Schirle
--Riley Lew

create table Manufacturers (
  manufacturerId varchar2(10) NOT NULL,
  constraint manufacturers_pk PRIMARY KEY (manufacturerId)
);

create table Catalogs (
  catalogId varchar2(10) NOT NULL,
  manufacturerId varchar2(10) NOT NULL,
  constraint catalogs_pk PRIMARY KEY (catalogId, manufacturerId),
  constraint catalogs_fk FOREIGN KEY (manufacturerId) REFERENCES Manufacturers
);

create table Fulfillers (
  fulfillerId varchar2(10) NOT NULL,
  constraint fulfillers_pk PRIMARY KEY (fulfillerId)
);

-- locationType is uint in WSDL
create table Locations (
  locationId varchar2(10) NOT NULL,
  fulfillerId varchar2(10) NOT NULL,
  name varchar2(50),
  locationType varchar2(50),
  latitude decimal(9,6),
  longitude decimal(9,6),
  status varchar2(50),
  safetyStockLimitDefault varchar2(10),
  constraint locations_pk PRIMARY KEY (locationId, fulfillerId),
  constraint locations_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers
);

create table Products (
  upc varchar2(10) NOT NULL,
  catalogId varchar2(10) NOT NULL,
  manufacturerId varchar2(10) NOT NULL,
  name varchar2(50),
  constraint products_pk PRIMARY KEY (upc),
  constraint products_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs
);

create table Bins (
  locationId varchar2(10) NOT NULL,
  fulfillerId varchar2(10) NOT NULL,
  name varchar2(50) NOT NULL,
  binType varchar2(50),
  status varchar2(10),
  constraint bins_pk PRIMARY KEY (locationId, fulfillerId, name),
  constraint bins_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations
);

create table LocationOffersCatalogs (
  catalogId varchar2(10) NOT NULL,
  manufacturerId varchar2(10) NOT NULL,
  locationId varchar2(10) NOT NULL,
  fulfillerId varchar2(10) NOT NULL,
  constraint loc_catalog_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs,
  constraint loc_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations,
  constraint loc_pk PRIMARY KEY (catalogId, manufacturerId, locationId, fulfillerId)
);

create table FulfillerCarriesProducts (
  fulfillerId varchar2(10) NOT NULL,
  productUpc varchar2(10) NOT NULL,
  sku varchar2(10) NOT NULL,
  constraint fcp_fulfiller_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers,
  constraint fcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products(upc),
  constraint fcp_pk PRIMARY KEY (fulfillerId, productUpc)
);

create table BinContainsProducts (
  productUpc varchar2(10) NOT NULL,
  binName varchar2(10) NOT NULL,
  fulfillerId varchar2(10) NOT NULL,
  locationId varchar2(10) NOT NULL,
  allocated varchar2(6),
  onHand varchar2(6),
  constraint bcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products(upc),
  constraint bcp_binname_fk FOREIGN KEY (locationId, fulfillerId, binName) REFERENCES Bins(locationId, fulfillerId, name),
  constraint bcp_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations,
  constraint bcp_pk PRIMARY KEY (productUpc, locationId, fulfillerId, binName)
);

create table LocationSellsProducts (
  productUpc varchar2(10) NOT NULL,
  fulfillerId varchar2(10) NOT NULL,
  locationId varchar2(10) NOT NULL,
  ltd varchar2(10),
  storeSku varchar2(10),
  safetyStock varchar2(10),
  constraint lsp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products(upc),
  constraint lsp_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations,
  constraint lsp_pk PRIMARY KEY (productUpc, locationId, fulfillerId)
);
