package com.example.timepatterntimer

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.tankery.lib.circularseekbar.CircularSeekBar
import me.tankery.lib.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0 // 0.01초마다 1씩 오르는 변수
    private var isRuned = false // 스톱워치 실행이 되면 true가 됨
    private var isTune = false
    private var timerTask: Timer? = null // Timer객체를 가리키는 참조변수
    private var pickTime: Int = 0 // 선택한 시간 저장 변수

    private val vm = StateViewModel()

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.bbibbi)
        onClickListener()
    }

    private fun onClickListener() {
        seekbar.setOnSeekBarChangeListener(object : OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar,
                progress: Float,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    var sec = progress.toInt()
                    var min = sec / 60
                    var hour = min / 60
                    min %= 60
                    if (hour == 1)
                        min = 60
                    textTime.text = String.format("%02d:00", min)
                }
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar) {}

            override fun onStartTrackingTouch(seekBar: CircularSeekBar) {}
        })

        cancelbtn.setOnClickListener {
            // 취소버튼 눌렀을 때,
            reset()
        }

        pausebtn.setOnClickListener {
            // 시작하고나서 일시정지 버튼 눌렀을 때,
            pausebtn.visibility = View.GONE
            timerTask?.cancel()
        }

        startbtn.setOnClickListener {
            if (!isRuned) { // 시작버튼 처음눌렀을 때
                pickTime = textTime.text.split(":")[0].toInt() * 60
                seekbar.progress = pickTime.toFloat()
                if (pickTime == 0) {
                    Toast.makeText(this, "slide the seekbar", Toast.LENGTH_SHORT).show()
                } else {
                    isRuned = true
                    seekbar.isEnabled = false
                }
            }
            if (pickTime != 0) {
                pausebtn.visibility = View.VISIBLE
                timerTask = timer(period = 10) {
                    time++
                    val sec_sub = time / 100 // 1초가 지나면 time은 100이되는데 이 때 sec은 1이 됨
                    var sec_tmp = pickTime - sec_sub
                    if (sec_tmp == 0) {
                        runOnUiThread {
                            reset()
                            timeoverlayout.visibility = View.VISIBLE
                            mediaPlayer?.start()
                            isTune = true
                        }
                    }
                    val min_tmp = (sec_tmp / 60) % 60
                    sec_tmp %= 60
                    // runOnUiThread메소드 호출 (ui관련 객체를 전달) <sam변환>
                    // 힘든 작업을 할 때는 워커 스레드에서 작용하는데 timer함수가 그렇다.
                    // 워커 스레드 작업 중에는 ui변경이 불가능하다. 하지만 runOnUiThread에 전달되는 클래스는 워커 스레드에서 벗어난다.
                    runOnUiThread {
                        if (isRuned){
                            textTime.text = String.format("%02d:%02d", min_tmp, sec_tmp)
                            seekbar.progress -= 0.008f
                        }
                    }
                }
            }

        }

        overbtn.setOnClickListener {
            isTune = false
            mediaPlayer?.release()
            //mediaPlayer.reset()
            timeoverlayout.visibility = View.GONE
        }

        setupbtn.setOnClickListener {
            var intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun reset() {
        timerTask?.cancel()
        time = 0
        textTime.text = "00:00"
        seekbar.progress = 0f
        isRuned = false
        pausebtn.visibility = View.GONE
        seekbar.isEnabled = true
    }

}
