package com.example.mybookmark.ui.mybooktower

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybookmark.R
import com.example.mybookmark.data.model.Memo
import com.example.mybookmark.ui.main.MainViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class MyBTowerActivity: AppCompatActivity() {

    private lateinit var memoViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val adapter = BookListAdapter()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        bookRcview.adapter = adapter
        bookRcview.layoutManager = linearLayoutManager
        bookRcview.setHasFixedSize(true)

        memoViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        memoViewModel.getAll().observe(this, Observer<List<Memo>> { memos->
            adapter.setMemos(memos)
        })

        backbtn.setOnClickListener {
            finish()
        }
    }
}