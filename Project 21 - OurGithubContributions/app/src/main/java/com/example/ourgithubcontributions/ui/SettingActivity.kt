package com.example.ourgithubcontributions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourgithubcontributions.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setClickListener()
    }

    private fun setClickListener(){
        btn_back.setOnClickListener {
            finish()
        }
    }
}