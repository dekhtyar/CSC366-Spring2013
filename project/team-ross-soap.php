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
  // GROUP
  // **********************************************************************
  function createFulfiller($FulfillerRequest) {
    return $this->api->createFulfiller($FulfillerRequest['FullfillerID'],
      $Fulfillerrequest['Name']) ? 0 : -1;
  }

  // **********************************************************************
  // Ross
  // **********************************************************************
  function getFulfillerStatus() {

  }

  // **********************************************************************
  // RILEY
  // **********************************************************************
  function createFulfillmentLocation($CreateFulfillmentLocationRequest) {
    $safetyStockLimitDefault = 0; // FIXME
    $mfgIdDefault = 0;
    $catalogIdDefaut = 0;
    // no longer checks if FulfillmentLocation exists. Just updates!
    $this->api->createFulfillmentLocation($CreateFulfillmentLocationRequest['LocationName'],
					  $CreateFulfillmentLocationRequest['ExternalLocationID'],
					  $CreateFulfillmentLocationRequest['RetailerLocationID'],
					  $CreateFulfillmentLocationRequest['FulfillerID'],
					  $CreateFulfillmentLocationRequest['LocationType'],
					  $CreateFulfillmentLocationRequest['Latitude'],
					  $CreateFulfillmentLocationRequest['Longitude'],
					  $CreateFulfillmentLocationRequest['Status'],
					  $safetyStockLimitDefault,
					  $mfgIdDefault;
					  $catalogIdDefault; 
  }

  // **********************************************************************
  // Riley
  // **********************************************************************
  function getFulfillmentLocations($GetFulfillmentLocationsRequest) {
    return $this->api->findLocations($GetFulfillmentLocationsRequest['FulfillerId'], 
				    $GetFulfillmentLocationsRequest['Catalog'],
				    $GetFulfillmentLocationsRequest['Location'],
				    $GetFulfillmentLocationsRequest['MaxLocations']; 

  }

  // **********************************************************************
  // Matt T
  // **********************************************************************
  function getFulfillmentLocationTypes() {

  }

  // **********************************************************************
  // Matt S
  // **********************************************************************
  function allocateInventory() {

  }

  // **********************************************************************
  // Ian
  // **********************************************************************
  function deallocateInventory() {

  }

  // **********************************************************************
  // Ross
  // **********************************************************************
  function fulfillInventory() {

  }

  // **********************************************************************
  // Matt T
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
  // Matt S
  // **********************************************************************
  function getBinTypes() {

  }

  // **********************************************************************
  // Riley
  // **********************************************************************
  function getBinStatuses() {

  }

  // **********************************************************************
  // Ian
  // *********************************************************************
  function getInventory() {

  }

  // **********************************************************************
  // Group
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
