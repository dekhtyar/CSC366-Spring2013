<?php 
include_once("db.php");

function createFulfiller( $id ) {
  $stmt = $db->prepare("INSERT INTO Fulfillers (fulfillerId) VALUES (:id)");
  $stmt->bindValue(':id', $id);
  $stmt->execute();
}

function createFulfillmentLocation($locationId, $fulfillerId, $name, $locationType, $latitude, $longitude, $status, $safetyStockLimitDefault) {
  $stmt = $db->prepare("INSERT INTO Fulfillers (locationId, fulfillerId, name, 
    locationType, latitude, longitude, status, safetyStockLimitDefault) 
    VALUES (:locationId, :fulfillerId, :name, :locationType, :latitude, 
    :longitude, :status, :safetyStockLimitDefault)");
  $stmt->bindValue(':fulfillerId',$fulfillerId);
  $stmt->bindValue(':locationId',$locationId);
  $stmt->bindValue(':retailerId',$retailerId);
  $stmt->bindValue(':locationName',$locationName);
  $stmt->bindValue(':typeId',$typeId;
  $stmt->bindValue(':latitude',$latidude);
  $stmt->bindValue(':longitude',$logintude);
  $stmt->bindValue(':status',$status);
  $stmt->bindValue(':countryCode',$countryCode);
  $stmt->execute();
  
} 
?>

