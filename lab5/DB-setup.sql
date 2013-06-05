/* Team GLADE: George Reyes, Lauren Thurston, Adin Miller, Diego Torres, Elizabeth Loui, Date: 05/09/2013, DB-setup.sql */

CREATE TABLE Retailer (
    FulfillerId INT PRIMARY KEY, 
    Name VARCHAR(75) 
    );

CREATE TABLE Location (
    FulfillerId INT REFERENCES Retailer(FulfillerId),
    ExternalFulfillerLocationId VARCHAR(25),
    InternalFulfillerLocationId INT PRIMARY KEY,
    Type VARCHAR(25), 
    Description VARCHAR(50),
    Latitude FLOAT,
    Longitude FLOAT,
    Status VARCHAR(25),
    SafetyStockLimit INT
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
    Id INT PRIMARY KEY AUTO_INCREMENT,
    InternalFulfillerLocationId INT REFERENCES Location(InternalFulfillerLocationId),
    ManufacturerId INT REFERENCES Manufacturer(ManufacturerId),
    CatalogId INT REFERENCES Catalog(CatalogId),
    UNIQUE(InternalFulfillerLocationId, ManufacturerId, CatalogId)
    );

CREATE TABLE StoreBin (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    InternalFulfillerLocationId INT REFERENCES Location(InternalFulfillerLocationId),
    Status VARCHAR(25),
    Type VARCHAR(25),
    Name VARCHAR(25),
    Description VARCHAR(50),
    UNIQUE(InternalFulfillerLocationId, Name)
    );

CREATE TABLE Product (
    Name VARCHAR(25),
    UPC VARCHAR(25) PRIMARY KEY,
    ManufacturerId INT REFERENCES Manufacturer(ManufacturerId),
    CatalogId INT REFERENCES Catalog(CatalogId)
    );
 
CREATE TABLE RetailerProduct (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    FulfillerId INT REFERENCES Retailer(FulfillerId),
    UPC VARCHAR(25) REFERENCES Product(UPC),
    SKU VARCHAR(25),
    UNIQUE(FulfillerId, UPC)
    );

CREATE TABLE LocationProduct (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    InternalFulfillerLocationId INT REFERENCES Location(InternalFulfillerLocationId),
    RetailerProductId INT REFERENCES RetailerProduct(Id),
    LTD INT,
    SafeStockLimit INT,
    UNIQUE(InternalFulfillerLocationId, RetailerProductId)
    );

CREATE TABLE ContainedInBin (
    BinId INT REFERENCES StoreBin(Id),
    LocationProductId INT REFERENCES LocationProduct(Id),
    OnHand INT,
    Allocated INT,		
    PRIMARY KEY(LocationProductId, BinId)
    );

