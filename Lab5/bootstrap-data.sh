#!/bin/bash
echo "Destroying database..."
python2 destroyDatabase.py

echo "Creating database..."
python2 createDatabase.py

echo "Creating fulfillment locations..."
python2 createFulfillmentLocation.py ../data/fulfiller\ locations.csv 2>/dev/null

echo "Creating bins..."
python2 createBin.py ../data/fulfiller\ location_bins.csv

echo "Refreshing inventory (1/3)..."
python2 refreshInventory.py ../data/fulfiller\ inventory\ available.csv

echo "Refreshing inventory (2/3)..."
python2 refreshInventory.py ../data/fulfiller\ inventory\ available\ bins.csv 1>&2 2>/dev/null

echo "Refreshing inventory (3/3)..."
python2 refreshInventory.py ../data/fulfiller\ inventory\ not\ available.csv 1>&2 2>/dev/null
