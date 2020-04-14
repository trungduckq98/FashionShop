
<?php
include "connect.php";
$text=$_POST['text'];



$mangsanpham = array();
$query = "SELECT * FROM sanpham WHERE tensanpham LIKE '%$text%' ";
$data = mysqli_query($conn, $query);
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