package com.example.madhumarga

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Inspection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hiveId: Int,
    val date: Long = System.currentTimeMillis(),
    val queenPresent: Boolean,
    val pestsSeen: Boolean,
    val activityLevel: String,
    val analysisResult: String,
    val healthScore: Int // 🔥 Added health score
)
