package com.example.transparenticonsplash

import android.content.Context
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout

class ContentView : androidx.appcompat.widget.AppCompatImageView{

    constructor(context: Context):super(context){
        initialize()
    }

    fun initialize(){
        setImageResource(R.drawable.capture)
    }
}