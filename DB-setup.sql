--  Slightly Blue Team
-- 
--  Alejandro Cervantes (acerva01)
--  Corey Farwell
--  Kyle Sletten
--  Lance Tyler
--  Ray Tam
-- 
--  CPE366 - Spring 2013
--  Lab 3


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
    FOREIGN KEY (ItemUPC, ItemSKU, FulfillerID) REFERENCES StoreItem  
);




CREATE TABLE Bin (
);


CREATE TABLE Fulfiller (
    Id      INT PRIMARY KEY
);

CREATE TABLE Item (
    UPC     INT PRIMARY KEY
);

CREATE TABLE Manufacturer (

);

CREATE TABLE Catalog (

);
