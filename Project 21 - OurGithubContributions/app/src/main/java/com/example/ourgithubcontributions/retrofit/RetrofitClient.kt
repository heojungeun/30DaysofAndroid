package com.example.ourgithubcontributions.retrofit

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private const val URL = "https://github.com/"

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(ScalarsConverterFactory.create())

    private val retrofit = builder.build()

    fun <S> createService(serviceClass: Class<S>?): S {
        return retrofit.create(serviceClass)
    }
}