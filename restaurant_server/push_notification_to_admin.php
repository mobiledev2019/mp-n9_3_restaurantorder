<?php 
//importing required files 
require_once ('connection.php');
require_once ('firebase.php');
require_once ('push.php'); 
mysqli_set_charset($con,'utf8');

$response = array(); 
$devicetokens = array();


$sqlGetAdmin = "SELECT token FROM device as A, user as B WHERE A.user_id = B.id AND B.is_admin = 1";
	$result = mysqli_query($con,$sqlGetAdmin);
   	while ($row = mysqli_fetch_array($result)) {
   			$token = $row["token"];
   			array_push($devicetokens, $token);
   		}
if($_SERVER['REQUEST_METHOD']=='POST'){	
	

	//hecking the required params 
	if(isset($_POST['title']) and isset($_POST['message'])){

		//creating a new push
		$push = null; 
		//first check if the push has an image with it
		if(isset($_POST['image'])){
			$push = new Push(
					$_POST['title'],
					$_POST['message'],
					$_POST['image']
				);
		}else{
			//if the push don't have an image give null in place of image
			$push = new Push(
					$_POST['title'],
					$_POST['message'],
					null
				);
		}

		//getting the push from push object
		$mPushNotification = $push->getPush(); 

		//creating firebase class object 
		$firebase = new Firebase(); 

		//sending push notification and displaying result 
		echo $firebase->send($devicetokens, $mPushNotification);
	}else{
		$response['success']=0;
		$response['message']='PARAMETERS MISSING';
	}
}else{
	$response['success']=0;
	$response['message']='INVALID REQUEST';
}

echo json_encode($response);
// echo json_encode($devicetokens);

?>