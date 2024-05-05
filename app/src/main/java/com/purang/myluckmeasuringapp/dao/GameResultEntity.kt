package com.purang.myluckmeasuringapp.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "table_result")
data class GameResultEntity(
    //string용
    /*@PrimaryKey
    val gameCode : String = UUID.randomUUID().toString(), // UUID로 고유한 문자열 값 생성
    */

    val userName : String = "",
    val gameDice : String = "",
    val gameRoulette : String = "",
    val gameSniffling : String = "",
    val gameDrawLots : String = "",
    val gameJelly : String = "",
    val gameDate : String = "",
    val gamePercentage : String = ""
) {
    @PrimaryKey(autoGenerate = true) var gameCode : Int = 0
}
