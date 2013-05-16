<?php
include_once("team-ross-soap.php");

$server = new SoapServer("core.wsdl");
$server->setClass('TeamRossSOAP');
$server->handle();
