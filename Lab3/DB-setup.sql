CREATE TABLE Fulfiller (
   id VARCHAR(100) PRIMARY KEY
);

CREATE TABLE Manufacturer (
   id VARCHAR(100) PRIMARY KEY
);

CREATE TABLE Location (
   ext_ful_location VARCHAR(100),
   fulfiller_id VARCHAR(100) REFERENCES Fulfiller(id),
   name VARCHAR(100) ,
   type ENUM('warehouse', 'storefront'),
   latitude FLOAT,
   longitude FLOAT, 
   status ENUM('Active', 'Inactive'),
   default_safety_stock INT,

   PRIMARY KEY(ext_ful_location, fulfiller_id)
);

CREATE TABLE Catalog (
   id VARCHAR(100),
   manufacturer_id VARCHAR(100) REFERENCES Manufacturer(id),
   fulfiller_id VARCHAR(100) REFERENCES Fulfiller(id),

   PRIMARY KEY(id, manufacturer_id, fulfiller_id)
);

CREATE TABLE Product (
   upc VARCHAR(12) PRIMARY KEY,
   catalog_id VARCHAR(100) REFERENCES Catalog(id)
);

CREATE TABLE Bin (
   name VARCHAR(100),
   fulfiller_id VARCHAR(100) REFERENCES Fulfiller(id),
   ext_ful_location VARCHAR(100) REFERENCES Location(ext_ful_location),

   PRIMARY KEY(name, fulfiller_id, ext_ful_location)
);

CREATE TABLE FulfillerSpecificProduct (
   sku VARCHAR(100),
   fulfiller_id VARCHAR(100) REFERENCES Fulfiller(id),
   upc VARCHAR(100) REFERENCES Product(upc),

   PRIMARY KEY(sku, fulfiller_id, upc)
);

CREATE TABLE HeldAt (
   fulfiller_id VARCHAR(100) REFERENCES Fulfiller(id),
   ext_ful_location VARCHAR(100) REFERENCES Location(ext_ful_location),
   sku VARCHAR(100) REFERENCES FulfillerSpecificProduct(sku),
   ltd FLOAT,
   safety_stock INT,

   PRIMARY KEY(fulfiller_id, ext_ful_location, sku)
);

CREATE TABLE StoredIn (
   sku VARCHAR(100),
   fulfiller_id VARCHAR(100),
   name VARCHAR(100),
   ext_ful_location VARCHAR(100),
   on_hand INT,
   num_allocated INT,

   PRIMARY KEY (sku, fulfiller_id, name, ext_ful_location),
   FOREIGN KEY (sku) REFERENCES FulfillerSpecificProduct(sku),
   FOREIGN KEY (name, fulfiller_id, ext_ful_location) REFERENCES Bin
);
