package com.purang.myluckmeasuringapp.bottom_navigation

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.purang.myluckmeasuringapp.BuildConfig
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.adapter.MemorialsAdapter
import com.purang.myluckmeasuringapp.dao.GameResultEntity
import com.purang.myluckmeasuringapp.database.ResultDatabase
import com.purang.myluckmeasuringapp.databinding.FragmentAccountBinding
import com.purang.myluckmeasuringapp.databinding.FragmentMemorialsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MemorialsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MemorialsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentMemorialsBinding
    private var adapter : MemorialsAdapter? = null
    private lateinit var gameData : List<GameResultEntity>
    private var db: ResultDatabase? = null
    private var adRequest : AdRequest? = null
    private var adId = BuildConfig.banner_ads_id
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
        binding = FragmentMemorialsBinding.inflate(inflater, container, false)
        db = ResultDatabase.getInstance(requireContext())
        initAd()
        initRecyclerView()


        return binding.root
    }

    private fun initRecyclerView() {
        CoroutineScope(Dispatchers.Main).launch {
            gameData = withContext(Dispatchers.IO) {
                db!!.resultDao().getAll().reversed()
            }
            if (gameData.isNotEmpty()) {
                binding.nestedScrollView.visibility = View.VISIBLE
                binding.nonData.visibility = View.GONE
            } else {
                binding.nestedScrollView.visibility = View.GONE
                binding.nonData.visibility = View.VISIBLE
            }

            adapter = MemorialsAdapter(gameData)
            binding.memorialRv.adapter = adapter
            binding.memorialRv.setHasFixedSize(true)
            binding.memorialRv.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initAd() {
        adRequest = AdRequest.Builder().build()
       /* binding.memoAd.adSize.apply {
             AdSize.BANNER
        }
        binding.memoAd.adUnitId = adId*/
        binding.memoAd.loadAd(adRequest!!)
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
         * @return A new instance of fragment MemorialsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MemorialsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}