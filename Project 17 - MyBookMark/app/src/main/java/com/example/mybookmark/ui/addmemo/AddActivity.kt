package com.example.mybookmark.ui.addmemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mybookmark.R

class AddActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addmemo)


    }

    companion object {
        const val EXTRA_BOOK_NAME = "EXTRA_BOOK_NAME"
        const val EXTRA_BOOK_CONTENT = "EXTRA_BOOK_CONTENT"
        const val EXTRA_BOOK_TIME = "EXTRA_BOOK_TIME"
        // photo를 어떻게 넘길것인가..
    }

}