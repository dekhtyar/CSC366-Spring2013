<?php
  include_once('soap-client.php');

  $argv[1]($client, new \stdClass);

  function createFulfiller($client, $request) {

  }

  function getFulfillerStatus($client, $request) {
    $request->fulfillerID = 91710;
    $ret = $client->getFulfillerStatus($request);
    print_r($ret);
  }

  function createFulfillmentLocation($client, $request) {

  }

  function getFulfillmentLocations($client, $request) {

  }

  function getFulfillmentLocationTypes($client, $request) {

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
