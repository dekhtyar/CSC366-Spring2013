/* Team GLADE: George Reyes, Lauren Thurston, Adin Miller, Diego Torres, Elizabeth Loui, Date: 05/09/2013, DB-setup.sql */

CREATE TABLE Retailer (
    FulfillerId INT PRIMARY KEY, 
    Name VARCHAR2(25) 
    );

CREATE TABLE Location (
    FulfillerId INT REFERENCES Retailer(FulfillerId),
    ExternalFulfillerLocationId INT,
    InternalFulfillerLocationId INT PRIMARY KEY,
    Type VARCHAR2(25), 
    Description VARCHAR2(50),
    Latitude INT,
    Longitude INT,
    Status VARCHAR(25),
    SafetyStockLimit INT,
    UNIQUE(FulfillerId, ExternalFulfillerLocationId)
    );

CREATE TABLE Manufacturer (
    ManufacturerId INT PRIMARY KEY
    );

CREATE TABLE Catalog (
    CatalogId INT,
    ManufacturerId INT REFERENCES Manufacturer(ManufacturerId),
    PRIMARY KEY (CatalogId, ManufacturerId)
    );

CREATE TABLE CatalogServedByLocation (
    Id INT PRIMARY KEY,
    InternalFulfillerLocationId INT REFERENCES Location(InternalFulfillerLocationId),
    ManufacturerId INT REFERENCES Manufacturer(ManufacturerId),
    CatalogId INT REFERENCES Catalog(CatalogId),
    UNIQUE(InternalFulfillerLocationId, ManufacturerId, CatalogId)
    );

CREATE TABLE StoreBin (
    Id INT,
    InternalFulfillerLocationId INT REFERENCES Location(InternalFulfillerLocationId),
    Status VARCHAR2(25),
    Type VARCHAR2(25),
    Name VARCHAR2(25),
    Description VARCHAR2(50),
    PRIMARY KEY (InternalFulfillerLocationId, Name)
    );

CREATE TABLE Product (
    Name VARCHAR2(25),
    UPC VARCHAR2(25) PRIMARY KEY,
    ManufacturerId INT REFERENCES Manufacturer(ManufacturerId),
    CatalogId INT REFERENCES Catalog(CatalogId)
    );
 
CREATE TABLE RetailerProduct (
    Id INT PRIMARY KEY,
    FulfillerId INT REFERENCES Retailer(FulfillerId),
    UPC VARCHAR2(25) REFERENCES Product(UPC),
    SKU VARCHAR2(25),
    UNIQUE(FulfillerId, UPC)
    );

CREATE TABLE LocationProduct (
    Id INT PRIMARY KEY,
    InternalFulfillerLocationId INT REFERENCES Location(InternalFulfillerLocationId),
    RetailerProductId INT REFERENCES RetailerProduct(Id),
    LTD INT,
    SafeStockLimit INT
    );

CREATE TABLE ContainedInBin (
    BinId INT REFERENCES StoreBin(Id),
    LocationProductId INT REFERENCES LocationProduct(Id),
    OnHand INT,
    Allocated INT,		
    PRIMARY KEY(LocationProductId, BinId)
    );

