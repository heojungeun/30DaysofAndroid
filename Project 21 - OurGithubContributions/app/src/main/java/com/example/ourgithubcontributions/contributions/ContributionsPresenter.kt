package com.example.ourgithubcontributions.contributions

import android.widget.Toast
import com.example.ourgithubcontributions.Converter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContributionsPresenter(
    private val mView: ContributionsInterface.View,
    private val mModel: ContributionsInterface.Model
) : ContributionsInterface.Presenter {

    override fun initUserContributions(userName: String) {

        mModel.buildRetrofit()?.getContrib(userName)?.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                mView.showFailure(null)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val svgString = response.body()
                    val list = Converter.svgToContributionsList(svgString!!)

                    mView.showContributions(list)
                } else {
                    mView.showFailure(response.message())
                }
            }
        })
    }

}