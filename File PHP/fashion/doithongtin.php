<?php
require "connect.php";
  $iduser = $_POST['iduser'];
  $sdtuser = $_POST['sdtuser'];
  $emailuser = $_POST['emailuser'];
  $diachiuser = $_POST['diachiuser'];



$query = " UPDATE user SET  user_sdt = '$sdtuser' , user_email = '$emailuser', user_address ='$diachiuser' WHERE user_id = '$iduser' ";

if(mysqli_query($conn, $query)){
    $queryget = "SELECT * FROM user WHERE user_id = $iduser ";

$user = array();
if(mysqli_num_rows(mysqli_query($conn,$queryget)) > 0){
    $data = mysqli_query($conn, $queryget);
    $row = mysqli_fetch_array($data);
     $user["ten"] =  $row['user_name'];
     $user["sdt"] =   $row['user_sdt'];
     $user["email"] =  $row['user_email'];
     $user["diachi"] =  $row['user_address'];
    
    
}
echo json_encode($user);

}else{
    echo "loi";
}


    
    




?>