<?php
require "connect.php";

$id = $_POST['idgiohang'];
$soluong = $_POST['soluong'];
$gia = $_POST['giathaydoi'];




$query = " UPDATE giohang SET soluong_sp = '$soluong' , gia_sp = '$gia' WHERE id_giohang = '$id' ";
if(mysqli_query($conn, $query)){
    echo "thanhcong";

}else
echo "loi";

?>