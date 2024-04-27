package com.purang.myluckmeasuringapp.game_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.ActivityJellyUpgradeBinding
import kotlin.random.Random

class JellyUpgradeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityJellyUpgradeBinding
    private var remainingChance = 0
    private var gameResult = ""
    private var preGameResult = ""
    private var nextJelly = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJellyUpgradeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preGameResult = intent.getStringExtra("result") as String
        initChance()

        binding.bottomBtn.setOnClickListener {
            if (remainingChance >= 1) {
                probabilityCalculation()
                remainingChance -= 1
            } else {
                Toast.makeText(this@JellyUpgradeActivity, "남은 기회가 없습니다!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bottomNextBtn.setOnClickListener {
            val intent = Intent(this@JellyUpgradeActivity, ResultActivity::class.java).apply {
                putExtra("result", "$preGameResult $gameResult")
            }
            startActivity(intent)
            finish()
        }
    }

    private fun initChance() {
        remainingChance = Random.nextInt(20) + 1
        val randomGenerator = Random(System.currentTimeMillis())
        val result = randomGenerator.nextInt(1, 100)
        println(result)

        binding.remainingChanceNum.text = remainingChance.toString()
    }

    private fun probabilityCalculation() {
        val nextRand = Random.nextInt(100) + 1

        if (nextRand == 1 && nextJelly) {
            if (remainingChance > 0) {
                Toast.makeText(this@JellyUpgradeActivity, "강화 성공!!!", Toast.LENGTH_SHORT).show()
                Glide.with(this@JellyUpgradeActivity)
                    .load(R.drawable.gold_main_jelly)
                    .into(binding.jellyIv)
                //binding.drawItemTv.text = item.number.toString()
                //nextJelly = true
                gameResult = "gold"
            }
        } else if (nextRand < 15 && !nextJelly) {
            if (remainingChance > 0) {
                Toast.makeText(this@JellyUpgradeActivity, "강화 성공!", Toast.LENGTH_SHORT).show()
                Glide.with(this@JellyUpgradeActivity)
                    .load(R.drawable.silver_main_jelly)
                    .into(binding.jellyIv)
                //binding.drawItemTv.text = item.number.toString()
                nextJelly = true
                gameResult = "silver"
            }
        }
        else {
            gameResult = "bronze"
            Toast.makeText(this@JellyUpgradeActivity, "강화 실패!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (remainingChance <= 0) {
            binding.bottomBtn.visibility = View.GONE
            binding.bottomNextBtn.visibility = View.VISIBLE
        }
    }
}