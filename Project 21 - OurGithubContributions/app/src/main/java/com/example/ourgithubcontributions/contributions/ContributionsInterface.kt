package com.example.ourgithubcontributions.contributions

import android.content.Context
import android.telecom.Call
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
        fun buildRetrofit(): RetrofitService?
        fun getUserName(context: Context): String
        fun saveUserName(context: Context, userName: String)
    }

}