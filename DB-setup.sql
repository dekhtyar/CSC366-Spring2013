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
   MfcID   INT,
   PRIMARY KEY (MfcID)
);
CREATE TABLE Catalog (
   MfcID        INT,
   CatalogID    INT,
   PRIMARY KEY (MfcID, CatalogID),
   FOREIGN KEY (MfcID)
        REFERENCES Manufacturer (MfcID)
);
CREATE TABLE Item (
    UPC           INT PRIMARY KEY,
    MfcID         INT,
    CatalogID     INT,
    FOREIGN KEY (MfcID, CatalogID)
        REFERENCES Catalog (MfcID, CatalogID)
);
CREATE TABLE Fulfiller (
    FulfillerID INT PRIMARY KEY
);
CREATE TABLE Location (
    FulfillerID         INT,
    ExternalID          INT,
    Name                VARCHAR(60),
    StoreType           VARCHAR(60),   -- i.e StoreFront, Warehouse, ect
    Latitude            FLOAT,
    Longitude           FLOAT,
    Status              VARCHAR(15),
    SafetyStock         INT,  -- Stocks info,
    PRIMARY KEY (FulfillerID, ExternalID),
    FOREIGN KEY (FulfillerID)
        REFERENCES Fulfiller (FulfillerID)
);
CREATE TABLE Bin (
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR(25),
    PRIMARY KEY (FulfillerID, ExternalID, Name),
    FOREIGN KEY (FulfillerID, ExternalID)
        REFERENCES Location (FulfillerID, ExternalID)
);
CREATE TABLE Seller (
    MfcID       INT,
    CatalogID   INT,
    FulfillerID INT,
    ExternalID  INT,
    PRIMARY KEY (MfcID, CatalogID, FulfillerID, ExternalID),
    FOREIGN KEY (MfcID, CatalogID)
        REFERENCES Catalog (MfcID, CatalogID),
    FOREIGN KEY (FulfillerID, ExternalID)
        REFERENCES Location(FulfillerID, ExternalID)
);
CREATE TABLE FulfillerItem (
    FulfillerID INT,
    SKU         INT,
    UPC         INT,
    PRIMARY KEY (FulfillerID, SKU),
    FOREIGN KEY (UPC)
        REFERENCES Item (UPC)
);
CREATE TABLE Stock (
    FulfillerID INT,
    ExternalID  INT,
    SKU         INT,
    Quantity    INT,
    Safety      INT,
    LTD         INT,
    PRIMARY KEY (FulfillerID, ExternalID, SKU),
    FOREIGN KEY (FulfillerID, ExternalID)
        REFERENCES Location (FulfillerID, ExternalID),
    FOREIGN KEY (FulfillerID, SKU)
        REFERENCES FulfillerItem (FulfillerID, SKU)
);
CREATE TABLE OnHand (
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR(25),
    SKU         INT,
    Quantity    INT,
    Allocated   INT,
    PRIMARY KEY (FulfillerID, ExternalID, Name, SKU),
    FOREIGN KEY (FulfillerID, ExternalID, Name)
        REFERENCES Bin (FulfillerID, ExternalID, Name),
    FOREIGN KEY (FulfillerID, SKU)
        REFERENCES FulfillerItem (FulfillerID, SKU)
);
CREATE TABLE OnHandOrder (
    OrderID     INT PRIMARY KEY,
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR(25),
    SKU         INT,
    CreatedOn   DATE,
    Quantity    INT,
    FOREIGN KEY (FulfillerID, ExternalID, Name)
        REFERENCES Bin (FulfillerID, ExternalID, Name),
    FOREIGN KEY (FulfillerID, ExternalID, Name, SKU)
        REFERENCES OnHand (FulfillerID, ExternalID, Name, SKU)
);
