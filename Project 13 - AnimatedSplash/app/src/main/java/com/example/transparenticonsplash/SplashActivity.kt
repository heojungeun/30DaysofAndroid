package com.example.transparenticonsplash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.transparenticonsplash.SplashView.ISplashListener
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity: AppCompatActivity() {

    private val SPLASH_TIME = 100L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            splash_view.splashAndDisappear(object : ISplashListener {
                override fun onStart() {}
                override fun onUpdate(completionFraction: Float) {}
                override fun onEnd() {
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                    finish()
                    //overridePendingTransition(0,android.R.anim.fade_out)
                }
            })

        },SPLASH_TIME)
        // run the animation and listen to the animation events (listener can be left as null)

    }

    override fun onBackPressed() {

    }
}