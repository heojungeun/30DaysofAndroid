package com.example.ourgithubcontributions.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.ui.contributions.ContributionsDay
import com.example.ourgithubcontributions.ui.contributions.ContributionsInterface

class MainActivity : AppCompatActivity(), ContributionsInterface.View {

    private lateinit var mPresenter: ContributionsInterface.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun showAppWidget(list: List<ContributionsDay>) {
        TODO("Not yet implemented")
    }

    override fun showFailure(msg: String?) {
        TODO("Not yet implemented")
    }
}