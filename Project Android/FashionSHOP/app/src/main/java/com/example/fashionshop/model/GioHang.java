package com.example.fashionshop.model;

public class GioHang {
    private int idgiohang;
    private int iduser;
    private int idsp;
    private String tensp;
    private String hinhsp;
    private int giasp;
    private int soluongsp;

    public GioHang(int idgiohang, int iduser, int idsp, String tensp, String hinhsp, int giasp, int soluongsp) {
        this.idgiohang = idgiohang;
        this.iduser = iduser;
        this.idsp = idsp;
        this.tensp = tensp;
        this.hinhsp = hinhsp;
        this.giasp = giasp;
        this.soluongsp = soluongsp;
    }

    public GioHang() {
    }

    public int getIdgiohang() {
        return idgiohang;
    }

    public void setIdgiohang(int idgiohang) {
        this.idgiohang = idgiohang;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }
}
