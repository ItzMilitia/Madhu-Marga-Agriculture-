package com.example.madhumarga

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "Harvest")
data class Harvest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hiveId: Int,   // 🔥 NEW
    val quantity: Float,
    val year: Int
)
