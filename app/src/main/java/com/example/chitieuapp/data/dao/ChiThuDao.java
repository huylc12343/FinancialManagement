package com.example.chitieuapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chitieuapp.data.entity.ChiThu;

import java.util.List;

@Dao
public interface ChiThuDao {
    @Insert
    void insert(ChiThu chiThu);

    @Query("SELECT * FROM ChiThu ORDER BY idChiThu DESC")
    List<ChiThu> getAll();

    @Query("SELECT IFNULL(SUM(sotien), 0) FROM ChiThu WHERE ngay BETWEEN :fromDate AND :toDate AND type = 0")
    int getSoTienChiInRange(String fromDate, String toDate);
    @Query("SELECT IFNULL(SUM(sotien), 0) FROM ChiThu WHERE ngay BETWEEN :fromDate AND :toDate AND type = 1")
    int getSoTienThuInRange(String fromDate, String toDate);

}
