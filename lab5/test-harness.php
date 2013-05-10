<?php
include_once('db.php');
include_once('team-ross-api.php');

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

  case 'empty':
    db_empty_tables($db);
    break;

  default:
    print "Usage: php test-harness.php create|seed|empty|destroy\n";
    break;

endswitch;

// **********************************************************************
// Create Tables
// **********************************************************************
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

// **********************************************************************
// Empty Tables
// **********************************************************************
function db_empty_tables($db) {
  try {
    $db->exec("DELETE FROM LocationOffersCatalogs;");
    $db->exec("DELETE FROM FulfillerCarriesProducts;");
    $db->exec("DELETE FROM BinContainsProducts;");
    $db->exec("DELETE FROM LocationSellsProducts;");
    $db->exec("DELETE FROM Products;");
    $db->exec("DELETE FROM Bins;");
    $db->exec("DELETE FROM Catalogs;");
    $db->exec("DELETE FROM Locations;");
    $db->exec("DELETE FROM Fulfillers;");

    print "Tables emptied.\n";
  }
  catch (PDOException $e) {
    print "Failed to empty tables\n";
  }
}

// **********************************************************************
// Destroy Tables
// **********************************************************************
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

// **********************************************************************
// Seed data from CSV files
// **********************************************************************
function db_seed($db) {
  $api = new TeamRossAPI($db);

  $csv = array();
  $csv['locations']           = 'data-csv-rfc/fulfiller_locations.csv';
  $csv['location_bins']       = 'data-csv-rfc/fulfiller_location_bins.csv';
  $csv['inv_available_bins']  = 'data-csv-rfc/fulfiller_inventory_available_bins.csv';
  $csv['inv_available']       = 'data-csv-rfc/fulfiller_inventory_available.csv';
  $csv['inv_not_available']   = 'data-csv-rfc/fulfiller_inventory_not_available.csv';

  // **********************************************************************
  // Locations
  // **********************************************************************
  $data = get_csv_data($csv['locations']);
  $fulfillerCheckStmt = $db->prepare("SELECT * FROM Fulfillers WHERE fulfillerId = :id");

  foreach($data as &$location) {
    $fulfillerCheckStmt->bindValue(':id', $location['fulfiller_id']);
    $fulfillerCheckStmt->execute();

    // Create new fulfiller if we need to
    if (!$fulfillerCheckStmt->fetch(PDO::FETCH_ASSOC))
      $api->createFulfiller($location['fulfiller_id'], $location['name']);

    // Create Fulfiller location
    $api->createFulfillmentLocation(
      $location['name'],
      $location['external_fulfiller_location_id'],
      $location['internal_fulfiller_location_id'],
      $location['fulfiller_id'],
      $location['description'],
      $location['latitude'],
      $location['longitude'],
      $location['status'],
      $location['safety_stock']
    );
  }

  // **********************************************************************
  // Bins
  // **********************************************************************
  $data = get_csv_data($csv['location_bins']);

  // **********************************************************************
  // Inventory Available Bins
  // **********************************************************************
  $data = get_csv_data($csv['inv_available_bins']);

  // **********************************************************************
  // Inventory Available
  // **********************************************************************
  $data = get_csv_data($csv['inv_available']);

  // **********************************************************************
  // Inventory Not Available
  // **********************************************************************
  $data = get_csv_data($csv['inv_not_available']);
}

// **********************************************************************
// Read data from CSV file and return array
// **********************************************************************
function get_csv_data($csv_file) {
  $delimiter = ',';
  $data = array();
  $handle = fopen($csv_file, "r");

  if (!$handle) die();

  $header = fgetcsv($handle);
  while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
    $data[] = array_combine($header, $line_array);
  }

  fclose($handle);
  return $data;
}
?>
