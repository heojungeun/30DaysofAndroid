package com.example.ourgithubcontributions.data

import android.app.Application

object CbRepositoryInjector {
    fun getCbRepositoryImpl(application: Application) : CbRepositoryImpl {
        return CbRepositoryImpl.getInstance(application)
    }
}