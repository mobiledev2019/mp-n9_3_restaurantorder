<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST') {
	$name = $_POST['name'];	
	$menu = $_POST['menu'];
	$price = $_POST['price'];	
	$description = $_POST['description'];
	$image_name = $_POST['image_name'];
	$image_code = $_POST['image_code'];
	$path = "images/item/$image_name";
		// Đường dẫn
	$actual_path = BASE_URL."$path";

	$query = "INSERT INTO item (name,menu,price,description, image) VALUES ('$name','$menu','$price','$description','$actual_path')";	
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