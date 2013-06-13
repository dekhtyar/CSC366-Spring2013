This is the folder that contains our final deliverables

The implementation of the api calls is present in api.java
Each api call has a method with the same name that performs the operation and returns the desired result.

The testing aspect of our project is present in Test.java
This program can call api calls directly (without using SOAP and the web server) and has been used to test each api call.
After the program is compiled like this:

javac -cp mysql-connector-java-5.1.25-bin.jar api.java Test.java

Api calls can be performed by providing command line arguments. More information about running these tests is provided in
the file test_cases.txt

Extra credit implemented:

Fulfillment locations (getFulfillmentLocations) can be searched by radius of a given request location

Inventory (getInventory) can be searched by radius of a given request location

There are a couple of known bugs:

It only happened once (during the demo), but an allocation of something that could not be allocated resulted in an
inconsistency between the available number of items and the onHand. However, that error could not be recreated with 
similar parameters and all other tests of allocation (allocateInventory) have been successful.

Adjust inventory is underimplemented

------ Deployment Instructions ----

This project was made to connect to the webUI using tomcat 7.0 and Axis2. These instructions assumes that the user knows to install a tomcat server on their machine. 

Please download the cpe366Test.war file, and click and drag that into your /usr/share/apache-tomcat-7.0.40/webapps/ folder. Once it is inside run the command "service tomcat start". This should automatically deploy the files onto the server. The endpoint to connect to the files on our vm was localhost:8080 although this can vary from system to system. The files that contain the files are located at /cpe366Test/services/CoreServiceService. Thus the complete endpoint used for the demo is http://localhost:8080/cpe366Test/services/CoreServiceService. In the web connection folder you can find the different files that were used in the implementation of the SOAP and XML parser. 

The file that called our api.java is located on the top level and is CoreServiceServiceSkeleton.java . This files the requests structure and the calling of the api.java methods to fill the responses to the SOAP parser.

