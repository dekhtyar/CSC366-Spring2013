--Kevin Stein  kestein@calpoly.edu
--Alex Spotnitz  aspotnitz@gmail.com
--Stephen Calabrese  sccalabr@calpoly.edu
--Alex Boltunov  aboltunov@gmail.com
--Luke Larson  lplarson@calpoly.edu

CREATE TABLE Fulfillers (
   FulfillerId              VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Locations (
   FulfillerId              VARCHAR2(50) REFERENCES Fulfillers,
   FulfillerLocationId      VARCHAR2(50),
   Name                     VARCHAR2(20),
   Type                     VARCHAR2(20),
   Latitude                 FLOAT,
   Longitude                FLOAT,
   Status                   INTEGER,
   DefaultSafetyStockLimit  INTEGER,
   PRIMARY KEY(FulfillerId, FulfillerLocationId),
   UNIQUE(Latitude, Longitude)   
);

CREATE TABLE Items (
   UPC                      CHAR(12)     PRIMARY KEY, 
   Name                     VARCHAR2(80) 
);

CREATE TABLE Bins (
   Name                     VARCHAR2(20),
   FulfillerId              VARCHAR2(50) REFERENCES Fulfillers,
   FulfillerLocationId      VARCHAR2(50) REFERENCES Locations(FulfillerLocationId),
   PRIMARY KEY(Name, FulfillerId, FulfillerLocationId)
);

CREATE TABLE StoredIn (
   UPC                      CHAR(12)     REFERENCES Items,
   FulfillerId              VARCHAR2(50) REFERENCES Fulfillers,
   FulfillerLocationId      VARCHAR2(50) REFERENCES Locations(FulfillerLocationId),
   Name                     VARCHAR2(20) REFERENCES Bins,
   OnHand                   INTEGER,
   Allocated                INTEGER,
   PRIMARY KEY(UPC, FulfillerId, FulfillerLocationId, Name)
);

CREATE TABLE StoredAt (
   UPC                      CHAR(12)     REFERENCES Items,
   FulfillerId              VARCHAR2(50) REFERENCES Fulfillers,
   FulfillerLocationId      VARCHAR2(50) REFERENCES Locations(FulfillerLocationId),
   LTD                      FLOAT,
   SafetyStockLimit         INTEGER,
   PRIMARY KEY(UPC, FulfillerId, FulfillerLocationId)
); 

CREATE TABLE Manufacturers (
   ManufacturerId           VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Catalogues (
   CatalogueId              VARCHAR2(50) PRIMARY KEY,
   ManufacturerId           VARCHAR2(50) REFERENCES Manufacturers
);

CREATE TABLE SubscribeTo (
   FulfillerId              VARCHAR2(50) REFERENCES Fulfillers,
   FulfillerLocationId      VARCHAR2(50) REFERENCES Locations(FulfillerLocationId),
   CatalogueId              VARCHAR2(50) REFERENCES Catalogues,
   PRIMARY KEY(FulfillerId, FulfillerLocationId, CatalogueId)
);

CREATE TABLE FulfilledBy (
   UPC                      CHAR(12)     REFERENCES Items,
   FulfillerId              VARCHAR2(50) REFERENCES Fulfillers,
   SKU                      VARCHAR2(50),
   PRIMARY KEY(UPC, FulfillerId)
);

CREATE TABLE ListedIn (
   UPC                      CHAR(12)     REFERENCES Items,
   CatalogueId              VARCHAR2(50) REFERENCES Catalogues,
   PRIMARY KEY(UPC, CatalogueId)
);

