<?php
  $csv = array();
  $csv['locations']           = 'data-csv-rfc/fulfiller_locations.csv';
  $csv['location_bins']       = 'data-csv-rfc/fulfiller_location_bins.csv';
  $csv['inv_available_bins']  = 'data-csv-rfc/fulfiller_inventory available bins.csv';
  $csv['inv_available']       = 'data-csv-rfc/fulfiller_inventory_available.csv';
  $csv['inv_not_available']   = 'data-csv-rfc/fulfiller_inventory_not_available.csv';

  // Comma separated values
  $delimiter = ',';

  // Start with locations
  $handle = fopen($csv['locations'], "r");
  if (!$handle) die();

  while (($line_array = fgetcsv($handle, 4000, $delimiter)) !== false) {
    print_r($line_array);
  }
  fclose($handle);
?>
