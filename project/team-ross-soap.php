<?php
include_once("team-ross-api.php");

class TeamRossSOAP {
  private $api;

  function __construct() {
    $hostname = 'localhost';
    $username = 'root';
    $password = 'password';
    $database = 'team_ross';

    try {
      $db = new PDO("mysql:host=$hostname;dbname=$database", $username, $password);
      $this->api = new TeamRossAPI($db);
    }
    catch (PDOException $e) {
      echo $e->getMessage();
      die();
    }
  }

  // **********************************************************************
  //
  // **********************************************************************
  function createFulfiller($FulfillerRequest) {
    return $this->api->createFulfiller($FulfillerRequest['FullfillerID'],
      $Fulfillerrequest['Name']) ? 0 : -1;
  }

  // **********************************************************************
  //
  // **********************************************************************
  function getFulfillerStatus() {

  }

  // **********************************************************************
  // RILEY
  // **********************************************************************
  function createFulfillmentLocation($fulfillerId, $retailerLocationId, $externalLocationId, $locationName,
				     $locationType, $latitude, $longitude, $status, $countryCode) {

  	$safetyStockLimitDefault = 10; // FIXME
  	// check if FulfillmentLocation exists!
  	// if location exists {
  	//	return false;
  	//}
  	//else {
  		$this->api->createFulfillmentLocation($locationName, $externalLocationId, $retailerLocationId,
  						       $fufillerId, $locationType, $latitude, $longitutde, $status,
  						       $safetyStockLimitDefault, NULL, NULL);
  	//}
  	return true; // Successfully created new FulfillmentLocation
  }

  // **********************************************************************
  //
  // **********************************************************************
  function getFulfillmentLocations() {

  }

  // **********************************************************************
  //
  // **********************************************************************
  function getFulfillmentLocationTypes() {

  }

  // **********************************************************************
  //
  // **********************************************************************
  function allocateInventory() {

  }

  // **********************************************************************
  //
  // **********************************************************************
  function deallocateInventory() {

  }

  // **********************************************************************
  //
  // **********************************************************************
  function fulfillInventory() {

  }

  // **********************************************************************
  //
  // **********************************************************************
  function getItemLocationsByFulfiller() {

  }

  // **********************************************************************
  // MATT S
  // **********************************************************************
  function createBin($CreateBinRequest) {
    return $this->api->createBin($CreateBinRequest['FulfillerLocationID'],
 		$CreateBinRequest['Name'], $CreateBinRequest['BinType'],
		$CreateBinRequest['BinStatus']) ? 0 : -1;
  }

  // **********************************************************************
  // IAN
  // **********************************************************************
  function getBins($GetBinsRequest) {
    $bins = $this->api->getBins($GetBinRequest['SearchTerm'], $GetBinRequest['FulfillerID'], $GetBinRequest['FulfillerLocationID']);

    return array_slice($bins, $GetBinRequest['ResultsStart'], $GetBinRequest['NumResults']);
  }

  // **********************************************************************
  //
  // **********************************************************************
  function getBinTypes() {

  }

  // **********************************************************************
  //
  // **********************************************************************
  function getBinStatuses() {

  }

  // **********************************************************************
  //
  // **********************************************************************
  function getInventory() {

  }

  // **********************************************************************
  //
  // **********************************************************************
  function adjustInventory() {

  }

  // **********************************************************************
  // MATT T
  // **********************************************************************
  function refreshInventory( $RefreshRequest ) {
    return $this->api->refreshInventory($RefreshRequest['items']);
  }
}
