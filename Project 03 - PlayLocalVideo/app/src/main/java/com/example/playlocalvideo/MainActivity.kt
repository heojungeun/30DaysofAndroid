package com.example.playlocalvideo

import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mVideoModel = arrayListOf<VideoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mAdapter = VideoRecyclerAdapter(this, mVideoModel)
        rcView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        rcView.layoutManager = lm
        rcView.setHasFixedSize(true)

        getVideo()
        mAdapter.setUp(mVideoModel)

    }

    private fun getVideo(){
        TODO() // 로컬 스토리지에 있는 동영상들을 불러와 mVideoModel 리스트에 저장할 것
    }

}
