-- Aaron Phung, Jack Henderson, Brandon Tanaka, Raymond Ching, James Aldag
-- CPE 366
-- Lab 4 (Revised from Lab 3)
-- Database Setup

CREATE TABLE Fulfiller(
    ID int PRIMARY KEY,
    Name VARCHAR2(30)
);

CREATE TABLE Locations(
    FulfillerID int REFERENCES Fulfiller,
    RetailerLocationID int, 
    ExternalLocationID  VARCHAR2(25),
    LocationName        VARCHAR2(50),
    LocationType        VARCHAR2(30),
    Latitude    FLOAT,
    Longitude   FLOAT,
    Status      INT CHECK (Status = 1 OR Status = 2),
    CountryCode VARCHAR2(10),
    SafeStock   int,
    ManufacturerID int,
    CatalogID   int,
    PRIMARY KEY(FulfillerID, ID)
);

CREATE TABLE Bins(
    BinID       int PRIMARY KEY,
    FulfillerID int REFERENCES Fulfiller,
    LocationID  int,
    BinType     VARCHAR2(20),
    BinStatus   VARCHAR2(20),
    BinName     VARCHAR2(20),
    FOREIGN KEY(FulfillerID, LocationID) REFERENCES Locations,
    UNIQUE(FulfillerID, LocationID, BinName)
);

CREATE TABLE Products(
    UPC         int PRIMARY KEY,
    Name        VARCHAR2(50)
);

CREATE TABLE FulfillerProduct(
    FulfillerID int REFERENCES Fulfiller,
    UPC         int REFERENCES Products,
    SKU         VARCHAR2(15),
    PRIMARY KEY(FulfillerID, UPC),
    UNIQUE (FulfillerID, SKU)
);

CREATE TABLE BinProduct(
    BinID       int REFERENCES Bins,
    UPC         int REFERENCES Products,
    FulfillerID int REFERENCES Fulfiller, 
    SKU         VARCHAR2(15),
    OnHand      int,
    Allocated   int,
    CHECK(OnHand - Allocated >= 0),
    FOREIGN KEY(FulfillerID, SKU) REFERENCES FulfillerProduct(FulfillerID, SKU),
    PRIMARY KEY(BinID, UPC)
);

CREATE TABLE LocationProduct(
    FulfillerID int REFERENCES Fulfiller,
    LocationID  int,
    UPC         int REFERENCES Products,
    SKU         VARCHAR2(15),
    SafeStock   int NOT NULL,
    LTD         int NOT NULL,
    FOREIGN KEY(FulfillerID, SKU) REFERENCES FulfillerProduct(FulfillerID, SKU),
    FOREIGN KEY(FulfillerID, LocationID) REFERENCES Locations,
    PRIMARY KEY(FulfillerID, LocationID, UPC)
);
