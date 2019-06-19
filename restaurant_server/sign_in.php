<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
/** Array for JSON response*/
$response = array();
if($_SERVER['REQUEST_METHOD']=='POST'){
	$email = $_POST['email'];
	$password = $_POST['password'];
	$token = $_POST['token'];
	
	$sql = "SELECT *  FROM user WHERE email = '$email' AND password='$password'";
	if(mysqli_num_rows(@mysqli_query($con,$sql)) > 0){
		$result= mysqli_query($con,$sql);
		$row = mysqli_fetch_array($result);
		$user_id = $row["id"];
		$user_name = $row["name"];
		$dob = $row["dob"];
		$email = $row["email"];
		$phone = $row["phone"];
		$password = $row["password"];
		$image = $row["image"];
		$is_admin = $row["is_admin"];
		
		$sqlInsertToken = "INSERT INTO device(token,user_id) VALUES ('$token','$user_id')";
		$resultInsertToken = mysqli_query($con,$sqlInsertToken);
		if ($resultInsertToken) {	

			$response["success"] = 1;
			$response["message"] = "SUCCESS";

			$response["user_id"] = $user_id;
			$response["user_name"] = $user_name;
			$response["dob"] = $dob;
			$response["email"] = $email;
			$response["phone"] = $phone;
			$response["password"] = $password;
			$response["image"] = $image;
			$response["is_admin"] = $is_admin;

		}else{
			$response["success"] = 0;
			$response["message"] = "FAILED";
		}
	}
	/**Return json*/
	echo json_encode($response);
} 

?>