<?php
	require_once('connect.php');
	
	/** Array for JSON response*/
	$response = array();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$username = $_POST['username'];
        $password = $_POST['password'];
        // $username = "duc123";
		// $password = "123";
		$sql = "SELECT  user_id FROM user WHERE user_name = '$username' AND user_password='$password'";
		if(mysqli_num_rows(mysqli_query($conn,$sql)) > 0){
			    $result= mysqli_query($conn,$sql);
                $row = mysqli_fetch_array($result);
				
				$user_id = $row["user_id"];
				
				$response["success"] = 1;
				$response["message"] = "Đăng nhập thành công";
				$response["user_id"] = $user_id;
		}else{
			$response["success"] = 0;
			$response["message"] = "Đăng nhập thất bại.";
		}
		/**Return json*/
		echo json_encode($response);
	} 
?>