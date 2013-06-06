<?php

$client = new SoapClient(
    "core.wsdl",
    array(
      // Stuff for development.
      'trace' => 1,
      'exceptions' => true,
      'cache_wsdl' => WSDL_CACHE_NONE,
      'features' => SOAP_SINGLE_ELEMENT_ARRAYS,

      // Auth credentials for the SOAP request.
      'login' => 'username',
      'password' => 'password',

      // Proxy url.
      'proxy_host' => 'localhost', // Do not add the schema here (http or https). It won't work.
      'proxy_port' => 80,

      // Auth credentials for the proxy.
      'proxy_login' => NULL,
      'proxy_password' => NULL
    )
  );
