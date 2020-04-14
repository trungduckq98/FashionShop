<?php
require "connect.php";

 $json = $_POST['donhang'];

$data = json_decode($json, true);
foreach ($data as $value){
    $iddonhang = $value['idgiohang'];
    $iduser = $value['iduser'];
    $idsp = $value['idsp'];
    $tensp = $value['tensp'];
    $hinhsp = $value['hinhsp'];
    $giasp = $value['giasp'];
    $soluongsp = $value['soluongsp'];
    $query = "INSERT INTO donhang (id_donhang, id_user, id_sp, ten_sp, hinh_sp, gia_sp, soluong_sp)
    VALUES ('$iddonhang', '$iduser', '$idsp', '$tensp', '$hinhsp', '$giasp', '$soluongsp')";
    $Dta = mysqli_query($conn, $query);

}
if($Dta){
    echo "1";
}else{
    echo "0";
}
?>