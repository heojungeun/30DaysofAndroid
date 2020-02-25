package com.example.customfont

import android.content.Intent
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

    private val listsize : Int = fontlist.size
    private var fidx : Int = 0


    private lateinit var mtext : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)


        bind.changeBtn.setOnClickListener{
            changeFont(fidx++)
        }

        bind.infobtn.setOnClickListener{

            val intent = Intent(this@MainActivity, InfoActivity::class.java)
            startActivityForResult(intent,100)
        }
    }

    fun changeFont(index : Int) {

        var fontindex = index
        if (fontindex == listsize)
            fontindex = 0

        var typeface = ResourcesCompat.getFont(this.applicationContext, fontlist.get(fontindex))
        bind.editText.setTypeface(typeface, Typeface.NORMAL)
        bind.changeBtn.setTypeface(typeface, Typeface.NORMAL)
        bind.infobtn.setTypeface(typeface, Typeface.NORMAL)

    }



}
