package com.example.timepatterntimer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class StateViewModel {

    var breakTime: Int = 0
    var patternNum: Int = 0

//    private val _breakTime = MutableLiveData<Int>()
//    val breakTime: LiveData<Int> get() = _breakTime
//
//    private val _patternNum = MutableLiveData<Int>()
//    val patternNum: LiveData<Int> get() = _patternNum
//
//    fun getBTString(): String{
//        return "$_breakTime 분"
//    }
//
//    fun getPNString(): String{
//        return "$_patternNum 번"
//    }

    fun setBreak(n: Int){
        breakTime = n
    }

    fun setPattern(n: Int){
        patternNum = n
    }

}