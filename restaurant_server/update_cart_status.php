<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST') {
	$cart_id = $_POST['cart_id'];
	$status = $_POST['cart_status'];
	$query = "UPDATE cart SET status = '$status' WHERE id = '$cart_id'";	
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