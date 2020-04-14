<?php
require "connect.php";

 $iduserget = $_POST['iduser'];






$manggiohang = array();
$query = "SELECT * FROM giohang WHERE id_user = $iduserget ";
$data = mysqli_query($conn, $query);
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