package com.example.videobackground

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        try {
            Thread.sleep(1000)
        }catch (e:InterruptedException){
            e.printStackTrace()
        }
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}