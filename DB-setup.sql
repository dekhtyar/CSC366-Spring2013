-- Aaron Phung, Jack Henderson, Brandon Tanaka, Raymond Ching, James Aldag
-- CPE 366
-- Lab 3
-- Database Setup

CREATE TABLE Locations(
    FulfillerID int,
    ID          int,
    Name        VARCHAR2(50),
    Type        VARCHAR2(30),
    Latitude    FLOAT,
    Longitude   FLOAT,
    Status      VARCHAR2(10),
    SafeStock   int,
    ManufacturerID int,
    CatalogID   int,
    PRIMARY KEY(FulfillerID, ID)
);

CREATE TABLE Bins(
    BinID       int PRIMARY KEY,
    FulfillerID int,
    LocationID  int,
    BinName     VARCHAR2(20),
    FOREIGN KEY(FulfillerID, LocationID) REFERENCES Locations,
    UNIQUE(FulfillerID, LocationID, BinName)
);

CREATE TABLE Products(
    UPC         int PRIMARY KEY,
    Name        VARCHAR2(50)
);

CREATE TABLE FulfillerProduct(
    FulfillerID int,
    UPC         int REFERENCES Products,
    SKU         int,
    PRIMARY KEY(FulfillerID, UPC)
);

CREATE TABLE BinProduct(
    BinID       int REFERENCES Bins,
    UPC         int REFERENCES Products,
    SKU         VARCHAR2(15) REFERENCES FulfillerProduct(SKU),
    OnHand      int,
    Allocated   int,
    CHECK(OnHand - Allocated >= 0),
    PRIMARY KEY(BinID, UPC)
);

CREATE TABLE LocationProduct(
    FulfillerID int,
    LocationID  int,
    UPC         int REFERENCES Products,
    SafeStock   int NOT NULL,
    LTD         int NOT NULL,
    FOREIGN KEY(FulfillerID, LocationID) REFERENCES Locations,
    PRIMARY KEY(FulfillerID, LocationID, UPC)
);
