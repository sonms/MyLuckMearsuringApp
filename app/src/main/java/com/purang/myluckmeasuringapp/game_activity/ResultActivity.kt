package com.purang.myluckmeasuringapp.game_activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.purang.myluckmeasuringapp.MainActivity
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.dao.GameResultEntity
import com.purang.myluckmeasuringapp.database.ResultDatabase
import com.purang.myluckmeasuringapp.databinding.ActivityResultBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    private var interstitialAd : InterstitialAd? = null
    private var db: ResultDatabase? = null

    private var preGameResult = ""
    private var resultData : GameResultEntity? = null
    private var sharedPref : SharedPreferences? = null
    private var preNickName : String = ""
    private var gamePercentage = ""
    lateinit var adLoader: AdLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)
        preGameResult = intent.getStringExtra("result") as String
        gamePercentage = intent.getStringExtra("percentage") as String
        //MobileAds.initialize(this@ResultActivity)
        db = ResultDatabase.getInstance(this@ResultActivity)
        initView()
        initAd()
        //initNativeAd()
        preNickName = sharedPref?.getString("saveNickname", "") ?: ""

        binding.bottomBtn.setOnClickListener {
            //data 저장도 여기서 Todo
            CoroutineScope(Dispatchers.IO).launch {
                if (resultData != null) {
                    db!!.resultDao().insertResult(resultData!!)
                }
            }
            //광고 테스트
            if (interstitialAd != null) {
                interstitialAd?.show(this@ResultActivity)
                interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdClicked() {
                        super.onAdClicked()
                        Log.d("onADClick", "ad clicked")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        Log.d("onaddismissed", "ad dismissed")
                        val intent = Intent(this@ResultActivity, MainActivity::class.java)
                        startActivity(intent)
                        interstitialAd = null
                        initAd()
                        finish()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)
                        Log.e("onadfailshow", p0.message)
                        interstitialAd = null
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        Log.d("onadimpression", "ad recorded an impression")
                    }

                    override fun onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent()
                        Log.d("onadshow", "ad show")
                    }
                }
            } else {
                val intent = Intent(this@ResultActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initView() {
        //binding.resultLottie.playAnimation()
        //binding.resultLottie.cancelAnimation()
        //0 주사위, 1 룰렛, 2 홀짝 3 제비뽑기, 4 강화
        val resultDataSet = preGameResult.split(" ").map { it.toString() }
        Log.e("resultTest", resultDataSet.toString())
        val mFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val mNow = System.currentTimeMillis();
        val mDate = Date(mNow);
        val date = mFormat.format(mDate);

        resultData = GameResultEntity(
            userName = preNickName.ifEmpty {
                "유저1"
            },
            gameDice = preGameResult[0].toString(),
            gameRoulette = preGameResult[1].toString(),
            gameSniffling = preGameResult[2].toString(),
            gameDrawLots = preGameResult[3].toString(),
            gameJelly = preGameResult[4].toString(),
            gameDate = date.toString(),
            gamePercentage = gamePercentage
        )

        // 페이드 아웃 애니메이션 설정
        val fadeOutAnimation = AlphaAnimation(0.0f, 1.0f)
        fadeOutAnimation.duration = 3000 // 애니메이션 지속 시간 설정 (밀리초)
        fadeOutAnimation.fillAfter = true // 애니메이션 종료 후 상태 유지

        // 아이템 뷰에 애니메이션 적용
        for (i in resultDataSet.indices) {
            val itemLl = LinearLayout(this)
            val itemText = TextView(this)
            val itemImage = ImageView(this)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
            itemLl.layoutParams = params
            itemLl.orientation = LinearLayout.HORIZONTAL
            itemLl.gravity = Gravity.CENTER

            itemText.layoutParams = params
            itemText.setTypeface(null, Typeface.BOLD)
            itemImage.layoutParams = params
            /*textView.layoutParams = params

            linearLayout.addView(textView)*/
            when(i) {
                0 -> {
                    itemText.text = "주사위 결과 : ${resultDataSet[0]}"
                    itemLl.addView(itemText)
                    itemLl.startAnimation(fadeOutAnimation)
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
                    itemLl.startAnimation(fadeOutAnimation)
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
                    itemLl.startAnimation(fadeOutAnimation)
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
                    itemLl.startAnimation(fadeOutAnimation)
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
                    itemLl.startAnimation(fadeOutAnimation)
                }
            }
            binding.resultLl.addView(itemLl)
        }

        binding.resultLottie.playAnimation()
        binding.resultAd.loadAd(AdRequest.Builder().build())

        binding.bottomBtn.visibility = View.VISIBLE
    }

    private fun initAd() { //전면광고
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    interstitialAd = p0
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    interstitialAd = null
                    Log.e("onAdFail", p0.message.toString())
                }
            }
        )
    }

    /*private fun initNativeAd() {
        adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad : NativeAd ->
                // Show the ad.
                if (adLoader.isLoading) {
                    Log.e("loaderTest", "succcccccccccccddssssssssssssss")
                    // The AdLoader is still loading ads.
                    // Expect more adLoaded or onAdFailedToLoad callbacks.
                    binding.resultAd2.setNativeAd(ad)
                    if (isDestroyed) {
                        ad.destroy()
                        return@forNativeAd
                    }
                } else {
                    // The AdLoader has finished loading ads.
                    if (isDestroyed) {
                        ad.destroy()
                        return@forNativeAd
                    }
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                    Log.e("nativeAdError", adError.message)
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }*/
}