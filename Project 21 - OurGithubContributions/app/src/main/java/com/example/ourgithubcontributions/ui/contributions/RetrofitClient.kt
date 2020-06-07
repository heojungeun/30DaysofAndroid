package com.example.ourgithubcontributions.ui.contributions

import com.example.ourgithubcontributions.R
import retrofit2.Retrofit
import java.lang.NullPointerException

class RetrofitClient{

    companion object {
        private val retrofitClient: RetrofitClient = RetrofitClient()

        fun getInstance() : RetrofitClient {
            return retrofitClient
        }
    }

    fun buildRetrofit() : RetrofitService? {
        val retrofit: Retrofit? = Retrofit.Builder()
            .baseUrl(R.string.baseUrl.toString())
            .build()

        return if (retrofit != null) {
            val service: RetrofitService = retrofit.create(RetrofitService::class.java)
            service
        }else{
            null
        }
    }

}