<?php
  include_once('db.php');
  include_once('db_functions.php');

  // **********************************************************************
  // Route test harness
  // **********************************************************************
  switch ($argv[1]):
    case 'seed':
      db_seed();
      break;

    default:
      print "Usage: php test-harness.php create|seed|empty|destroy\n";
      break;

  endswitch;

  function db_seed() {
    $csv = array();
    $csv['locations']           = 'data-csv-rfc/fulfiller_locations.csv';
    $csv['location_bins']       = 'data-csv-rfc/fulfiller_location_bins.csv';
    $csv['inv_available_bins']  = 'data-csv-rfc/fulfiller_inventory_available_bins.csv';
    $csv['inv_available']       = 'data-csv-rfc/fulfiller_inventory_available.csv';
    $csv['inv_not_available']   = 'data-csv-rfc/fulfiller_inventory_not_available.csv';

    // Comma separated values
    $delimiter = ',';

    // **********************************************************************
    // Locations
    // **********************************************************************
    $handle = fopen($csv['locations'], "r");
    if (!$handle) die();

    $header = fgetcsv($handle);
    while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
      print_r(array_combine($header, $line_array));
    }
    fclose($handle);

    // **********************************************************************
    // Bins
    // **********************************************************************
    $handle = fopen($csv['location_bins'], "r");
    if (!$handle) die();

    $header = fgetcsv($handle);
    while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
      print_r(array_combine($header, $line_array));
    }
    fclose($handle);

    // **********************************************************************
    // Inventory Available Bins
    // **********************************************************************
    $handle = fopen($csv['inv_available_bins'], "r");
    if (!$handle) die();

    $header = fgetcsv($handle);
    while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
      print_r(array_combine($header, $line_array));
    }
    fclose($handle);

    // **********************************************************************
    // Inventory Available
    // **********************************************************************
    $handle = fopen($csv['inv_available'], "r");
    if (!$handle) die();

    $header = fgetcsv($handle);
    while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
      print_r(array_combine($header, $line_array));
    }
    fclose($handle);

    // **********************************************************************
    // Inventory Not Available
    // **********************************************************************
    $handle = fopen($csv['inv_not_available'], "r");
    if (!$handle) die();

    $header = fgetcsv($handle);
    while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
      print_r(array_combine($header, $line_array));
    }
    fclose($handle);
  }

?>
