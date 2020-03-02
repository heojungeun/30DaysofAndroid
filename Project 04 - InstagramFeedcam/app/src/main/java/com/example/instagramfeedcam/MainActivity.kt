package com.example.instagramfeedcam

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import android.transition.Slide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(window) {
                requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
                // set an slide transition
                enterTransition = Slide().also {
                    it.slideEdge = Gravity.START
                }
                exitTransition = Slide().also {
                    it.slideEdge = Gravity.END
                }
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        camerabtn.setOnClickListener{

            var intent = Intent(this,CameraActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

        }
    }
}
