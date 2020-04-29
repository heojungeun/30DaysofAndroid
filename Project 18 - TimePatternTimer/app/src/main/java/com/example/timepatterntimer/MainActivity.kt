package com.example.timepatterntimer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.tankery.lib.circularseekbar.CircularSeekBar
import me.tankery.lib.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time=0 // 0.01초마다 1씩 오르는 변수
    private var isRunning=false // 스톱워치 실행이 되면 true가 됨
    private var timerTask: Timer?=null // Timer객체를 가리키는 참조변수
    private var lap=1 // 저장이 몇 개 됐는지 알리는 변수
    private var lastTimeBackPressed:Long=-1500 // 이전에 버튼을 눌렀을 때 시간

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
                if(hour==1)
                    min = 60
                textTime.text = String.format("%02d:00", min)
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar) {}
        })

        cancelbtn.setOnClickListener {
            textTime.text = "00:00"
            seekbar.progress = 0F
        }

        pausebtn.setOnClickListener {
            timerTask?.cancel()
        }

        restartbtn.setOnClickListener {

        }

        // TODO 스타트 버튼 restart랑 합치게 변수들 내보내기
        startbtn.setOnClickListener {
            pausebtn.visibility = View.VISIBLE
            var curTime = textTime.text.split(":")
            var sec = curTime[0].toInt()*60
            timerTask=timer(period=10){
                time++
                val sec_sub = time/100 // 1초가 지나면 time은 100이되는데 이 때 sec은 1이 됨
                val sec_tmp = sec - sec_sub
                val min_tmp = (sec/60)%60
                // runonuithread메소드 호출 (ui관련 객체를 전달) <sam변환>
                // 힘든 작업을 할 때는 워커 스레드에서 작용하는데 timer함수가 그렇다.
                // 워커 스레드 작업 중에는 ui변경이 불가능하다. 하지만 runonuithread에 전달되는 클래스는 워커 스레드에서 벗어난다.
                runOnUiThread{
                    textTime.text = String.format("%02d:%02d", min_tmp, sec_tmp)
                }
            }
        }

        setupbtn.setOnClickListener {
            var intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
        }
    }
}
