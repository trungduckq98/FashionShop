<?php
include "connect.php";
$mangspmoinhat = array();
$query = "SELECT * FROM sanpham ORDER BY ID DESC LIMIT 6";
$data = mysqli_query($conn, $query);
while($row = mysqli_fetch_assoc($data)){
    array_push($mangspmoinhat, new Sanphammoinhat(
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
echo json_encode($mangspmoinhat);
class Sanphammoinhat{
    function Sanphammoinhat($id, $tensp, $giasp, $hinhanhsp, $doituongsp, $sizesp, $motasp, $idloaisp){
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