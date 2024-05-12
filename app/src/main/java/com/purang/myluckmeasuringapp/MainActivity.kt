package com.purang.myluckmeasuringapp

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.purang.myluckmeasuringapp.Helper.SharedPreferences
import com.purang.myluckmeasuringapp.Helper.ThemeHelper
import com.purang.myluckmeasuringapp.bottom_navigation.AccountFragment
import com.purang.myluckmeasuringapp.bottom_navigation.HomeFragment
import com.purang.myluckmeasuringapp.bottom_navigation.MemorialsFragment
import com.purang.myluckmeasuringapp.bottom_navigation.StatisticsFragment
import com.purang.myluckmeasuringapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    //private lateinit var bottomSheetDialog: BottomSheetDialog


    private val TAG_HOME = "home_fragment"
    private val TAG_STATISTICS = "statistics_fragment"
    private val TAG_MEMORIALS = "memorials_fragment"
    private val TAG_ACCOUNT= "account_fragment"

    private var isReady = false
    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        ThemeHelper.applyTheme(this)
        setContentView(mBinding.root)
        MobileAds.initialize(this) {} //광고 초기화화

        showBottomSheetAd(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, HomeFragment())
            .commit()
        initNavigationBar()



    }
    private fun showBottomSheetAd(context: Context) {
        val sharedPreference = SharedPreferences()
        val lastBottomSheetTime = sharedPreference.getLastBottomSheetTime(context)
        val currentTime = System.currentTimeMillis()

        // 마지막으로 바텀시트가 띄워진 시간부터 24시간이 지났는지 확인
        if (currentTime - lastBottomSheetTime >= 24 * 60 * 60 * 1000) {
            // 24시간이 지났으므로 바텀시트를 띄웁니다.
            // 여기에 바텀시트를 띄우는 코드를 추가합니다.
            val bottomSheet = BottomAdFragment()
            //bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            bottomSheet.show(this.supportFragmentManager, bottomSheet.tag)
            bottomSheet.apply {
                setCallback(object : BottomAdFragment.OnSendFromBottomSheetDialog{
                    override fun sendValue(value: String) {
                        Log.d("test", "BottomSheetDialog -> 액티비티로 전달된 값 : $value")
                        Toast.makeText(this@MainActivity, "24시간 동안 끄기로 설정되었습니다", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            // 바텀시트가 띄워진 시간을 저장합니다.
            sharedPreference.setLastBottomSheetTime(context, currentTime)
        }
    }

    private fun initData() {
        // 별도의 데이터 처리가 없기 때문에 3초의 딜레이를 줌.
        // 선행되어야 하는 작업이 있는 경우, 이곳에서 처리 후 isReady를 변경.
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
        }
        isReady = true
    }
    private fun initSplashScreen() {
        initData()
        val splashScreen = installSplashScreen()
        val content: View = findViewById(android.R.id.content)
        // SplashScreen이 생성되고 그려질 때 계속해서 호출된다.
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isReady) {
                        // 3초 후 Splash Screen 제거
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready
                        false
                    }
                }
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener {splashScreenView ->
                val animScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 8f)
                val animScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 8f)
                val animAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)

                ObjectAnimator.ofPropertyValuesHolder(
                    splashScreenView.iconView,
                    animAlpha,
                    animScaleX,
                    animScaleY
                ).run {
                    interpolator = AnticipateInterpolator()
                    duration = 300L
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
        }
    }

    private fun initNavigationBar() {
        mBinding.bottomNavigationView.
        setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    /*oldFragment = HomeFragment()
                    oldTAG = TAG_HOME*/
                    //setToolbarView(TAG_HOME, oldTAG)
                    //setFragment(TAG_HOME, HomeFragment())
                   /* toolbarType = "기본"
                    setToolbarView(toolbarType)*/
                    /*isClicked = false
                    isSettingClicked = false*/
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_content, HomeFragment())
                        .commit()
                }

                R.id.navigation_statistics -> {
                    /*oldFragment = SearchFragment()
                    oldTAG = TAG_SEARCH
                    //setToolbarView(TAG_HOME, oldTAG)
                    setFragment(TAG_SEARCH, SearchFragment())
                    toolbarType = "기본"
                    setToolbarView(toolbarType)
                    isClicked = false
                    isSettingClicked = false*/
                    //setFragment(TAG_STATISTICS, StatisticsFragment())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_content, StatisticsFragment())
                        .commit()
                }

                R.id.navigation_memorials -> {
                    /*val intent = Intent(this, NoticeBoardEditActivity::class.java).apply {
                        putExtra("type","ADD")
                    }
                    requestActivity.launch(intent)*/
                    //setFragment(TAG_MEMORIALS, MemorialsFragment())
                    /*oldFragment = HomeFragment()
                    oldTAG = TAG_HOME
                    //setToolbarView(TAG_HOME, oldTAG)
                    setFragment(TAG_HOME, HomeFragment())
                    setToolbarView(false)
                    isClicked = false
                    isSettingClicked = false*/
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_content, MemorialsFragment())
                        .commit()
                }


                R.id.navigation_account -> {
                    /*oldFragment = AccountFragment()
                    oldTAG = TAG_ACCOUNT
                    //setToolbarView(TAG_HOME, oldTAG)
                    setFragment(TAG_ACCOUNT, AccountFragment())
                    toolbarType = "기본"
                    setToolbarView(toolbarType)
                    isClicked = false
                    isSettingClicked = false*/
                    //setFragment(TAG_ACCOUNT, AccountFragment())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_content, AccountFragment())
                        .commit()
                }


                else -> {

                    //setFragment(TAG_HOME, HomeFragment())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_content, HomeFragment())
                        .commit()
                }
            }
            true
        }
    }

    private fun setFragment(tag : String, fragment: Fragment) {
        val manager : FragmentManager = supportFragmentManager
        val bt = manager.beginTransaction()

        /*if (manager.findFragmentByTag(tag) == null) {
            bt.add(R.id.fragment_content, fragment, tag).addToBackStack(null)
        }*/
        val existingFragment = manager.findFragmentByTag(tag)

        if (existingFragment == null) {
            // Fragment가 스택에 없으면 add를 사용하여 추가하고 백 스택에 추가
            bt.add(R.id.fragment_content, fragment, tag).addToBackStack(null)
        } else {
            // Fragment가 스택에 있으면 replace를 사용하여 교체
            bt.replace(R.id.fragment_content, fragment, tag)
        }

        val home = manager.findFragmentByTag(TAG_HOME)
        val statistics = manager.findFragmentByTag(TAG_STATISTICS)
        val account = manager.findFragmentByTag(TAG_ACCOUNT)
        val memorials = manager.findFragmentByTag(TAG_MEMORIALS)

        if (home != null) {
            bt.hide(home)
        }
        if (statistics != null) {
            bt.hide(statistics)
        }
        if (account != null) {
            bt.hide(account)
        }
        if (memorials != null) {
            bt.hide(memorials)
        }


        if (tag == TAG_HOME) {
            if (home != null) {
                bt.show(home)
            }
        }
        else if (tag == TAG_STATISTICS) {
            if (statistics != null) {
                bt.show(statistics)
            }
        }
        else if (tag == TAG_ACCOUNT) {
            if (account != null) {
                bt.show(account)
            }
        }

        bt.commitAllowingStateLoss()
    }

}