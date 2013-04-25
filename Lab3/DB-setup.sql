CREATE TABLE Fulfiller (
   id VARCHAR(100),

   PRIMARY KEY (id)
);

CREATE TABLE Manufacturer (
   id VARCHAR(100),

   PRIMARY KEY (id)
);

CREATE TABLE Location (
   ext_ful_location VARCHAR(100),
   fulfiller_id VARCHAR(100),
   name VARCHAR(100) ,
   type ENUM('warehouse', 'storefront'),
   latitude FLOAT,
   longitude FLOAT, 
   status ENUM('Active', 'Inactive'),
   default_safety_stock INT,

   PRIMARY KEY (ext_ful_location, fulfiller_id),
   FOREIGN KEY (fulfiller_id) REFERENCES Fulfiller(id)
);

CREATE TABLE Catalog (
   id VARCHAR(100),
   manufacturer_id VARCHAR(100),
   fulfiller_id VARCHAR(100),

   PRIMARY KEY (id, manufacturer_id, fulfiller_id),
   FOREIGN KEY (manufacturer_id) REFERENCES Manufacturer(id),
   FOREIGN KEY (fulfiller_id) REFERENCES Fulfiller(id)
);

CREATE TABLE Product (
   upc VARCHAR(12),
   catalog_id VARCHAR(100),

   PRIMARY KEY (upc),
   FOREIGN KEY (catalog_id) REFERENCES Catalog(id)
);

CREATE TABLE Bin (
   name VARCHAR(100),
   fulfiller_id VARCHAR(100),
   ext_ful_location VARCHAR(100),

   PRIMARY KEY (name, fulfiller_id, ext_ful_location),
   FOREIGN KEY (fulfiller_id) REFERENCES Fulfiller(id),
   FOREIGN KEY (ext_ful_location) REFERENCES Location
);

CREATE TABLE FulfillerSpecificProduct (
   sku VARCHAR(100),
   fulfiller_id VARCHAR(100),
   upc VARCHAR(100),

   PRIMARY KEY (sku, fulfiller_id, upc),
   FOREIGN KEY (fulfiller_id) REFERENCES Fulfiller(id),
   FOREIGN KEY (upc) REFERENCES Product
);

CREATE TABLE HeldAt (
   fulfiller_id VARCHAR(100),
   ext_ful_location VARCHAR(100),
   sku VARCHAR(100),
   ltd FLOAT,
   safety_stock INT,

   PRIMARY KEY (fulfiller_id, ext_ful_location, sku),
   FOREIGN KEY (fulfiller_id, ext_ful_location) REFERENCES Location,
   FOREIGN KEY (sku) REFERENCES FulfillerSpecificProduct
);

CREATE TABLE StoredIn (
   sku VARCHAR(100),
   fulfiller_id VARCHAR(100),
   name VARCHAR(100),
   ext_ful_location VARCHAR(100),
   on_hand INT,
   num_allocated INT,

   PRIMARY KEY (sku, fulfiller_id, name, ext_ful_location),
   FOREIGN KEY (sku) REFERENCES FulfillerSpecificProduct,
   FOREIGN KEY (name, fulfiller_id, ext_ful_location) REFERENCES Bin
);
