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
