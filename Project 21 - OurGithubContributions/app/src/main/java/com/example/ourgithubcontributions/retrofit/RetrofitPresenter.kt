package com.example.ourgithubcontributions.retrofit

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import com.barryzhang.tcontributionsview.TContributionsView
import com.example.ourgithubcontributions.SharedPreferenceManager
import com.example.ourgithubcontributions.extension.Converter
import com.example.ourgithubcontributions.data.model.ContributionsDay
import com.example.ourgithubcontributions.extension.toast
import com.example.ourgithubcontributions.ui.adapter.DateContributionsAdapter
import com.example.ourgithubcontributions.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitPresenter{

    fun getContributions(userName: String, context: Context){

        RetrofitClient.createService(RetrofitService::class.java).getContributions(userName).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("LoginActivity", t.message.toString())
                context.toast("There is not userName")
            }

            override fun onResponse(call: Call<String>, response: Response<String>){
                if(response.isSuccessful){
                    SharedPreferenceManager.token = userName
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }else{
                    context.toast("There is not userName")
                }
            }
        })
    }

    fun getContributions(userName: String, tcbview: TContributionsView){
        RetrofitClient.createService(RetrofitService::class.java).getContributions(userName).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MainActivity", t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val res: String = response.body().toString()
                    if (res.isNotEmpty()) {
                        val list = Converter.svgToContributionsList(res)
                        if (list.isNotEmpty()) {
                            useDateContributionsAdapter(tcbview, list)
                        }
                    }
                }
            }
        })
    }

    fun useDateContributionsAdapter(
        contributionsView: TContributionsView,
        cbList: List<ContributionsDay>
    ) {

        val adapter =
            DateContributionsAdapter()

        adapter.weekCount = cbList.size / 7 + 1
        adapter.setEndDay(cbList.last().day)
        cbList.forEach {
            val lev = when (it.color) {
                "#c6e48b" -> 1
                "#7bc96f" -> 2
                "#239a3b" -> 3
                "#196127" -> 4
                else -> 0
            }
            adapter.put(it.day, lev)
        }

        contributionsView.adapter = adapter
        contributionsView.visibility = View.VISIBLE
    }

}