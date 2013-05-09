<?php 
include_once("db.php");

function createFulfiller( $id ) {
  $stmt = $db->prepare("INSERT INTO Fulfillers (fulfillerId) VALUES (:id)");
  $stmt->bindValue(':id', $id);
  $stmt->execute();
}

function createFulfillmentLocation($locationId, $fulfillerId, $locationName, 
  $locationType, $latitude, $longitude, $status, $safetyStock) {
    $stmt = $db->prepare("
      INSERT INTO Fulfillers 
          (locationId, fulfillerId, name, locationType, latitude, longitude, 
            status, safetyStockLimitDefault) 
      VALUES 
        (:locationId, :fulfillerId, :name, :locationType, :latitude, :longitude,
    :status, :safetyStock)");
  $stmt->bindValue(':locationId',$locationId);
  $stmt->bindValue(':fulfillerId',$fulfillerId);
  $stmt->bindValue(':locationName',$locationName);
  $stmt->bindValue(':locationType',$locationType;
  $stmt->bindValue(':latitude',$latidude);
  $stmt->bindValue(':longitude',$longintude);
  $stmt->bindValue(':status',$status);
  $stmt->bindValue(':safeyStock',$safetyStock);
  $stmt->execute();
} 

function createBin($BinName, $FillerId, $LocationId, $binType, $Status) {
  
}
function refreshInventory( $LocationName, $SKU, $UPC, $BinID, 
  $Quantity, $LTD, $SafetyStock) {
  
}
?>

