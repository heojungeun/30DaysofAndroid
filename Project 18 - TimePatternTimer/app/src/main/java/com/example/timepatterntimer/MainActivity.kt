package com.example.timepatterntimer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.tankery.lib.circularseekbar.CircularSeekBar
import me.tankery.lib.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekbar.setOnSeekBarChangeListener(object : OnCircularSeekBarChangeListener {

            var finalProgress: String = "00:00:00"
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar,
                progress: Float,
                fromUser: Boolean
            ) {
                var sec = progress.toInt()
                var min = sec/60
                var hour = min/60
                min %= 60
                textTime.text = String.format("%02d:%02d", hour,min)
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar) {}
        })

        cancelbtn.setOnClickListener {
            textTime.text = "00:00:00"
        }

        startbtn.setOnClickListener {
            
        }

        setupbtn.setOnClickListener {
            var intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
        }
    }
}
