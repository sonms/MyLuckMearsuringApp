package com.purang.myluckmeasuringapp.game_activity

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.adapter.DrawLotsAdapter
import com.purang.myluckmeasuringapp.dao.DrawLotsData
import com.purang.myluckmeasuringapp.databinding.ActivityDrawLotsBinding
import kotlin.random.Random

class DrawLotsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDrawLotsBinding
    private var gameResult = ""
    private var preGameResult = ""
    private var itemData = ArrayList<DrawLotsData>()
    private var gridLayoutManager = GridLayoutManager(this@DrawLotsActivity, 4)
    private lateinit var adapter : DrawLotsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawLotsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preGameResult = intent.getStringExtra("result") as String

        initAdapter()

        binding.bottomBtn.setOnClickListener {
            val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_rv_anim)
            // 애니메이션 적용
            binding.nested.startAnimation(fadeInAnimation)
            binding.drawRv.startAnimation(fadeInAnimation)
            binding.nested.visibility = View.VISIBLE
            /*setData()
            initAdapter()*/
        }

        binding.bottomNextBtn.setOnClickListener {
            val intent = Intent(this@DrawLotsActivity, JellyUpgradeActivity::class.java).apply {
                putExtra("result", "$preGameResult $gameResult")
            }
            startActivity(intent)
            finish()
        }
    }

    private fun setData() {
        val random = Random.nextInt(20)
        itemData.clear()
        for (i in 1..20) {
            if (i == random) {
                itemData.add(DrawLotsData(i, "win"))
            } else {
                itemData.add(DrawLotsData(i, "lose"))
            }
        }
    }

    private fun initAdapter() {
        setData()
        adapter = DrawLotsAdapter(itemData)
        binding.drawRv.adapter = adapter
        binding.drawRv.setHasFixedSize(true)
        binding.drawRv.layoutManager = gridLayoutManager
        binding.nested.visibility = View.GONE


        adapter.setItemClickListener(object : DrawLotsAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, itemId: Int?) {
                val item = itemData[position]
                if (item.content == "win") {
                    val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this@DrawLotsActivity , R.color.winning_color1)) //승인
                    // 배경색 변경
                    view.backgroundTintList = colorStateList
                    gameResult = "win"
                    Toast.makeText(this@DrawLotsActivity, "성공!!", Toast.LENGTH_SHORT).show()
                } else {
                    val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this@DrawLotsActivity , R.color.purang_gray7)) //승인
                    // 배경색 변경
                    view.backgroundTintList = colorStateList
                    gameResult = "lose"
                    Toast.makeText(this@DrawLotsActivity, "땡~", Toast.LENGTH_SHORT).show()
                }

                binding.bottomNextBtn.visibility = View.VISIBLE
                binding.bottomBtn.visibility = View.GONE
                binding.drawRv.isClickable = false

                //binding.drawRv.remo
            }
        })
    }
}