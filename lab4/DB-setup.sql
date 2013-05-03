--Kevin Stein  kestein@calpoly.edu
--Alex Spotnitz  aspotnitz@gmail.com
--Stephen Calabrese  sccalabr@calpoly.edu
--Alex Boltunov  aboltunov@gmail.com
--Luke Larson  lplarson@calpoly.edu

CREATE TABLE Manufacturers (
   ManufacturerId           VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Catalogues (
   ManufacturerId           VARCHAR2(50) REFERENCES Manufacturers,
   CatalogueId              VARCHAR2(50),
   PRIMARY KEY (ManufacturerId, CatalogueId)
);

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
   PRIMARY KEY (FulfillerId, FulfillerLocationId),
   UNIQUE (Latitude, Longitude)   
);

CREATE TABLE Items (
   UPC                      CHAR(12)     PRIMARY KEY, 
   ManufacturerId           VARCHAR(50),
   CatalogueId              VARCHAR(50),
   Name                     VARCHAR2(80),
   FOREIGN KEY (ManufacturerId, CatalogueId)
    REFERENCES Catalogues (ManufacturerId, CatalogueId)
);

CREATE TABLE FulfilledBy (
   UPC                      CHAR(12)     REFERENCES Items,
   FulfillerId              VARCHAR2(50) REFERENCES Fulfillers,
   SKU                      VARCHAR2(50),
   PRIMARY KEY (UPC, FulfillerId),
   UNIQUE (FulfillerId, SKU)
);

CREATE TABLE Bins (
   FulfillerId              VARCHAR2(50),
   FulfillerLocationId      VARCHAR2(50),
   Name                     VARCHAR2(20),
   BinType                  VARCHAR2(20),
   PRIMARY KEY (FulfillerId, FulfillerLocationId, Name),
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations (FulfillerId, FulfillerLocationId)
);

CREATE TABLE StoredIn (
   UPC                      CHAR(12),
   FulfillerId              VARCHAR2(50),
   FulfillerLocationId      VARCHAR2(50),
   Name                     VARCHAR2(20),
   OnHand                   INTEGER,
   Allocated                INTEGER,
   PRIMARY KEY (UPC, FulfillerId, FulfillerLocationId, Name),
   FOREIGN KEY (UPC, FulfillerId)
    REFERENCES FulfilledBy,
   FOREIGN KEY (FulfillerId, FulfillerLocationId, Name)
    REFERENCES Bins
);

CREATE TABLE StoredAt (
   UPC                      CHAR(12),
   FulfillerId              VARCHAR2(50),
   FulfillerLocationId      VARCHAR2(50),
   LTD                      FLOAT,
   SafetyStockLimit         INTEGER,
   PRIMARY KEY (UPC, FulfillerId, FulfillerLocationId),
   FOREIGN KEY (UPC, FulfillerId)
    REFERENCES FulfilledBy,
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations (FulfillerId, FulfillerLocationId)
); 

CREATE TABLE SubscribeTo (
   FulfillerId              VARCHAR2(50),
   FulfillerLocationId      VARCHAR2(50),
   ManufacturerId           VARCHAR2(50),
   CatalogueId              VARCHAR2(50),
   PRIMARY KEY (FulfillerId, FulfillerLocationId, ManufacturerId, CatalogueId),
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations (FulfillerId, FulfillerLocationId),
   FOREIGN KEY (ManufacturerId, CatalogueId)
    REFERENCES Catalogues
);

