<?php
$user_name = "root";
$user_pass = "";
$db_name = "restaurant";
$host_name = "localhost";

$con = mysqli_connect($host_name,$user_name,$user_pass,$db_name);
mysqli_set_charset($con,'utf8');

define('BASE_URL','http://192.168.0.73:80/restaurant/');
define('FIREBASE_API_KEY','AAAAuA6D6e0:APA91bGLKlYzDdfvcMILidO6smzdaDFWZ_VhE-vMUHMMCI6OaUDGa-ro6PKuR3wl_etQnKw4HIdy34JZzp7LIqj02H5TH49aZU_IXsVhZlG_3khV-lM0dlMizEIKAwyK-1E_F4L5iBwd');
?>
