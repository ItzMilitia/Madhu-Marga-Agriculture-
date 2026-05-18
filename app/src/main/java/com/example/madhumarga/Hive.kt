package com.example.madhumarga

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hive(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val location: String
)