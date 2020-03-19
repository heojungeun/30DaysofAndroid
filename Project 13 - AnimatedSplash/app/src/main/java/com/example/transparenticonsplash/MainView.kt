package com.example.transparenticonsplash

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.view.*

class MainView: FrameLayout {

    constructor(context: Context):super(context){
        initialize()
    }

    private lateinit var mSplashView: SplashView

    fun initialize(){
        var context = getContext()
        mSplashView = SplashView(context);
        mSplashView.setDuration(1000); // the animation will last 0.5 seconds
        mSplashView.setBackgroundColor(Color.WHITE); // transparent hole will look white before the animation
        mSplashView.setIconColor(Color.rgb(23, 169, 229)); // this is the Twitter blue color
        mSplashView.setIconResource(R.drawable.ictwitter3); // a Twitter icon with transparent hole in it
        mSplashView.setRemoveFromParentOnEnd(true); // remove the SplashView from MainView once animation is completed

        // add the view
        addView(mSplashView);
    }

    fun getSplashView(): SplashView{
        return mSplashView
    }

    fun unsetSplashView(){
        mSplashView.visibility = View.GONE
    }
}