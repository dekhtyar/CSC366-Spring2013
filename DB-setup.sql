--Team Ross
-----------
--Ian McBride
--Ross McKelvie
--Matt Tondreau
--Matt Schirle
--Riley Lew

CREATE TABLE Manufacturers (
  manufacturerId varchar2(10) NOT NULL,

  constraint manufacturers_pk PRIMARY KEY (manufacturerId)
);

CREATE TABLE Catalogs (
  catalogId varchar2(10) NOT NULL,
  manufacturerId varchar2(10) NOT NULL,

  constraint catalogs_pk PRIMARY KEY (catalogId, manufacturerId),
  constraint catalogs_fk FOREIGN KEY (manufacturerId) REFERENCES Manufacturers
);

CREATE TABLE Locations (
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

CREATE TABLE LocationOffersCatalogs (
  catalogId varchar2(10) FOREIGN KEY REFERENCES Catalogs(catalogId) NOT NULL,
  locationId varchar2(10) FOREIGN KEY REFERENCES Locations(locationId) NOT NULL,
  fulfillerId varchar2(10) FOREIGN KEY REFERENCES Fulfillers(fulfillerId) NOT NULL,
);

CREATE TABLE Fulfillers (
  fulfillerId varchar2(10) NOT NULL,

  constraint fulfillers_pk PRIMARY KEY (fulfillerId)
);

CREATE TABLE FulfillerCarriesProducts (
  fulfillerId varchar2(10) FOREIGN KEY REFERENCES Fulfillers(fulfillerId) NOT NULL,
  productUpc varchar2(10) FOREIGN KEY REFERENCES Products(upc) NOT NULL,
  sku varchar2(10) NOT NULL
);

CREATE TABLE Products (
  upc varchar2(10) NOT NULL,
  catalogId varchar2(10) FOREIGN KEY REFERENCES Catalogs(catalogId) NOT NULL,
  manufacturerId varchar2(10) FOREIGN KEY REFERENCES Manufacturers(manufacturerId) NOT NULL,
  name varchar2(50),

  constraint products_pk PRIMARY KEY (upc)
);

CREATE TABLE Bins (
  locationId varchar2(10) NOT NULL,
  fulfillerId varchar2(10) NOT NULL,
  name varchar2(50) NOT NULL,
  binType varchar2(50),
  status varchar2(10),

  constraint bins_pk PRIMARY KEY (locationId, fulfillerId, name)
  constraint location_fk FOREIGN KEY (locationId) REFERENCES Locations,
  constraint fulfiller_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers
);

CREATE TABLE BinContainsProducts (
  productUpc varchar2(10) FOREIGN KEY REFERENCES Products(upc) NOT NULL,
  binName varchar2(10) FOREIGN KEY REFERENCES Bins(name) NOT NULL,
  fulfillerId varchar2(10) FOREIGN KEY REFERENCES Fulfillers(fulfillerId) NOT NULL,
  locationId varchar2(10) FOREIGN KEY REFERENCES Locations(locationId) NOT NULL
  allocated varchar2(6),
  onHand varchar2(6)
);

CREATE TABLE LocationSellsProducts (
  productUpc varchar2(10) FOREIGN KEY REFERENCES Products(upc) NOT NULL,
  fulfillerId varchar2(10) FOREIGN KEY REFERENCES Fulfillers(fulfillerId) NOT NULL,
  locationId varchar2(10) FOREIGN KEY REFERENCES Locations(locationId) NOT NULL,
  ltd varchar2(10),
  storeSku varchar2(10),
  safetyStock varchar2(10)
);
