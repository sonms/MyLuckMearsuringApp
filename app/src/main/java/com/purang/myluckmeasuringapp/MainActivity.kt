package com.purang.myluckmeasuringapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.purang.myluckmeasuringapp.bottom_navigation.AccountFragment
import com.purang.myluckmeasuringapp.bottom_navigation.HomeFragment
import com.purang.myluckmeasuringapp.bottom_navigation.MemorialsFragment
import com.purang.myluckmeasuringapp.bottom_navigation.StatisticsFragment
import com.purang.myluckmeasuringapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding

    private val TAG_HOME = "home_fragment"
    private val TAG_STATISTICS = "statistics_fragment"
    private val TAG_MEMORIALS = "memorials_fragment"
    private val TAG_ACCOUNT= "account_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initNavigationBar()
    }

    private fun initNavigationBar() {
        mBinding.bottomNavigationView.
        setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    /*oldFragment = HomeFragment()
                    oldTAG = TAG_HOME*/
                    //setToolbarView(TAG_HOME, oldTAG)
                    setFragment(TAG_HOME, HomeFragment())
                   /* toolbarType = "기본"
                    setToolbarView(toolbarType)*/
                    /*isClicked = false
                    isSettingClicked = false*/
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
                    setFragment(TAG_STATISTICS, StatisticsFragment())
                }

                R.id.navigation_memorials -> {
                    /*val intent = Intent(this, NoticeBoardEditActivity::class.java).apply {
                        putExtra("type","ADD")
                    }
                    requestActivity.launch(intent)*/
                    setFragment(TAG_MEMORIALS, MemorialsFragment())
                    /*oldFragment = HomeFragment()
                    oldTAG = TAG_HOME
                    //setToolbarView(TAG_HOME, oldTAG)
                    setFragment(TAG_HOME, HomeFragment())
                    setToolbarView(false)
                    isClicked = false
                    isSettingClicked = false*/
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
                    setFragment(TAG_ACCOUNT, AccountFragment())
                }


                else -> {

                    setFragment(TAG_HOME, HomeFragment())
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