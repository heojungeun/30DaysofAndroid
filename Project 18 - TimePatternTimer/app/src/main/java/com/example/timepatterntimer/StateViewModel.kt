package com.example.timepatterntimer

class StateViewModel {

    var breakTime: Int = 0
    var patternNum: Int = 0

    fun getBTString(): String{
        return "$breakTime 분"
    }

    fun getPNString(): String{
        return "$patternNum 번"
    }

    fun setBreak(n: Int){
        breakTime = n
    }

    fun setPattern(n: Int){
        patternNum = n
    }

}