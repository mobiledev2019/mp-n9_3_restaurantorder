<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
/** Array for JSON response*/
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$user_id = $_POST['id'];
	$user_name = $_POST['name'];
	$dob = $_POST['dob'];
	$email = $_POST['email'];
	$phone = $_POST['phone'];
	$password = $_POST['password'];

	$sqlCheck = "SELECT email FROM user WHERE email = '$email'";
	$result = @mysqli_query($con,$sqlCheck);
	if (mysqli_num_rows($result) != 0) {	
		$response["success"] = 0;
		$response["message"] = "EXISTED";
	} else {	
		$sqlInsert = "INSERT INTO user(id,name,dob,email,phone,password) VALUES('$user_id','$user_name','$dob','$email','$phone','$password')";
		$resultInsert = @mysqli_query($con,$sqlInsert);
		if ($resultInsert) {
			$response["success"] = 1;
			$response["message"] = "SUCCESS";
		} else {
			$response["success"] = 0;
			$response["message"] = "FAILED";
		}
	}	
	/**Return json*/
	echo json_encode($response);
	}
?>
