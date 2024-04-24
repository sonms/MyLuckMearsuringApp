package com.purang.myluckmeasuringapp.game_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.ActivityDrawLotsBinding

class DrawLotsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDrawLotsBinding
    private var gameResult = ""
    private var preGameResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawLotsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}