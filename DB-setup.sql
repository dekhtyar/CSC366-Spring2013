-- Cait Rahm
-- Chase Ricketts
-- Peter Faiman
-- Jessica Cosio
-- PostgreSQL

CREATE TABLE Fulfillers(
    fulfillerid int PRIMARY KEY CHECK(fulfillerid > 0)
);

CREATE TABLE Manufacturers(
    manufacturerid int PRIMARY KEY CHECK(manufacturerid > 0)
);

CREATE TABLE Catalogs(
    id serial PRIMARY KEY,
    catalogid varchar(80) UNIQUE,
    manufacturerid int REFERENCES manufacturers NOT NULL
);

CREATE TABLE Locations(
    id serial PRIMARY KEY,
    name varchar(80),
    fulfillerid int REFERENCES fulfillers,
    extfulfillerid varchar(80),
    type varchar(80),
    coordinates point,
    status boolean,
    safetystocklimit int,
    UNIQUE(fulfillerid,extfulfillerid)
);

CREATE TABLE LocationsManCats(
    locationid int REFERENCES locations,
    mancatid int REFERENCES catalogs,
    PRIMARY KEY(locationid,mancatid)
);

CREATE TABLE Products(
    upc varchar(80) PRIMARY KEY,
    name varchar(80)
);

CREATE TABLE FulfillersProducts(
    sku varchar(80),
    fulfillerid int REFERENCES fulfillers,
    upc varchar(80) REFERENCES products,
    PRIMARY KEY(sku,fulfillerid),
    UNIQUE(fulfillerid,upc)
);

CREATE TABLE LocationsProducts(
    ltd real,
    safetystocklimit int,
    locationid int REFERENCES locations,
    fulfillerid int REFERENCES FulfillersProducts(fulfillerid),
    sku varchar(80) REFERENCES FulfillersProducts(sku),
    PRIMARY KEY(fulfillerid,sku, locationid)
);

CREATE TABLE ProductsManCats(
    upc varchar(80) REFERENCES products,
    mancatid int REFERENCES catalogs,
    PRIMARY KEY(upc,mancatid)
);

CREATE TABLE Bins(
    id serial PRIMARY KEY,
    name varchar(80),
    type varchar(50),
    locationid int REFERENCES locations NOT NULL,
    UNIQUE(name,type,locationid)
);

CREATE TABLE BinsProducts(
    onhandinventory int CHECK(availableinventory = onhandinventory-allocatedinventory),
    allocatedinventory int CHECK(availableinventory = onhandinventory-allocatedinventory),
    availableinventory int CHECK(availableinventory = onhandinventory-allocatedinventory),
    fulfillerid int REFERENCES FulfillersProducts(fulfillerid),
    sku varchar(80) REFERENCES FulfillersProducts(sku),
    binid int REFERENCES bins(id),
    PRIMARY KEY(fulfillerid,sku, binid)
);
