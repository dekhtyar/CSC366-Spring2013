CREATE TABLE Fulfiller(
    ID int PRIMARY KEY,
    Name VARCHAR2(30)
);

CREATE TABLE Location(
    FulfillerID int REFERENCES Fulfiller,
    Id int,
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

CREATE TABLE Bin(
    BinID       int PRIMARY KEY,
    FulfillerID int REFERENCES Fulfiller,
    LocationID  int,
    BinType     VARCHAR2(20),
    BinStatus   VARCHAR2(20),
    BinName     VARCHAR2(20),
    FOREIGN KEY(FulfillerID, LocationID) REFERENCES Location,
    UNIQUE(FulfillerID, LocationID, BinName)
);

CREATE TABLE Product(
    UPC         int PRIMARY KEY,
    Name        VARCHAR2(50)
);

CREATE TABLE FulfillerProduct(
    FulfillerID int REFERENCES Fulfiller,
    UPC         int REFERENCES Product,
    SKU         VARCHAR2(15),
    PRIMARY KEY(FulfillerID, UPC),
    UNIQUE (FulfillerID, SKU)
);

CREATE TABLE BinProduct(
    BinID       int REFERENCES Bin,
    UPC         int REFERENCES Product,
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
    UPC         int REFERENCES Product,
    SKU         VARCHAR2(15),
    SafeStock   int NOT NULL,
    LTD         int NOT NULL,
    FOREIGN KEY(FulfillerID, SKU) REFERENCES FulfillerProduct(FulfillerID, SKU),
    FOREIGN KEY(FulfillerID, LocationID) REFERENCES Location,
    PRIMARY KEY(FulfillerID, LocationID, UPC)
);
