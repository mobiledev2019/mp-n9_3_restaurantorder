<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
/** Array for JSON response*/
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$id = $_POST['cart_id'];
	$user_id = $_POST['user_id'];
	$table_number = $_POST['table_number'];
	$price = $_POST['cart_price'];
	$sqlInsert = "INSERT INTO cart(id,user_id,table_number,price) VALUES('$id','$user_id','$table_number','$price')";
	$sqlUpdateTable = "UPDATE r_table SET current_user_id = '$user_id', status = '0' WHERE number = $table_number";
	$resultInsert = @mysqli_query($con,$sqlInsert);
	$resultUpdateTable = @mysqli_query($con,$sqlUpdateTable);
	if ($resultInsert AND $resultUpdateTable) {
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
