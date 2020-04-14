<?php
require "connect.php";

$id = $_POST['idgiohang'];
$iduserget = $_POST['iduser'];
$soluong = $_POST['soluong'];
$gia = $_POST['giathaydoi'];

$query = " UPDATE giohang SET soluong_sp = '$soluong' , gia_sp = '$gia' WHERE id_giohang = '$id' ";
if(mysqli_query($conn, $query)){
    $manggiohang = array();
$queryget = "SELECT * FROM giohang WHERE id_user = $iduserget ";
$data = mysqli_query($conn, $queryget);
while ( $row = mysqli_fetch_assoc($data)){
    array_push($manggiohang, new GioHang(
        $row['id_giohang'],
        $row['id_user'],
        $row['id_sp'],
        $row['ten_sp'],
        $row['hinh_sp'],
        $row['gia_sp'],
        $row['soluong_sp']
        
    ));
}

echo json_encode($manggiohang);
}else{
    echo "loi";
}

class GioHang{
    function GioHang($idgiohang, $iduser, $idsp, $tensp, $hinhsp, $giasp, $soluongsp){
        $this->idgiohang = $idgiohang;
        $this->iduser = $iduser;
        $this->idsp = $idsp;
        $this->tensp = $tensp;
        $this->hinhsp = $hinhsp;
        $this->giasp = $giasp;
        $this->soluongsp = $soluongsp;
        
    }
}


?>