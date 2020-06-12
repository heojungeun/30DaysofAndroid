package com.example.ourgithubcontributions

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    companion object {
        private var instance: MyApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        SharedPreferenceManager.init(this)
        super.onCreate()
    }
}