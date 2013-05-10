<?php
class TeamRossAPI {
  private $db;

  public function __construct($db_handle) {
    $this->db = $db_handle;
  }

  public function createFulfiller( $id, $name ) {
    $stmt = $this->db->prepare("INSERT INTO Fulfillers (fulfillerId, name) VALUES (:id,:name)");
    $stmt->bindValue(':id', $id);
    $stmt->bindValue(':name',$name);
    $stmt->execute();
  }

  public function createFulfillmentLocation($locationName, $extLID, $intLID,
    $fulfillerId, $locationType, $latitude, $longitude, $status, $safetyStock) {
      $stmt = $this->db->prepare("
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
    }

  public function createBin($intLID, $name, $binType, $status) {
    $stmt = $this->db->prepare("
      INSERT INTO Bins
        (locationId, binName, binType, status)
      VALUES
        (:locationId, :binName, :binType, :status);
    ");
    $stmt->bindValue(':locationId', $intLID);
    $stmt->bindValue(':binName', $name);
    $stmt->bindValue(':binType', $binType);
    $stmt->bindValue(':status', $status);
    $stmt->execute();
  }

  public function refreshInventory($fulfillerId, $locationId, $items) {
    $item = array();

    $stmt1 = $this->db->prepare("INSERT INTO BinContainsProducts(BinId, FulfillerId, LocationId, UPC, Allocated, OnHand)
      VALUES(:binId, :fulfillerId, :locationId, :UPC, :allocated, :onHand)");
    $stmt1->bindParam(':binId', $item['BinId']);
    $stmt1->bindParam(':fulfillerId', $item['FulfillerId']);
    $stmt1->bindParam(':locationId', $item['LocationId']);
    $stmt1->bindParam(':UPC', $item['UPC']);
    $stmt1->bindParam(':allocated', $item['Allocated']);
    $stmt1->bindParam(':onHand', $item['Onhand']);

    $stmt2 = $this->db->prepare("INSERT INTO FulfillerCarriesProducts(FulfillerId, UPC, SKU)
      VALUES(:fulfillerId, :UPC, :SKU)");
    $stmt2->bindParam(':fulfillerId', $item['FulfillerId']);
    $stmt2->bindParam(':UPC', $item['UPC']);
    $stmt2->bindParam(':SKU', $item['SKU']);

    $stmt3 = $this->db->prepare("INSERT INTO LocationSellsProducts(FulfillerId, LocationId, UPC, SafetyStock, LTD)
      VALUES(:fulfillerId, :locationId, :UPC, :safetyStock, :LTD)");
    $stmt3->bindParam(':fulfillerId', $item['FulfillerId']);
    $stmt3->bindParam(':locationId', $item['LocationId']);
    $stmt3->bindParam(':UPC', $item['UPC']);
    $stmt3->bindParam(':safetyStock', $item['SafetyStock']);
    $stmt3->bindParam(':LTD', $item['LTD']);

    foreach($items as $item) {
      $stmt1->execute();
      $stmt2->execute();
      $stmt3->execute();
    }
  }
}
?>
