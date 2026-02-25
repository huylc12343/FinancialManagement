package com.example.chitieuapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ChiThu")
public class ChiThu {
    @PrimaryKey(autoGenerate = true)
    public int idChiThu;
    public String ngay;
    public String ghichu;
    public int sotien;
    public String danhmuc;
    public int type;

    public ChiThu(String ngay, String ghichu, int sotien, String danhmuc, int type) {
        this.ngay = ngay;
        this.ghichu = ghichu;
        this.sotien = sotien;
        this.danhmuc = danhmuc;
        this.type = type;
    }

    public int getIdChiThu() {
        return idChiThu;
    }

    public String getNgay() {
        return ngay;
    }

    public String getGhichu() {
        return ghichu;
    }

    public int getSotien() {
        return sotien;
    }

    public String getDanhmuc() {
        return danhmuc;
    }

    public int getType() {
        return type;
    }

    public void setIdChiThu(int idChiThu) {
        this.idChiThu = idChiThu;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public void setDanhmuc(String danhmuc) {
        this.danhmuc = danhmuc;
    }

    public void setType(int type) {
        this.type = type;
    }
}
