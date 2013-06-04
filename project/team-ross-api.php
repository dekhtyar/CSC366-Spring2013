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

  public function getFullfilerStatus($fulfiller_id) {
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
      // Create Location
      $stmt = $this->db->prepare("
      	IF EXISTS (SELECT * FROM Locations WHERE internalLocationId = :internalLocationId)
      	  UPDATE Locations
      	  SET (externalLocationId = :externalLocationId,
      	       fulfillerId = :fulfillerId,
      	       locationType = :locationType ,
      	       latitude = :latitude,
      	       longitude = :longitude,
      	       status = :status,
      	       safetyStockLimitDefault = :safetyStockLimitDefault)
      	  WHERE internalLocationId = :internalLocationId

      	ELSE
      	  INSERT INTO Locations
      	   (externalLocationId, internalLocationId, fulfillerId, locationType,
      	    latitude, longitude, status, safetyStockLimitDefault)
      	  VALUES
      	    (:externalLocationId, :internalLocationId, :fulfillerId, :locationType,
      	    :latitude, :longitude, :status, :safetyStockLimitDefault);
      ");

    $stmt->bindValue(':externalLocationId', $extLID);
    $stmt->bindValue(':internalLocationId', $intLID);
    $stmt->bindValue(':fulfillerId', $fulfillerId);
    $stmt->bindValue(':locationType', $locationType);
    $stmt->bindValue(':latitude', strval($latitude));
    $stmt->bindValue(':longitude', strval($longitude));
    $stmt->bindValue(':status', $status);
    $stmt->bindValue(':safetyStockLimitDefault', $safetyStock);

    $stmt->execute();
    // Create catalog if missing
    if (!$this->getCatalog($catalogId))
      $this->createCatalog($catalogId, $mfgId);

    // Create LocationOffersCatalog
    $relational = $this->db->prepare("
      INSERT INTO LocationOffersCatalogs (catalogId, manufacturerId, internalLocationId, fulfillerId)
      VALUES (:catalogId, :manufacturerId, :internalLocationId, :fulfillerId);
    ");

    $relational->bindParam(':catalogId', $catalogId);
    $relational->bindParam(':manufacturerId', $mfgId);
    $relational->bindParam(':internalLocationId', $intLID);
    $relational->bindParam(':fulfillerId', $fulfillerId);

    $relational->execute();
  }

  public function createBin($intLID, $name, $binType, $status) {
    $stmt = $this->db->prepare("
      INSERT INTO Bins
        (internalLocationId, binName, binType, status, fulfillerId)
      VALUES
        (:internalLocationId, :binName, :binType, :status, :fulfillerId);
    ");

    $stmt->bindValue(':internalLocationId', $intLID);
    $stmt->bindValue(':binName', $name);
    $stmt->bindValue(':binType', $binType);
    $stmt->bindValue(':status', $status);
    $stmt->bindValue(':fulfillerId', $this->getFulfillerIdFromLocationId($intLID));

    return $stmt->execute();
  }

  private function getBin($binName, $fulfillerId, $internalLocationId) {
    $stmt = $this->db->prepare("
      SELECT * FROM Bins
      WHERE binName = :binName
      AND fulfillerId = :fulfillerId
      AND internalLocationId = :internalLocationId
    ");

    $stmt->bindValue(':binName', $binName);
    $stmt->bindValue(':fulfillerId', $fulfillerId);
    $stmt->bindValue(':internalLocationId', $internalLocationId);

    $stmt->execute();

    return $stmt->fetch(PDO::FETCH_ASSOC);
  }

  private function getBins($binName, $fulfillerId, $internalLocationId) {
    $stmt = $this->db->prepare("
      SELECT * FROM Bins
      WHERE binName LIKE :binName
      AND fulfillerId = :fulfillerId
      AND internalLocationId = :internalLocationId
    ");

    $stmt->bindValue(':binName', $binName);
    $stmt->bindValue(':fulfillerId', $fulfillerId);
    $stmt->bindValue(':internalLocationId', $internalLocationId);

    $stmt->execute();

    $arr = array();
    while ($arr[] = $stmt->fetch(PDO::FETCH_ASSOC));
    return $arr;
  }

	private function getBinTypes() {
		 $stmt = $this->db->prepare("
      SELECT binType FROM Bins
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

  public function refreshInventory($items) {
    // STATEMENTS
    $stmt1 = $this->db->prepare("
      INSERT INTO BinContainsProducts
        (binName, fulfillerId, internalLocationId, productUpc, allocated, onHand)
      VALUES
        (:binName, :fulfillerId, :internalLocationId, :productUpc, '0', :onHand)
    ");

    $stmt2 = $this->db->prepare("INSERT INTO FulfillerCarriesProducts(fulfillerId, productUpc, sku)
        VALUES(:fulfillerId, :productUpc, :sku)");

    $stmt3 = $this->db->prepare("
        INSERT INTO LocationSellsProducts (internalLocationId, productUpc, storeSku, safetyStock, ltd)
        VALUES(:internalLocationId, :productUpc, :storeSku, :safetyStock, :ltd)
      ");

    // UPDATE INVENTORY FOR EACH ITEM
    foreach ($items as $item) {
      $fulfillerId = $this->getFulfillerIdFromLocationId($item['internal_fulfiller_location_id']);

      $stmt1->bindParam(':binName', $item['bin_name']);
      $stmt1->bindParam(':fulfillerId', $fulfillerId);
      $stmt1->bindParam(':internalLocationId', $item['internal_fulfiller_location_id']);
      $stmt1->bindParam(':productUpc', $item['UPC']);
      $stmt1->bindParam(':onHand', $item['onhand']);

      $stmt2->bindParam(':fulfillerId', $fulfillerId);
      $stmt2->bindParam(':productUpc', $item['UPC']);
      $stmt2->bindParam(':sku', $item['SKU']);

      $stmt3->bindParam(':internalLocationId', $item['internal_fulfiller_location_id']);
      $stmt3->bindParam(':productUpc', $item['UPC']);
      $stmt3->bindParam(':storeSku', $item['SKU']);
      $stmt3->bindParam(':safetyStock', $item['safety_stock']);
      $stmt3->bindParam(':ltd', $item['ltd']);

      // create product if missing
      if (!$this->getProductFromUpc($item['UPC']))
        $this->createProduct($item);

      // create bin if missing
      if (!$this->getBin($item['bin_name'], $fulfillerId, $item['internal_fulfiller_location_id']))
        $this->createBin($item['internal_fulfiller_location_id'], $item['bin_name'], '', '');

      // execute queries
      $stmt1->execute();
      $stmt2->execute();
      $stmt3->execute();
    }
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

  public function findLocations($fulfillerId, $catalog, $location, $maxlocations) {
  // developers.google.com/maps/articles/phpsqlsearch_v3
    $stmt = $this->db->prepare("
      SELECT l.externalLocationId, ( 3959 * acos( cos( radians(:latitude) ) * cos( radians( l.latitude ) ) * cos( radians( l.longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( l.latitude ) ) ) ) AS distance
      FROM Locations l INNER JOIN LocationOffersCatalogs lc
      ON lc.internalLocationId = l.interalLocationId
      WHERE lc.fulfillerId = :fulfillerId
	    AND lc.manufacturerId = :mfgId
	    AND lc.catalogId = :catalogId
      HAVING distance < :distance
      ORDER BY distance
      LIMIT 0 , :maxlocations
    ");
    $stmt->bindParam(':fufillerId', $fulfillerId);
    $stmt->bindParam(':mfgId', $catalog['mfg_id']);
    $stmt->bindParam(':catalogId', $catalog['catalog_id']);
    $stmt->bindParam(':latitude', $location['latitude']);
    $stmt->bindParam(':longitude', $location['longitude']);
    $stmt->bindParam(':distance', $location['radius']);
    $stmt->bindParam(':maxlocations', $maxlocations);
    $stmt->execute();

    return $stmt->fetch(PDO::FETCH_ASSOC);
  }
}
