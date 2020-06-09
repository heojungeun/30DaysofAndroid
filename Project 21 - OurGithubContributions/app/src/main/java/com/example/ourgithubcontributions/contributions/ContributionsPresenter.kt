package com.example.ourgithubcontributions.contributions

import android.widget.Toast
import com.example.ourgithubcontributions.Converter
import okhttp3.Callback
import okhttp3.Response
import okhttp3.Call
import java.io.IOException

class ContributionsPresenter(
    private val mView: ContributionsInterface.View,
    private val mModel: ContributionsInterface.Model
) {

    fun initUserContributions(userName: String) {

        //mModel.buildRetrofit()?.getContrib(userName)?.enqueue(object : Callback<String> {
//        mModel.buildRetrofit(userName).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                mView.showFailure(e.message)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    val svgString = response.body().toString()
//                    val list = Converter.svgToContributionsList(svgString)
//
//                    mView.showContributions(list)
//                } else {
//                    mView.showFailure(response.message())
//                }
//            }
//        })
    }

}