package com.purang.myluckmeasuringapp.game_activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat.animate
import com.bumptech.glide.Glide
import com.purang.myluckmeasuringapp.Helper.SharedPreferences
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.ActivityDiceBinding
import kotlin.random.Random

class DiceActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDiceBinding
    private val imageResources = ArrayList<Int>()
    private var gameResult = -1
    private var gamePercentage = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDiceImg()

        binding.diceBtn.setOnClickListener {
            showRandomImageWithAnimation()
        }

        binding.diceNextBtn.setOnClickListener {
            gamePercentage = (1.0/6.0)
            val temp = "%.7f".format(gamePercentage.toDouble())
            val intent = Intent(this, RouletteWheelActivity::class.java).apply {
                putExtra("result", gameResult.toString())
                putExtra("percentage", temp)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun setDiceImg() {
        imageResources.apply {
            add(R.drawable.dice1)
            add(R.drawable.dice2)
            add(R.drawable.dice3)
            add(R.drawable.dice4)
            add(R.drawable.dice5)
            add(R.drawable.dice6)
        }
    }

    private fun showRandomImageWithAnimation() {
        // 랜덤으로 이미지 선택
        val randomIndex = Random.nextInt(imageResources.size)
        val randomImageResource = imageResources[randomIndex]
        val sp = SharedPreferences()
        Log.e("randomTest", randomImageResource.toString())

        // 애니메이션으로 이미지 전환
        binding.diceImg.animate().alpha(0f).setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    // 애니메이션이 끝난 후 이미지 변경
                   if (!isDestroyed) {
                       Glide.with(this@DiceActivity)
                           .load(randomImageResource)
                           .into(binding.diceImg)
                       binding.diceImg.animate().alpha(1f).setDuration(1000).start() // 알파값 초기화하고 다시 표시
                   }
                }
            }).start()
        gameResult = randomIndex+1
        Log.e("gameResult", gameResult.toString())

        sp.setDice(this@DiceActivity, gameResult)

        binding.diceBtn.visibility = View.GONE
        binding.diceNextBtn.visibility = View.VISIBLE
    }
}