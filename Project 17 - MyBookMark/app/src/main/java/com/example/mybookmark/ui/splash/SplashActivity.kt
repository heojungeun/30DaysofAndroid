package com.example.mybookmark.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.mybookmark.ui.main.MainActivity

class SplashActivity: Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try{
            Thread.sleep(2000)
        }catch (e: InterruptedException){
            e.printStackTrace()
        }
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}