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
    return array('createFulfillerReturn' => $this->api->createFulfiller($FulfillerRequest->FullfillerID,
      $FulfillerRequest->Name) ? 0 : -1);
  }

  // **********************************************************************
  // Ross
  // **********************************************************************
  function getFulfillerStatus($getFulfillerStatusRequest) {
    return array('getFulfillerStatusReturn' => $this->api->getFulfillerStatus($getFulfillerStatusRequest->fulfillerID) ? 1 : 0);
  }

  // **********************************************************************
  // RILEY
  // **********************************************************************
  function createFulfillmentLocation($CreateFulfillmentLocationRequest) {
    $safetyStockLimitDefault = 0;
    $mfgIdDefault = 0;
    $catalogIdDefaut = 0;
    // no longer checks if FulfillmentLocation exists. Just updates!

    return array('createFulfillmentLocationReturn' => $this->api->createFulfillmentLocation($CreateFulfillmentLocationRequest->LocationName,
            $CreateFulfillmentLocationRequest->ExternalLocationID,
            $CreateFulfillmentLocationRequest->RetailerLocationID,
            $CreateFulfillmentLocationRequest->FulfillerID,
            $CreateFulfillmentLocationRequest->LocationType,
            $CreateFulfillmentLocationRequest->Latitude,
            $CreateFulfillmentLocationRequest->Longitude,
            $CreateFulfillmentLocationRequest->Status,
            $safetyStockLimitDefault,
            $mfgIdDefault,
            $catalogIdDefault
          )
        );
  }

  // **********************************************************************
  // Riley
  // **********************************************************************
  function getFulfillmentLocations($GetFulfillmentLocationsRequest) {
    return array('getFulfillmentLocationsReturn' => $this->api->findLocations($GetFulfillmentLocationsRequest->FulfillerID,
                    $GetFulfillmentLocationsRequest->Catalog,
                    $GetFulfillmentLocationsRequest->Location,
                    $GetFulfillmentLocationsRequest->MaxLocations
                  )
                );
  }

  // **********************************************************************
  // Matt T
  // **********************************************************************
  function getFulfillmentLocationTypes() {
    return array("FULFILLER", "RETAILER","RETAILER" );
  }

  // **********************************************************************
  // Matt S
  // **********************************************************************
  function allocateInventory($UpdateRequest) {
    return $this->api->allocateInventory($UpdateItem['FulfillerId'],
      $UpdateItem['Items']) ? 0 : -1;
  }

  // **********************************************************************
  // Ian
  // **********************************************************************
  function deallocateInventory() {

  }

  // **********************************************************************
  // Ross
  // **********************************************************************
  function fulfillInventory($fulfillInventoryRequest) {
    return $this->api->fulfillInventory($fulfillInventoryRequest);
  }

  // **********************************************************************
  // MATT S
  // **********************************************************************
  function createBin($CreateBinRequest) {
    return array(
      'createBinReturn' => $this->api->createBin($CreateBinRequest->FulfillerId,
                           $CreateBinRequest->Name, $CreateBinRequest->BinType,
                           $CreateBinRequest->BinStatus) ? 0 : -1
    );
  }

  // **********************************************************************
  // IAN
  // **********************************************************************
  function getBins($GetBinsRequest) {
    $bins = $this->api->getBins($GetBinRequest['SearchTerm'], $GetBinRequest['FulfillerLocationID']);

    return array(
      'getBinsReturn' => array(
        'Bins' => array_slice($bins, $GetBinRequest['ResultsStart'], $GetBinRequest['NumResults']),
        'ResultCount' => count($bins)
      ));
  }

  // **********************************************************************
  // Matt S
  // **********************************************************************
  function getBinTypes() {
    $bins = $this->api->getBinTypes();
    $returnArr = array();

    foreach ($bins as $bin)
      if ($bin['binType'])
        $returnArr[]['BinType'] = $bin['binType'];

    return array('getBinTypesReturn' => $returnArr);
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
    if ($this->api->adjustInventory($AdjustRequest['FulfillerId'],
      $AdjustRequest['ExternalLocationID'], $AdjustRequest['items'])) {
      return "SUCCESS!";
    }
    return "FAILURE!";
  }

  // **********************************************************************
  // MATT T
  // **********************************************************************
  function refreshInventory( $RefreshRequest ) {
    $eid = $RefreshRequest['ExternalLocationID'];
    $fid = $RefreshRequest['FulfillerID'];
    return $this->api->refreshInventory($eid, $fid, $RefreshRequest['items']);
  }
}
