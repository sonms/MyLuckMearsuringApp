package com.purang.myluckmeasuringapp.Helper

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferences {
    private val dice1 = "dice1"
    private val dice2 = "dice2"
    private val dice3 = "dice3"
    private val dice4 = "dice4"
    private val dice5 = "dice5"
    private val dice6 = "dice6"

    private val rouletteWin = "rouletteWin"
    private val rouletteLose = "rouletteLose"

    private val snifflingWin = "snifflingWin"
    private val snifflingLose = "snifflingLose"

    private val drawLotsWin = "drawLotsWin"
    private val drawLotsLose = "drawLotsLose"

    private val jellyBronze = "jellyBronze"
    private val jellySilver = "jellySilver"
    private val jellyGold = "jellyGold"

    private fun getSharedPreferences(ctx: Context?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx!!)
    }


    fun setDice(ctx: Context?, diceResult: Int?) {
        val editor = getSharedPreferences(ctx).edit()
        val temp = getDice(ctx, diceResult)
        when(diceResult) {
            1 -> {
                editor.putInt(dice1, temp+1)
                editor.apply()
            }

            2-> {
                editor.putInt(dice2,  temp+1)
                editor.apply()
            }

            3->{
                editor.putInt(dice3,  temp+1)
                editor.apply()
            }

            4->{
                editor.putInt(dice4,  temp+1)
                editor.apply()
            }

            5->{
                editor.putInt(dice5,  temp+1)
                editor.apply()
            }

            6->{
                editor.putInt(dice6,  temp+1)
                editor.apply()
            }
        }
    }

    fun getDice(ctx: Context?, dice : Int?): Int {
        var tag = ""
        when(dice) {
            1 -> {
                tag = dice1
            }
            2 -> {
                tag = dice2
            }
            3->{
                tag = dice3
            }

            4->{
                tag = dice4
            }

            5->{
                tag = dice5
            }

            6->{
                tag = dice6
            }
        }
        return getSharedPreferences(ctx).getInt(tag, 0)
    }

    fun setRoulette(ctx: Context?, rouletteResult: String?) {
        val editor = getSharedPreferences(ctx).edit()
        val temp = getRoulette(ctx, rouletteResult)
        when(rouletteResult) {
            "win" -> {
                editor.putInt(rouletteWin, temp+1)
                editor.apply()
            }

            "lose" -> {
                editor.putInt(rouletteLose, temp+1)
                editor.apply()
            }

        }
    }

    fun getRoulette(ctx: Context?, rouletteResult: String?) : Int {
        var tag = ""
        when(rouletteResult) {
            "win" -> {
                tag = rouletteWin
            }

            "lose" -> {
                tag = rouletteLose
            }
        }

        return getSharedPreferences(ctx).getInt(tag, 0)
    }


    fun setSniffling(ctx: Context?, snifflingResult: String?) {
        val editor = getSharedPreferences(ctx).edit()
        val temp = getSniffling(ctx, snifflingResult)
        when(snifflingResult) {
            "win" -> {
                editor.putInt(snifflingWin, temp+1)
                editor.apply()
            }

            "lose" -> {
                editor.putInt(snifflingLose, temp+1)
                editor.apply()
            }

        }
    }

    fun getSniffling(ctx: Context?, snifflingResult: String?) : Int {
        var tag = ""
        when(snifflingResult) {
            "win" -> {
                tag = snifflingWin
            }

            "lose" -> {
                tag = snifflingLose
            }
        }

        return getSharedPreferences(ctx).getInt(tag, 0)
    }

    fun setDrawLots(ctx: Context?, drawLotsResult: String?) {
        val editor = getSharedPreferences(ctx).edit()
        val temp = getDrawLots(ctx, drawLotsResult)
        when(drawLotsResult) {
            "win" -> {
                editor.putInt(drawLotsWin, temp+1)
                editor.apply()
            }

            "lose" -> {
                editor.putInt(drawLotsLose, temp+1)
                editor.apply()
            }

        }
    }

    fun getDrawLots(ctx: Context?, drawLotsResult: String?) : Int {
        var tag = ""
        when(drawLotsResult) {
            "win" -> {
                tag = drawLotsWin
            }

            "lose" -> {
                tag = drawLotsLose
            }
        }

        return getSharedPreferences(ctx).getInt(tag, 0)
    }


    fun setJelly(ctx: Context?, jellyResult: String?) {
        val editor = getSharedPreferences(ctx).edit()
        val temp = getJelly(ctx, jellyResult)
        when(jellyResult) {
            "gold" -> {
                editor.putInt(jellyGold, temp+1)
                editor.apply()
            }

            "silver" -> {
                editor.putInt(jellySilver, temp+1)
                editor.apply()
            }

            "bronze" -> {
                editor.putInt(jellyBronze, temp+1)
                editor.apply()
            }

        }
    }

    fun getJelly(ctx: Context?, jellyResult: String?) : Int {
        var tag = ""
        when(jellyResult) {
            "gold" -> {
                tag = jellyGold
            }

            "silver" -> {
                tag = jellySilver
            }

            "bronze" -> {
                tag = jellyBronze
            }
        }

        return getSharedPreferences(ctx).getInt(tag, 0)
    }
    //private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences()
    fun clear(ctx: Context?) {
        getSharedPreferences(ctx).edit().clear().apply()
    }
}