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

CREATE TABLE Catalog (
  catalogId varchar2(10) PRIMARY KEY NOT NULL,
  manufacturerId varchar2(10) FOREIGN KEY REFERENCES Manufacturers(manufacturerId) NOT NULL
)

CREATE TABLE Locations (
  locationId varchar2(10) PRIMARY KEY NOT NULL,
  fulfillerId varchar2(10) PRIMARY KEY NOT NULL,
  name varchar2(50),
  type varchar2(50),
  latitude decimal(9,6),
  longitude decimal(9,6),
  status varchar2(50),
  safetyStockLimitDefault varchar2(10)
)

CREATE TABLE Offers (
  catalogId varchar2(10) FOREIGN KEY NOT NULL,
  extFulfLocId varchar2(10) FOREIGN KEY NOT NULL
)

CREATE TABLE Fulfillers (
  fulfillerId varchar2(10) PRIMARY KEY NOT NULL
)

CREATE TABLE FulfillerCarries (
  fulfillerId varchar2(10) FOREIGN KEY NOT NULL,
  productUpc varchar2(10) FOREIGN KEY NOT NULL,
  sku varchar2(10) NOT NULL
)

CREATE TABLE Products (
  upc varchar2(10) PRIMARY KEY NOT NULL,
  catalogId varchar2(10) FOREIGN KEY NOT NULL,
  manufacturerId varchar2(10) FOREIGN KEY NOT NULL,
  name varchar2(50)
)

CREATE TABLE Bins (
  locationId varchar2(10) FOREIGN KEY NOT NULL,
  fulfillerId varchar2(10) FOREIGN KEY NOT NULL,
  name varchar2(50),
  type varchar2(50),
  status varchar2(10)
)

CREATE TABLE Contains (
  productUpc varchar2(10) FOREIGN KEY NOT NULL,
  allocated varchar2(6),
  onHand varchar2(6)
)

CREATE TABLE LocationSells (
  ltd varchar2(10),
  storeSku varchar2(10) NOT NULL,
  safetyStock varchar2(10)
)
