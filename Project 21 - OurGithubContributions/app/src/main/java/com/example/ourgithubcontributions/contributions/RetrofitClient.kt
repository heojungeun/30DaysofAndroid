package com.example.ourgithubcontributions.contributions

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private var instance: Retrofit? = null
    //private val gson = GsonBuilder().setLenient().create()

    fun getInstance(): Retrofit {
        if (instance == null){
            instance = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://github.com/")
                .build()
        }

        return instance!!
    }
}