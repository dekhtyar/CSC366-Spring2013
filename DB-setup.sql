--Kevin Stein  kestein@calpoly.edu
--Alex Spotnitz  aspotnitz@gmail.com
--Stephen Calabrese  sccalabr@calpoly.edu
--Alex Boltunov  aboltunov@gmail.com
--Luke Larson  lplarson@calpoly.edu

CREATE TABLE Manufacturers (
   ManufacturerId           VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Catalogues (
   CatalogueId              VARCHAR2(50) PRIMARY KEY,
   ManufacturerId           VARCHAR2(50) REFERENCES Manufacturers
);

CREATE TABLE Fulfillers (
   FulfillerId              INTEGER PRIMARY KEY
);

CREATE TABLE Locations (
   FulfillerId              INTEGER REFERENCES Fulfillers,
   FulfillerLocationId      INTEGER,
   Name                     VARCHAR2(40),
   Type                     VARCHAR2(30),
   Latitude                 FLOAT,
   Longitude                FLOAT,
   Status                   Varchar(30),
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
   FulfillerId              INTEGER REFERENCES Fulfillers,
   FulfillerLocationId      INTEGER,
   PRIMARY KEY(Name, FulfillerId, FulfillerLocationId),
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations(FulfillerId, FulfillerLocationId)
);

CREATE TABLE StoredIn (
   UPC                      CHAR(12)     REFERENCES Items,
   FulfillerId              INTEGER REFERENCES Fulfillers,
   FulfillerLocationId      INTEGER,
   Name                     VARCHAR2(20),
   OnHand                   INTEGER,
   Allocated                INTEGER,
   PRIMARY KEY(UPC, FulfillerId, FulfillerLocationId, Name),
   FOREIGN KEY (Name, FulfillerId, FulfillerLocationId)
    REFERENCES Bins(Name, FulfillerId, FulfillerLocationId)
);

CREATE TABLE StoredAt (
   UPC                      CHAR(12)     REFERENCES Items,
   FulfillerId              INTEGER REFERENCES Fulfillers,
   FulfillerLocationId      INTEGER,
   LTD                      FLOAT,
   SafetyStockLimit         INTEGER,
   PRIMARY KEY(UPC, FulfillerId, FulfillerLocationId),
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations(FulfillerId, FulfillerLocationId)
); 

CREATE TABLE SubscribeTo (
   FulfillerId              INTEGER REFERENCES Fulfillers,
   FulfillerLocationId      INTEGER,
   CatalogueId              VARCHAR2(50) REFERENCES Catalogues,
   PRIMARY KEY(FulfillerId, FulfillerLocationId, CatalogueId),
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations(FulfillerId, FulfillerLocationId)
);

CREATE TABLE FulfilledBy (
   UPC                      CHAR(12)     REFERENCES Items,
   FulfillerId              INTEGER REFERENCES Fulfillers,
   SKU                      VARCHAR2(50),
   PRIMARY KEY(UPC, FulfillerId)
);

CREATE TABLE ListedIn (
   UPC                      CHAR(12)     REFERENCES Items,
   CatalogueId              VARCHAR2(50) REFERENCES Catalogues,
   PRIMARY KEY(UPC, CatalogueId)
);

