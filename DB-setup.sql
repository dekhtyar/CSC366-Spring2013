CREATE TABLE FullfillerIds (
	FullfillerId			VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Locations (
	FullfillerId			   VARCHAR2(50) REFERENCES FullfillerIds,
   FullfillerLocationId		VARCHAR2(50),
	Name				         VARCHAR2(20),
	Type				         VARCHAR2(20),
	Latitude			         FLOAT,
	Longitude			      FLOAT,
	Status				      INTEGER,
	DefaultSafetyStockLimit	INTEGER,
	PRIMARY KEY(FullfillerId, FullfillerLocationId),
	UNIQUE(Latitude, Longitude)
	);

CREATE TABLE Items (
	UPC				   VARCHAR2(20) PRIMARY KEY, 
	Name				   VARCHAR2(80), 
   ManufacturerId		VARCHAR2(20) REFERENCES Manufacturers, 
   CatalogueId			VARCHAR2(20) REFERENCES Catalogues
);

CREATE TABLE Bins (
	Name				         VARCHAR2(20),
	FullfillerId			   VARCHAR2(50) REFERENCES FullfillerIds,
   FullfillerLocationId		VARCHAR2(50) REFERENCES Locations(FullfillerLocationId),
	PRIMARY KEY(Name, FullfullerId, FullfillerLocationId)
	);

CREATE TABLE StoredIn (
	UPC				INTEGER REFERENCES Items,
	OnHand			INTEGER,
	Allocated	   INTEGER,
	Name				VARCHAR2(20) REFERENCES Bins,
	PRIMARY KEY(UPC, Name)
	);

CREATE TABLE StoredAt (
	LTD				         INTEGER,
	SafetyStockLimit		   INTEGER,
	UPC				         INTEGER REFERENCES Items,
	FullfillerId			   VARCHAR2(50) REFERENCES FullfillerIds,
   FullfillerLocationId		VARCHAR2(50) REFERENCES Locations(FullfillerLocationId),
	PRIMARY KEY(UPC, FullfullerId, FullfillerLocationId)
	); 

CREATE TABLE Manufacturers (
	ManufacturerId		VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Catalogues (
	CatalogueId			VARCHAR2(50) PRIMARY KEY,
	ManufacturerId		VARCHAR2(50) REFERENCES Manufacturers
);

CREATE TABLE SubscribesTo (
	FullfillerId			   VARCHAR2(50) REFERENCES FullfillerIds,
   FullfillerLocationId		VARCHAR2(50) REFERENCES Locations(FullfillerLocationId),
	CatalogueId			      VARCHAR2(50),
	PRIMARY KEY(CatalogueId, FullfullerId, FullfillerLocationId)
);

