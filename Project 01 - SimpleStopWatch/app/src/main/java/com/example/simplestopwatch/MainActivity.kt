package com.example.simplestopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.simplestopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    private lateinit var bind : ActivityMainBinding

    private var time=0
    private var timerTask: Timer?=null
    private var isRunning=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bind = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        bind.resetbtn.setOnClickListener{
            reset()
        }

        bind.playbtn.setOnClickListener{
            start()
        }

        bind.pausebtn.setOnClickListener{
            pause()
        }
    }

    fun start(){
        timerTask = timer(period = 10){// 0.1초에 한 번씩 호출
            time++
            val sec = time/100
            val milli = time%100
            val min = sec / 60

            runOnUiThread{
                bind.seconds.text = "$sec"
                bind.millisec.text = "$milli"
                bind.minute.text = "$min"
            }
        }
    }

    fun pause(){
        timerTask?.cancel()
    }

    fun reset(){
        timerTask?.cancel()

        if (isRunning)
            isRunning = false

        time= 0

        bind.seconds.text = "00"
        bind.millisec.text = "00"
        bind.minute.text = "00"
    }


//    fun onResetClick(view: View){
//        reset()
//    }
//
//    fun onPlayClick(view: View){
//        start()
//    }
//
//    fun onPauseClick(view: View){
//        pause()
//    }
}
