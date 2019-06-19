<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
/** Array for JSON response*/
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$number = $_POST['number'];
	$type = $_POST['type'];

	$sqlCheck = "SELECT number FROM r_table WHERE number = '$number'";
	$result = @mysqli_query($con,$sqlCheck);
	if (mysqli_num_rows($result) != 0) {	
		$response["success"] = 0;
		$response["message"] = "EXISTED";
	} else {	
		$sqlInsert = "INSERT INTO r_table(number,type) VALUES('$number','$type')";
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
