package com.example.fashionshop.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int id;
    private String tensp;
    private int giasp;
    private String hinhsp;
    private String doituongsp;
    private String sizesp;
    private String motasp;
    private int idloaisp;

    public SanPham() {
    }

    public SanPham(int id, String tensp, int giasp, String hinhsp, String doituongsp, String sizesp, String motasp, int idloaisp) {
        this.id = id;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.doituongsp = doituongsp;
        this.sizesp = sizesp;
        this.motasp = motasp;
        this.idloaisp = idloaisp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public String getDoituongsp() {
        return doituongsp;
    }

    public void setDoituongsp(String doituongsp) {
        this.doituongsp = doituongsp;
    }

    public String getSizesp() {
        return sizesp;
    }

    public void setSizesp(String sizesp) {
        this.sizesp = sizesp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public int getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(int idloaisp) {
        this.idloaisp = idloaisp;
    }
}
