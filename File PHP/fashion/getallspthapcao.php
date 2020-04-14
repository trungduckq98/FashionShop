<?php
require "connect.php";
$query = "SELECT * FROM sanpham ORDER BY giasanpham ASC";
$data = mysqli_query($conn, $query);
$mangsanpham = array();
while ( $row = mysqli_fetch_assoc($data)){
    array_push($mangsanpham, new Sanpham(
        $row['id'],
        $row['tensanpham'],
        $row['giasanpham'],
        $row['hinhanhsanpham'],
        $row['doituongsanpham'],
        $row['sizesanpham'],
        $row['motasanpham'],
        $row['idloaisanpham']
    ));
}

echo json_encode($mangsanpham);

class Sanpham{
    function Sanpham($id, $tensp, $giasp, $hinhanhsp, $doituongsp, $sizesp, $motasp, $idloaisp){
        $this->id = $id;
        $this->tensp = $tensp;
        $this->giasp = $giasp;
        $this->hinhanhsp = $hinhanhsp;
        $this->doituongsp = $doituongsp;
        $this->sizesp = $sizesp;
        $this->motasp = $motasp;
        $this->idloaisp = $idloaisp;
    }
}
?>