<?php
require "connect.php";
  $iduser = $_POST['iduser'];






$query = "SELECT * FROM user WHERE user_id = $iduser ";

$user = array();
if(mysqli_num_rows(mysqli_query($conn,$query)) > 0){
    $data = mysqli_query($conn, $query);
    $row = mysqli_fetch_array($data);
     $user["ten"] =  $row['user_name'];
     $user["sdt"] =   $row['user_sdt'];
     $user["email"] =  $row['user_email'];
     $user["diachi"] =  $row['user_address'];
    
    
}
echo json_encode($user);



?>