USE inventory;

SET foreign_key_checks = 0;

TRUNCATE TABLE StoredIn;
TRUNCATE TABLE HeldAt;
TRUNCATE TABLE FulfillerSpecificProduct;
TRUNCATE TABLE Bin;
TRUNCATE TABLE Product;
TRUNCATE TABLE Catalog;
TRUNCATE TABLE Location;
TRUNCATE TABLE Manufacturer;
TRUNCATE TABLE Fulfiller;
TRUNCATE TABLE FulfillFor;

SET foreign_key_checks = 1;
