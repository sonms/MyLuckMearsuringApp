package com.purang.myluckmeasuringapp.bottom_navigation

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.purang.myluckmeasuringapp.BottomAdFragment
import com.purang.myluckmeasuringapp.BuildConfig
import com.purang.myluckmeasuringapp.Helper.SharedPreferences
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.FragmentStatisticsBinding
import dalvik.system.ZipPathValidator.setCallback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatisticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatisticsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentStatisticsBinding
    private var adRequest : AdRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        initDataSet()

        return binding.root
    }

    private fun initDataSet() {
        val sp = SharedPreferences()

        val dice1 = sp.getDice(requireContext(), 1)
        val dice2 = sp.getDice(requireContext(), 2)
        val dice3 = sp.getDice(requireContext(), 3)
        val dice4 = sp.getDice(requireContext(), 4)
        val dice5 = sp.getDice(requireContext(), 5)
        val dice6 = sp.getDice(requireContext(), 6)

        val rouletteW = sp.getRoulette(requireContext(), "win")
        val rouletteL = sp.getRoulette(requireContext(), "lose")

        val sniW = sp.getSniffling(requireContext(), "win")
        val sniL = sp.getSniffling(requireContext(), "lose")

        val dlW = sp.getDrawLots(requireContext(), "win")
        val dlL = sp.getDrawLots(requireContext(), "lose")

        val jG = sp.getJelly(requireContext(), "gold")
        val jS = sp.getJelly(requireContext(), "silver")
        val jB = sp.getJelly(requireContext(), "bronze")

        binding.dice1Tv.text = dice1.toString()
        binding.dice2Tv.text = dice2.toString()
        binding.dice3Tv.text = dice3.toString()
        binding.dice4Tv.text = dice4.toString()
        binding.dice5Tv.text = dice5.toString()
        binding.dice6Tv.text = dice6.toString()

        binding.rouletteWinTv.text = rouletteW.toString()
        binding.rouletteLoseTv.text = rouletteL.toString()

        binding.snifflingWinTv.text = sniW.toString()
        binding.snifflingLoseTv.text = sniL.toString()

        binding.drawsLotsWinTv.text = dlW.toString()
        binding.drawLotsLoseTv.text = dlL.toString()

        binding.jellyGoldTv.text = jG.toString()
        binding.jellySilverTv.text = jS.toString()
        binding.jellyBronzeTv.text = jB.toString()
    }

    private fun initAd() {
        adRequest = AdRequest.Builder().build()
        //binding.statisAd.adUnitId = BuildConfig.banner_ads_id
        binding.statisAd.loadAd(adRequest!!)
    }
    override fun onResume() {
        super.onResume()
        loadAdIfNeeded()
    }

    override fun onPause() {
        super.onPause()
        stopAdLoading()
    }

    private fun loadAdIfNeeded() {
        if (isScreenOn()) {
            initAd()
        }
    }

    private fun stopAdLoading() {
        adRequest = null
    }

    private fun isScreenOn(): Boolean {
        val powerManager = requireActivity().getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isInteractive
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatisticsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatisticsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}