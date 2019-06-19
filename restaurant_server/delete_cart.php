<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST') {
	$cart_id = $_POST['cart_id'];
	$queryCart = "DELETE  FROM cart WHERE id = '$cart_id'";	
	$queryCartItem = "DELETE  FROM cart_item WHERE cart_id = '$cart_id'";	
	if (mysqli_query($con,$queryCart)AND mysqli_query($con,$queryCartItem)) {
		$response["success"] = 1;
		$response["message"] = "DELETE_SUCCESS";

	}
} else {
	$response["success"] = 0;
	$response["message"] = "DELETE_FAILED";
}
		// echoing JSON response
echo json_encode($response);
?>