package com.hanifa.application.utils.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hanifa.application.model.entity.Pengeluaran;
import com.hanifa.application.utils.database.daos.PengeluaranDao;

@Database(entities = {Pengeluaran.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PengeluaranDao pengeluaranDao();
}