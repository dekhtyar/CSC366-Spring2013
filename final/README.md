This is the folder that contains our final deliverables

The implementation of the api calls is present in api.java
Each api call has a method with the same name that performs the operation and returns the desired result.

The testing aspect of our project is present in Test.java
This program can call api calls directly (without using SOAP and the web server) and has been used to test each api call.
After the program is compiled like this:

javac -cp mysql-connector-java-5.1.25-bin.jar api.java Test.java

Api calls can be performed by providing command line arguments. More information about running these tests is provided in
the file test_cases

Extra credit implemented:

Fulfillment locations (getFulfillmentLocations) can be searched by radius of a given request location

Inventory (getInventory) can be searched by radius of a given request location

There are a few known bugs:

It only happened once (during the demo), but an allocation of something that could not be allocated resulted in an
incosistency between the available number of items and the onHand. However, that error could not be recreated with 
similar parameters and all other tests of allocation (allocateInventory) have been successful.

Adjust inventory is underimplemented
