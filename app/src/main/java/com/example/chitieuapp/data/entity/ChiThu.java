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
}
