<?php
require "connect.php";
  $idsp = $_POST['idsanpham'];





$query = "SELECT * FROM sanpham WHERE id = $idsp ";

$sanpham = array();
if(mysqli_num_rows(mysqli_query($conn,$query)) > 0){
    $data = mysqli_query($conn, $query);
    $row = mysqli_fetch_array($data);
     $sanpham["id"] =  $row['id'];
     $sanpham["tensp"] =   $row['tensanpham'];
     $sanpham["giasp"] =  $row['giasanpham'];
     $sanpham["hinhsp"] =  $row['hinhanhsanpham'];
     $sanpham["doituongsp"] =  $row['doituongsanpham'];
     $sanpham["sizesp"] =  $row['sizesanpham'];
     $sanpham["motasp"] =  $row['motasanpham'];
     $sanpham["idloaisp"] =  $row['idloaisanpham'];
    
}
echo json_encode($sanpham);



?>