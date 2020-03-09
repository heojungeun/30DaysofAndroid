package com.example.randomcolormusic

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val colors = IntArray(5)
    private val rnd = java.util.Random()
    private var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var msPlayer: MediaPlayer? = MediaPlayer.create(this,R.raw.flashback)



        mbtn.setOnClickListener {
            randomColor()
            if (cnt == 50){
                // music pause
                msPlayer?.pause()
                cnt = 0
            }
            else{
                if(cnt == 0){
                    // music play
                    msPlayer?.start()
                }
                cnt += 1
            }

            progress.progress = cnt
        }
    }

    private fun randomColor(){
        for (i in 0..4)
            colors[i] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),rnd.nextInt(256))

        val gd = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)

        gd.cornerRadius = 3f
        backgroud_layout.background = gd
    }
}
