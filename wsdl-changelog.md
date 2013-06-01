1 June 2013
-----------

* Removed the getItemLocationsByFulfiller operation.

* All operations now use the ExternalLocationID instead of the
  FulfillerLocationID (internal) or LocationName. This way, locations are
  consistently identified throughout the WSDL.

7 May 2013
----------

Major operation changes:

* **The createManufacturerCatalog operation was completely removed.
  Manufacterer Catalog pairs should be added to Locations if/when they appear
  in updates.**
* getFulfillmentLocations simplified per Dave's instructions by
  removal of multiple fields.

Type changes:

* Bin: Type and Status to string. How the Type and Status is stored is up to
  each group, it will always be passed as a string.
* Location: Type changed to string. Same as BinType.

Deletions:

* LocationUPC, was synonymous with PartNumber (SKU)
* Floor, STHEnabled, RestockEnabled, PickupEnabled all unused
* all elements and types not referenced
* arrays of elements not used as arrays
* nillable on all PartNumber and UPC
* nillable on some/all LocationIDs
* descriptions of Bin types and statuses
* RetailerID, was synonymous with FulfillerID
* RetailerLocationID where redundant with FulfillerLocationID
* ManufacturerID/ManufacturerLocationID (unneeded FKs)

3 May 2013
----------

* Removed the AuthenticationHeader
* Added FulfillerID to all request complexTypes
* Modified SOAP definitons to exclude AuthenticationHeader
