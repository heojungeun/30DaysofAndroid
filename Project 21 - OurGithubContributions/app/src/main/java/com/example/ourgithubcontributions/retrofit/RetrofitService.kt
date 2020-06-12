package com.example.ourgithubcontributions.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {

    @GET("users/{user}/contributions")
    fun getContributions(@Path("user") user: String?): Call<String>

}