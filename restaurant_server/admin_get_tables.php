<?php
require_once('connection.php');
mysqli_set_charset($con,'utf8');
$query = "SELECT * FROM r_table ";
$result = mysqli_query($con,$query);
$output = array();
if ($result) {
	while($row = mysqli_fetch_array($result)) 
	{
		$number = $row["number"];
		$type = $row["type"];
		$table_status = $row["status"];
		$current_user_id = $row["current_user_id"];
		$table_bookings = array();
		$sql = "SELECT A.*, B.name, B.email FROM table_booking A INNER JOIN user B ON A.user_id = B.id WHERE table_number = $number";
		$q = mysqli_query($con, $sql);
		while($r = mysqli_fetch_array($q)){
			$id = $r["id"];
			$table_number = $r["table_number"];
			$time_booking = $r["time_booking"];
			$user_name = $r["name"];
			$user_email = $r["email"];
			$phone_booking = $r["phone_booking"];
			$table_booking = new TableBooking($id, $table_number, $time_booking,$user_name,$user_email,$phone_booking);
			array_push($table_bookings, $table_booking);
		}		
		array_push($output, new Table($number,$type,$table_status,$current_user_id,$table_bookings));
	}
}

	echo json_encode($output); //return json array
	
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
		var $current_user_id;
		function Table($number,$type,$table_status, $current_user_id, $table_bookings){
			$this->number = $number;
			$this->type = $type;
			$this->table_status = $table_status;
			$this->current_user_id = $current_user_id;
			$this->table_bookings = $table_bookings;
		}
	}
	?>