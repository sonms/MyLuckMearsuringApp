package com.purang.myluckmeasuringapp.game_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.ActivityJellyUpgradeBinding

class JellyUpgradeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityJellyUpgradeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJellyUpgradeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}