package com.example.customfont

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.util.Linkify
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customfont.databinding.ActivityFontinfoBinding
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import java.util.regex.Matcher
import java.util.regex.Pattern


class InfoActivity : AppCompatActivity(){

    private lateinit var bind : ActivityFontinfoBinding

    private val linklist : List<String> = listOf(
        "http://kukde.co.kr/?page_id=627",
        "https://github.com/Dalgona/neodgm",
        "https://www.mapo.go.kr/site/main/content/mapo04010201",
        "http://company.gmarket.co.kr/company/about/company/company--font.asp",
        "https://www.woowahan.com/#/fonts",
        "http://www.bingfont.co.kr/bingfont.html"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_fontinfo)

        val mTransform = object : Linkify.TransformFilter {
            override fun transformUrl(match: Matcher, url: String): String {
                return ""
            }
        }

        val patternlink : Pattern = Pattern.compile("Link")

        Linkify.addLinks(bind.font1, patternlink, linklist[0],null,mTransform)
        Linkify.addLinks(bind.font2, patternlink, linklist[1],null,mTransform)
        Linkify.addLinks(bind.font3, patternlink, linklist[2],null,mTransform)
        Linkify.addLinks(bind.font4, patternlink, linklist[3],null,mTransform)
        Linkify.addLinks(bind.font5, patternlink, linklist[4],null,mTransform)
        Linkify.addLinks(bind.font6, patternlink, linklist[5],null,mTransform)

        bind.backbtn.setOnClickListener{
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}