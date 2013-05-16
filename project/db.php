<?php
   $hostname = 'localhost';
   $username = 'root';
   $password = 'password';
   $database = 'team_ross';
   
   try {
      $db = new PDO("mysql:host=$hostname;dbname=$database", $username, $password);
   }
   catch (PDOException $e) {
      echo $e->getMessage();
   }
?>
