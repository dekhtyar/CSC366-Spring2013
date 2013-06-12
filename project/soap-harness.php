<?php
  include_once('soap-client.php');

  switch ($argv[1]):

    case 'create':
      print "Running createFulfiller, createFulfillmentLocation, and createBin\n\n";
      createFulfiller($client, new \stdClass);
      createFulfillmentLocation($client, new \stdClass);
      // createBin($client, new \stdClass);
      break;

    default:
      $argv[1]($client, new \stdClass);
      break;

  endswitch;

  function createFulfiller($client, $request) {
    $data = get_csv_data('csv_data/fulfiller_locations.csv');
    $request->request = new \stdClass;

    foreach ($data as $location) {
      $request->request->FulfillerID = $location['fulfiller_id'];
      $request->request->Name = $location['name'];

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
    $data = get_csv_data('csv_data/fulfiller_locations.csv');
    $request->request = new \stdClass;

    foreach ($data as $location) {
      $request->request->FulfillerID = $location['fulfiller_id'];
      $request->request->Catalog = $location['catalog_id'];

      $ret = $client->getFulfillmentLocations($request);
      print_r($ret);
    }
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

  }

  function createBin($client, $request) {
    $data = get_csv_data('csv_data/fulfiller_location_bins.csv');

    $request->request = new \stdClass;

    foreach ($data as $bin) {
      $request->request->FulfillerID = $bin['internal_fulfiller_location_id'];
      $request->request->BinID = $bin['bin_name'];
      $request->request->ExternalLocationID = null;
      $request->request->BinType = $bin['bin_type'];
      $request->request->BinStatus = $bin['bin_status'];
      $request->request->Name = $bin['bin_name'];

      print_r($client->createBin($request));
    }
  }

  function getBins($client, $request) {
    $request->request = new \stdClass;
    $request->request->FulfillerID = 91710;
    $request->request->ExternalLocationID = null;
    $request->request->SearchTerm = null;
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

  function refreshInventory($client, $request) {

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