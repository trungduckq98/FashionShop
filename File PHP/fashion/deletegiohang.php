<?php
require "connect.php";

$id = $_POST['idgiohang'];

$query = "DELETE FROM giohang WHERE id_giohang = $id";
if(mysqli_query($conn, $query)){
    echo "thanhcong";
}else{
    echo "loi";
}
?>