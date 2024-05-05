package com.purang.myluckmeasuringapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract interface ResultDao {
    @Query("SELECT * FROM table_result")
    fun getAll() : List<GameResultEntity>

    @Insert
    fun insertResult(gameResultEntity: GameResultEntity)

    @Delete
    fun deleteResult(gameResultEntity: GameResultEntity)
}