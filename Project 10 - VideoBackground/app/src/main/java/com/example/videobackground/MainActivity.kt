package com.example.videobackground

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mMediaPlayer: MediaPlayer
    private var mcrtVideoPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var uri = Uri.parse("android.resource://" +
                packageName + "/" + R.raw.vb02)

        videobg.setVideoURI(uri)
        videobg.start()

        videobg.setOnPreparedListener(object : MediaPlayer.OnPreparedListener{
            override fun onPrepared(mp: MediaPlayer) {
                mMediaPlayer = mp
                mMediaPlayer.isLooping = true
                if (mcrtVideoPosition != 0){
                    mMediaPlayer.seekTo(mcrtVideoPosition)
                    mMediaPlayer.start()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        mcrtVideoPosition = mMediaPlayer.currentPosition
        videobg.pause()
    }

    override fun onResume() {
        super.onResume()
        videobg.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer.release()
    }
}
