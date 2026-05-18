package com.example.madhumarga

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HiveDao {

    @Insert
    fun insertHive(hive: Hive)

    @Query("SELECT * FROM Hive")
    fun getAllHives(): List<Hive>

    @Insert
    fun insertHarvest(harvest: Harvest)

    @Query("SELECT * FROM Harvest")
    fun getAllHarvest(): List<Harvest>

    @Query("SELECT * FROM Harvest WHERE hiveId = :hiveId")
    fun getHarvestsForHive(hiveId: Int): List<Harvest>

    @Insert
    fun insertInspection(inspection: Inspection)

    @Query("SELECT * FROM Inspection WHERE hiveId = :hiveId")
    fun getInspectionsForHive(hiveId: Int): List<Inspection>
}