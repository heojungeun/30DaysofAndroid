package com.example.ourgithubcontributions.contributions

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {

    @GET("/users/{user}/contributions")
    fun getContrib(@Path("user") user: String?): Call<String>

}