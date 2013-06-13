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
    //$data = get_csv_data('csv_data/fulfiller_locations.csv');
    $request->request = new \stdClass;

    //foreach ($data as $location) {

    $request->request->FulfillerID = 91710;
    $request->request->Catalog = new \stdClass;
    $request->request->Catalog->ManufacturerID = 1392;
    $request->request->Catalog->CatalogID = 1;
    $request->request->Location = new \stdClass;
    $request->request->Location->Unit = 'MILES';
    $request->request->Location->Radius = 30;
    $request->request->Location->PostalCode = 93450;
    $request->request->Location->Latitude = 45.000100;
    $request->request->Location->Longitude = -93.083100;
    $request->request->Location->CountryCode = 5;
    $request->request->MaxLocations = 5;

     // $request->request->FulfillerID = $location['fulfiller_id'];
     // $request->request->Catalog = $location['catalog_id'];
    $ret = $client->getFulfillmentLocations($request);
    //  $ret = array('GetFulfillerId' => 1, 2);
    print_r($ret);
    //}
  }

  function getFulfillmentLocationTypes($client, $request) {
    $ret = $client->getFulfillmentLocationTypes($request);
    print_r($ret);
  }

  function allocateInventory($client, $request) {

  }

  function deallocateInventory($client, $request) {

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

    foreach ($data as $bin) {
			$request->request->FulfillerID = 48590;
      $request->request->ExternalLocationID = $bin['external_fulfiller_location_id'];
      $request->request->BinID = $bin['bin_name'];
      $request->request->BinType = $bin['bin_type'];
      $request->request->BinStatus = $bin['bin_status'];
      $request->request->Name = $bin['bin_name'];

      print_r($client->createBin($request));
    }
  }

  function getBins($client, $request) {
    $request->request = new \stdClass;
    $request->request->FulfillerID = 48590;
    $request->request->ExternalLocationID = 600;
    $request->request->SearchTerm = 'Default';
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

  function getInventory($client, $request) {

  }

  function adjustInventory($client, $request) {

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
