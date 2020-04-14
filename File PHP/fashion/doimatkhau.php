<?php
require "connect.php";

$iduser = $_POST['iduser'];
$mkcu = $_POST['mkcu'];
$mkmoi = $_POST['mkmoi'];


$query = " UPDATE user SET user_password = '$mkmoi'  WHERE user_id = '$iduser' AND user_password = '$mkcu' ";
if(mysqli_query($conn, $query)){
    $queryget = "SELECT * FROM user WHERE user_id = $iduser ";
    if(mysqli_num_rows(mysqli_query($conn,$queryget)) > 0){
        $data = mysqli_query($conn, $queryget);
        $row = mysqli_fetch_array($data);
         $pass =  $row['user_password'];
         if($pass == $mkmoi){
             echo "thanhcong";
         }else{
             echo "mkcukhongchinhxac";
         }
        
        
        
    }

}else
echo "loi";

?>