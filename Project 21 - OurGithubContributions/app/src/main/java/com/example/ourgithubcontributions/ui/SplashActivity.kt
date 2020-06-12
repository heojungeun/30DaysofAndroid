package com.example.ourgithubcontributions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.SharedPreferenceManager

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (SharedPreferenceManager.token == "-1"){
            // 처음 앱 들어왔을때
        }else{
            // 자동 로그인하기
        }
    }

}