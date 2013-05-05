CREATE DATABASE IF NOT EXISTS inventory;
USE inventory;

CREATE TABLE IF NOT EXISTS Fulfiller (
   id INT,

   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Manufacturer (
   id VARCHAR(100),

   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Location (
   ext_ful_loc_id VARCHAR(100),
   int_ful_loc_id INT,
   fulfiller_id INT,
   name VARCHAR(100),
   type VARCHAR(100), -- "description" in CSV
   latitude FLOAT,
   longitude FLOAT, 
   status VARCHAR(100),
   default_safety_stock INT,

   PRIMARY KEY (ext_ful_loc_id, fulfiller_id),
   FOREIGN KEY (fulfiller_id) REFERENCES Fulfiller(id)
);

CREATE TABLE IF NOT EXISTS Catalog (
   id VARCHAR(100),
   manufacturer_id VARCHAR(100),

   PRIMARY KEY (id, manufacturer_id),
   FOREIGN KEY (manufacturer_id) REFERENCES Manufacturer(id)
);

CREATE TABLE IF NOT EXISTS FulfillFor (
   fulfiller_id INT,
   ext_ful_loc_id VARCHAR(100),
   catalog_id VARCHAR(100),
   manufacturer_id VARCHAR(100),

   PRIMARY KEY (fulfiller_id, ext_ful_loc_id, catalog_id, manufacturer_id),
   FOREIGN KEY (ext_ful_loc_id, fulfiller_id) REFERENCES Location(ext_ful_loc_id, fulfiller_id),
   FOREIGN KEY (catalog_id, manufacturer_id) REFERENCES Catalog(id, manufacturer_id)
);

CREATE TABLE IF NOT EXISTS Product (
   upc VARCHAR(12),
   catalog_id VARCHAR(100),
   manufacturer_id VARCHAR(100),
   name VARCHAR(1),

   PRIMARY KEY (upc),
   FOREIGN KEY (catalog_id, manufacturer_id) REFERENCES Catalog(id, manufacturer_id)
);

CREATE TABLE IF NOT EXISTS Bin (
   name VARCHAR(100),
   fulfiller_id INT, 
   ext_ful_loc_id VARCHAR(100),
   type VARCHAR(100),
   status VARCHAR(100),

   PRIMARY KEY (name, fulfiller_id, ext_ful_loc_id),
   FOREIGN KEY (ext_ful_loc_id, fulfiller_id) REFERENCES Location(ext_ful_loc_id, fulfiller_id)
);

CREATE TABLE IF NOT EXISTS FulfillerSpecificProduct (
   sku VARCHAR(100),
   fulfiller_id INT,
   upc VARCHAR(100),

   PRIMARY KEY (sku, fulfiller_id),
   UNIQUE (fulfiller_id, upc),
   FOREIGN KEY (fulfiller_id) REFERENCES Fulfiller(id),
   FOREIGN KEY (upc) REFERENCES Product(upc)
);

CREATE TABLE IF NOT EXISTS HeldAt (
   fulfiller_id INT,
   ext_ful_loc_id VARCHAR(100),
   sku VARCHAR(100),
   ltd FLOAT,
   safety_stock INT,

   PRIMARY KEY (fulfiller_id, ext_ful_loc_id, sku),
   FOREIGN KEY (ext_ful_loc_id, fulfiller_id) REFERENCES Location(ext_ful_loc_id, fulfiller_id),
   FOREIGN KEY (sku) REFERENCES FulfillerSpecificProduct(sku)
);

CREATE TABLE IF NOT EXISTS StoredIn (
   sku VARCHAR(100),
   fulfiller_id INT,
   bin_name VARCHAR(100),
   ext_ful_loc_id VARCHAR(100),
   on_hand INT,
   num_allocated INT DEFAULT 0,

   PRIMARY KEY (sku, fulfiller_id, bin_name, ext_ful_loc_id),
   FOREIGN KEY (sku) REFERENCES FulfillerSpecificProduct(sku),
   FOREIGN KEY (bin_name, fulfiller_id, ext_ful_loc_id) REFERENCES Bin (name, fulfiller_id, ext_ful_loc_id)
);
