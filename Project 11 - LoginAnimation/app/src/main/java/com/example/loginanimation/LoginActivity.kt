package com.example.loginanimation

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val animtransRights = AnimationUtils.loadAnimation(this,R.anim.translate_right)
        val animShake = AnimationUtils.loadAnimation(this,R.anim.shake)
        edtxtid.startAnimation(animtransRights)
        edtxtpw.startAnimation(animtransRights)
        loginbtn.startAnimation(animtransRights)

        backbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }

        loginbtn.setOnClickListener {
            if (edtxtid.text.isBlank() || edtxtpw.text.isBlank()){
                loginbtn.startAnimation(animShake)
            }
            else{
                Toast.makeText(this,"you login!",Toast.LENGTH_SHORT).show()
            }
        }

    }
}