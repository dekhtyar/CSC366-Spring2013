/* Team GLADE:
   George Reyes
   Lauren Thurston
   Adin Miller
   Diego Torres
   Elizabeth Loui

   DB-setup.sql */

CREATE TABLE retailer (
    FulfillerId INT PRIMARY KEY, 
    Name VARCHAR2(25) 
    );

CREATE TABLE manufacturer (
    Id INT PRIMARY KEY
    );

CREATE TABLE catalog (
    Id INT PRIMARY KEY,
    ManufacturerId INT REFERENCES manufacturer(Id)
    );

CREATE TABLE inventoryRecord (
    Name VARCHAR2(25) UNIQUE,
    Upc INT,
    CatalogId INT REFERENCES catalog(Id),
    UNIQUE(UPC),
    PRIMARY KEY(CatalogId,UPC)
    );

CREATE TABLE retail_Location (
    FulfillerId INT REFERENCES retailer(FulfillerId), 
    ExternFulfillerLocId INT,
    Type VARCHAR2(25),
    SafetyStockLimit INT,
    CatalogSet VARCHAR2(25),	
    Latitude INT,
    Longitude INT,
    Name VARCHAR(25),
    Status VARCHAR(25),
    UNIQUE(ExternFulfillerLocId),
    PRIMARY KEY(FulfillerId,ExternFulfillerLocId)
    );

CREATE TABLE catalogServedByLocation (
    CatalogId INT REFERENCES catalog(Id),
    FulfillerId INT REFERENCES retailer(FulfillerId),
    ExternalFulfillerLocationId INT REFERENCES retail_Location(ExternFulfillerLocId)
    );

CREATE TABLE storeBin (
    BinId INT PRIMARY KEY,
    Name VARCHAR2(25),
    FulfillerId INT REFERENCES retailer(FulfillerId),
    ExternalFulfillerId INT REFERENCES retail_Location(ExternFulfillerLocId)
    );

CREATE TABLE retailerProduct (
    SKU VARCHAR2(25) PRIMARY KEY,
    UPC INT REFERENCES inventoryRecord(Upc),
    ManufacturerId INT REFERENCES manufacturer(Id),
    CatalogId INT REFERENCES catalog(Id)
    );

CREATE TABLE locationProduct (
    Id INT PRIMARY KEY,
    Name VARCHAR2(25) REFERENCES inventoryRecord(Name),
    SKU VARCHAR2(25) REFERENCES retailerProduct(SKU),
    UPC INT REFERENCES inventoryRecord(Upc),
    FulfillerId INT REFERENCES retailer(FulfillerId),
    ExternalFulfillerId INT REFERENCES retail_Location(ExternFulfillerLocId),
    LTD INT,
    SafeStockLimit INT,
    ONHAND INT CHECK(ONHAND >= 0),
    UNIQUE (SKU, UPC, FulfillerId,ExternalFulfillerId)
    );

CREATE TABLE containedInBin (
    LocationProductId INT REFERENCES locationProduct(Id),
    BinId INT REFERENCES storeBin(BinId),
    SafeStockLimit INT,		
    PRIMARY KEY(LocationProductId,BinId)
    );

