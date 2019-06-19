<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
/** Array for JSON response*/
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$item_id = $_POST['item_id'];
	$quantity = $_POST['quantity'];
	$price = $_POST['cart_item_price'];
	$cart_id = $_POST['cart_id'];
	$sqlInsert = "INSERT INTO cart_item(item_id,quantity,price,cart_id) VALUES('$item_id','$quantity','$price','$cart_id')";
	$resultInsert = @mysqli_query($con,$sqlInsert);
	if ($resultInsert) {
		$response["success"] = 1;
		$response["message"] = "SUCCESS";
	} else {
		$response["success"] = 0;
		$response["message"] = "FAILED";
	}
	
	/**Return json*/
	echo json_encode($response);
}
?>
