<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST') {
	$number = $_POST['number'];
	$status = $_POST['table_status'];
	$query = "UPDATE r_table SET status = '$status' WHERE number = '$number'";	
	if (mysqli_query($con,$query)) {
		$response["success"] = 1;
		$response["message"] = "SUCCESS";
	}
} else {
	$response["success"] = 0;
	$response["message"] = "FAILED";
}
		// echoing JSON response
echo json_encode($response);
?>