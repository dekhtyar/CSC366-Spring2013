--Team Ross
-----------
--Ian McBride
--Ross McKelvie
--Matt Tondreau
--Matt Schirle
--Riley Lew

CREATE TABLE Manufacturers (
  manufacturerId varchar2(10) PRIMARY KEY,
)

CREATE TABLE Catalog (
  catalogId varchar2(10) PRIMARY KEY,
  manufacturerId varchar2(10) FOREIGN KEY REFERENCES Manufacturers(manufacturerId)
)

CREATE TABLE Locations (
  extFulfLocId varchar2(10) PRIMARY KEY,
  name text,
  type text,
  latitude decimal(9,6),
  longitude decimal(9,6),
  status text,
  safetyStockLimitDefault varchar2(10)
)

CREATE TABLE Offers (
  catalogId varchar2(10) FOREIGN KEY,
  extFulfLocId varchar2(10) FOREIGN KEY
)

CREATE TABLE Fulfillers (
  fulfillerId varchar2(10) PRIMARY KEY
)

CREATE TABLE FulfillerCarries (
  fulfillerId varchar2(10) FOREIGN KEY,
  productUpc varchar2(10) FOREIGN KEY,
  sku varchar2(10)
)

CREATE TABLE Products (
  upc varchar2(10) PRIMARY KEY,
  name text
)

CREATE TABLE Bins (
  name varchar2(10),
  type text,
  status varchar2(10)
)

CREATE TABLE Contains (
  productUpc varchar2(10) FOREIGN KEY,
  allocated varchar2(6),
  onHand varchar2(6)
)

CREATE TABLE LocationSells (
  ltd varchar2(10),
  storeSku varchar2(10),
  safetyStock varchar2(10)
)

CREATE TABLE Indexes (
  productUpc varchar2(10) FOREIGN KEY,
  catalogId varchar2(10) FOREIGN KEY,
  manufacturerId varchar2(10) FOREIGN KEY
)
