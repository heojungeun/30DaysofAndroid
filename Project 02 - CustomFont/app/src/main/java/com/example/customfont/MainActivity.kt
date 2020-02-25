package com.example.customfont

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.customfont.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind : ActivityMainBinding

    private val fontlist : List<Int> = listOf(
        R.font.sdkukdetopokkialt,
        R.font.neodgm,
        R.font.mapogoldenpier,
        R.font.gmarketsanslight,
        R.font.bmeuljiro,
        R.font.binggraebold,
        R.font.binggraemelona,
        R.font.binggraetaom,
        R.font.binggraetwobold
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var typeface : Typeface? = ResourcesCompat.getFont(this.applicationContext, R.font.binggraetwobold)
        bind.editText.setTypeface(typeface, Typeface.NORMAL)

    }


}
