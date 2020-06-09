package com.example.ourgithubcontributions.contributions

import android.content.Context
import okhttp3.Call
import com.example.ourgithubcontributions.data.ContributionsDay

interface ContributionsInterface {

    interface Presenter {
        fun initUserContributions(userName: String): Unit
    }

    interface View {
        fun showContributions(list: List<ContributionsDay>)
        fun showFailure(msg: String?)
    }

    interface Model {
        fun buildRetrofit(userName: String): RetrofitService?
        fun getUserName(context: Context): String
        fun saveUserName(context: Context, userName: String)
    }

}