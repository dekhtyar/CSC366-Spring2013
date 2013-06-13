Running Tests
-------------

### Reset database
``php bulk-ops.php destroy && php bulk-ops.php create``

### Seed all data (lab05)
``php bulk-ops.php reset`` OR ``php bulk-ops.php seed``  
**Note: ``reset`` will destory and create tables prior to seeding**

### Test inserts via SOAP
``php soap-harness.php create``

### Test an individual SOAP function
``php soap-harness.php <functionName>``  
**Note: do not include parens or params!**

Other Deliverables
------------------
Visit [our final project wiki page](https://github.com/dekhtyar/CSC366-Spring2013/wiki/Final-Project-Team-Ross)
