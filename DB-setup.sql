-- Cait Rahm
-- Peter Faiman
-- Jessica Cosio
-- Chase Ricketts

-- PostgreSQL

CREATE TYPE ActivityStatus AS ENUM ('active', 'inactive');
CREATE TYPE LocationType AS ENUM ('FULFILLER', 'RETAILER', 'MANUFACTURER');

CREATE TABLE Fulfillers (
    id INT PRIMARY KEY CHECK (id > 0),
    status ActivityStatus DEFAULT 'active'
);

CREATE TABLE Manufacturers (
    id INT PRIMARY KEY CHECK (id > 0)
);

CREATE TABLE Catalogs (
    id INT PRIMARY KEY CHECK (id > 0),
    manufacturerId INT REFERENCES Manufacturers,
    UNIQUE (manufacturerId, id)
);

CREATE TABLE Locations (
    name VARCHAR(126),
    fulfillerId INT REFERENCES Fulfillers,
    externalFulfillerId VARCHAR(126),
    id INT PRIMARY KEY,
    description VARCHAR(126),
    type LocationType,
    coordinates POINT, -- TODO change to GEOGRAPHY(POINT, 4326)
    status ActivityStatus DEFAULT 'active',
    UNIQUE (fulfillerId, externalFulfillerId)
);

CREATE TABLE LocationCatalogs (
    locationId INT REFERENCES Locations,
    catalogId INT REFERENCES Catalogs(id),
    PRIMARY KEY (locationId, catalogId)
);

CREATE TABLE Products (
    upc VARCHAR(126) PRIMARY KEY,
    name VARCHAR(126),
    catalogId INT REFERENCES Catalogs
);

CREATE TABLE FulfillerProducts (
    sku VARCHAR(126),
    upc VARCHAR(126) REFERENCES Products,
    fulfillerId INT REFERENCES Fulfillers,
    PRIMARY KEY (fulfillerId, sku),
    UNIQUE (fulfillerId, upc)
);

CREATE TABLE LocationProducts (
    locationId INT REFERENCES Locations,
    fulfillerId INT,
    sku VARCHAR(126),
    ltd DOUBLE PRECISION,
    safetyStock INT,
    PRIMARY KEY (locationId, sku),
    FOREIGN KEY (fulfillerId, sku) REFERENCES FulfillerProducts
);

CREATE TABLE Bins (
    id SERIAL PRIMARY KEY,
    locationId INT REFERENCES Locations NOT NULL,
    name VARCHAR(126),
    type VARCHAR(126),
    status VARCHAR(126),
    UNIQUE(locationId, type, name)
);

CREATE TABLE BinProducts (
    binId INT REFERENCES bins(id),
    fulfillerId INT,
    sku VARCHAR(126),
    onHand INT,
    allocated INT,
    PRIMARY KEY(binId, sku),
    FOREIGN KEY (fulfillerId, sku) REFERENCES FulfillerProducts
);
