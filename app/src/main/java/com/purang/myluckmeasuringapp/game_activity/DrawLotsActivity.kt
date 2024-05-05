package com.purang.myluckmeasuringapp.game_activity

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private var gamePercentage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawLotsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preGameResult = intent.getStringExtra("result") as String
        gamePercentage = intent.getStringExtra("percentage") as String
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
            val temp = "%.7f".format(gamePercentage.toDouble())
            val intent = Intent(this@DrawLotsActivity, JellyUpgradeActivity::class.java).apply {
                putExtra("result", "$preGameResult $gameResult")
                putExtra("percentage", temp)
            }
            startActivity(intent)
            finish()
        }

        adapter.setItemClickListener(object : DrawLotsAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, itemId: Int?) {
                val item = itemData[position]
                Log.e("drawactivity", itemData[position].content)
                if (item.content == "win") {
                    gamePercentage = (gamePercentage.toDouble() * (1.0 / itemData.size)).toString()
                    gameResult = "win"
                    Toast.makeText(this@DrawLotsActivity, "성공!!", Toast.LENGTH_SHORT).show()
                } else {
                    gamePercentage = (gamePercentage.toDouble() * ( (itemData.size - 1).toDouble() / itemData.size)).toString()
                    gameResult = "lose"
                    Toast.makeText(this@DrawLotsActivity, "땡~", Toast.LENGTH_SHORT).show()
                }
                binding.bottomNextBtn.visibility = View.VISIBLE
                binding.bottomBtn.visibility = View.GONE
                binding.drawRv.isClickable = false

                binding.drawRv.setOnClickListener(null)
                adapter.setItemClickListener(null)
                //binding.drawRv.remo
            }
        })
    }

    private fun setData() {
        val random = Random.nextInt(20)
        itemData.clear()
        for (i in 0..19) {
            if (i == random) {
                itemData.add(DrawLotsData(i+1, "win", false))
            } else {
                itemData.add(DrawLotsData(i+1, "lose", false))
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
    }
}