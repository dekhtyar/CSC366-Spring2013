-- Kevin Stein  kestein@calpoly.edu
-- Alex Spotnitz  aspotnitz@gmail.com
-- Stephen Calabrese  sccalabr@calpoly.edu
-- Alex Boltunov  aboltunov@gmail.com
-- Luke Larson  lplarson@calpoly.edu

CREAT TABLE Manufacturers (
   ManufacturerId           INTEGER PRIMARY KEY
);

CREATE TABLE Catalogues (
   ManufacturerId           INTEGER REFERENCES Manufacturers,
   CatalogueId              INTEGER,
   PRIMARY KEY (ManufacturerId, CatalogueId)
);

CREATE TABLE Fulfillers (
   FulfillerId              VARCHAR(50) PRIMARY KEY,
   FulfillerName            VARCHAR(50)
);

CREATE TABLE Locations (
   FulfillerId              VARCHAR(50) REFERENCES Fulfillers,
   FulfillerLocationId      VARCHAR(50),
   Name                     VARCHAR(20),
   Type                     VARCHAR(20),
   Latitude                 FLOAT,
   Longitude                FLOAT,
   Status                   INTEGER,
   DefaultSafetyStockLimit  INTEGER,
   PRIMARY KEY (FulfillerId, FulfillerLocationId)
);

CREATE TABLE Items (
   UPC                      CHAR(14) PRIMARY KEY, 
   ManufacturerId           INTEGER,
   CatalogueId              INTEGER,
   Name                     VARCHAR(80),
   FOREIGN KEY (ManufacturerId, CatalogueId)
    REFERENCES Catalogues (ManufacturerId, CatalogueId)
);

CREATE TABLE FulfilledBy (
   UPC                      CHAR(14) REFERENCES Items,
   FulfillerId              VARCHAR(50) REFERENCES Fulfillers,
   SKU                      VARCHAR(50),
   PRIMARY KEY (UPC, FulfillerId),
   UNIQUE (SKU, FulfillerId)
);

CREATE TABLE Bins (
   FulfillerId              VARCHAR(50),
   FulfillerLocationId      VARCHAR(50),
   Name                     VARCHAR(20),
   BinType                  VARCHAR(20),
   BinStatus                VARCHAR(20),
   PRIMARY KEY (FulfillerId, FulfillerLocationId, Name),
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations (FulfillerId, FulfillerLocationId)
);

CREATE TABLE StoredIn (
   SKU                      VARCHAR(50),
   FulfillerId              VARCHAR(50),
   FulfillerLocationId      VARCHAR(50),
   Name                     VARCHAR(20),
   OnHand                   INTEGER,
   Allocated                INTEGER,
   PRIMARY KEY (SKU, FulfillerId, FulfillerLocationId, Name),
   FOREIGN KEY (SKU, FulfillerId)
    REFERENCES FulfilledBy (SKU, FulfillerId),
   FOREIGN KEY (FulfillerId, FulfillerLocationId, Name)
    REFERENCES Bins (FulfillerId, FulfillerLocationId, Name)
);

CREATE TABLE StoredAt (
   SKU                      VARCHAR(50),
   FulfillerId              VARCHAR(50),
   FulfillerLocationId      VARCHAR(50),
   LTD                      FLOAT,
   SafetyStockLimit         INTEGER,
   PRIMARY KEY (SKU, FulfillerId, FulfillerLocationId),
   FOREIGN KEY (SKU, FulfillerId)
    REFERENCES FulfilledBy (SKU, FulfillerId),
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations (FulfillerId, FulfillerLocationId)
); 

CREATE TABLE SubscribeTo (
   FulfillerId              VARCHAR(50),
   FulfillerLocationId      VARCHAR(50),
   ManufacturerId           INTEGER,
   CatalogueId              INTEGER,
   PRIMARY KEY (FulfillerId, FulfillerLocationId, ManufacturerId, CatalogueId),
   FOREIGN KEY (FulfillerId, FulfillerLocationId)
    REFERENCES Locations (FulfillerId, FulfillerLocationId),
   FOREIGN KEY (ManufacturerId, CatalogueId)
    REFERENCES Catalogues (ManufacturerId, CatalogueId)
);

