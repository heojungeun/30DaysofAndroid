package com.example.slidemenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isMenuOpen = false
    private lateinit var tDownAnim : Animation
    private lateinit var tUpAnim : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tDownAnim = AnimationUtils.loadAnimation(this,R.anim.translate_down)
        tUpAnim = AnimationUtils.loadAnimation(this,R.anim.translate_up)

        val animListener = SlidingMenuAnimationListener()

        tDownAnim.setAnimationListener(animListener)
        tUpAnim.setAnimationListener(animListener)

        menubtn.setOnClickListener {
            if (isMenuOpen){ // 메뉴를 닫을 때
                totalpage.startAnimation(tUpAnim)
            }else{ // 메뉴를 열 때
                hiddenMenu.visibility = View.VISIBLE
                totalpage.startAnimation(tDownAnim)
            }
        }

    }

    inner class SlidingMenuAnimationListener : Animation.AnimationListener{

        override fun onAnimationStart(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {
            if (isMenuOpen){
                hiddenMenu.visibility = View.GONE
                isMenuOpen = false
            }else{
                isMenuOpen = true
            }
        }

        override fun onAnimationRepeat(animation: Animation?) {

        }
    }
}
