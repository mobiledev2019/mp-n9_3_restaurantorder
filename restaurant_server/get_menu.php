<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$query = "SELECT * FROM menu";
$result = mysqli_query($con,$query);
$output = array();

while($row = mysqli_fetch_array($result)) {		
	$id = $row["id"];
	$name = $row["name"];
	$image 	= $row["image"];

	array_push($output, new Menu($id,$name,$image));
}

	echo json_encode($output); //return json array
	
	// Create object
	class Menu{
		var $id;
		var $name;
		var $image;
		function Menu($id,$name,$image){
			$this->id = $id;
			$this->name = $name;
			$this->image = $image;

		}
	}
	?>