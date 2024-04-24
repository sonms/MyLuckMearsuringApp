package com.purang.myluckmeasuringapp.game_activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bluehomestudio.luckywheel.WheelItem
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.ActivityRouletteWheelBinding
import kotlin.random.Random

class RouletteWheelActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRouletteWheelBinding
    private val wheelData : MutableList<WheelItem> = ArrayList()//= ArrayList<com.bluehomestudio.luckywheel.WheelItem>()
    private var randomIdx = -1 //1~15 총 룰렛갯수 + 당첨 룰렛 idx
    private var point = -1
    private var gameResult = ""
    private var preGameResult = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouletteWheelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preGameResult = intent.getStringExtra("result") as String
        setWheelData() //룰렛판 세팅

        //룰렛이 다 돌아갔을 때
        binding.wheel.setLuckyWheelReachTheTarget {
            if (point == randomIdx) {
                gameResult = "${randomIdx}개의 원판에서 당첨!!"
                Toast.makeText(this@RouletteWheelActivity, "당첨~!~!~!", Toast.LENGTH_SHORT).show()
            } else {
                gameResult = "${point}개의 원판에서 꽝.."
                Toast.makeText(this@RouletteWheelActivity, "꽝...ㅠㅠ", Toast.LENGTH_SHORT).show()
            }

            binding.wheelBtn.visibility = View.GONE
            binding.wheelNextBtn.visibility = View.VISIBLE
        }

        //시작
        binding.wheelBtn.setOnClickListener {
            point = Random.nextInt(12)+2
            binding.wheel.rotateWheelTo(point)
        }

        binding.wheelNextBtn.setOnClickListener {
            val intent = Intent(this@RouletteWheelActivity, SnifflingActivity::class.java).apply {
                putExtra("result", "$preGameResult $gameResult")
            }
            startActivity(intent)
            finish()
        }
    }


    private fun setWheelData() {
        //val winningTargetIdx = Random.nextInt(15)+1 //
        val idx = (Random.nextInt(12)+2)
        randomIdx = if (idx % 2 != 0) {//2~16 총 룰렛갯수 + 당첨 룰렛 idx + 짝수만
            idx + 1
        } else {
            idx
        }
        /*val bitmapDrawableWinningIcon = R.drawable.winning_icon
        val bitmapDrawableLoseIcon = R.drawable.lose_icon*/
        val bitmapDrawableLoseIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.lose_icon, null)
        val bitmapDrawableWinningIcon: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.winning_icon, null)

        val bitmap1: Bitmap = vectorToBitmap(bitmapDrawableWinningIcon!!)
        val bitmap2 : Bitmap = vectorToBitmap(bitmapDrawableLoseIcon!!)
        //val drawable1: Drawable = BitmapDrawable(this@RouletteWheelActivity.resources, bitmap1)


        //val bitmap1 = bitmapDrawableWinningIcon.bitmap
        //val bitmap2 = bitmapDrawableLoseIcon.bitmap

        Log.e("randomIdx", randomIdx.toString())

        repeat(randomIdx) {
            //println(it)
            if (it == randomIdx-1) {
                //println("ekekekekekekek")
                wheelData.add(com.bluehomestudio.luckywheel.WheelItem(Color.YELLOW, bitmap1, "당첨!!"))
            } else {
                //println("mvmvmvmvmvvmvmvm")
                wheelData.add(com.bluehomestudio.luckywheel.WheelItem(R.color.purang_gray7, bitmap2, "꽝"))
            }
        }

        binding.wheel.addWheelItems(wheelData)
    }

    fun vectorToBitmap(vectorDrawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }
}