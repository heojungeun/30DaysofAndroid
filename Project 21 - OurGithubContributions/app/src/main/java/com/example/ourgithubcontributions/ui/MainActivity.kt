package com.example.ourgithubcontributions.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.barryzhang.tcontributionsview.TContributionsView
import com.barryzhang.tcontributionsview.adapter.DateContributionsAdapter
import com.example.ourgithubcontributions.Converter
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.contributions.RetrofitClient
import com.example.ourgithubcontributions.contributions.RetrofitService
import com.example.ourgithubcontributions.data.ContributionsDay
import com.example.ourgithubcontributions.databinding.ActivityMainBinding
import com.example.ourgithubcontributions.toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRetrofit()

        val cbView = findViewById<TContributionsView>(R.id.MainContributionView)

        btn_update.setOnClickListener {
            val edtStr = edit_username.text.toString()
            if(edtStr.isNullOrEmpty()){
                toast("There is not userName")
                return@setOnClickListener
            }
            getContributions(edtStr, cbView)
        }
    }

    private fun setRetrofit() {
        retrofit = RetrofitClient.getInstance()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    private fun getContributions(userName: String, tcbview: TContributionsView) {
        retrofitService.getContributions(userName).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                toast("Fail retrofit connect")
                Log.e("MainActivity",t.message)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    val res : String = response.body().toString()
                    if (res.isNotEmpty()){
                        val list = Converter.svgToContributionsList(res)
                        if (list.isNotEmpty())
                            useDateContributionsAdapter(tcbview, list)
                    }
                }
            }
        })

    }

    private fun useDateContributionsAdapter(contributionsView: TContributionsView, cbList: List<ContributionsDay>) {
        val adapter: DateContributionsAdapter = object : DateContributionsAdapter() {
            override fun mapDate(date: String): String {
                return if (date.contains("T")) {
                    date.split("T").toTypedArray()[0]
                } else date
            }
        }

        // 테스트용 데이터
//        adapter.weekCount = 10
//        adapter.setEndDay("2020-06-09")
//        adapter.put("2020-06-01", 1)
//        adapter.put("2020-05-17", 4)
//        adapter.put("2020-05-16", 4)
//        adapter.put("2020-05-15", 3)
//        adapter.put("2020-05-14", 2)
//        contributionsView.adapter = adapter
        adapter.weekCount = 26
        adapter.setEndDay(cbList.last().day)
        cbList.forEach {
            val lev = when(it.color){
                R.color.colorFirst -> 1
                R.color.colorSecond -> 2
                R.color.colorThird -> 3
                R.color.colorAccent -> 4
                else -> 0
            }
            adapter.put(it.day, lev)
        }
        contributionsView.adapter = adapter
    }

}
