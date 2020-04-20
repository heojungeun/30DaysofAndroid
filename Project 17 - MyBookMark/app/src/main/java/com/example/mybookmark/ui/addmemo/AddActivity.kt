package com.example.mybookmark.ui.addmemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mybookmark.R
import com.example.mybookmark.data.model.Memo
import com.example.mybookmark.ui.main.MainViewModel
import kotlinx.android.synthetic.main.activity_addmemo.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity: AppCompatActivity(){

    private lateinit var memoViewModel: MainViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addmemo)

        memoViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if(intent != null && intent.hasExtra(EXTRA_BOOK_NAME) && intent.hasExtra(EXTRA_BOOK_CONTENT)
            && intent.hasExtra(EXTRA_BOOK_TIME)){
            edit_title_txtview.setText(intent.getStringExtra(EXTRA_BOOK_NAME))
            edit_content_txtview.setText(intent.getStringExtra(EXTRA_BOOK_CONTENT))
            id = intent.getLongExtra(EXTRA_BOOK_ID, -1)
        }

        backbtn.setOnClickListener {
            finish()
        }

        savebtn.setOnClickListener {
            val name = edit_title_txtview.text.toString()
            val ctnt = edit_content_txtview.text.toString()
            val date = getTodayDate().toString()

            if (name.isEmpty() && ctnt.isEmpty()){
                Toast.makeText(this,"제목과 내용을 입력해주세요.",Toast.LENGTH_SHORT).show()
            } else {
                val addmemo = Memo(id, date, name, ctnt,"")
                memoViewModel.insert(addmemo)
                finish()
            }
        }
    }

    fun getTodayDate() : String {
        var today = Calendar.getInstance().time
        var formatter = SimpleDateFormat("yyyy.MM.dd")
        return formatter.format(today)
    }

    companion object {
        const val EXTRA_BOOK_ID = "EXTRA_BOOK_ID"
        const val EXTRA_BOOK_NAME = "EXTRA_BOOK_NAME"
        const val EXTRA_BOOK_CONTENT = "EXTRA_BOOK_CONTENT"
        const val EXTRA_BOOK_TIME = "EXTRA_BOOK_TIME"
        // photo를 어떻게 넘길것인가..
    }

}