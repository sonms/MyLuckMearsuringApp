package com.purang.myluckmeasuringapp.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface ResultDao {
    @Query("SELECT * FROM table_result")
    fun getAll() : List<GameResultEntity>

    @Insert
    fun insertResult(gameResultEntity: GameResultEntity)

    @Delete
    fun deleteResult(gameResultEntity: GameResultEntity)
}