--  Slightly Blue Team
-- 
--  Alejandro Cervantes (acerva01)
--  Corey Farwell
--  Kyle Sletten
--  Lance Tyler
--  Ray Tam (rmtam)
-- 
--  CPE366 - Spring 2013
--  Lab 3

CREATE TABLE Manufacturer (

);

CREATE TABLE Catalog (
   --PRIMARY KEY?
);

CREATE TABLE Fulfiller (
    Id      INT PRIMARY KEY,
);

CREATE TABLE Item (
    UPC           INT PRIMARY KEY,
    SKU           INT PRIMARY KEY,
    -- CatalogId     INT,
    -- ManufactureId INT,
    FOREIGN KEY (FulfillerID) REFERENCES Fulfiller
);

CREATE TABLE Location (
    ExternalId          INT PRIMARY KEY,
    Name                VARCHAR2(60),
    StoreType           VARCHAR2(60),   -- i.e StoreFront, Warehouse, ect
    Latitude            FLOAT,
    Longitude           FLOAT,
    Status              VARCHAR2(15),
    SafetyStock         INT,  -- Stocks info
    CatalogID           INT,
    ManufacturerID      INT,  -- /stocks info
    ItemUPC             VARCHAR2(20), -- These foreign keys reference a StoreItem
    ItemSKU             VARCHAR2(20),
    FulfillerID         VARCHAR2(20),
    FOREIGN KEY (ManufacturerID, CatalogID) REFERENCES Catalog,
    FOREIGN KEY (FulfillerID) REFERENCES Fulfiller
);


CREATE TABLE Bin (
);

CREATE TABLE StoreItem (
    FulfillerID INT,
    SKU         INT,
    UPC         INT,
    PRIMARY KEY (FulfillerID, SKU),
    FOREIGN KEY (UPC) REFERENCES Item
);

CREATE TABLE OnHand (
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR2(20),
    SKU         INT,
    Count       INT,
    Allocated   INT,
    PRIMARY KEY (FulfillerID, ExternalID, Name, SKU),
    FOREIGN KEY (FulfillerID, ExternalID, Name) REFERENCES Bin,
    FOREIGN KEY (FulfillerID, SKU) REFERENCES StoreItem
);

CREATE TABLE Allocation (
    OrderId     INT PRIMARY KEY,
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR2(20),
    SKU         INT,
    CreatedOn   DATE,
    Quantity    INT,
    FOREIGN KEY (FulfillerID, ExternalID, Name) REFERENCES Bin,
    FOREIGN KEY (FulfillerID, ExternalID, Name, SKU) REFERENCES OnHand
);
