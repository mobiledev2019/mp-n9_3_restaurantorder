<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
/** Array for JSON response*/
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$user_id = $_POST['user_id'];

	$sqlCart = "SELECT DISTINCT MY_CART.id as cart_id, MY_CART.user_id, MY_CART.price, MY_CART.time,MY_CART.table_number, MY_CART.status as cart_status, 
	MY_TABLE.type AS table_type, MY_TABLE.status AS table_status
	FROM cart MY_CART 
	INNER JOIN cart_item MY_CART_ITEM ON MY_CART.id = MY_CART_ITEM.cart_id
	INNER JOIN r_table MY_TABLE ON MY_CART.table_number = MY_TABLE.number
	WHERE MY_CART.USER_id = '$user_id' AND MY_CART.status <> '2' AND MY_CART.status <> '3'";
	$result = @mysqli_query($con,$sqlCart);
	$output = array();
	if ($result) {
		while($row = mysqli_fetch_array($result)) 
		{
			$cart_id = $row["cart_id"];
			$cart_price = $row["price"];
			$time = $row["time"];

			$number = $row["table_number"];
			$type = $row["table_type"];
			$table_status = $row["table_status"];
			$cart_status = $row["cart_status"];

			$cart_items = array();
			$sqlCartItem = "SELECT MY_CART_ITEM.*, MY_ITEM.id, MY_ITEM.name, MY_ITEM.menu, MY_ITEM.price as item_price,MY_ITEM.description, MY_ITEM.image,MY_ITEM.status 
			FROM cart_item MY_CART_ITEM
			INNER JOIN item MY_ITEM ON MY_CART_ITEM.item_id = MY_ITEM.id WHERE MY_CART_ITEM.cart_id = '$cart_id'";
			$resultCartItem = mysqli_query($con, $sqlCartItem);
			while($r1 = mysqli_fetch_array($resultCartItem)){
				$quantity = $r1["quantity"];
				$cart_item_price = $r1["price"];
				$id = $r1["id"];
				$name = $r1["name"];
				$menu = $r1["menu"];
				$quantity = $r1["quantity"];
				$price = $r1["item_price"];
				$description = $r1["description"];
				$image = $r1["image"];
				$item_status = $r1["status"];
				$item = new Item($id,$name,$menu,$price,$description,$image,$item_status);
				$cart_item = new CartItem($item, $quantity, $cart_item_price);
				array_push($cart_items, $cart_item);
			}		
			$table = new Table($number,$type,$table_status);
			array_push($output, new Cart($cart_id,$table,$cart_price,$time,$cart_status,$cart_items));
		}
		$response["success"] = 1;
		$response["message"] = "SUCCESS";
		$response["output"] = $output;
		
	} 
	/**Return json*/
	else {
		$response["success"] = 0;
		$response["message"] = "FAILED";
	}
	echo json_encode($response);
}
class Cart{
	var $cart_id;
	var $table;
	var $cart_price;
	var $time;
	var $cart_status;
	var $cart_items;
	function Cart($cart_id, $table, $cart_price, $time,$cart_status,$cart_items){
		$this->cart_id = $cart_id;
		$this->table = $table;
		$this->cart_price = $cart_price;
		$this->time = $time;
		$this->cart_status = $cart_status;
		$this->cart_items = $cart_items;
	}
}
class CartItem{
	var $item;
	var $quantity;
	var $cart_item_price;
	function CartItem($item,$quantity,$cart_item_price){
		$this->item = $item;
		$this->quantity = $quantity;
		$this->cart_item_price = $cart_item_price;
	}
}

	// Create object
class Table{
	var $number;
	var $type;
	var $table_status;
	function Table($number,$type,$table_status){
		$this->number = $number;
		$this->type = $type;
		$this->table_status = $table_status;
	}
}

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
