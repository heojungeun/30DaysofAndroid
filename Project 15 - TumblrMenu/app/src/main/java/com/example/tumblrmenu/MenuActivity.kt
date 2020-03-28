package com.example.tumblrmenu

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val animtransRightfar = AnimationUtils.loadAnimation(this,R.anim.trans_rightfar)
        val animtransLeftfar = AnimationUtils.loadAnimation(this,R.anim.trans_leftfar)
        val animtransRightmid = AnimationUtils.loadAnimation(this,R.anim.trans_rightmid)
        val animtransLeftmid = AnimationUtils.loadAnimation(this,R.anim.trans_leftmid)
        val animtransRights = AnimationUtils.loadAnimation(this,R.anim.trans_right)
        val animtransLefts = AnimationUtils.loadAnimation(this,R.anim.trans_left)

        chatbtn.startAnimation(animtransRights)
        quotebtn.startAnimation(animtransRightmid)
        textbtn.startAnimation(animtransRightfar)

        videobtn.startAnimation(animtransLefts)
        linkbtn.startAnimation(animtransLeftmid)
        photobtn.startAnimation(animtransLeftfar)

        backbtn.setOnClickListener {

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

        }

    }
}
