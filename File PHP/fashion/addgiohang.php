<?php
require "connect.php";

$iduser = $_POST['iduser'];
$idsp = $_POST['idsp'];
$tensp = $_POST['tensp'];
$hinhsp = $_POST['hinhsp'];
$giasp = $_POST['giasp'];
$soluongsp = $_POST['soluongsp'];




$query = "INSERT INTO giohang VALUES(null, '$iduser', '$idsp', '$tensp', '$hinhsp' , '$giasp', '$soluongsp')";
if(mysqli_query($conn, $query)){
    echo "thanhcong";
}else{
    echo "loi";
}
?>