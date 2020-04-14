<?php
	require_once('connect.php');
	
	/** Array for JSON response*/
	$response = array();
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		$username = $_POST['username'];
		$password = $_POST['password'];
        $sdt = $_POST['sdt'];
        $email = $_POST['email'];
        $address = $_POST['address'];

        
        
        
		$sqlCheck = "SELECT user_name FROM user WHERE user_name = '$username'";
		$result = @mysqli_query($conn,$sqlCheck);
		if (mysqli_num_rows($result) != 0) {	
				$response["success"] = 0;
				$response["message"] = "Tên đăng nhập đã có người sử dụng!";
			} else {	
				$sqlInsert = "INSERT INTO user (user_name,user_password,user_sdt, user_email, user_address) VALUES ('$username','$password','$sdt', '$email', '$address')";
				$resultInsert = @mysqli_query($conn,$sqlInsert);
				if ($resultInsert) {
						// $sqlGetInfo = "SELECT user_id FROM user WHERE user_name = '$username' AND password = '$password'";
						// $result = mysqli_query($conn,$sqlGetInfo);
						// $row = mysqli_fetch_array($result);
						// $user_id = $row["user_id"];
						$response["success"] = 1;
						$response["message"] = "Đăng ký thành công!";
						//$response["user_id"] = $user_id;
						
				} else {
					$response["success"] = 0;
					$response["message"] = "Đăng ký thất bại!";
				}
			}	
			/**Return json*/
		echo json_encode($response);
	}
?>
 
