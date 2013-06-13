<?php
include_once('team-ross-api.php');

// **********************************************************************
// Set up mysql pdo handle
// **********************************************************************
$hostname = 'localhost';
$username = 'root';
$password = 'password';
$database = 'team_ross';
$db = null;

try {
  $db = new PDO("mysql:host=$hostname;dbname=$database", $username, $password);
}
catch (PDOException $e) {
  echo 'Connection failed: ' . $e->getMessage();
}

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

  case 'reset':
    db_destroy_tables($db);
    db_create_tables($db);
    db_seed($db);
    break;

  default:
    print "Usage: php test-harness.php create|seed|empty|destroy|reset\n";
    break;

endswitch;

// **********************************************************************
// Create Tables
// **********************************************************************
function db_create_tables($db) {
  try {
    $db->exec("
      create table Catalogs (
        catalogId VARCHAR(11) NOT NULL,
        manufacturerId VARCHAR(11) NOT NULL,
        constraint catalogs_pk PRIMARY KEY (catalogId, manufacturerId)
      );
    ");

    $db->exec("
      create table Fulfillers (
        fulfillerId VARCHAR(11) NOT NULL,
        name VARCHAR(32) NOT NULL,
        constraint fulfillers_pk PRIMARY KEY (fulfillerId)
      );
    ");

    $db->exec("
      create table Locations (
        externalLocationId VARCHAR(11) NOT NULL,
        internalLocationId VARCHAR(11) NOT NULL,
        fulfillerId VARCHAR(11) NOT NULL,
        locationType VARCHAR(50),
        latitude decimal(9,6),
        longitude decimal(9,6),
        status VARCHAR(50),
        safetyStockLimitDefault VARCHAR(10),
        constraint locations_pk PRIMARY KEY (internalLocationId),
        constraint locations_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers (fulfillerId)
      );
    ");

    $db->exec("
      create table Products (
        upc VARCHAR(11) NOT NULL,
        catalogId VARCHAR(11) NOT NULL,
        manufacturerId VARCHAR(11) NOT NULL,
        name VARCHAR(50),
        constraint products_pk PRIMARY KEY (upc),
        constraint products_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs (catalogId, manufacturerId)
      );
    ");

    $db->exec("
      create table Bins (
        internalLocationId VARCHAR(11) NOT NULL,
        binName VARCHAR(50) NOT NULL,
        binType VARCHAR(50),
        status VARCHAR(10),
        constraint bins_pk PRIMARY KEY (internalLocationId, binName),
        constraint bins_fk FOREIGN KEY (internalLocationId) REFERENCES Locations (internalLocationId)
      );
    ");

    $db->exec("
      create table LocationOffersCatalogs (
        catalogId VARCHAR(11) NOT NULL,
        manufacturerId VARCHAR(11) NOT NULL,
        internalLocationId VARCHAR(11) NOT NULL,
        constraint loc_catalog_fk FOREIGN KEY (catalogId, manufacturerId) REFERENCES Catalogs (catalogId, manufacturerId) ON UPDATE CASCADE,
        constraint loc_location_fk FOREIGN KEY (internalLocationId) REFERENCES Locations (internalLocationId) ON UPDATE CASCADE,
        constraint loc_pk PRIMARY KEY (catalogId, manufacturerId, internalLocationId)
      );
    ");

    $db->exec("
      create table FulfillerCarriesProducts (
        fulfillerId VARCHAR(11) NOT NULL,
        productUpc VARCHAR(11) NOT NULL,
        sku VARCHAR(11) NOT NULL,
        constraint fcp_fulfiller_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers (fulfillerId),
        constraint fcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
        constraint fcp_pk PRIMARY KEY (fulfillerId, productUpc)
      );
    ");

    $db->exec("
      create table BinContainsProducts (
        productUpc VARCHAR(11) NOT NULL,
        binName VARCHAR(11) NOT NULL,
        internalLocationId VARCHAR(11) NOT NULL,
        fulfillerId VARCHAR(11) NOT NULL,
        constraint bcp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
        constraint bcp_binname_fk FOREIGN KEY (internalLocationId, binName) REFERENCES Bins (internalLocationId, binName),
        constraint bcp_location_fk FOREIGN KEY (internalLocationId) REFERENCES Locations (internalLocationId),
        constraint bcp_fulfiller_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers (fulfillerId),
        constraint bcp_pk PRIMARY KEY (productUpc, fulfillerId, internalLocationId, binName)
      );
    ");

    $db->exec("
      create table LocationSellsProducts (
        productUpc VARCHAR(10) NOT NULL,
        internalLocationId VARCHAR(10) NOT NULL,
        ltd VARCHAR(10),
        storeSku VARCHAR(10),
        allocated VARCHAR(6) DEFAULT '0',
        onHand VARCHAR(6),
        safetyStock VARCHAR(10),
        fulfillerId VARCHAR(11) NOT NULL,
        constraint lsp_productUpc_fk FOREIGN KEY (productUpc) REFERENCES Products (upc),
        constraint lsp_location_fk FOREIGN KEY (internalLocationId) REFERENCES Locations (internalLocationId),
        constraint lsp_fulfiller_fk FOREIGN KEY (fulfillerId) REFERENCES Fulfillers (fulfillerId),
        constraint lsp_pk PRIMARY KEY (productUpc, fulfillerId, internalLocationId)
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
  $csv = array();
  $csv['locations']           = 'csv_data/fulfiller_locations.csv';
  $csv['location_bins']       = 'csv_data/fulfiller_location_bins.csv';
  $csv['inv_available_bins']  = 'csv_data/fulfiller_inventory_available_bins.csv';
  $csv['inv_available']       = 'csv_data/fulfiller_inventory_available.csv';
  $csv['inv_not_available']   = 'csv_data/fulfiller_inventory_not_available.csv';

  // **********************************************************************
  // Locations
  // **********************************************************************
  print "Seeding Locations";
  $data = get_csv_data($csv['locations']);
  $fulfillerCheckStmt = $db->prepare("SELECT * FROM Fulfillers WHERE fulfillerId = :id");
  $fulfillersProcessed = array();

  foreach ($data as &$location) {
    $fulfillerCheckStmt->bindValue(':id', $location['fulfiller_id']);
    $fulfillerCheckStmt->execute();

    // Create new fulfiller if we need to
    if (!$fulfillerCheckStmt->fetch(PDO::FETCH_ASSOC) && !in_array($location['fulfiller_id'], $fulfillersProcessed)) {
      createFulfiller($location['fulfiller_id'], $location['name'], $db);
      $fulfillersProcessed[] = $location['fulfiller_id'];
    }

    // Create Fulfiller location
    createFulfillmentLocation(
      $location['name'],
      $location['external_fulfiller_location_id'],
      $location['internal_fulfiller_location_id'],
      $location['fulfiller_id'],
      $location['description'],
      $location['latitude'],
      $location['longitude'],
      $location['status'],
      $location['safety_stock'],
      $location['mfg_id'],
      $location['catalog_id'],
      $db
    );

    if (++$count % 50 == 0) print ".";
  }

  // **********************************************************************
  // Bins
  // **********************************************************************
  print "\nSeeding Bins";
  $data = get_csv_data($csv['location_bins']);
  $count = 0;

  foreach($data as &$bin) {
    createBin(
      $bin['internal_fulfiller_location_id'],
      $bin['bin_name'],
      $bin['bin_type'],
      $bin['bin_status'],
      $db
    );

    if (++$count % 150 == 0) print ".";
  }

  // **********************************************************************
  // Inventory
  // **********************************************************************
  print "\nSeeding Inventory.";
  $data = array();
  $data[] = get_csv_data($csv['inv_available_bins']);
  $data[] = get_csv_data($csv['inv_available']);
  $data[] = get_csv_data($csv['inv_not_available']);

  print ".";
  foreach($data as &$data_chunk) {
    seedInventory($data_chunk, $db);
    print ".";
  }

  print "\nData seeded.\n";
}

function seedInventory($items, $db) {
  // STATEMENTS
  $stmt1 = $db->prepare("
    INSERT INTO BinContainsProducts
      (binName, internalLocationId, productUpc, fulfillerId)
    VALUES
      (:binName, :internalLocationId, :productUpc, :fulfillerId)
  ");

  $stmt2 = $db->prepare("
    INSERT INTO FulfillerCarriesProducts(fulfillerId, productUpc, sku)
    VALUES(:fulfillerId, :productUpc, :sku)
  ");

  $stmt3 = $db->prepare("
    INSERT INTO LocationSellsProducts (internalLocationId, productUpc, storeSku, safetyStock, ltd, allocated, onHand, fulfillerId)
    VALUES(:internalLocationId, :productUpc, :storeSku, :safetyStock, :ltd, '0', :onHand, :fulfillerId)
  ");

  // UPDATE INVENTORY FOR EACH ITEM
  foreach ($items as $item) {
    $fulfillerId = getFulfillerIdFromLocationId($item['internal_fulfiller_location_id'], $db);

    $stmt1->bindParam(':binName', $item['bin_name']);
    $stmt1->bindParam(':internalLocationId', $item['internal_fulfiller_location_id']);
    $stmt1->bindParam(':productUpc', $item['UPC']);
    $stmt1->bindParam(':fulfillerId', $fulfillerId);

    $stmt2->bindParam(':fulfillerId', $fulfillerId);
    $stmt2->bindParam(':productUpc', $item['UPC']);
    $stmt2->bindParam(':sku', $item['SKU']);

    $stmt3->bindParam(':internalLocationId', $item['internal_fulfiller_location_id']);
    $stmt3->bindParam(':productUpc', $item['UPC']);
    $stmt3->bindParam(':storeSku', $item['SKU']);
    $stmt3->bindParam(':safetyStock', $item['safety_stock']);
    $stmt3->bindParam(':ltd', $item['ltd']);
    $stmt3->bindParam(':onHand', $item['onHand']);
    $stmt3->bindParam(':fulfillerId', $fulfillerId);

    // create product if missing
    if (!getProductFromUpc($item['UPC'], $db))
      createProduct($item, $db);

    // create bin if missing
    if (!getBin($item['bin_name'], $item['internal_fulfiller_location_id'], $db))
      print "Bin doesn't exist.\n";

    // execute queries
    else {
      $stmt1->execute();
      $stmt2->execute();
      $stmt3->execute();
    }
  }
}

function createBin($internalLocationId, $binName, $binType, $binStatus, $db) {
  $stmt = $db->prepare("
    INSERT INTO Bins
      (internalLocationId, binName, binType, status)
    VALUES
      (:internal_location_id, :binName, :binType, :status);
  ");

  $stmt->bindValue(':internal_location_id', $internalLocationId);
  $stmt->bindValue(':binName', $binName);
  $stmt->bindValue(':binType', $binType);
  $stmt->bindValue(':status', $binStatus);

  if (!$stmt->execute()) {
    print "\ncreateBin\n";
    $out = print_r($stmt->errorInfo(), True);
    error_log($out);
    return False;
  }
  return True;
}

function createFulfillmentLocation($locationName, $extLID, $intLID,
  $fulfillerId, $locationType, $latitude, $longitude, $status, $safetyStock,$mfgId, $catalogId, $db) {
  $success = TRUE;
    // Create Location, update if exists
    $stmt = $db->prepare("
       INSERT INTO Locations
         (externalLocationId, internalLocationId, fulfillerId, locationType,
          latitude, longitude, status, safetyStockLimitDefault)
        VALUES
          (:externalLocationId, :internalLocationId, :fulfillerId, :locationType,
          :latitude, :longitude, :status, :safetyStockLimitDefault)
    ");

  $stmt->bindValue(':externalLocationId', $extLID);
  $stmt->bindValue(':internalLocationId', $intLID);
  $stmt->bindValue(':fulfillerId', $fulfillerId);
  $stmt->bindValue(':locationType', $locationType);
  $stmt->bindValue(':latitude', strval($latitude));
  $stmt->bindValue(':longitude', strval($longitude));
  $stmt->bindValue(':status', $status);
  $stmt->bindValue(':safetyStockLimitDefault', $safetyStock);
  if (!$stmt->execute()) {
    print "\ncreateFulfillmentLocation\n";
    $out = print_r($stmt->errorInfo(), True);
    error_log($out);
    $success = FALSE;
  }

  // Create catalog if missing
  if (!getCatalog($catalogId, $db))
    createCatalog($catalogId, $mfgId, $db);

  // Create default Bin
  print $intLID . "\n";
  if (!getBin('Default', $intLID, $db))
    createBin($intLID, 'Default', 'Default', $status, $db);

  // Create LocationOffersCatalog
  $relational = $db->prepare("
    INSERT INTO LocationOffersCatalogs (catalogId, manufacturerId, internalLocationId)
    VALUES (:catalogId, :manufacturerId, :internalLocationId);
  ");

  $relational->bindParam(':catalogId', $catalogId);
  $relational->bindParam(':manufacturerId', $mfgId);
  $relational->bindParam(':internalLocationId', $intLID);

  if (!$relational->execute())  {
    print "\ncreateFulfillmentLocation RELATIONAL\n";
    $out = print_r($relational->errorInfo(), true);
    error_log($out);
    $success = FALSE;
  }

  return (($success == FALSE) ? 0 : 1);
}

function createFulfiller( $id, $name, $db ) {
  $stmt = $db->prepare("INSERT INTO Fulfillers (fulfillerId, name) VALUES (:id,:name)");
  $stmt->bindValue(':id', $id);
  $stmt->bindValue(':name',$name);

  if (!$stmt->execute()) {
    print "\ncreateFulfiller\n";
    print_r($stmt->errorInfo());
  }
}

function getBin($binName, $internalLocationId, $db) {
  $stmt = $db->prepare("
    SELECT * FROM Bins
    WHERE binName = :binName
    AND internalLocationId = :internalLocationId
  ");

  if ($binName == 0)
    $binName = 'Default';

  $stmt->bindValue(':binName', $binName);
  $stmt->bindValue(':internalLocationId', $internalLocationId);

  $stmt->execute();

  return $stmt->fetch(PDO::FETCH_ASSOC);
}

function createProduct($product, $db) {
  if (!getCatalog($product['catalog_id'], $db))
    createCatalog($product['catalog_id'], $product['mfg_id'], $db);

  $stmt = $db->prepare("
    INSERT INTO Products
      (upc, catalogId, manufacturerId, name)
    VALUES
      (:upc, :catalogId, :manufacturerId, :name);
  ");

  $stmt->bindParam(':upc', $product['UPC']);
  $stmt->bindParam(':catalogId', $product['catalog_id']);
  $stmt->bindParam(':manufacturerId', $product['mfg_id']);
  $stmt->bindParam(':name', $product['product_name']);

  $stmt->execute();
}

function getCatalog($catalog_id, $db) {
  $stmt = $db->prepare("
    SELECT * FROM Catalogs
    WHERE catalogId = :catalogId
  ");
  $stmt->bindParam(':catalogId', $catalog_id);

  if (!$stmt->execute()) {
    print "\ngetCatalog\n";
    print_r($stmt->errorInfo());
  }

  return $stmt->fetch(PDO::FETCH_ASSOC);
}

function createCatalog($catalog_id, $mfg_id, $db) {
  $stmt = $db->prepare("
    INSERT INTO Catalogs
      (catalogId, manufacturerId)
    VALUES
      (:catalogId, :manufacturerId)
  ");

  $stmt->bindParam(':catalogId', $catalog_id);
  $stmt->bindParam(':manufacturerId', $mfg_id);

  if (!$stmt->execute()) {
    print "\ncreateCatalog\n";
    print_r($stmt->errorInfo());
  }
}

function getProductFromUpc($upc, $db) {
  $stmt = $db->prepare("
    SELECT * FROM Products
    WHERE upc = :upc
  ");
  $stmt->bindParam(':upc', $upc);
  $stmt->execute();

  return $stmt->fetch(PDO::FETCH_ASSOC);
}

function getFulfillerIdFromLocationId($internalLocationId, $db) {
  $stmt = $db->prepare("SELECT fulfillerId FROM Locations WHERE internalLocationId = :internalLocationId");
  $stmt->bindParam(':internalLocationId', $internalLocationId);
  $stmt->execute();

  $fetch = $stmt->fetch(PDO::FETCH_ASSOC);
  return $fetch['fulfillerId'];
}

// **********************************************************************
// Read data from CSV file and return array
// **********************************************************************
function get_csv_data($csv_file) {
  $delimiter = ',';
  $data = array();
  $handle = fopen($csv_file, "r");

  if (!$handle) {
    print "Failed to open CSV: " . $csv_file . "\n";
    die();
  }

  $header = fgetcsv($handle);
  while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
    $data[] = array_combine($header, $line_array);
  }

  fclose($handle);
  return $data;
}
?>
