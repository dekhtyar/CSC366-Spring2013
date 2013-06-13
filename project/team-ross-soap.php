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
      //Both of these to throw exceptions
      $db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
      $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
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
    $request = $FulfillerRequest->request;
    return array('createFulfillerReturn' =>
      $this->api->createFulfiller($request->FulfillerID, $request->Name) ? 0 : -1);
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
    $request = $CreateFulfillmentLocationRequest->request;

    $safetyStockLimitDefault = 0;
    $mfgIdDefault = 0;
    $catalogIdDefault = 0;

    $countrycode = $CreateFulfillmentLocationRequest->CountryCode; // FIXME: Doesn't exist in DB!

    // no longer checks if FulfillmentLocation exists. Just updates!
    return array('createFulfillmentLocationReturn' =>
      $this->api->createFulfillmentLocation(
            $request->LocationName,
            $request->ExternalLocationID,
            $request->RetailerLocationID,
            $request->FulfillerID,
            $request->LocationType,
            $request->Latitude,
            $request->Longitude,
            $request->Status,
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
    return array('getFulfillmentLocationsReturn' =>
        $this->api->findLocations($GetFulfillmentLocationsRequest->FulfillerID,
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
  function deallocateInventory($UpdateItem) {
    foreach ($UpdateItem['Items'] as $CurrElem)
      $CurrElem['Quantity'] = $CurrElem['Quantity'] * -1;

    return $this->api->allocateInventory($UpdateItem['FulfillerId'],
                         $UpdateItem['Items']) ? 0 : -1;
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
    $request = $CreateBinRequest->request;
    return array('createBinReturn' => $this->api->createBin(
				$request->FulfillerID, $request->ExternalLocationID,
				$request->Name, $request->BinType,
				$request->BinStatus
			) ? 0 : -1
    );
  }

  // **********************************************************************
  // IAN
  // **********************************************************************
  function getBins($GetBinsRequest) {
    $request = $GetBinsRequest->request;
    $bins = $this->api->getBins($request->SearchTerm, $request->FulfillerLocationID);

    return array(
      'getBinsReturn' => array(
        'Bins' => $bins,
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
    $bins = $this->api->getBinStatuses;
    $returnArr = array();

    foreach ($bins as $bin)
      if ($bin['BinStatus'])
        $returnArr[]['BinStatus'] = $bin['status'];

    return array('getBinStatusesReturn' => $returnArr);

  }

  // **********************************************************************
  // Ian
  // *********************************************************************
  function getInventory($GetInventoryRequest) {
    return $this->api->getInventory($GetInventoryRequest['FulfillerId'],
            $GetInventoryRequest['Catalog'],
            $GetInventoryRequest['Quantities'],
            $GetInventoryRequest['LocationNames'],
            $GetInventoryRequest['Location'],
            $GetInventoryRequest['Type'],
            $GetInventoryRequest['Limit'],
            $GetInventoryRequest['IgnoreSafetyStock'],
            $GetInventoryRequest['IncludeNegativeInventory'],
            $GetInventoryRequest['OrderByLTD']
          );
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
    $out = print_r( $RefreshRequest, true );
    $eid = $RefreshRequest->ExternalLocationID;
    $fid = $RefreshRequest->FulfillerID;
    return $this->api->refreshInventory($eid, $fid, $RefreshRequest->Items);
  }
}
