<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST') {
	$name = $_POST['name'];	
	$image_name = $_POST['image_name'];
	$image_code = $_POST['image_code'];
	$path = "images/menu/$image_name";
		// Đường dẫn
	$actual_path = BASE_URL."$path";

	$query = "INSERT INTO menu (name,image) VALUES ('$name','$actual_path')";	
	if (mysqli_query($con,$query)) {
		file_put_contents($path,base64_decode($image_code));
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