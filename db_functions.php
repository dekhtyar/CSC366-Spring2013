<?php
include_once("db.php");

function createFulfiller( $id, $name ) {
  $stmt = $db->prepare("INSERT INTO Fulfillers (fulfillerId, name) VALUES (:id,:name)");
  $stmt->bindValue(':id', $id);
  $stmt->bindValue(':name',$name);
  $stmt->execute();
}

function createFulfillmentLocation($locationName, $extLID, $intLID, 
  $fulfillerId, $locationType, $latitude, $longitude, $status, $safetyStock) {
    $stmt = $db->prepare(" 
      INSERT INTO Locations
      (externalLocationID, internalLocationID, fulfillerId, locationType, 
      latitude, longitude, status, safetyStockLimitDefault)
      VALUES
      (:extLID, :intLID, :fulfillerId, :locationType, :latitude, :longitude,
      :status, :safetyStock)");
    $stmt->bindValue(':extLID',$extLID);
    $stmt->bindValue(':intLID',$intLID);
    $stmt->bindValue(':fulfillerId',$fulfillerId);
    $stmt->bindValue(':locationType',$locationType;
    $stmt->bindValue(':latitude',$latidude);
    $stmt->bindValue(':longitude',$longintude);
    $stmt->bindValue(':status',$status);
    $stmt->bindValue(':safeyStock',$safetyStock);
    $stmt->execute();
  }

function createBin($binName, $fulfillerId, $locationId, $binType, $status)
{
  $stmt = $db->prepare("INSERT INTO Bins( BinName, FulfillerID, LocationID, binType, Status) VALUES (:BinName, :FulfillerID, :FulfillerLocationID, :BinTypeID, :BinStatusID)");
  $stmt->bindValue(':BinName', $binName);
  $stmt->bindValue(':FulfillerId', $fulfillerId);
  $stmt->bindValue(':FulfillerLocationId', $locationId);
  $stmt->bindValue(':BinTypeID', $binType);
  $stmt->bindValue(':BinStatusID', $status);
  $stmt->execute();
}

function refreshInventory($fulfillerId, $locationId, $items) {
  $item = array();

  $stmt1 = $db->prepare("INSERT INTO BinContainsProducts(BinId, FulfillerId, LocationId, UPC, Allocated, OnHand)
    VALUES(:binId, :fulfillerId, :locationId, :UPC, :allocated, :onHand)");
  $stmt1->bindParam(':binId', $item['BinId']);
  $stmt1->bindParam(':fulfillerId', $item['FulfillerId']);
  $stmt1->bindParam(':locationId', $item['LocationId']);
  $stmt1->bindParam(':UPC', $item['UPC']);
  $stmt1->bindParam(':allocated', $item['Allocated']);
  $stmt1->bindParam(':onHand', $item['Onhand']);

  $stmt2 = $db->prepare("INSERT INTO FulfillerCarriesProducts(FulfillerId, UPC, SKU)
    VALUES(:fulfillerId, :UPC, :SKU)");
  $stmt2->bindParam(':fulfillerId', $item['FulfillerId']);
  $stmt2->bindParam(':UPC', $item['UPC']);
  $stmt2->bindParam(':SKU', $item['SKU']);

  $stmt3 = $db->prepare("INSERT INTO LocationSellsProducts(FulfillerId, LocationId, UPC, SafetyStock, LTD)
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
?>

