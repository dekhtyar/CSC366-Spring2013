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

-- BEGIN Mfc

CREATE TABLE Manufacturer (
   MfcId          INT PRIMARY KEY
);

CREATE TABLE Catalog (
   CatalogId      INT,
   MfcBy          INT,
   PRIMARY KEY (CatalogId, MfcBy)
   FOREIGN KEY (MfcBy) REFERENCES Manufacturer
);

CREATE TABLE Item (
    UPC           INT PRIMARY KEY,
    CatalogId     INT,
    ManufactureId INT,
    FOREIGN KEY (FulfillerID) REFERENCES Fulfiller
);

-- END Mfc

-- BEGIN Fulfiller

CREATE TABLE Fulfiller (
    FulfillerId      VARCHAR2(20) PRIMARY KEY,
);

CREATE TABLE Location (
    FulfillerID         VARCHAR2(20),
    ExternalId          INT PRIMARY KEY,
    Name                VARCHAR2(60),
    StoreType           VARCHAR2(60),   -- i.e StoreFront, Warehouse, ect
    Latitude            FLOAT,
    Longitude           FLOAT,
    Status              VARCHAR2(15),
    SafetyStock         INT,  -- Stocks info
    FOREIGN KEY (ManufacturerID, CatalogID) REFERENCES Catalog,
    FOREIGN KEY (FulfillerID) REFERENCES Fulfiller
);

-- Incomplete
CREATE TABLE Bin (
    Name                VARCHAR2(25),
    LocId               INT,
    PRIMARY KEY (Name, Location),
    FOREIGN KEY (LocId) REFERENCES Location
);

CREATE TABLE Seller (
    MfcID       INT,
    CatalogID   INT,
    FulfillerID INT,
    ExternalID  INT,
    PRIMARY KEY (MfcID, CatalogID, FulfillerID, ExternalID),
    FOREIGN KEY (MfcID, CatalogID) REFERENCES Catalog,
    FOREIGN KEY (FulfillerID, ExternalID) REFERENCES Location
);

-- END Fulfiller

-- BEGIN StoreItem

CREATE TABLE StoreItem (
    FulfillerID INT,
    SKU         INT,
    UPC         INT,
    PRIMARY KEY (FulfillerID, SKU),
    FOREIGN KEY (UPC) REFERENCES Item
);

CREATE TABLE Stock (
    FulfillerID INT,
    ExternalID  INT,
    SKU         INT,
    Quantity    INT,
    Safety      INT,
    LTD         INT,
    PRIMARY KEY (FulfillerID, ExternalID, SKU),
    FOREIGN KEY (FulfillerID, ExternalID) REFERENCES Location,
    FOREIGN KEY (FulfillerID, SKU) REFERENCESS StoreItem
);

CREATE TABLE OnHand (
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR2(20),
    SKU         INT,
    Quantity    INT,
    Allocated   INT,
    PRIMARY KEY (FulfillerID, ExternalID, Name, SKU),
    FOREIGN KEY (FulfillerID, ExternalID, Name) REFERENCES Bin,
    FOREIGN KEY (FulfillerID, SKU) REFERENCES StoreItem
);

-- END Stock

-- BEGIN Order

CREATE TABLE OnHandOrder (
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

-- END Order
