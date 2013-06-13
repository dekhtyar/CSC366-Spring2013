Running Tests
-------------

## Reset database
###``php bulk-ops.php destroy && php bulk-ops.php create``

## Test inserts via SOAP
###``php soap-harness.php create``

## Seed all data (lab05)
###``php bulk-ops.php reset`` OR ``php bulk-ops.php seed``
**Note: ``reset`` will destory and create tables prior to seeding**

## Test an individual function
###``php soap-harness.php <functionName>``
**Note: do not include parens or params!**
