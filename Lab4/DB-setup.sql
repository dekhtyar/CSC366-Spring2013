CREATE TABLE Fulfiller (
   id VARCHAR(100),

   PRIMARY KEY (id)
);

CREATE TABLE Manufacturer (
   id VARCHAR(100),

   PRIMARY KEY (id)
);

CREATE TABLE Location (
   ext_ful_loc_id VARCHAR(100),
   int_ful_loc_id INT,
   fulfiller_id INT,
   name VARCHAR(100),
   type VARCHAR(100), -- "description" in CSV
   latitude FLOAT,
   longitude FLOAT, 
   status VARCHAR(100),
   default_safety_stock INT,

   PRIMARY KEY (int_ful_loc_id, fulfiller_id),
   FOREIGN KEY (fulfiller_id) REFERENCES Fulfiller(id)
);

CREATE TABLE Catalog (
   id VARCHAR(100),
   manufacturer_id VARCHAR(100),

   PRIMARY KEY (id, manufacturer_id),
   FOREIGN KEY (manufacturer_id) REFERENCES Manufacturer(id)
);

CREATE TABLE FulfillFor (
   fulfiller_id VARCHAR(100),
   int_ful_loc_id INT,
   catalog_id VARCHAR(100),
   manufacturer_id VARCHAR(100),

   PRIMARY KEY (fulfiller_id, int_ful_loc_id, catalog_id, manufacturer_id),
   FOREIGN KEY (int_ful_loc_id, fulfiller_id) REFERENCES Location,
   FOREIGN KEY (catalog_id, manufacturer_id) REFERENCES Catalog
);

CREATE TABLE Product (
   upc VARCHAR(12),
   catalog_id VARCHAR(100),
   manufacturer_id VARCHAR(100),
   name VARCHAR(1),

   PRIMARY KEY (upc),
   FOREIGN KEY (catalog_id, manufacturer_id) REFERENCES Catalog
);

CREATE TABLE Bin (
   name VARCHAR(100),
   fulfiller_id VARCHAR(100),
   int_ful_loc_id INT,
   type VARCHAR(100),
   status VARCHAR(100),

   PRIMARY KEY (name, fulfiller_id, int_ful_loc_id),
   FOREIGN KEY (fulfiller_id, int_ful_loc_id) REFERENCES Location
);

CREATE TABLE FulfillerSpecificProduct (
   sku VARCHAR(100),
   fulfiller_id VARCHAR(100),
   upc VARCHAR(100),

   PRIMARY KEY (sku, fulfiller_id),
   UNIQUE (fulfiller_id, upc),
   FOREIGN KEY (fulfiller_id) REFERENCES Fulfiller(id),
   FOREIGN KEY (upc) REFERENCES Product
);

CREATE TABLE HeldAt (
   fulfiller_id VARCHAR(100),
   int_ful_loc_id INT,
   sku VARCHAR(100),
   ltd FLOAT,
   safety_stock INT,

   PRIMARY KEY (fulfiller_id, int_ful_loc_id, sku),
   FOREIGN KEY (fulfiller_id, int_ful_loc_id) REFERENCES Location,
   FOREIGN KEY (sku) REFERENCES FulfillerSpecificProduct
);

CREATE TABLE StoredIn (
   sku VARCHAR(100),
   fulfiller_id VARCHAR(100),
   bin_name VARCHAR(100),
   int_ful_loc_id INT,
   on_hand INT,
   num_allocated INT,

   PRIMARY KEY (sku, fulfiller_id, bin_name, int_ful_loc_id),
   FOREIGN KEY (sku) REFERENCES FulfillerSpecificProduct,
   FOREIGN KEY (bin_name, fulfiller_id, int_ful_loc_id) REFERENCES Bin (name, fulfiller_id, int_ful_loc_id)
);
