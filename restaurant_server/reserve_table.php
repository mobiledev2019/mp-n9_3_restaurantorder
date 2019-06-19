<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
/** Array for JSON response*/
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$table_number = $_POST['table_number'];
	$time_booking = $_POST['time_booking'];
	$user_id = $_POST['user_id'];
	$phone_booking = $_POST['phone_booking'];
	$sqlCheck = "SELECT time_booking FROM table_booking WHERE time_booking = '$time_booking'";
	$result = @mysqli_query($con,$sqlCheck);
	if (mysqli_num_rows($result) != 0) {	
		$response["success"] = 0;
		$response["message"] = "EXISTED";
	} else {	
		$sqlInsert = "INSERT INTO table_booking(table_number,time_booking,user_id,phone_booking) VALUES('$table_number','$time_booking','$user_id','$phone_booking')";
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
