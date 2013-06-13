<?php
  include_once('soap-client.php');

  switch ($argv[1]):

    case 'create':
      print "Running createFulfiller, createFulfillmentLocation, and createBin\n\n";
      createFulfiller($client, new \stdClass);
      createFulfillmentLocation($client, new \stdClass);
      createBin($client, new \stdClass);
      break;

    default:
      $argv[1]($client, new \stdClass);
      break;

  endswitch;

  function createFulfiller($client, $request) {
    $fulfillersProcessed = array();
    $data = get_csv_data('csv_data/fulfiller_locations.csv');
    $request->request = new \stdClass;

    foreach ($data as $location) {
      $request->request->FulfillerID = $location['fulfiller_id'];
      $request->request->Name = $location['name'];

      if (in_array($location['fulfiller_id'], $fulfillersProcessed))
        continue;
      else
        $fulfillersProcessed[] = $location['fulfiller_id'];

      $ret = $client->createFulfiller($request);
      print_r($ret);
    }
  }

  function getFulfillerStatus($client, $request) {
    $request->fulfillerID = 91710;
    $ret = $client->getFulfillerStatus($request);
    print_r($ret);
  }

  function createFulfillmentLocation($client, $request) {
    $data = get_csv_data('csv_data/fulfiller_locations.csv');
    $request->request = new \stdClass;

    foreach ($data as $location) {
      $request->request->FulfillerID = $location['fulfiller_id'];
			$request->request->ExternalLocationID = $location['external_fulfiller_location_id'];
			$request->request->RetailerLocationID = $location['internal_fulfiller_location_id'];
      $request->request->LocationName = $location['name'];
      $request->request->LocationType = null;
      $request->request->Latitude = $location['latitude'];
      $request->request->Longitude = $location['longitude'];
      $request->request->Status = $location['status'];

      $ret = $client->createFulfillmentLocation($request);
      print_r($ret);
    }
  }

  function getFulfillmentLocations($client, $request) {
    $request->request = new \stdClass;

    $request->request->FulfillerID = 91710;
    $request->request->Catalog = new \stdClass;
    $request->request->Catalog->ManufacturerID = 1748;
    $request->request->Catalog->CatalogID = 1;
    $request->request->Location = new \stdClass;
    $request->request->Location->Unit = 'MILES';
    $request->request->Location->Radius = 100;
    $request->request->Location->PostalCode = 93450;
    $request->request->Location->Latitude = 45.200100;
    $request->request->Location->Longitude = -93.083100;
    $request->request->Location->CountryCode = 5;
    $request->request->MaxLocations = 5;

    $ret = $client->getFulfillmentLocations($request);
    print_r($ret);
  }

  function getFulfillmentLocationTypes($client, $request) {
    $ret = $client->getFulfillmentLocationTypes($request);
    print_r($ret);
  }

  function allocateInventory($client, $request) {
    $request->request = new \stdClass;
    $request->request->FulfillerID = 69170;

    $request->request->FulfillerLocationCatalog = new \stdClass;
    $request->request->FulfillerLocationCatalog->ExternalLocationID = 440008;
    $request->request->FulfillerLocationCatalog->ManufacturerCatalog = new \stdClass;

    $request->request->FulfillerLocationCatalog->ManufacturerCatalog->ManufacturerID = 11416;
    $request->request->FulfillerLocationCatalog->ManufacturerCatalog->CatalogID = 0;

    $item = new \stdClass;
    $item->PartNumber = 8888010248;
    $item->UPC = 8888010248;
    $item->Quantity = 2;
    $item->OrderID = null;
    $item->OrderItemID = null;
    $item->ShipmentID = null;
    $item->ExternalLocationID = 440008;

    $item2 = new \stdClass;
    $item2->PartNumber = 8888010248;
    $item2->UPC = 8888010248;
    $item2->Quantity = 3;
    $item2->OrderID = null;
    $item2->OrderItemID = null;
    $item2->ShipmentID = null;
    $item2->ExternalLocationID = 440008;

    $request->request->Items = array($item, $item2);

    $ret = $client->allocateInventory($request);
    print_r($ret);
  }

  function deallocateInventory($client, $request) {
    $request->request = new \stdClass;
    $request->request->FulfillerID = 69170;

    $request->request->FulfillerLocationCatalog = new \stdClass;
    $request->request->FulfillerLocationCatalog->ExternalLocationID = 440008;
    $request->request->FulfillerLocationCatalog->ManufacturerCatalog = new \stdClass;

    $request->request->FulfillerLocationCatalog->ManufacturerCatalog->ManufacturerID = 11416;
    $request->request->FulfillerLocationCatalog->ManufacturerCatalog->CatalogID = 0;

    $item = new \stdClass;
    $item->PartNumber = 8888010248;
    $item->UPC = 8888010248;
    $item->Quantity = 2;
    $item->OrderID = null;
    $item->OrderItemID = null;
    $item->ShipmentID = null;
    $item->ExternalLocationID = 440008;

    $item2 = new \stdClass;
    $item2->PartNumber = 8888010248;
    $item2->UPC = 8888010248;
    $item2->Quantity = 3;
    $item2->OrderID = null;
    $item2->OrderItemID = null;
    $item2->ShipmentID = null;
    $item2->ExternalLocationID = 440008;

    $request->request->Items = array($item, $item2);

    $ret = $client->deallocateInventory($request);
    print_r($ret);
  }

  function fulfillInventory($client, $request) {
    $request->request = new \stdClass;
    $request->request->FulfillerID = 69170;

    $request->request->FulfillerLocationCatalog = new \stdClass;
    $request->request->FulfillerLocationCatalog->ExternalLocationID = 440008;
    $request->request->FulfillerLocationCatalog->ManufacturerCatalog = new \stdClass;

    $request->request->FulfillerLocationCatalog->ManufacturerCatalog->ManufacturerID = 11416;
    $request->request->FulfillerLocationCatalog->ManufacturerCatalog->CatalogID = 0;

    $item = new \stdClass;
    $item->PartNumber = 8888010248;
    $item->UPC = 8888010248;
    $item->Quantity = 2;
    $item->OrderID = null;
    $item->OrderItemID = null;
    $item->ShipmentID = null;
    $item->ExternalLocationID = 440008;

    $item2 = new \stdClass;
    $item2->PartNumber = 8888010248;
    $item2->UPC = 8888010248;
    $item2->Quantity = 3;
    $item2->OrderID = null;
    $item2->OrderItemID = null;
    $item2->ShipmentID = null;
    $item2->ExternalLocationID = 440008;

    $request->request->Items = array($item, $item2);

    $ret = $client->fulfillInventory($request);
    print_r($ret);
  }

  function createBin($client, $request) {
    $data = get_csv_data('csv_data/fulfiller_location_bins.csv');

    $request->request = new \stdClass;
		$request->request->FulfillerID = 48590;
    $request->request->ExternalLocationID = 600;
    $request->request->BinID = 'teamross';
    $request->request->BinType = 'teamrossinserted';
    $request->request->BinStatus = 'inactive';
    $request->request->Name = 'teamross';

    print_r($client->createBin($request));
    error_log($client->__getLastResponse());
  }

  function getBins($client, $request) {
    $request->request = new \stdClass;
    $request->request->FulfillerID = 48590;
    $request->request->ExternalLocationID = 600;
    $request->request->SearchTerm = 'teamross';
    $request->request->NumResults = 100;
    $request->request->ResultsStart = 0;

    print_r($client->getBins($request));
  }

  function getBinTypes($client, $request) {
    print_r($client->getBinTypes($request));
  }

  function getBinStatuses($client, $request) {
    $ret = $client->getBinStatuses($request);
    print_r($ret);
  }

  function getInventoryStandard($client, $request) {
    $iq1 = new \stdClass;
    $iq1->PartNumber = 14239745;
    $iq1->UPC = 14239745;
    $iq1->Quantity = 2;

    $loc1 = new \stdClass;
    $loc1->ExternalLocationID = 600;

    $request->request = new \stdClass;
    $request->request->FulfillerID = 48590;
    $request->request->Catalog = new \stdClass;
    $request->request->Catalog->ManufacturerID = 10636;
    $request->request->Catalog->CatalogID = 0;
    $request->request->Quantities = array($iq1);
    $request->request->LocationIDs = $loc1;
    $request->request->Location = null;
    $request->request->Type = "ANY";
    $request->request->Limit = 500;
    $request->request->IgnoreSafetyStock = true;
    $request->request->IncludeNegativeInventory = false;
    $request->request->OrderByLTD = false;

    $ret = $client->getInventory($request);
    print_r($ret);
  }

  function getInventoryNoLocsGiven($client, $request) {
    $iq1 = new \stdClass;
    $iq1->PartNumber = 8888032133;
    $iq1->UPC = 8888032133;
    $iq1->Quantity = 2;
		
    $request->request = new \stdClass;
    $request->request->FulfillerID = 69170;
    $request->request->Catalog = new \stdClass;
    $request->request->Catalog->ManufacturerID = 11416;
    $request->request->Catalog->CatalogID = 0;
    $request->request->Quantities = array($iq1);
    $request->request->LocationIDs = null;
    $request->request->Location = null;
    $request->request->Type = "ANY";
    $request->request->Limit = 500;
    $request->request->IgnoreSafetyStock = true;
    $request->request->IncludeNegativeInventory = false;
    $request->request->OrderByLTD = false;

    $ret = $client->getInventory($request);
    print_r($ret);
  }

  function getInventoryMultiLocsGiven($client, $request) {
    $iq1 = new \stdClass;
    $iq1->PartNumber = 8888032133;
    $iq1->UPC = 8888032133;
    $iq1->Quantity = 1;

    $request->request = new \stdClass;
    $request->request->FulfillerID = 69170;
    $request->request->Catalog = new \stdClass;
    $request->request->Catalog->ManufacturerID = 11416;
    $request->request->Catalog->CatalogID = 0;
    $request->request->Quantities = array($iq1);
    $request->request->LocationIDs = array('ExternalLocationID' => "440006");
    $request->request->Location = null;
    $request->request->Type = "ANY";
    $request->request->Limit = 500;
    $request->request->IgnoreSafetyStock = true;
    $request->request->IncludeNegativeInventory = false;
    $request->request->OrderByLTD = false;

    $ret = $client->getInventory($request);
    print_r($ret);
  }

  function getInventoryPartial($client, $request) {
    $iq1 = new \stdClass;
    $iq1->PartNumber = 8888032133;
    $iq1->UPC = 8888032133;
    $iq1->Quantity = 1;

    $iq2 = new \stdClass;
    $iq2->PartNumber = 8888056664;
    $iq2->UPC = 8888056664;
    $iq2->Quantity = 2;

    $iq3 = new \stdClass;
    $iq3->PartNumber = 14239745;
    $iq3->UPC = 14239745;
    $iq3->Quantity = 1;

    $locs = new \stdClass;
    $locs->ExternalLocationID = 440005;

    $request->request = new \stdClass;
    $request->request->FulfillerID = 69170;
    $request->request->Catalog = new \stdClass;
    $request->request->Catalog->ManufacturerID = 11416;
    $request->request->Catalog->CatalogID = 0;
    $request->request->Quantities = array($iq1, $iq2, $iq3);
    $request->request->LocationIDs = $locs;
    $request->request->Location = null;
    $request->request->Type = "PARTIAL";
    $request->request->Limit = 500;
    $request->request->IgnoreSafetyStock = true;
    $request->request->IncludeNegativeInventory = false;
    $request->request->OrderByLTD = false;

    $ret = $client->getInventory($request);
    print_r($ret);
  }

  function getInventoryAll($client, $request) {
    $iq1 = new \stdClass;
    $iq1->PartNumber = 8888032133;
    $iq1->UPC = 8888032133;
    $iq1->Quantity = 1;

    $iq2 = new \stdClass;
    $iq2->PartNumber = 8888056664;
    $iq2->UPC = 8888056664;
    $iq2->Quantity = 2;

    $locs = new \stdClass;
    $locs->ExternalLocationID = 440005;

    $request->request = new \stdClass;
    $request->request->FulfillerID = 69170;
    $request->request->Catalog = new \stdClass;
    $request->request->Catalog->ManufacturerID = 11416;
    $request->request->Catalog->CatalogID = 0;
    $request->request->Quantities = array($iq1, $iq2, $iq3);
    $request->request->LocationIDs = $locs;
    $request->request->Location = null;
    $request->request->Type = "ALL";
    $request->request->Limit = 500;
    $request->request->IgnoreSafetyStock = true;
    $request->request->IncludeNegativeInventory = false;
    $request->request->OrderByLTD = false;

    $ret = $client->getInventory($request);
    print_r($ret);
  }

  function adjustInventory($client, $request) {
		$Request->ExternalLocationID = 440008;
    $Request->FulfillerID = 69170;
    $item = new \stdClass;
    $item->PartNumber = 8888010248;
    $item->UPC = 8888010248;
    $item->BinID = 'Default';
    $item->Quantity = 99;
    $item->LTD = 20;
    $item->SafetyStock = 10;
    $Request->Items  = array( $item );

    $ret = $client->adjustInventory($Request);
    print_r($ret);
    print "\n";
  }

  function refreshInventory($client, $Request) {
    $Request->ExternalLocationID = 440008;
    $Request->FulfillerID = 69170;
    
    $item = new \stdClass;
    $item->PartNumber = 8888010248;
    $item->UPC = 8888010248;
    $item->BinID = 'Default';
    $item->Quantity = 99;
    $item->LTD = 20;
    $item->SafetyStock = 10;
    
    $Request->Items  = array( $item );

    $ret = $client->refreshInventory($Request);
    print_r($ret);
    print "\n";
  }

  // **********************************************************************
  // Read data from CSV file and return array
  // **********************************************************************
  function get_csv_data($csv_file) {
    $delimiter = ',';
    $data = array();
    $handle = fopen($csv_file, "r");

    if (!$handle) {
      print "Failed to open CSV: " . $csv_file . "\n";
      die();
    }

    $header = fgetcsv($handle);
    while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
      $data[] = array_combine($header, $line_array);
    }

    fclose($handle);
    return $data;
  }
