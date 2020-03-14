package com.example.loginanimation

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.backbtn
import kotlinx.android.synthetic.main.activity_signup.edtxtid
import kotlinx.android.synthetic.main.activity_signup.edtxtpw

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val animtransRights = AnimationUtils.loadAnimation(this,R.anim.translate_right)
        val animShake = AnimationUtils.loadAnimation(this,R.anim.shake)
        edtxtid.startAnimation(animtransRights)
        edtxtpw.startAnimation(animtransRights)
        signupbtn.startAnimation(animtransRights)

        backbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }

        signupbtn.setOnClickListener {
            if (edtxtid.text.isBlank() || edtxtpw.text.isBlank()){
                signupbtn.startAnimation(animShake)
            }
            else{
                Toast.makeText(this,"you sign up!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}