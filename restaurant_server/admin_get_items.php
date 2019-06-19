<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$query = "SELECT * FROM item";
$result = mysqli_query($con,$query);
$output = array();

while($row = mysqli_fetch_array($result)) 
{		
	$id = $row["id"];
	$name = $row["name"];
	$menu = $row["menu"];
	$price = $row["price"];
	$description = $row["description"];
	$image = $row["image"];
	$item_status = $row["status"];


	array_push($output, new Item($id,$name,$menu,$price,$description,$image,$item_status));
}

	echo json_encode($output); //return json array
	
	// Create object
	class Item{
		var $id;
		var $name;
		var $menu;
		var $price;
		var $description;
		var $image;
		var $item_status;
		function Item($id,$name,$menu,$price,$description,$image,$item_status){
			$this->id = $id;
			$this->name = $name;
			$this->menu = $menu;
			$this->price = $price;
			$this->description = $description;
			$this->image = $image;
			$this->item_status = $item_status;
		}
	}
	?>