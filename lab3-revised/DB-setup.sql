Create Table Locations(
   ExtFulfillerId INT,
   FulfillerID INT,
   Name VARCHAR(100),
   Type VARCHAR(100),
   Latitude VARCHAR(10),
   Longitude VARCHAR(10),
   Status VARCHAR(15),
   SafetyStock INT CHECK (SafetyStock >=0),
   Primary Key (ExtFulfillerId, FulfillerID)
);

Create Table Clients(
   CatID INT,
   ManID INT,
   Primary Key (CatID, ManID)
);

Create Table Serves(
   CatID INT,
   ManID INT,
   ExtFulfillerId INT,
   FulfillerID INT,
   Primary Key (CatID, ManID, ExtFulfillerId, FulfillerID),
   Foreign Key (ExtFulfillerId, FulfillerID) References Locations(ExtFulfillerId, FulfillerID) ON DELETE CASCADE
   Foreign Key (CatID,ManID) references Clients(CatID, ManID)

);



CREATE TABLE Bins(
	Name VARCHAR(30),
	ExtFulfillerID INT,
	FulfillerID INT,
	status VARCHAR(30),
	type VARCHAR(30),
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
   Primary Key (SKU, FulfillerID),
   Foreign Key (UPC) References Products(UPC),
);

CREATE TABLE Holds(
	Name VARCHAR(30),
	FulfillerID INT,
	ExtFulfillerID INT,
	SKU INT,
	UPC INT,
	Allocation INT,
	OnHand INT,
	PRIMARY KEY (Name, FulfillerID, ExtFulfillerID, SKU),
	FOREIGN KEY (Name) references Bins(Name),
	FOREIGN KEY (SKU, FulfillerID) references Inventory(SKU, FulfillerID),
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
   Primary Key (ExtFulfillerId, FulfillerID, SKU),
   Foreign Key (ExtFulfillerId, FulfillerID) References Locations(ExtFulfillerId, FulfillerID) ON DELETE CASCADE,
   Foreign Key (UPC) References Products(UPC) ON DELETE CASCADE,
   Foreign Key (FulfillerID, SKU) References Inventory(FulfillerID, SKU) ON DELETE CASCADE
);

