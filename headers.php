<?  
    $host = "localhost";
    $username = "root";
    $password = "password";
    $con = mysqli_connect(host,username,password,dbname);
    if (mysqli_connect_errno($con))
        {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
              }
?>
