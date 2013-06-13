#!/bin/bash

DERBY=`find . -name 'derby.jar'`
DERBY_CLIENT=`find . -name 'derbyclient.jar'`
DERBY_TOOLS=`find . -name 'derbytools.jar'`
DB_LOCATION="jdbc:derby:server/db/;create=true"

echo "$DERBY"
echo "$DERBY_CLIENT"
echo "connect '$DB_LOCATION';"
if [ "$DERBY_CLIENT" ]; then
    java -cp "$DERBY:$DERBY_CLIENT:$DERBY_TOOLS" org.apache.derby.tools.ij
fi
