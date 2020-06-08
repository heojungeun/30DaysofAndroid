package com.example.ourgithubcontributions.contributions

import android.content.Context
import com.example.ourgithubcontributions.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContributionsModel : ContributionsInterface.Model{

    companion object {
        private val retrofitClient: ContributionsModel = ContributionsModel()

        fun getInstance() : ContributionsModel {
            return retrofitClient
        }
    }

    override fun buildRetrofit() : RetrofitService? {
        val retrofit: Retrofit? = Retrofit.Builder()
            .baseUrl("https://github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return if (retrofit != null) {
            val service: RetrofitService = retrofit.create(RetrofitService::class.java)
            service
        }else{
            null
        }
    }

    override fun getUserName(context: Context): String {
        val sp = context.getSharedPreferences("github_contributions", Context.MODE_PRIVATE)
        return sp.getString("username", "") ?: ""
    }

    override fun saveUserName(context: Context, userName: String) {
        val editor = context.getSharedPreferences("github_contributions", Context.MODE_PRIVATE).edit()
        editor.putString("username", userName)
        editor.apply()
    }

}