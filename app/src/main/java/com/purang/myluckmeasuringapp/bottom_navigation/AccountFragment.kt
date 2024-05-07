package com.purang.myluckmeasuringapp.bottom_navigation

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat.recreate
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.purang.myluckmeasuringapp.Helper.ThemeHelper
import com.purang.myluckmeasuringapp.R
import com.purang.myluckmeasuringapp.databinding.FragmentAccountBinding
import com.purang.myluckmeasuringapp.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentAccountBinding
    private var preNickName : String? = null

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
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        val sharedPref = requireActivity().getSharedPreferences("saveData", Context.MODE_PRIVATE)
        preNickName = sharedPref.getString("saveNickname", "") ?: ""
        Log.e("prenickname", preNickName.toString())
        if (preNickName != null) {
            binding.accountNickname.text = preNickName
        } else {
            binding.accountNickname.text = "유저1"
        }

        binding.accountNickname.setOnClickListener {
            val builder : AlertDialog.Builder = AlertDialog.Builder(context)
            val input = EditText(context) // EditText 생성

            // AlertDialog에 EditText 추가
            builder.setView(input)

            val ad : AlertDialog = builder.create()

            builder.setTitle("닉네임 설정")
            //builder.setMessage("정말로 삭제하시겠습니까?")

            builder.setNegativeButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                    val nickname = input.text.toString() // EditText에서 텍스트 가져오기
                    //isFirstAccountEdit = sharedPref.getString("isFirstAccountEdit", "") ?: ""

                    with(sharedPref.edit()) {
                        putString("saveNickname", nickname)
                        apply() // 비동기적으로 데이터를 저장
                    }
                    binding.accountNickname.text = nickname
                    ad.dismiss()
                })

            builder.setPositiveButton("취소",
                DialogInterface.OnClickListener { dialog, which ->
                    ad.dismiss()
                })
            builder.show()
        }

        binding.accountLicense.setOnClickListener {
            val intent = Intent(requireActivity(), OssLicensesMenuActivity::class.java)

            startActivity(intent)

            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이선스")
        }

        binding.accountSwitch.setOnClickListener {
            // 현재 선택된 테마 인덱스 가져오기
            val currentThemeIndex = getCurrentThemeIndex(requireContext())

            // 테마 토글하기
            val newThemeIndex = if (currentThemeIndex == 0) 1 else 0

            // 테마 저장
            ThemeHelper.saveThemeSelection(requireContext(), newThemeIndex)

            // 액티비티 다시 시작
            recreate(requireActivity())
        }

        return binding.root
    }
    // 현재 선택된 테마 인덱스 반환
    private fun getCurrentThemeIndex(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(ThemeHelper.PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(ThemeHelper.PREF_SELECTED_THEME, 0)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}