package com.purang.myluckmeasuringapp.game_activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.ActivitySnifflingBinding
import kotlin.random.Random

class SnifflingActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySnifflingBinding
    private var gameResult = ""
    private var preGameResult = ""
    private var random = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnifflingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preGameResult = intent.getStringExtra("result") as String

        initUISet()
        randomNum()

        binding.bottomBtn.setOnClickListener {
            val imageView = binding.cupIconIv
            val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.anim)
            imageView.startAnimation(shakeAnimation)
            binding.bottomBtnLl.visibility = View.GONE
            binding.selectBtnLl.visibility = View.VISIBLE
            //val result = randomNum()
            /*shakeAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {

                }

                override fun onAnimationRepeat(p0: Animation?) {
                    //TODO("Not yet implemented")
                }
            })*/
        }
        //홀
        binding.select1Btn.setOnClickListener {
            // 300dp 만큼 이동하는 Translate 애니메이션 생성
            val translateYAnimation = ObjectAnimator.ofFloat(
                binding.cupIconIv,
                "translationY",
                -300f // 위로 300dp만큼 이동
            ).apply {
                duration = 1000 // 애니메이션 지속 시간 설정 (예: 1초)
            }
            // 애니메이션 시작
            translateYAnimation.start()
            // 애니메이션 리스너 설정
            translateYAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(p0: Animator) {
                    binding.bottomBtn.visibility = View.GONE
                    binding.bottomNextBtn.visibility = View.VISIBLE
                    binding.selectBtnLl.visibility = View.GONE

                    binding.snifflingResultTv.text = random
                    binding.snifflingResultTv.visibility = View.VISIBLE
                     if (random.split(" ").last() == "홀") {
                         gameResult = "win"
                         Toast.makeText(this@SnifflingActivity, "정답!!!", Toast.LENGTH_SHORT).show()
                    } else {
                         gameResult ="lose"
                         Toast.makeText(this@SnifflingActivity, "땡~!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onAnimationCancel(p0: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(p0: Animator) {
                    TODO("Not yet implemented")
                }
            })
        }

        //짝
        binding.select2Btn.setOnClickListener {
            // 300dp 만큼 이동하는 Translate 애니메이션 생성
            val translateYAnimation = ObjectAnimator.ofFloat(
                binding.cupIconIv,
                "translationY",
                -300f // 위로 300dp만큼 이동
            ).apply {
                duration = 1000 // 애니메이션 지속 시간 설정 (예: 1초)
            }
            // 애니메이션 시작
            translateYAnimation.start()
            // 애니메이션 리스너 설정
            translateYAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(p0: Animator) {
                    binding.bottomBtn.visibility = View.GONE
                    binding.bottomNextBtn.visibility = View.VISIBLE
                    binding.selectBtnLl.visibility = View.GONE

                    binding.snifflingResultTv.text = random
                    binding.snifflingResultTv.visibility = View.VISIBLE
                    if (random.split(" ").last() == "짝") {
                        gameResult = "win"
                        Toast.makeText(this@SnifflingActivity, "정답!!!", Toast.LENGTH_SHORT).show()
                    } else {
                        gameResult ="lose"
                        Toast.makeText(this@SnifflingActivity, "땡~!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onAnimationCancel(p0: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(p0: Animator) {
                    TODO("Not yet implemented")
                }
            })
        }

        binding.bottomNextBtn.setOnClickListener {
            val intent = Intent(this@SnifflingActivity, DrawLotsActivity::class.java).apply {
                putExtra("result", "$preGameResult $gameResult")
            }
            startActivity(intent)
            finish()
        }
    }

    private fun initUISet() {
        binding.selectBtnLl.visibility = View.GONE
        binding.bottomBtnLl.visibility = View.VISIBLE
        binding.bottomBtn.visibility = View.VISIBLE
        binding.bottomNextBtn.visibility = View.GONE
    }

    private fun randomNum() {
        val randomNumber = Random.nextInt(100) + 1
        random = if (randomNumber % 2 == 0) {
            "$randomNumber 짝"
        } else {
            "$randomNumber 홀"
        }
    }
}