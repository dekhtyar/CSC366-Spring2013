Create Table Locations(
   ExtFulfillerID VARCHAR(15),
   FulfillerID INT,
   Name VARCHAR(100),
   Type VARCHAR(100),
   Latitude double,
   Longitude double,
   Status INT,
   SafetyStock INT CHECK (SafetyStock >=0),
   Primary Key (ExtFulfillerID, FulfillerID)
);

Create Table Clients(
   CatID INT,
   ManID INT,
   Primary Key (CatID, ManID)
);

Create Table Serves(
   CatID INT,
   ManID INT,
   ExtFulfillerID INT,
   FulfillerID INT,
   Primary Key (CatID, ManID, ExtFulfillerID, FulfillerID),
   Foreign Key (ExtFulfillerID, FulfillerID) References Locations(ExtFulfillerID, FulfillerID) ON DELETE CASCADE,
   Foreign Key (CatID,ManID) references Clients(CatID, ManID) ON DELETE CASCADE

);

CREATE TABLE Bins(
	BinID int AUTO_INCREMENT,
	Name VARCHAR(30),
	ExtFulfillerID INT,
	FulfillerID INT,
	status VARCHAR(30),
	type VARCHAR(30),
	PRIMARY KEY (BinID),
	FOREIGN KEY (ExtFulfillerID, FulfillerID) references Locations(ExtFulfillerID, FulfillerID) ON DELETE CASCADE
);

Create Table Products(
   UPC INT,
   CatID INT,
   ManID INT,
   Primary Key (UPC),
   Foreign Key (CatID, ManID) References Clients(CatID, ManID) ON DELETE CASCADE
);

Create Table Inventory(
   UPC INT,
   SKU INT,
   FulfillerID INT,
   Primary Key (SKU, FulfillerID),
   Foreign Key (UPC) References Products(UPC)
);

CREATE TABLE Holds(
	BinID INT,
	SKU INT,
	UPC INT,
	Allocation INT,
	OnHand INT,
	PRIMARY KEY (BinID, SKU),
	FOREIGN KEY (BinID) references Bins(BinID),
	FOREIGN KEY (SKU) references Inventory(SKU),
	FOREIGN KEY (UPC) references Products(UPC)
);

Create Table Stocks(
   ExtFulfillerID INT,
   FulfillerID INT,
   SKU INT,
   UPC INT,
   LTD INT,
   SafetyStock Int,
   Primary Key (ExtFulfillerID, FulfillerID, SKU),
   Foreign Key (ExtFulfillerID, FulfillerID) References Locations(ExtFulfillerID, FulfillerID) ON DELETE CASCADE,
   Foreign Key (UPC) References Products(UPC) ON DELETE CASCADE,
   Foreign Key (FulfillerID, SKU) References Inventory(FulfillerID, SKU) ON DELETE CASCADE
);

