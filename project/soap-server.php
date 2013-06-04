<?php
include_once("team-ross-soap.php");

$data = file_get_contents('php://input');

$server = new SoapServer("core.wsdl");
$server->setClass('TeamRossSOAP');
$server->handle($data);
