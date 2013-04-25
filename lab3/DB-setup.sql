Create Table Locations(
   ExtFulfillerId INT,
   FulfillerID INT,
   Name Char(100),
   Type Char(100),
   Latitude Char(10),
   Longitude Char(10),
   Status Char(15),
   SafetyStock INT CHECK (SafetyStock >=0),
   Primary Key (ExtFulfillerId, FulfillerID)
);

Create Table Serves(
   CatID INT,
   ManID INT,
   ExtFulfillerId INT,
   FulfillerID INT,
   Primary Key (CatID, ManID, ExtFulfillerId, FulfillerID),
   Foreign Key (ExtFulfillerId, FulfillerID) References Locations(ExtFulfillerId, FulfillerID) ON DELETE CASCADE

);

Create Table Clients(
   CatID INT,
   ManID INT,
   Primary Key (CatID, ManID)
);

CREATE TABLE Bins(
	Name VARCHAR2(30),
	ExtFulfillerID INT,
	FulfillerID INT,
	status VARCHAR2(30),
	type VARCHAR2(30),
	PRIMARY KEY (Name, ExtFulfillerID, FulfillerID)
	FOREIGN KEY (ExtFulfillerID, FulfillerID) references Locations(ExtFulfillerId, FulfillerID)
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
   Primary Key (UPC, SKU, FulfillerID),
   Foreign Key (UPC) References Products(UPC),
   Foreign key (FulfillerID) References Locations(FulfillerID)
);

CREATE TABLE Holds(
	Name VARCHAR2(30),
	FulfillerID INT,
	ExtFulfillerID INT,
	SKU INT,
	UPC INT,
	Allocation INT,
	OnHand INT,
	PRIMARY KEY (Name, FulfillerID, ExtFulfillerID, SKU, UPC),
	FOREIGN KEY (Name) references Bins(Name),
	FOREIGN KEY (SKU) references Inventory(SKU),
	FOREIGN KEY (UPC) references Products(UPC),
	FOREIGN KEY (ExtFulfillerID, FulfillerID) references Locations(ExtFulfillerId, FulfillerID)
);

Create Table Stocks(
   ExtFulfillerId INT,
   FulfillerID INT,
   SKU INT,
   UPC INT,
   LTD INT,
   SafetyStock Int,
   Primary Key (ExtFulfillerId, FulfillerID, SKU, UPC),
   Foreign Key (ExtFulfillerId, FulfillerID) References Locations(ExtFulfillerId, FulfillerID) ON DELETE CASCADE,
   Foreign Key (UPC) References Products(UPC) ON DELETE CASCADE,
   Foreign Key (UPC, SKU) References Inventory(UPC, SKU) ON DELETE CASCADE
);

