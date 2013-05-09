<?php
include_once('db.php');
include_once('db_functions.php');

// **********************************************************************
// Route test harness
// **********************************************************************
switch ($argv[1]):
  case 'seed':
    db_seed($db);
    break;

  case 'create':
    db_create_tables($db);
    break;

  case 'destroy':
    db_destroy_tables($db);
    break;

  default:
    print "Usage: php test-harness.php create|seed|empty|destroy\n";
    break;

endswitch;

function db_create_tables($db) {
  try {
    $db->exec("
      create table Catalogs (
        catalogId VARCHAR(10) NOT NULL,
        manufacturerId VARCHAR(10) NOT NULL,
        constraint catalogs_pk PRIMARY KEY (catalogId, manufacturerId)
      );
    ");

    $db->exec("
      create table Fulfillers (
        fulfillerId VARCHAR(10) NOT NULL,
        name VARCHAR(32) NOT NULL,
        constraint fulfillers_pk PRIMARY KEY (fulfillerId)
      );
    ");

    $db->exec("
      create table Locations (
        externalLocationId VARCHAR(10) NOT NULL,
        internalLocationId VARCHAR(32) NOT NULL,
        fulfillerId VARCHAR(10) NOT NULL,
        locationType VARCHAR(50),
        latitude decimal(9,6),
        longitude decimal(9,6),
        status VARCHAR(50),
        safetyStockLimitDefault VARCHAR(10),
        constraint locations_pk PRIMARY KEY (internalLocationId, fulfillerId),
        constraint locations_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers (fulfillerId)
      );
    ");

    $db->exec("
      create table Products (
        upc VARCHAR(10) NOT NULL,
        catalogId VARCHAR(10) NOT NULL,
        manufacturerId VARCHAR(10) NOT NULL,
        name VARCHAR(50),
        constraint products_pk PRIMARY KEY (upc),
        constraint products_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs (catalogId, manufacturerId)
      );
    ");

    $db->exec("
      create table Bins (
        locationId VARCHAR(10) NOT NULL,
        fulfillerId VARCHAR(10) NOT NULL,
        binName VARCHAR(50) NOT NULL,
        binType VARCHAR(50),
        status VARCHAR(10),
        constraint bins_pk PRIMARY KEY (locationId, fulfillerId, binName),
        constraint bins_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations (internalLocationId, fulfillerId)
      );
    ");

    $db->exec("
      create table LocationOffersCatalogs (
        catalogId VARCHAR(10) NOT NULL,
        manufacturerId VARCHAR(10) NOT NULL,
        locationId VARCHAR(10) NOT NULL,
        fulfillerId VARCHAR(10) NOT NULL,
        constraint loc_catalog_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs (catalogId, manufacturerId),
        constraint loc_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations (internalLocationId, fulfillerId),
        constraint loc_pk PRIMARY KEY (catalogId, manufacturerId, locationId, fulfillerId)
      );
    ");

    $db->exec("
      create table FulfillerCarriesProducts (
        fulfillerId VARCHAR(10) NOT NULL,
        productUpc VARCHAR(10) NOT NULL,
        sku VARCHAR(10) NOT NULL,
        constraint fcp_fulfiller_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers (fulfillerId),
        constraint fcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
        constraint fcp_pk PRIMARY KEY (fulfillerId, productUpc)
      );
    ");

    $db->exec("
      create table BinContainsProducts (
        productUpc VARCHAR(10) NOT NULL,
        binName VARCHAR(10) NOT NULL,
        fulfillerId VARCHAR(10) NOT NULL,
        locationId VARCHAR(10) NOT NULL,
        allocated VARCHAR(6),
        onHand VARCHAR(6),
        constraint bcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
        constraint bcp_binname_fk FOREIGN KEY (locationId, fulfillerId, binName) REFERENCES Bins (locationId, fulfillerId, binName),
        constraint bcp_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations (internalLocationId, fulfillerId),
        constraint bcp_pk PRIMARY KEY (productUpc, locationId, fulfillerId, binName)
      );
    ");

    $db->exec("
      create table LocationSellsProducts (
        productUpc VARCHAR(10) NOT NULL,
        fulfillerId VARCHAR(10) NOT NULL,
        locationId VARCHAR(10) NOT NULL,
        ltd VARCHAR(10),
        storeSku VARCHAR(10),
        safetyStock VARCHAR(10),
        constraint lsp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
        constraint lsp_location_fk FOREIGN KEY (locationId, fulfillerId) REFERENCES Locations (internalLocationId, fulfillerId),
        constraint lsp_pk PRIMARY KEY (productUpc, locationId, fulfillerId)
      );
    ");

    print "Tables created.\n";
  } catch (PDOException $e) {
    print "Failed to create tables\n";
  }
}

function db_destroy_tables($db) {
  try {
    $db->exec("DROP TABLE LocationOffersCatalogs;");
    $db->exec("DROP TABLE FulfillerCarriesProducts;");
    $db->exec("DROP TABLE BinContainsProducts;");
    $db->exec("DROP TABLE LocationSellsProducts;");
    $db->exec("DROP TABLE Products;");
    $db->exec("DROP TABLE Bins;");
    $db->exec("DROP TABLE Catalogs;");
    $db->exec("DROP TABLE Locations;");
    $db->exec("DROP TABLE Fulfillers;");

    print "Tables destroyed.\n";
  }
  catch (PDOException $e) {
    print "Failed to destroy tables\n";
  }
}

function db_seed($db) {
  $csv = array();
  $csv['locations']           = 'data-csv-rfc/fulfiller_locations.csv';
  $csv['location_bins']       = 'data-csv-rfc/fulfiller_location_bins.csv';
  $csv['inv_available_bins']  = 'data-csv-rfc/fulfiller_inventory_available_bins.csv';
  $csv['inv_available']       = 'data-csv-rfc/fulfiller_inventory_available.csv';
  $csv['inv_not_available']   = 'data-csv-rfc/fulfiller_inventory_not_available.csv';

  // Comma separated values
  $delimiter = ',';

  // **********************************************************************
  // Locations
  // **********************************************************************
  $handle = fopen($csv['locations'], "r");
  if (!$handle) die();

  $header = fgetcsv($handle);
  while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
    print_r(array_combine($header, $line_array));
  }
  fclose($handle);

  // **********************************************************************
  // Bins
  // **********************************************************************
  $handle = fopen($csv['location_bins'], "r");
  if (!$handle) die();

  $header = fgetcsv($handle);
  while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
    print_r(array_combine($header, $line_array));
  }
  fclose($handle);

  // **********************************************************************
  // Inventory Available Bins
  // **********************************************************************
  $handle = fopen($csv['inv_available_bins'], "r");
  if (!$handle) die();

  $header = fgetcsv($handle);
  while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
    print_r(array_combine($header, $line_array));
  }
  fclose($handle);

  // **********************************************************************
  // Inventory Available
  // **********************************************************************
  $handle = fopen($csv['inv_available'], "r");
  if (!$handle) die();

  $header = fgetcsv($handle);
  while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
    print_r(array_combine($header, $line_array));
  }
  fclose($handle);

  // **********************************************************************
  // Inventory Not Available
  // **********************************************************************
  $handle = fopen($csv['inv_not_available'], "r");
  if (!$handle) die();

  $header = fgetcsv($handle);
  while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
    print_r(array_combine($header, $line_array));
  }
  fclose($handle);
}
?>
