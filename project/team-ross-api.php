<?php
error_reporting(E_ERROR);

class TeamRossAPI {
  private $db;

  public function __construct($db_handle) {
    $this->db = $db_handle;
  }

  public function createFulfiller( $id, $name ) {
    $stmt = $this->db->prepare("INSERT INTO Fulfillers (fulfillerId, name) VALUES (:id,:name)");
    $stmt->bindValue(':id', $id);
    $stmt->bindValue(':name',$name);
    return $stmt->execute();
  }

  public function getFulfillerStatus($fulfiller_id) {
    $stmt = $this->db->prepare("
      SELECT count(*) total
      FROM Locations
      WHERE fulfillerId = :id
      AND status = 'active'
    ");
    $stmt->bindvalue(':id', $fulfiller_id);
    $stmt->execute();

    $fetch = $stmt->fetch(PDO::FETCH_ASSOC);
    return $fetch['total'];
  }

  public function createFulfillmentLocation($locationName, $extLID, $intLID,
    $fulfillerId, $locationType, $latitude, $longitude, $status, $safetyStock,$mfgId, $catalogId) {
    $success = TRUE;
      // Create Location, update if exists
      $stmt = $this->db->prepare("
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
      $out = print_r($stmt->errorInfo(), True);
      error_log($out);
      $success = FALSE;
    }

    // Create catalog if missing
    if (!$this->getCatalog($catalogId))
      $this->createCatalog($catalogId, $mfgId);

    // Create default Bin
    $this->createBin($intLID, 'Default', 'Default', $status);

    // Create LocationOffersCatalog
    $relational = $this->db->prepare("
      INSERT INTO LocationOffersCatalogs (catalogId, manufacturerId, internalLocationId)
      VALUES (:catalogId, :manufacturerId, :internalLocationId);
    ");

    $relational->bindParam(':catalogId', $catalogId);
    $relational->bindParam(':manufacturerId', $mfgId);
    $relational->bindParam(':internalLocationId', $intLID);

    if (!$relational->execute())  {
      $out = print_r($relational->errorInfo(), true);
      error_log($out);
      $success = FALSE;
    }

    return (($success == FALSE) ? 0 : 1);
  }

  public function createBin($fulfillerId, $externalLocationId, $name, $binType, $status) {
    $stmt = $this->db->prepare("
      INSERT INTO Bins
        (internalLocationId, binName, binType, status)
      VALUES
        ((SELECT FIRST(internalLocationId)
					FROM Locations
					WHERE fulfillerId = :fulfillerId 
					AND externalLocationId = :externalLocationId),
					:binName, :binType, :status);
    ");

    $stmt->bindValue(':fulfillerId', $fulfillerId);
    $stmt->bindValue(':externalLocationId', $externalLocationId);
    $stmt->bindValue(':binName', $name);
    $stmt->bindValue(':binType', $binType);
    $stmt->bindValue(':status', $status);

    return $stmt->execute();
  }

  private function getBin($binName, $internalLocationId) {
    $stmt = $this->db->prepare("
      SELECT * FROM Bins
      WHERE binName = :binName
      AND internalLocationId = :internalLocationId
    ");

    $stmt->bindValue(':binName', $binName);
    $stmt->bindValue(':internalLocationId', $internalLocationId);

    $stmt->execute();

    return $stmt->fetch(PDO::FETCH_ASSOC);
  }

  public function getBins($binName, $internalLocationId) {
    $stmt = $this->db->prepare("
      SELECT * FROM Bins
      WHERE binName LIKE :binName
      AND internalLocationId = :internalLocationId
    ");

    $stmt->bindValue(':binName', $binName);
    $stmt->bindValue(':internalLocationId', $internalLocationId);

    $stmt->execute();

    $arr = array();
    while ($arr[] = $stmt->fetch(PDO::FETCH_ASSOC));
    return $arr;
  }

  public function getBinTypes() {
     $stmt = $this->db->prepare("
      SELECT DISTINCT(binType) FROM Bins
    ");

    $stmt->execute();

    $arr = array();
    while ($arr[] = $stmt->fetch(PDO::FETCH_ASSOC));
    return $arr;
  }

  public function getBinStatuses() {
    $stmt = $this->db->prepare("
      SELECT DISTINCT status FROM Bins
      WHERE status IS NOT NULL
    ");

    $stmt->execute();

    $arr = array();
    while ($arr[] = $stmt->fetch(PDO::FETCH_ASSOC));
      return $arr;
  }

  private function getFulfillerIdFromLocationId($internalLocationId) {
    $stmt = $this->db->prepare("SELECT fulfillerId FROM Locations WHERE internalLocationId = :internalLocationId");
    $stmt->bindParam(':internalLocationId', $internalLocationId);
    $stmt->execute();

    $fetch = $stmt->fetch(PDO::FETCH_ASSOC);
    return $fetch['fulfillerId'];
  }

  public function refreshInventory($ExternalLocationId, $FulfillerID, $items) {
    // STATEMENTS
      return error_log('test'); //Not excecuted -- not in log
    try {
      $stmt1 = $this->db->prepare("
        INSERT INTO BinContainsProducts
          (binName, internalLocationId, productUpc, fulfillerId)
        VALUES
          (:binName, :internalLocationId, :productUpc, :fulfillerId)
      ");

      $stmt2 = $this->db->prepare("
        INSERT INTO FulfillerCarriesProducts(fulfillerId, productUpc, sku)
        VALUES(:fulfillerId, :productUpc, :sku)
      ");

      $stmt3 = $this->db->prepare("
        INSERT INTO LocationSellsProducts 
          (storeSku, safetyStock, ltd, allocated)
          VALUES (:storeSku, :safetyStock, :ltd, '0')
          WHERE fulfillerId=:fulfillerID
            AND productUpc=:productUpc
            AND internalLocationId=:internalLocationId
        ");

      $stmt4 = $this->db->prepare(
          "SELECT internalLocationId
            FROM Locations
            WHERE externalLocationId=:externalLocationId
              AND fulfillerId=:fulfillerId");

      $stmt4->bindParam(":fulfillerId", $FulfillerID);
      $stmt4->bindParam(":externalLocationId", $ExternalLocationId);

      $stmt4->execute();
      $fetch = $stmt4->fetch(PDO::FETCH_ASSOC);

      // UPDATE INVENTORY FOR EACH ITEM
      foreach ($items as $item) {
            
        $stmt1->bindParam(':binName', $item->BinID);
        $stmt1->bindParam(':internalLocationId', $fetch['internalLocationId']);
        $stmt1->bindParam(':productUpc', $item->UPC);
        $stmt1->bindParam(':fulfillerId', $FulfillerId);
        
        $stmt2->bindParam(':fulfillerId', $FulfillerId);
        $stmt2->bindParam(':productUpc', $item->UPC);
        $stmt2->bindParam(':sku', $item->PartNumber);
        $stmt3->bindParam(':internalLocationId', $fetch['internalLocationId']);
        $stmt3->bindParam(':productUpc', $item->UPC);
        $stmt3->bindParam(':storeSku', $item->PartNumber);
        $stmt3->bindParam(':safetyStock', $item->SafetyStock);
        $stmt3->bindParam(':ltd', $item->LTD);
        $stmt3->bindParam(':fulfillerId', $FulfillerId);
        
        // create bin if missing
        if (!$this->getBin($item->BinID, $fetch['internalLocationId']))
          print "Bin doesn't exist.\n";

        // execute queries
        else {
          $stmt1->execute();
          $stmt2->execute();
          $stmt3->execute();
        }
        
      }
    } catch ( Exception $e ) {
      error_log( $e->what() );
      return 0;
    }
    return 2;//array();
  }

  private function allocateInventory($fulfillerId, $items) {
    $success = True;

    $stmt = $this->db->prepare("
      UPDATE LocationSellsProducts
      SET allocated = allocated + :quantity
      WHERE fulfillerId = :fulfillerId
        AND internalLocationId =
          (SELECT FIRST(internalLocationId)
          FROM Locations
          WHERE fulfillerId = :fulfillerId2
            AND  externalLocationId = :externalLocationId);
    ");

    $stmt->bindParam(":fulfillerId", $fulfillerId);
    $stmt->bindParam(":fulfillerId2", $fulfillerId);

    foreach ($items as $item) {
      $stmt->bindParam(":externalLocationId", $item['ExternalLocationID']);
      $stmt->bindParam(":quantity", $item['Quantity']);

      if (!$stmt->execute())
        $success = False;
    }

    return $success;
  }

  public function fulfillInventory($fulfillInventoryRequest) {
    $success = true;
    $fulfillerId = $fulfillInventoryRequest['FulfillerId'];
    $items = $fulfillInventoryRequest['Items'];

    # TODO: Use getInventory to get the quantity before trying to subtract

    $stmt = $this->db->prepare("
      UPDATE LocationSellsProducts
      SET quantity = (quantity - :quantity)
      WHERE fulfillerId = :fulfillerId
        AND internalLocationId =
          (SELECT FIRST(internalLocationId)
          FROM Locations
          WHERE fulfillerId = :fulfillerId2
            AND externalLocationId = :externalLocationId);
    ");

    $stmt->bindParam(":fulfillerId", $fulfillerId);
    $stmt->bindParam(":fulfillerId2", $fulfillerId);

    foreach ($items as $item) {
      $stmt->bindParam(":externalLocationId", $item['ExternalLocationID']);
      $stmt->bindParam(":quantity", $item['Quantity']);

      if (!$stmt->execute())
        $success = false;
    }

    return $success;
  }

  public function createProduct($product) {
    if (!$this->getCatalog($product['catalog_id']))
      $this->createCatalog($product['catalog_id'], $product['mfg_id']);

    $stmt = $this->db->prepare("
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

  public function createCatalog($catalog_id, $mfg_id) {
    $stmt = $this->db->prepare("
      INSERT INTO Catalogs
        (catalogId, manufacturerId)
      VALUES
        (:catalogId, :manufacturerId)
    ");

    $stmt->bindParam(':catalogId', $catalog_id);
    $stmt->bindParam(':manufacturerId', $mfg_id);

    $stmt->execute();
  }

  private function getProductFromUpc($upc) {
    $stmt = $this->db->prepare("
      SELECT * FROM Products
      WHERE upc = :upc
    ");
    $stmt->bindParam(':upc', $upc);
    $stmt->execute();

    return $stmt->fetch(PDO::FETCH_ASSOC);
  }

  private function getCatalog($catalog_id) {
    $stmt = $this->db->prepare("
      SELECT * FROM Catalogs
      WHERE catalogId = :catalogId
    ");
    $stmt->bindParam(':catalogId', $catalogId);
    $stmt->execute();

    return $stmt->fetch(PDO::FETCH_ASSOC);
  }

  public function getFulfillerLocationType($externalLocationID) {
    $stmt = $this->prepare("SELECT locationType
      FROM Locations WHERE externalLocationId=':externalLocationId'");
    $stmt->bindParam(':externalLocationId', $externalLocationID);
    $stmt->execute();

    return $stmt->fetch(PDO::FETCH_ASSOC);
  }

  public function findLocations($fulfillerId, $catalog, $location, $maxlocations) {
  // developers.google.com/maps/articles/phpsqlsearch_v3
    $stmt = $this->db->prepare("
      SELECT FulfillerId, ExternalLocationID
      FROM (

      SELECT
  lc.fulfillerId AS FulFillerId,
  l.externalLocationId AS ExternalLocationID,
  ( 3959 * acos( cos( radians(:latitude) ) * cos( radians( l.latitude ) ) *
  cos( radians( l.longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) *
  sin( radians( l.latitude ) ) ) ) AS distance
      FROM Locations l INNER JOIN LocationOffersCatalogs lc
      ON lc.internalLocationId = l.interalLocationId
      WHERE lc.fulfillerId = :fulfillerId
      AND lc.manufacturerId = :mfgId
      AND lc.catalogId = :catalogId
      HAVING distance < :distance
      ORDER BY distance
      LIMIT 0 , :maxlocations

      ) myview
    ");
    $stmt->bindParam(':fufillerId', $fulfillerId);
    $stmt->bindParam(':mfgId', $catalog['mfg_id']);
    $stmt->bindParam(':catalogId', $catalog['catalog_id']);
    $stmt->bindParam(':latitude', $location['latitude']);
    $stmt->bindParam(':longitude', $location['longitude']);
    $stmt->bindParam(':distance', $location['radius']);
    $stmt->bindParam(':maxlocations', $maxlocations);
    $stmt->execute();

    return $stmt->fetchALL(PDO::FETCH_ASSOC);
  }

  public function adjustInventory($fulfillerId, $externalLocationId, $items) {
    $success = True;

    $stmt = $this->db->prepare("
      UPDATE LocationSellsProducts
        onHand = :quantity
      WHERE productUpc = :upc
        AND internalLocatioId =
          (SELECT FIRST(internalLocationId)
          FROM Locations
          WHERE fulfillerId = :fulfillerId
            AND  externalLocationId = :externalLocationId);
    ");

    $stmt->bindParam(":fulfillerId", $fulfillerId);
    $stmt->bindParam(":externalLocationId", $externalLocationId);

    foreach ($items as $item) {
      $stmt->bindParam(":upc", $item['UPC']);
      $stmt->bindParam(":quantity", $item['Quantity']);

      if (!$stmt->execute()) {
        $success = False;
      }
    }

    return $success;
  }

  public function getInventory($fulfillerID, $catalog, $quantities, $locationNames, $type, $limit, $ignoreSafetyStock, $includeNegInv, $orderByLTD) {
    $str = "SELECT externalLocationId AS LocationName,
             catalogId AS CatalogID,
             manufacturerId AS ManufacturerID,
             onHand AS OnHand,
             (onHand – allocated) – safetyStock AS Available,
             sku AS PartNumber,
             productUpc AS UPC,
             ltd AS LTD,
             safetyStock AS SafetyStock
      FROM ((Locations
      NATURAL JOIN LocationSellsProducts)
      NATURAL JOIN LocationOffersCatalog)
      NATURAL JOIN FulfillerCarriesProduct
      WHERE fulfillerId = :fulfillerID
      AND catalogId = :catalogID
      AND manufacturerId = :manufacturerID
      AND externalLocationId = :locationName";

    // Build rest of query string
    if (!$includeNegInv) {
      $str = $str . " AND (allocated";

      if (!$ignoreSafetyStock) {
        $str = $str . " + safetyStock";
      }
      $str = $str . ") < onHand";
    }
    if ($orderByLTD)
      $str = $str . " ORDER BY ltd";
    $str = $str . "LIMIT 0, :limit;";

    $stmt = $this->db->prepare($str);

    $stmt->bindParam(':fulfillerID', $fulfillerID);
    $stmt->bindParam(':catalogID', $catalog['CatalogID']);
    $stmt->bindParam(':manufacturerID', $catalog['ManufacturerID']);
    $stmt->bindParam(':type', $type);
    $stmt->bindParam(':quantities', $quantities);
    $stmt->bindParam(':limit', $limit);

    $arr = array();
    foreach ($locationNames as $currName) {
      $stmt->bindParam(':locationName', $currName);

      $stmt->execute();
      while ($arr[] = $stmt->fetch(PDO::FETCH_ASSOC));
    }

    return $arr;
  }

  public function seedInventory($items) {
    // STATEMENTS
    $stmt1 = $this->db->prepare("
      INSERT INTO BinContainsProducts
        (binName, internalLocationId, productUpc, fulfillerId)
      VALUES
        (:binName, :internalLocationId, :productUpc, :fulfillerId)
    ");

    $stmt2 = $this->db->prepare("
      INSERT INTO FulfillerCarriesProducts(fulfillerId, productUpc, sku)
      VALUES(:fulfillerId, :productUpc, :sku)
    ");

    $stmt3 = $this->db->prepare("
      INSERT INTO LocationSellsProducts (internalLocationId, productUpc, storeSku, safetyStock, ltd, allocated, onHand, fulfillerId)
      VALUES(:internalLocationId, :productUpc, :storeSku, :safetyStock, :ltd, '0', :onHand, :fulfillerId)
    ");

    // UPDATE INVENTORY FOR EACH ITEM
    foreach ($items as $item) {
      $fulfillerId = $this->getFulfillerIdFromLocationId($item['internal_fulfiller_location_id']);

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
      if (!$this->getProductFromUpc($item['UPC']))
        $this->createProduct($item);

      // create bin if missing
      if (!$this->getBin($item['bin_name'], $item['internal_fulfiller_location_id']))
        print "Bin doesn't exist.\n";

      // execute queries
      else {
        $stmt1->execute();
        $stmt2->execute();
        $stmt3->execute();
      }
    }
  }
}
