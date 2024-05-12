package com.purang.myluckmeasuringapp.game_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.purang.myluckmeasuringapp.Helper.SharedPreferences
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.dao.MyViewModel
import com.purang.myluckmeasuringapp.databinding.ActivityJellyUpgradeBinding
import kotlin.random.Random

class JellyUpgradeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityJellyUpgradeBinding
    private var remainingChance = 0
    private var gameResult = ""
    private var preGameResult = ""
    private var nextJelly = false
    private lateinit var viewModel: MyViewModel
    private var gamePercentage = ""
    private val sp = SharedPreferences()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJellyUpgradeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preGameResult = intent.getStringExtra("result") as String
        gamePercentage = intent.getStringExtra("percentage") as String

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        // 초기 remainingChance 값을 설정합니다.
        initChance()


        // remainingChance LiveData를 관찰하여 값이 변경될 때마다 UI를 업데이트합니다.
        viewModel.remainingChanceLiveData.observe(this, Observer { remainingChance ->
            // UI 업데이트 로직을 여기에 작성합니다.
            binding.remainingChanceNum.text = remainingChance.toString()
            Log.d("viewmodel", remainingChance.toString())
            if (remainingChance <= 0) {
                binding.bottomBtn.visibility = View.GONE
                binding.bottomNextBtn.visibility = View.VISIBLE
            }
        })

        binding.bottomBtn.setOnClickListener {
            if (remainingChance >= 1) {
                probabilityCalculation()
                remainingChance -= 1
                viewModel.updateRemainingChance(remainingChance)
            } else {
                Toast.makeText(this@JellyUpgradeActivity, "남은 기회가 없습니다!", Toast.LENGTH_SHORT).show()
                binding.bottomBtn.visibility = View.GONE
                binding.bottomNextBtn.visibility = View.VISIBLE
            }
        }

        binding.bottomNextBtn.setOnClickListener {
            /*gamePercentage = when(gameResult) {
                "gold" -> {
                    (gamePercentage.toDouble() * 1.0/100).toString()
                }

                "silver" -> {
                    (gamePercentage.toDouble() * 14.0/100).toString()
                }

                else -> {
                    (gamePercentage.toDouble() * 85.0/100).toString()
                }
            }*/
            val temp = "%.7f".format(gamePercentage.toDouble())
            val intent = Intent(this@JellyUpgradeActivity, ResultActivity::class.java).apply {
                putExtra("result", "$preGameResult $gameResult")
                putExtra("percentage", temp)
            }
            startActivity(intent)
            finish()
        }
    }


    private fun initChance() {
        remainingChance = Random.nextInt(20) + 1
        /*val randomGenerator = Random(System.currentTimeMillis())
        val result = randomGenerator.nextInt(1, 100)
        println(result)*/
        viewModel.updateRemainingChance(remainingChance)
        binding.remainingChanceNum.text = remainingChance.toString()
    }

    private fun probabilityCalculation() {
        val nextRand = Random.nextInt(100) + 1
        //Log.e("jelly", nextRand.toString())
        if (nextRand == 99 && nextJelly) {
            if (remainingChance > 0) {
                //gamePercentage = (gamePercentage.toDouble() * 1.0/100).toString()
                Toast.makeText(this@JellyUpgradeActivity, "강화 성공!!!", Toast.LENGTH_SHORT).show()
                Glide.with(this@JellyUpgradeActivity)
                    .load(R.drawable.gold_main_jelly)
                    .into(binding.jellyIv)
                //binding.drawItemTv.text = item.number.toString()
                //nextJelly = true
                gameResult = "gold"
                /*gameResult = when (gameResult) {
                    "gold" -> {
                        "gold"
                    }
                    "silver" -> {
                        "silver"
                    }
                    else -> {
                        "bronze"
                    }
                }*/
                gamePercentage = (gamePercentage.toDouble() * 1.0/100).toString()
                sp.setJelly(this@JellyUpgradeActivity, gameResult)
                viewModel.updateRemainingChance(0)
            }
        } else if (nextRand < 15 && !nextJelly) {
            if (remainingChance > 0) {
                //gamePercentage = (gamePercentage.toDouble() * 1.0/100).toString()
                Toast.makeText(this@JellyUpgradeActivity, "강화 성공!", Toast.LENGTH_SHORT).show()
                Glide.with(this@JellyUpgradeActivity)
                    .load(R.drawable.silver_main_jelly)
                    .into(binding.jellyIv)
                //binding.drawItemTv.text = item.number.toString()
                nextJelly = true
                gameResult = "silver"
                sp.setJelly(this@JellyUpgradeActivity, gameResult)
                gamePercentage = (gamePercentage.toDouble() * 14.0/100).toString()
                /*gameResult = when (gameResult) {
                    "gold" -> {
                        "gold"
                    }
                    "silver" -> {
                        "silver"
                    }
                    else -> {
                        "bronze"
                    }
                }*/
            }
        }
        else {
            gameResult = when (gameResult) {
                "gold" -> {
                    "gold"
                }
                "silver" -> {
                    "silver"
                }
                else -> {
                    "bronze"
                }
            }
            gamePercentage = (gamePercentage.toDouble() * 85.0/100).toString()
            sp.setJelly(this@JellyUpgradeActivity, gameResult)
            Toast.makeText(this@JellyUpgradeActivity, "강화 실패!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}