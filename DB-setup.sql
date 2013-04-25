-- Cait Rahm
-- Chase Ricketts
-- Peter Faiman
-- Jessica Cosio
-- PostgreSQL


CREATE TABLE Fulfillers(
fulfillerid varchar(80) PRIMARY KEY);


CREATE TABLE Manufacturers(
manufacturerid varchar(80) PRIMARY KEY);                 


CREATE TABLE Catalogs(
id serial PRIMARY KEY,
catalogid varchar(80) UNIQUE,
manufacturerid varchar(80) REFERENCES manufacturers NOT NULL
);


CREATE TABLE Locations(
id serial PRIMARY KEY,
name varchar(80),
fulfillerid varchar(80) REFERENCES fulfillers,
extfulfillerid varchar(80),
type varchar(80),
coordinates point, 
status boolean,
safetystocklimit int,
UNIQUE(fulfillerid,extfulfillerid));


CREATE TABLE LocationsManCats(
locationid int REFERENCES locations,
mancatid int REFERENCES catalogs,
PRIMARY KEY(locationid,mancatid));


CREATE TABLE Products(
upc varchar(80) PRIMARY KEY,
name varchar(80));


CREATE TABLE FulfillersProducts(
id serial PRIMARY KEY,
sku varchar(80),
fulfillerid varchar(80) REFERENCES fulfillers,
upc varchar(80) REFERENCES products,
UNIQUE(sku,fulfillerid),
UNIQUE(fulfillerid,upc),
UNIQUE(upc,sku));


CREATE TABLE LocationsProducts(
ltd real,
safetystocklimit int,
locationid int REFERENCES locations,
fulfillerproductid int REFERENCES fulfillersproducts,
PRIMARY KEY(fulfillerproductid, locationid));


CREATE TABLE ProductsManCats(
upc varchar(80) REFERENCES products,
mancatid int REFERENCES catalogs,
PRIMARY KEY(upc,mancatid));         


CREATE TABLE Bins(
id serial PRIMARY KEY,
name varchar(80),
type varchar(50),
locationid int REFERENCES locations NOT NULL, 
UNIQUE(name,type,locationid));


CREATE TABLE BinsProducts(
onhandinventory int CHECK(availableinventory = onhandinventory-allocatedinventory),
allocatedinventory int CHECK(availableinventory = onhandinventory-allocatedinventory),
availableinventory int CHECK(availableinventory = onhandinventory-allocatedinventory),
fulfillerproductid int REFERENCES fulfillersproducts,
binid int REFERENCES bins(id),
PRIMARY KEY(fulfillerproductid, binid));