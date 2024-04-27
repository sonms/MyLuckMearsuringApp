package com.purang.myluckmeasuringapp.game_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.purang.myluckmeasuringapp.MainActivity
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    private var preGameResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preGameResult = intent.getStringExtra("result") as String
        initView()

        binding.bottomBtn.setOnClickListener {
            //data 저장도 여기서 Todo
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initView() {
        //binding.resultLottie.playAnimation()
        //binding.resultLottie.cancelAnimation()
        //0 주사위, 1 룰렛, 2 홀짝 3 제비뽑기, 4 강화
        val resultDataSet = preGameResult.split(" ").map { it.toString() }
        for (i in resultDataSet.indices) {
            val itemLl = LinearLayout(this)
            val itemText = TextView(this)
            val itemImage = ImageView(this)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            itemLl.layoutParams = params
            itemLl.orientation = LinearLayout.HORIZONTAL

            itemText.layoutParams = params
            itemImage.layoutParams = params
            /*textView.layoutParams = params

            linearLayout.addView(textView)*/
            when(i) {
                0 -> {
                    itemText.text = "주사위 결과 : ${resultDataSet[0]}"
                    itemLl.addView(itemText)
                }
                1 -> {
                    if (resultDataSet[1] == "win") {
                        itemText.text = "룰렛 결과 : "
                        itemImage.setImageResource(R.drawable.winning_icon)
                    } else {
                        itemText.text = "룰렛 결과 : "
                        itemImage.setImageResource(R.drawable.lose_icon)
                    }

                    itemLl.addView(itemText)
                    itemLl.addView(itemImage)
                }
                2->{
                    if (resultDataSet[2] == "win") {
                        itemText.text = "홀짝 결과 : "
                        itemImage.setImageResource(R.drawable.winning_icon)
                    } else {
                        itemText.text = "홀짝 결과 : "
                        itemImage.setImageResource(R.drawable.lose_icon)
                    }

                    itemLl.addView(itemText)
                    itemLl.addView(itemImage)
                }
                3->{
                    if (resultDataSet[3] == "win") {
                        itemText.text = "제비뽑기 결과 : "
                        itemImage.setImageResource(R.drawable.winning_icon)
                    } else {
                        itemText.text = "제비뽑기 결과 : "
                        itemImage.setImageResource(R.drawable.lose_icon)
                    }

                    itemLl.addView(itemText)
                    itemLl.addView(itemImage)
                }
                4->{
                    if (resultDataSet[4] == "bronze") {
                        itemText.text = "젤리 강화하기 결과 : "
                        itemImage.setImageResource(R.drawable.bronze_main_jelly)
                    } else if (resultDataSet[4] == "silver") {
                        itemText.text = "젤리 강화하기 결과 : "
                        itemImage.setImageResource(R.drawable.silver_main_jelly)
                    } else if (resultDataSet[4] == "gold") {
                        itemText.text = "젤리 강화하기 결과 : "
                        itemImage.setImageResource(R.drawable.gold_main_jelly)
                    }

                    itemLl.addView(itemText)
                    itemLl.addView(itemImage)
                }
            }
            binding.resultLl.addView(itemLl)
        }
        binding.resultLottie.playAnimation()
    }

}