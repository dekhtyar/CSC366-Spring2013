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
    $this->createBin($fulfillerId, $extLID, 'Default', 'Default', $status);

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
        ((SELECT internalLocationId
					FROM Locations
					WHERE fulfillerId = :fulfillerId
					AND externalLocationId = :externalLocationId
					LIMIT 1),
					:binName, :binType, :status);
    ");

    $stmt->bindValue(':fulfillerId', $fulfillerId);
    $stmt->bindValue(':externalLocationId', $externalLocationId);
    $stmt->bindValue(':binName', $name);
    $stmt->bindValue(':binType', $binType);
    $stmt->bindValue(':status', $status);

		if (!$stmt->execute()) {
			$out = print_r($stmt->errorInfo(), True);
			error_log($out);
			return false;
		}
		return true;
  }

  private function getBin($binName, $internalLocationId) {
    $stmt = $this->db->prepare("
      SELECT * FROM Bins
      WHERE binName = :binName
      AND internalLocationId = :internalLocationId
    ");

    if( $binName == 0 ) {
      $binName = 'Default';
    }
    $stmt->bindValue(':binName', $binName);
    $stmt->bindValue(':internalLocationId', $internalLocationId);

    $stmt->execute();

    return $stmt->fetch(PDO::FETCH_ASSOC);
  }

  public function getBins($binName, $fulfillerId, $externalLocationId) {
    $stmt = $this->db->prepare("
      SELECT * FROM Bins
      WHERE binName LIKE :binName
      AND internalLocationId = (SELECT internalLocationId
          FROM Locations
          WHERE fulfillerId = :fulfillerId
          AND externalLocationId = :externalLocationId
          LIMIT 1)
    ");

    if ($binName === 0) {
      $binName = 'Default';
    }

    $stmt->bindValue(':binName', $binName);
    $stmt->bindValue(':fulfillerId', $fulfillerId);
    $stmt->bindValue(':externalLocationId', $externalLocationId);

    $stmt->execute();

    $arr = array();
    while ($sel = $stmt->fetch(PDO::FETCH_ASSOC))
      $arr[] = $sel;

    return $arr;
  }

  public function getBinTypes() {
     $stmt = $this->db->prepare("
      SELECT DISTINCT(binType) FROM Bins
    ");

    $stmt->execute();

    $arr = array();
    while ($sel = $stmt->fetch(PDO::FETCH_ASSOC))
      $arr[] = $sel;

    return $arr;
  }

  public function getBinStatuses() {
    $stmt = $this->db->prepare("
      SELECT DISTINCT status FROM Bins
      WHERE status IS NOT NULL
    ");

    $stmt->execute();

    $arr = array();
    while ($sel = $stmt->fetch(PDO::FETCH_ASSOC))
      $arr[] = $sel['status'];

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
    try {
      $stmt1 = $this->db->prepare("
        INSERT INTO BinContainsProducts
          (binName, internalLocationId, productUpc, fulfillerId)
        VALUES
          (:binName, :internalLocationId, :productUpc, :fulfillerId)
          ON DUPLICATE KEY UPDATE
            binName=VALUES(binName),
            internalLocationId=VALUES(internalLocationId),
            productUpc=VALUES(productUpc),
            fulfillerId=VALUES(fulfillerId)
      ");

      $stmt2 = $this->db->prepare("
        INSERT INTO FulfillerCarriesProducts(fulfillerId, productUpc, sku)
        VALUES(:fulfillerId, :productUpc, :sku)
          ON DUPLICATE KEY UPDATE
            fulfillerId=VALUES(fulfillerId),
            productUpc=VALUES(productUpc),
            sku=VALUES(sku)
      ");

      $stmt3 = $this->db->prepare("
        UPDATE LocationSellsProducts SET
          storeSku=:storeSku, safetyStock=:safetyStock, ltd=:ltd
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
        if (0 == $item->BinID) {
          $item->BinID = 'Default';
        }

        $stmt1->bindParam(':binName', $item->BinID);
        $stmt1->bindParam(':internalLocationId', $fetch['internalLocationId']);
        $stmt1->bindParam(':productUpc', $item->UPC);
        $stmt1->bindParam(':fulfillerId', $FulfillerID);

        $stmt2->bindParam(':fulfillerId', $FulfillerID);
        $stmt2->bindParam(':productUpc', $item->UPC);
        $stmt2->bindParam(':sku', $item->PartNumber);

        $stmt3->bindParam(':storeSku',$item->PartNumber);
        $stmt3->bindParam(':safetyStock',$item->SafetyStock);
        $stmt3->bindParam(':ltd',$item->LTD);
        $stmt3->bindParam(':fulfillerID',$FulfillerID);
        $stmt3->bindParam(':productUpc',$item->UPC);
        $stmt3->bindParam(':internalLocationId',$fetch['internalLocationId']);

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
      error_log( $e->getMessage() );
      return false;
    }

    return true;
  }

  public function allocateInventory($allocateInventoryRequest) {
    $success = true;
    $fulfillerId = $allocateInventoryRequest->request->FulfillerID;
    $items = $allocateInventoryRequest->request->Items;

    $stmt = $this->db->prepare("
      UPDATE LocationSellsProducts
      SET allocated = (allocated + :quantity)
      WHERE fulfillerId = :fulfillerId
      AND productUpc = :productUpc
      AND internalLocationId =
        (SELECT internalLocationId
        FROM Locations
        WHERE fulfillerId = :fulfillerId2
        AND externalLocationId = :externalLocationId
        LIMIT 1);
    ");

    $stmt->bindParam(":fulfillerId", $fulfillerId);
    $stmt->bindParam(":fulfillerId2", $fulfillerId);

    // Convert items to a type we can understand
    if (!is_array($items->items))
      $items = array($items->items);
    else $items = $items->items;

    $x = print_r($items, true);
    error_log($x);

    foreach ($items as $item) {
      error_log("UPDATING WITH NEW QUANITYT: " . $item->Quantity);
      $stmt->bindParam(":productUpc", $item->UPC);
      $stmt->bindParam(":externalLocationId", $item->ExternalLocationID);
      $stmt->bindParam(":quantity", $item->Quantity);

      if (!$stmt->execute())
        $success = False;
    }

    return $success;
  }

  public function deallocateInventory($deallocateInventoryRequest) {
    $success = true;
    $fulfillerId = $deallocateInventoryRequest->request->FulfillerID;
    $items = $deallocateInventoryRequest->request->Items;

    $stmt = $this->db->prepare("
      UPDATE LocationSellsProducts
      SET allocated = (allocated - :quantity)
      WHERE fulfillerId = :fulfillerId
      AND productUpc = :productUpc
      AND internalLocationId =
        (SELECT internalLocationId
        FROM Locations
        WHERE fulfillerId = :fulfillerId2
        AND externalLocationId = :externalLocationId
        LIMIT 1);
    ");

    $stmt->bindParam(":fulfillerId", $fulfillerId);
    $stmt->bindParam(":fulfillerId2", $fulfillerId);

    // Convert items to a type we can understand
    if (!is_array($items->items))
      $items = array($items->items);
    else $items = $items->items;

    $x = print_r($items, true);
    error_log($x);

    foreach ($items as $item) {
      error_log("UPDATING WITH NEW QUANITYT: " . $item->Quantity);
      $stmt->bindParam(":productUpc", $item->UPC);
      $stmt->bindParam(":externalLocationId", $item->ExternalLocationID);
      $stmt->bindParam(":quantity", $item->Quantity);

      if (!$stmt->execute())
        $success = False;
    }

    return $success;
  }

  public function fulfillInventory($fulfillInventoryRequest) {
    $success = true;
    $fulfillerId = $fulfillInventoryRequest->request->FulfillerID;
    $items = $fulfillInventoryRequest->request->Items;

    $stmt = $this->db->prepare("
      UPDATE LocationSellsProducts
      SET onHand = (onHand - :quantity),
      allocated = (allocated - :quantity2)
      WHERE fulfillerId = :fulfillerId
      AND productUpc = :productUpc
      AND internalLocationId =
        (SELECT internalLocationId
        FROM Locations
        WHERE fulfillerId = :fulfillerId2
        AND externalLocationId = :externalLocationId
        LIMIT 1);
    ");

    $stmt->bindParam(":fulfillerId", $fulfillerId);
    $stmt->bindParam(":fulfillerId2", $fulfillerId);

    // Convert items to a type we can understand
    if (!is_array($items->items))
      $items = array($items->items);
    else $items = $items->items;

    foreach ($items as $item) {
      $stmt->bindParam(":productUpc", $item->UPC);
      $stmt->bindParam(":externalLocationId", $item->ExternalLocationID);
      $stmt->bindParam(":quantity", $item->Quantity);
      $stmt->bindParam(':quantity2', $item->Quantity);

      if (!$stmt->execute()) {
        $success = false;
        $err = print_r($stmt->errorInfo(), true);
        error_log($err);
      }
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
    $stmt->bindParam(':catalogId', $catalog_id);
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
        SELECT
          l.fulfillerId AS FulfillerID,
          l.externalLocationId AS ExternalLocationID,
          ( 3959 * acos( cos( radians(:latitude0) ) * cos( radians( l.latitude ) ) *
          cos( radians( l.longitude ) - radians(:longitude) ) + sin( radians(:latitude1) ) *
          sin( radians( l.latitude ) ) ) ) AS distance
        FROM Locations l INNER JOIN LocationOffersCatalogs lc
        ON lc.internalLocationId = l.internalLocationId
        WHERE l.fulfillerId = :fulfillerId
        AND lc.manufacturerId = :mfgId
        AND lc.catalogId = :catalogId
        HAVING distance < :distance
        ORDER BY distance
        LIMIT 0 , :limit
    ");
    $stmt->bindParam(':fulfillerId', $fulfillerId);
    $stmt->bindParam(':mfgId', $catalog->ManufacturerID);
    $stmt->bindParam(':catalogId', $catalog->CatalogID);
    $stmt->bindParam(':latitude0', $location->Latitude);
    $stmt->bindParam(':latitude1', $location->Latitude);
    $stmt->bindParam(':longitude', $location->Longitude);
    $stmt->bindParam(':distance', $location->Radius);
    $stmt->bindParam(':limit', $maxlocations, PDO::PARAM_INT);

    if (!$stmt->execute()) {
        $success = false;
        $err = print_r($stmt->errorInfo(), true);
        error_log($err);
		}

		$arr = array();
    while ($loc = $stmt->fetch(PDO::FETCH_ASSOC)) {
			$assignment = new \stdClass;
			$assignment->FulfillerID = $loc['FulfillerID'];
			$assignment->ExternalLocationID = $loc['ExternalLocationID'];
      $arr[] = $assignment;
		}
    return $arr;
  }

  public function adjustInventory($fulfillerId, $externalLocationId, $items) {
    $success = True;

    $stmt = $this->db->prepare("
      UPDATE LocationSellsProducts
			SET onHand = :quantity
      WHERE productUpc = :upc
        AND internalLocationId =
	        (SELECT internalLocationId
	        FROM Locations
	        WHERE fulfillerId = :fulfillerId
	        AND externalLocationId = :externalLocationId
	        LIMIT 1);
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
	if (!is_array($quantities->items))
	  $items = array($quantities->items);
  else $items = $quantities->items;

      $str = "SELECT externalLocationId AS LocationName,
                   catalogId AS CatalogID,
                   manufacturerId AS ManufacturerID,
                   onHand AS OnHand,
                   (onHand - allocated";
    if (!$ignoreSafetyStock) {
        $str = $str . " - safetyStock";
    }
    $str = $str . ") AS Available,
                   sku AS PartNumber,
                   productUpc AS UPC,
                   ltd AS LTD,
                   safetyStock AS SafetyStock
            FROM ((Locations
                 NATURAL JOIN LocationSellsProducts)
                 NATURAL JOIN LocationOffersCatalogs)
                 NATURAL JOIN FulfillerCarriesProducts
            WHERE fulfillerId = :fulfillerID
              AND catalogId = :catalogID
              AND manufacturerId = :manufacturerID
              AND (";

    $i = 0;
    foreach ($locationNames as $currLoc) {
      if ($i != 0)
        $str .= " OR ";

      $str .= "externalLocationId = :location" . $i;
    }

    if (count($locationNames) > 0)
      $str = $str . ") \nAND (";

    $i = 0;
    foreach ($items as $currItem) {
      if ($i > 0) {
        if ($type = "ANY" || $type == "PARTIAL")
          $str = $str . "\nOR ";
        else
          $str = $str . "\nAND ";
      }

      if ( !($includeNegInv && $type == "ANY") ) {
        $str = $str . "( (onHand - allocated ";

        if (!$ignoreSafetyStock)
          $str = $str . "- safetyStock ";

        $str = $str . ") >= :quantity" . $i . " AND ";
      }

      $str = $str . "productUpc = :upc" . $i . ")";
      // We can ignore PartNumber since UPC isn't nillable

      $i++;
    }

    $str = $str . ")";


		error_log($str);

    if ($orderByLTD) $str    = $str . "\n            ORDER BY ltd DESC";
    if ($limit != null) $str = $str . "\n            LIMIT 0, :limit";

    $stmt = $this->db->prepare($str);
    $stmt->bindParam(':fulfillerID', $fulfillerID, PDO::PARAM_INT);
    $stmt->bindParam(':catalogID', $catalog->CatalogID, PDO::PARAM_INT);
    $stmt->bindParam(':manufacturerID', $catalog->ManufacturerID, PDO::PARAM_INT);

    if ($limit != null)
      $stmt->bindParam(':limit', $limit, PDO::PARAM_INT);

    // Bind Quantities
    $i = 0;
    $one = 1;
    foreach ($items as $currItem) {
      $stmt->bindParam(":upc" . $i, $currItem->UPC);

      if ($type === "ANY")
        $stmt->bindParam(":quantity" . $i, $one, PDO::PARAM_INT);
      else
        $stmt->bindParam(":quantity" . $i, $currItem->Quantity, PDO::PARAM_INT);

      $i++;
    }

    // Bind locations
    $i = 0;
    foreach ($locationNames as $currLoc)
      $stmt->bindParam(':location' . $i++, $currLoc);

    // Execute
    if (!$stmt->execute())
			error_log(print_r($stmt->errorInfo(), true));

		error_log($stmt->rowCount());

    $arr = array();
    while ($sel = $stmt->fetch(PDO::FETCH_OBJ))
      $arr[] = $sel;

    return $arr;
  }
}
