package com.purang.myluckmeasuringapp.bottom_navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.purang.myluckmeasuringapp.Helper.SharedPreferences
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.database.ResultDatabase
import com.purang.myluckmeasuringapp.database.ResultDatabase.Companion.clearAllTables
import com.purang.myluckmeasuringapp.databinding.FragmentHomeBinding
import com.purang.myluckmeasuringapp.game_activity.DiceActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var binding : FragmentHomeBinding? = null

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
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding?.homeStartBtn?.setOnClickListener {
            val intent = Intent(requireActivity(), DiceActivity::class.java)
            startActivity(intent)
        }

        /*val sharedPreferences = SharedPreferences()
        sharedPreferences.clear(requireContext())
        // ResultDatabase 인스턴스 가져오기
        val db: ResultDatabase? = ResultDatabase.getInstance(requireContext())

        // 백그라운드 스레드에서 clearAllTables() 호출
        CoroutineScope(Dispatchers.IO).launch {
            // 데이터베이스 테이블 초기화
            db?.clearAllTables()
        }*/

        return binding?.root
    }

    override fun onPause() {
        super.onPause()
        binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}