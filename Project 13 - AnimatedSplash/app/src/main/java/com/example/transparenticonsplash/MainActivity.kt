package com.example.transparenticonsplash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.transparenticonsplash.SplashView.ISplashListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val DO_XML = false

    private var mMainView: ViewGroup? = null
    private var mSplashView: SplashView? = null
    private var mContentView: View? = null
    private val mHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // change the DO_XML variable to switch between code and xml
        // change the DO_XML variable to switch between code and xml
        if (DO_XML) {
            // inflate the view from XML and then get a reference to it
            setContentView(R.layout.activity_main)
            mMainView = main_view as ViewGroup
            mSplashView = splash_view as SplashView
        } else {
            // create the main view and it will handle the rest
            mMainView = MainView(applicationContext)
            mSplashView = (mMainView as MainView).getSplashView()
            setContentView(mMainView)
        }

        // pretend like we are loading data
        startLoadingData()


    }

    private fun startLoadingData() {
        // finish "loading data" in a random time between 1 and 3 seconds
        val random = Random()
        var delaynum = 1000L
        Handler().postDelayed({
            onLoadingDataEnded()
        }, delaynum)
    }

    private fun onLoadingDataEnded() {
        val context: Context = applicationContext
        // now that our data is loaded we can initialize the content view
        mContentView = ContentView(context)
        // add the content view to the background
        mMainView!!.addView(mContentView, 0)

        // start the splash animation
        mSplashView!!.splashAndDisappear(object : ISplashListener {
            override fun onStart() {
                // log the animation start event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash started")
                }
            }

            override fun onUpdate(completionFraction: Float) {
                // log animation update events
                if (BuildConfig.DEBUG) {
                    Log.d(
                        TAG,
                        "splash at " + String.format(
                            "%.2f",
                            completionFraction * 100
                        ) + "%"
                    )
                }
            }

            override fun onEnd() {
                // log the animation end event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash ended")
                }
                // free the view so that it turns into garbage
                mSplashView = null
                if (!DO_XML) {
                    // if inflating from code we will also have to free the reference in MainView as well
                    // otherwise we will leak the View, this could be done better but so far it will suffice
                    (mMainView as MainView).unsetSplashView()
                }
            }
        })


    }
}
