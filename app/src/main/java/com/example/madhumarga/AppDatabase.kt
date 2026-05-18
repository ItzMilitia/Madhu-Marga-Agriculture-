package com.example.madhumarga

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Hive::class, Harvest::class, Inspection::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hiveDao(): HiveDao
}