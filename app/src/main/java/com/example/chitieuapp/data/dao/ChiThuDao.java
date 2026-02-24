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
}
