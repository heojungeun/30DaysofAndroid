package com.example.customfont

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customfont.databinding.ActivityFontinfoBinding

class InfoActivity : AppCompatActivity(){

    private lateinit var bind : ActivityFontinfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_fontinfo)


    }
}