--Kevin Stein  kestein@calpoly.edu
--Alex Spotnitz  aspotnitz@gmail.com
--Stephen Calabrese  sccalabr@calpoly.edu
--Alex Boltunov  aboltunov@gmail.com
--Luke Larson  lplarson@calpoly.edu
CREATE TABLE FullfillerIds (
   FullfillerId            VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Locations (
   FullfillerId            VARCHAR2(50) REFERENCES FullfillerIds,
   FullfillerLocationId	   VARCHAR2(50) UNIQUE,
   Name                    VARCHAR2(20),
   Type                    VARCHAR2(20),
   Latitude                FLOAT,
   Longitude               FLOAT,
   Status                  INTEGER,
   DefaultSafetyStockLimit	INTEGER,
   PRIMARY KEY(FullfillerId, FullfillerLocationId),
   UNIQUE(Latitude, Longitude)   
);

CREATE TABLE Manufacturers (
   ManufacturerId	   VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Catalogues (
   CatalogueId	      VARCHAR2(50) PRIMARY KEY,
   ManufacturerId 	VARCHAR2(50) REFERENCES Manufacturers
);

CREATE TABLE Items (
   UPC               VARCHAR2(20) PRIMARY KEY, 
   Name              VARCHAR2(80), 
   ManufacturerId    VARCHAR2(20) REFERENCES Manufacturers, 
   CatalogueId    	VARCHAR2(20) REFERENCES Catalogues
);

CREATE TABLE Bins (
   Name                    VARCHAR2(20),
   FullfillerId            VARCHAR2(50) REFERENCES FullfillerIds,
   FullfillerLocationId 	VARCHAR2(50) REFERENCES Locations(FullfillerLocationId),
   PRIMARY KEY(Name, FullfillerId, FullfillerLocationId)
   --UNIQUE(Name, FullfillerLocationId)
);

CREATE TABLE StoredIn (
   UPC            VARCHAR2(20) REFERENCES Items,
   OnHand         INTEGER,
   Allocated      INTEGER,
   BinName        VARCHAR2(20) REFERENCES Bins(Name),
   PRIMARY KEY(UPC, BinName)
);

CREATE TABLE StoredAt (
   LTD                     INTEGER,
   SafetyStockLimit        INTEGER,
   UPC                     VARCHAR2(20) REFERENCES Items,
   FullfillerId            VARCHAR2(50) REFERENCES FullfillerIds,
   FullfillerLocationId	   VARCHAR2(50) REFERENCES Locations(FullfillerLocationId),
   PRIMARY KEY(UPC, FullfillerId, FullfillerLocationId)
); 

CREATE TABLE SubscribesTo (
   FullfillerId            VARCHAR2(50) REFERENCES FullfillerIds,
   FullfillerLocationId	   VARCHAR2(50) REFERENCES Locations(FullfillerLocationId),
   CatalogueId             VARCHAR2(50) REFERENCES Catalogues,
   PRIMARY KEY(CatalogueId, FullfillerId, FullfillerLocationId)
);

CREATE TABLE FullfilledBy (
   SKU				VARCHAR2(50),
	FullfillerId			VARCHAR2(50) REFERENCES FullfillerIds,
	UPC				VARCHAR2(20) REFERENCES Items,
	PRIMARY KEY(SKU, FullfillerId, UPC)
);

