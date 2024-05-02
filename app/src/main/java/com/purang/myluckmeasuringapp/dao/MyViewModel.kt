package com.purang.myluckmeasuringapp.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    // remainingChance를 저장하기 위한 MutableLiveData
    private val _remainingChanceLiveData = MutableLiveData<Int>()
    val remainingChanceLiveData: LiveData<Int>
        get() = _remainingChanceLiveData

    // 초기값 설정
    init {
        _remainingChanceLiveData.value = 0 // 초기값은 0으로 설정할 수 있습니다.
    }

    // remainingChance 값을 업데이트하는 메서드
    fun updateRemainingChance(newRemainingChance: Int) {
        _remainingChanceLiveData.value = newRemainingChance
    }
}