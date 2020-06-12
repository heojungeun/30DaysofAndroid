package com.example.ourgithubcontributions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.Retrofit.RetrofitPresenter
import com.example.ourgithubcontributions.extension.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setClickListener()
    }

    private fun setClickListener() {

        login_btn.setOnClickListener {
            val editText = login_edt.text.toString()
            if(editText.isEmpty()){
                toast("There is not userName")
                return@setOnClickListener
            }
            RetrofitPresenter().getContributions(editText, this)
        }

    }
}