--  Slightly Blue Team
-- 
--  Alejandro Cervantes (acerva01)
--  Corey Farwell
--  Kyle Sletten
--  Lance Tyler
--  Ray Tam (rmtam)
-- 
--  CPE366 - Spring 2013

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
    InternalID          INT,
    ExternalID          VARCHAR(20),
    Name                VARCHAR(60),
    StoreType           VARCHAR(60),   -- i.e StoreFront, Warehouse, ect
    Latitude            FLOAT,
    Longitude           FLOAT,
    Status              VARCHAR(15),
    SafetyStock         INT,  -- Stocks info,
    PRIMARY KEY (InternalID),
    FOREIGN KEY (FulfillerID)
        REFERENCES Fulfiller (FulfillerID)
);
CREATE TABLE Bin (
    LocationID  INT,
    Name        VARCHAR(25),
    BinType     VARCHAR(20),
    BinStatus   VARCHAR(20),
    PRIMARY KEY (LocationID, Name),
    FOREIGN KEY (LocationID)
        REFERENCES Location (InternalID)
);
CREATE TABLE Seller (
    MfcID       INT,
    CatalogID   INT,
    LocationID  INT,
    PRIMARY KEY (MfcID, CatalogID, LocationID),
    FOREIGN KEY (MfcID, CatalogID)
        REFERENCES Catalog (MfcID, CatalogID),
    FOREIGN KEY (LocationID)
        REFERENCES Location
);
CREATE TABLE FulfillerItem (
    FulfillerID INT,
    SKU         INT,
    UPC         INT,
    PRIMARY KEY (FulfillerID, SKU),
    FOREIGN KEY (FulfillerID)
        REFERENCES Fulfiller (FulfillerID),
    FOREIGN KEY (UPC)
        REFERENCES Item (UPC)
);
CREATE TABLE Stock (
    LocationID  INT,
    FulfillerID INT,
    SKU         INT,
    Quantity    INT,
    Safety      INT,
    LTD         FLOAT,
    PRIMARY KEY (LocationID, SKU),
    FOREIGN KEY (LocationID)
        REFERENCES Location (InternalID),
    FOREIGN KEY (FulfillerID, SKU)
        REFERENCES FulfillerItem (FulfillerID, SKU)
);
CREATE TABLE OnHand (
    LocationID  INT,
    Name        VARCHAR(25),
    FulfillerID INT,
    SKU         INT,
    Quantity    INT,
    Allocated   INT,
    PRIMARY KEY (LocationID, Name, SKU),
    FOREIGN KEY (LocationID, Name)
        REFERENCES Bin (LocationID, Name),
    FOREIGN KEY (FulfillerID, SKU)
        REFERENCES FulfillerItem (FulfillerID, SKU)
);
CREATE TABLE OnHandOrder (
    OrderID     INT,
    LocationID  INT,
    Name        VARCHAR(25),
    FulfillerID INT,
    SKU         INT,
    CreatedOn   DATE,
    Quantity    INT,
    PRIMARY KEY (OrderID, LocationID, Name, FulfillerID, SKU),
    FOREIGN KEY (LocationID, Name)
        REFERENCES Bin (LocationID, Name),
    FOREIGN KEY (LocationID, Name, SKU)
        REFERENCES OnHand (LocationID, Name, SKU)
);
