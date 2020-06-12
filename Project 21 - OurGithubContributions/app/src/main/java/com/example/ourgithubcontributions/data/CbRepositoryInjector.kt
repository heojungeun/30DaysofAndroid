package com.example.ourgithubcontributions.data

import android.app.Application

object CbRepositoryInjector {
    fun getCbRepositoryImpl() : CbRepositoryImpl {
        return CbRepositoryImpl.getInstance()
    }
}