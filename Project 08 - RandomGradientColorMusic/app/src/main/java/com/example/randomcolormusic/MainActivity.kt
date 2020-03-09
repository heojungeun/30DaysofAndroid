package com.example.randomcolormusic

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val colors = IntArray(5)
    private val rnd = java.util.Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        randomColor()

        mbtn.setOnClickListener {
            randomColor()
        }
    }

    fun randomColor(){
        for (i in 0..4)
            colors[i] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),rnd.nextInt(256))

        val gd = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)

        gd.cornerRadius = 3f
        backgroud_layout.background = gd
    }
}
