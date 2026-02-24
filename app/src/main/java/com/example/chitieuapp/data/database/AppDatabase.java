package com.example.chitieuapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.chitieuapp.data.dao.ChiThuDao;
import com.example.chitieuapp.data.entity.ChiThu;

@Database(entities = {ChiThu.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract ChiThuDao chiThuDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "chithu_db"
                    ).fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
