<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$response = array();
if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$statistics_year = $_POST['statistics_year'];

$sqlCart = "SELECT DISTINCT MY_CART.id as cart_id, MY_CART.user_id, MY_CART.price, MY_CART.time,MY_CART.table_number, MY_CART.status as cart_status, 
MY_USER.name , MY_USER.dob, MY_USER.email, MY_USER.phone, MY_USER.password, MY_USER.image, MY_USER.is_admin, MY_TABLE.type AS table_type, MY_TABLE.status AS table_status
FROM cart MY_CART 
INNER JOIN cart_item MY_CART_ITEM ON MY_CART.id = MY_CART_ITEM.cart_id
INNER JOIN user MY_USER ON MY_CART.user_id = MY_USER.id
INNER JOIN r_table MY_TABLE ON MY_CART.table_number = MY_TABLE.number
WHERE YEAR(MY_CART.time) = '$statistics_year' AND MY_CART.status <> '0'AND MY_CART.status <> '1'AND MY_CART.status <> '3'";
$result = mysqli_query($con,$sqlCart);
$output = array();
if ($result) {
	while($row = mysqli_fetch_array($result)) 
	{
		$cart_id = $row["cart_id"];
		$cart_price = $row["price"];
		$time = $row["time"];
		$user_id = $row["user_id"];
		$user_name = $row["name"];
		$dob = $row["dob"];
		$email = $row["email"];
		$phone = $row["phone"];
		$password = $row["password"];
		$image = $row["image"];
		$is_admin = $row["is_admin"];
		$number = $row["table_number"];
		$type = $row["table_type"];
		$table_status = $row["table_status"];
		$cart_status = $row["cart_status"];
		$user = new User($user_id,$user_name,$dob,$email,$phone,$password,$image,$is_admin);
		$table_bookings = array();
		$cart_items = array();
		$sqlTableBooking = "SELECT TABLE_BOOKING.*, USER.name, USER.email FROM table_booking TABLE_BOOKING INNER JOIN user USER ON TABLE_BOOKING.user_id = USER.id WHERE table_number = $number";
		$resultTableBooking = mysqli_query($con, $sqlTableBooking);
		while($r = mysqli_fetch_array($resultTableBooking)){
			$booking_id = $r["id"];
			$table_number = $r["table_number"];
			$time_booking = $r["time_booking"];
			$user_name = $r["name"];
			$user_email = $r["email"];
			$phone_booking = $r["phone_booking"];
			$table_booking = new TableBooking($booking_id, $table_number, $time_booking,$user_name,$user_email,$phone_booking);
			array_push($table_bookings, $table_booking);
		}
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
		$table = new Table($number,$type,$table_status,$table_bookings);
		array_push($output, new Cart($cart_id,$user,$table,$cart_price,$time,$cart_status,$cart_items));
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
		echo json_encode($response); //return json array
}
	
	class Cart{
		var $cart_id;
		var $user;
		var $table;
		var $cart_price;
		var $time;
		var $cart_status;
		var $cart_items;
		function Cart($cart_id,$user, $table, $cart_price, $time,$cart_status,$cart_items){
			$this->cart_id = $cart_id;
			$this->user = $user;
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
	class TableBooking{
		var $id;
		var $table_number;
		var $time_booking;
		var $user_name;
		var $user_email;
		var $phone_booking;
		function TableBooking($id,$table_number,$time_booking,  $user_name, $user_email, $phone_booking){
			$this->id = $id;
			$this->table_number = $table_number;
			$this->time_booking = $time_booking;
			$this->user_name = $user_name;
			$this->user_email = $user_email;
			$this->phone_booking = $phone_booking;
		}
	}
	
	// Create object
	class Table{
		var $number;
		var $type;
		var $table_status;
		var $table_bookings;
		function Table($number,$type,$table_status, $table_bookings){
			$this->number = $number;
			$this->type = $type;
			$this->table_status = $table_status;
			$this->table_bookings = $table_bookings;
		}
	}
	class User{
		var $user_id;
		var $user_name;
		var $dob;
		var $email;
		var $phone;
		var $password;
		var $image;
		var $is_admin;
		function User($user_id,$user_name,$dob,$email,$phone,$password,$image,$is_admin){
			$this->user_id = $user_id;
			$this->user_name = $user_name;
			$this->dob=$dob;
			$this->email = $email;
			$this->phone = $phone;
			$this->password = $password;
			$this->image = $image;
			$this->is_admin = $is_admin;
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
