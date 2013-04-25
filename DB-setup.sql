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
   MfcID   INT PRIMARY KEY
);

CREATE TABLE Catalog (
   MfcID        INT,
   CatalogID    INT,
   PRIMARY KEY (MfcID, CatalogID)
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

-- END Mfc

-- BEGIN Fulfiller

CREATE TABLE Fulfiller (
    FulfillerID INT PRIMARY KEY,
);

CREATE TABLE Location (
    FulfillerID         INT,
    ExternalID          INT,
    Name                VARCHAR2(60),
    StoreType           VARCHAR2(60),   -- i.e StoreFront, Warehouse, ect
    Latitude            FLOAT,
    Longitude           FLOAT,
    Status              VARCHAR2(15),
    SafetyStock         INT,  -- Stocks info,
    PRIMARY KEY (FulfillerID, ExternalID),
    FOREIGN KEY (MfcID, CatalogID)
        REFERENCES Catalog (MfcID, CatalogID),
    FOREIGN KEY (FulfillerID)
        REFERENCES Fulfiller (FulfillerID)
);

-- Incomplete
CREATE TABLE Bin (
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR2(25),
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

-- END Fulfiller

-- BEGIN StoreItem

CREATE TABLE StoreItem (
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
        REFERENCES StoreItem (FulfillerID, SKU)
);

CREATE TABLE OnHand (
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR2(25),
    SKU         INT,
    Quantity    INT,
    Allocated   INT,
    PRIMARY KEY (FulfillerID, ExternalID, Name, SKU),
    FOREIGN KEY (FulfillerID, ExternalID, Name)
        REFERENCES Bin (FulfillerID, ExternalID, Name),
    FOREIGN KEY (FulfillerID, SKU)
        REFERENCES StoreItem (FulfillerID, SKU)
);

-- END Stock

-- BEGIN Order

CREATE TABLE OnHandOrder (
    OrderID     INT PRIMARY KEY,
    FulfillerID INT,
    ExternalID  INT,
    Name        VARCHAR2(25),
    SKU         INT,
    CreatedOn   DATE,
    Quantity    INT,
    FOREIGN KEY (FulfillerID, ExternalID, Name)
        REFERENCES Bin (FulfillerID, ExternalID, Name),
    FOREIGN KEY (FulfillerID, ExternalID, Name, SKU)
        REFERENCES OnHand (FulfillerID, ExternalID, Name, SKU)

);

-- END Order
