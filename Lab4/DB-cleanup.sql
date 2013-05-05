USE inventory;

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS StoredIn;
DROP TABLE IF EXISTS HeldAt;
DROP TABLE IF EXISTS FulfillerSpecificProduct;
DROP TABLE IF EXISTS Bin;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Catalog;
DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS Manufacturer;
DROP TABLE IF EXISTS Fulfiller;

SET foreign_key_checks = 1;
