package com.example.ourgithubcontributions.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.barryzhang.tcontributionsview.TContributionsView
import com.barryzhang.tcontributionsview.adapter.DateContributionsAdapter
import com.example.ourgithubcontributions.Converter
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.contributions.RetrofitClient
import com.example.ourgithubcontributions.contributions.RetrofitService
import com.example.ourgithubcontributions.data.ContributionsDay
import com.example.ourgithubcontributions.toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService: RetrofitService

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var dialogEditText: EditText
    private lateinit var dialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRetrofit()
        setAlertDialog()

        setClickListener()
    }

    private fun setAlertDialog(){
        builder = AlertDialog.Builder(this)
        dialogView = layoutInflater.inflate(R.layout.dialog_add, null)
        dialogEditText = dialogView.findViewById<EditText>(R.id.dialogEdt)
        builder.setView(dialogView)
            .setPositiveButton("Add"){ dialog: DialogInterface?, which: Int ->
                sendUsername(dialogEditText.text.toString())
            }
            .setNegativeButton("Cancel"){dialog: DialogInterface?, which: Int ->
                // nothing
            }
        dialog = builder.create()
    }

    private fun setRetrofit() {
        retrofit = RetrofitClient.getInstance()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    private fun setClickListener(){
        btn_add.setOnClickListener {
            dialog.show()
        }
    }

    private fun sendUsername(edtStr : String){
        val cbView = findViewById<TContributionsView>(R.id.MainContributionView)
        if(edtStr.isEmpty()){
            toast("There is not userName")
            return
        }
        getContributions(edtStr, cbView)
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

        adapter.weekCount = cbList.size / 7 + 1
        adapter.setEndDay(cbList.last().day)
        cbList.forEach {
            val lev = when(it.color){
                "#c6e48b" -> 1
                "#7bc96f" -> 2
                "#239a3b"-> 3
                "#196127" -> 4
                else -> 0
            }
            adapter.put(it.day, lev)
        }
        contributionsView.adapter = adapter
        contributionsView.visibility = View.VISIBLE
    }

}
