package com.example.fashionshop.model;

public class LoaiSanPham {
    private int id;
    private String tenloaisp;
    private String hinhloaisp;

    public LoaiSanPham() {
    }

    public LoaiSanPham(int id, String tenloaisp, String hinhloaisp) {
        this.id = id;
        this.tenloaisp = tenloaisp;
        this.hinhloaisp = hinhloaisp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhloaisp() {
        return hinhloaisp;
    }

    public void setHinhloaisp(String hinhloaisp) {
        this.hinhloaisp = hinhloaisp;
    }
}
