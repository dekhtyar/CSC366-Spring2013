--Team Ross
-----------
--Ian McBride
--Ross McKelvie
--Matt Tondreau
--Matt Schirle
--Riley Lew

CREATE TABLE Manufacturers (
  manufacturerId varchar2(10) PRIMARY KEY NOT NULL,
)

CREATE TABLE Catalogs (
  catalogId varchar2(10) PRIMARY KEY NOT NULL,
  manufacturerId varchar2(10) PRIMARY KEY FOREIGN KEY REFERENCES Manufacturers(manufacturerId) NOT NULL
)

CREATE TABLE Locations (
  locationId varchar2(10) PRIMARY KEY NOT NULL,
  fulfillerId varchar2(10) PRIMARY KEY FOREIGN KEY REFERENCES Fulfillers(fulfillerId) NOT NULL,
  name varchar2(50),
  type varchar2(50),
  latitude decimal(9,6),
  longitude decimal(9,6),
  status varchar2(50),
  safetyStockLimitDefault varchar2(10)
)

CREATE TABLE LocationOffersCatalogs (
  catalogId varchar2(10) FOREIGN KEY REFERENCES Catalogs(catalogId) NOT NULL,
  locationId varchar2(10) FOREIGN KEY REFERENCES Locations(locationId) NOT NULL,
  fulfillerId varchar2(10) FOREIGN KEY REFERENCES Fulfillers(fulfillerId) NOT NULL
)

CREATE TABLE Fulfillers (
  fulfillerId varchar2(10) PRIMARY KEY NOT NULL
)

CREATE TABLE FulfillerCarriesProducts (
  fulfillerId varchar2(10) FOREIGN KEY REFERENCES Fulfillers(fulfillerId) NOT NULL,
  productUpc varchar2(10) FOREIGN KEY REFERENCES Products(upc) NOT NULL,
  sku varchar2(10) NOT NULL
)

CREATE TABLE Products (
  upc varchar2(10) PRIMARY KEY NOT NULL,
  catalogId varchar2(10) FOREIGN KEY REFERENCES Catalogs(catalogId) NOT NULL,
  manufacturerId varchar2(10) FOREIGN KEY REFERENCES Manufacturers(manufacturerId) NOT NULL,
  name varchar2(50)
)

CREATE TABLE Bins (
  locationId varchar2(10) PRIMARY KEY FOREIGN KEY REFERENCES Locations(locationId) NOT NULL,
  fulfillerId varchar2(10) PRIMARY KEY FOREIGN KEY REFERENCES Fulfillers(fulfillerId) NOT NULL,
  name varchar2(50) PRIMARY KEY,
  type varchar2(50),
  status varchar2(10)
)

CREATE TABLE BinContainsProducts (
  productUpc varchar2(10) FOREIGN KEY REFERENCES Products(upc) NOT NULL,
  allocated varchar2(6),
  onHand varchar2(6)
)

CREATE TABLE LocationSellsProducts (
  ltd varchar2(10),
  storeSku varchar2(10),
  safetyStock varchar2(10)
)
