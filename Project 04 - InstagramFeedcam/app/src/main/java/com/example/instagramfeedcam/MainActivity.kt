package com.example.instagramfeedcam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        camerabtn.setOnClickListener{

            var intent = Intent(this,CameraActivity::class.java)
            startActivityForResult(intent, 100)

        }
    }
}
