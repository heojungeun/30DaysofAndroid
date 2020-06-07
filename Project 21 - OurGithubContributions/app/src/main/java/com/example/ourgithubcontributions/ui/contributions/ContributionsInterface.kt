package com.example.ourgithubcontributions.ui.contributions

import android.content.Context
import android.telecom.Call

interface ContributionsInterface {

    interface Presenter {
        fun initUserContributions(userName: String): Unit
    }

    interface View {
        fun showAppWidget(list: List<ContributionsDay>)
        fun showFailure(msg: String?)
    }

    interface Model {
        fun getUserContributions(userName: String): Call
        fun getUserName(context: Context): String
        fun saveUserName(context: Context, userName: String)
    }

}