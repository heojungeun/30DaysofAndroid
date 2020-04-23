package com.example.mybookmark.ui.addmemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mybookmark.R
import com.example.mybookmark.data.model.Memo
import com.example.mybookmark.ui.main.MainViewModel
import com.example.mybookmark.ui.memoview.ItemviewActivity
import kotlinx.android.synthetic.main.activity_addmemo.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity: AppCompatActivity(){

    private lateinit var memoViewModel: MainViewModel
    private var id: Long? = null
    private var add_islike: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addmemo)

        memoViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if(intent != null && intent.hasExtra(EXTRA_BOOK_NAME) && intent.hasExtra(EXTRA_BOOK_CONTENT)
            && intent.hasExtra(EXTRA_BOOK_TIME)&& intent.hasExtra(EXTRA_BOOK_LIKE)){
            edit_title_txtview.setText(intent.getStringExtra(EXTRA_BOOK_NAME))
            edit_content_txtview.setText(intent.getStringExtra(EXTRA_BOOK_CONTENT))
            id = intent.getLongExtra(EXTRA_BOOK_ID, -1)
            add_islike = intent.getIntExtra(EXTRA_BOOK_LIKE,0)
            if(add_islike == 1){
                likebtn.setBackgroundResource(R.drawable.ic_thumb_up_purple_25dp)
                hatebtn.setBackgroundResource(R.drawable.ic_thumb_down_black_25dp)
            }else if(add_islike == -1){
                likebtn.setBackgroundResource(R.drawable.ic_thumb_up_black_25dp)
                hatebtn.setBackgroundResource(R.drawable.ic_thumb_down_purple_25dp)
            }
        }

        backbtn.setOnClickListener {
            finish()
        }

        savebtn.setOnClickListener {
            val name = edit_title_txtview.text.toString()
            val ctnt = edit_content_txtview.text.toString()
            val date = getTodayDate()

            if (name.isEmpty() && ctnt.isEmpty()){
                Toast.makeText(this,"제목과 내용을 입력해주세요.",Toast.LENGTH_SHORT).show()
            } else {
                val addmemo = Memo(id, date, name, ctnt,"", add_islike)
                memoViewModel.insert(addmemo)

                val intent = Intent()
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_NAME, addmemo.bookname)
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_TIME, addmemo.time)
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_CONTENT, addmemo.content)
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_LIKE, addmemo.islike)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        likebtn.setOnClickListener {
            // 좋아요 버튼이 눌러졌을 때, 이미 눌러진 상태일 경우
            if(add_islike == 1){
                add_islike = 0
                likebtn.setBackgroundResource(R.drawable.ic_thumb_up_black_25dp)
            }// 좋아요 버튼이 눌러졌을 때, null or hate 상태일 경우
            else{
                add_islike = 1
                likebtn.setBackgroundResource(R.drawable.ic_thumb_up_purple_25dp)
                hatebtn.setBackgroundResource(R.drawable.ic_thumb_down_black_25dp)
            }
        }

        hatebtn.setOnClickListener {
            // 싫어요 버튼이 눌러졌을 때, 이미 눌러진 상태일 경우
            if(add_islike == -1){
                add_islike = 0
                hatebtn.setBackgroundResource(R.drawable.ic_thumb_down_black_25dp)
            }
            else{
                add_islike = -1
                likebtn.setBackgroundResource(R.drawable.ic_thumb_up_black_25dp)
                hatebtn.setBackgroundResource(R.drawable.ic_thumb_down_purple_25dp)
            }
        }

        edit_title_txtview.setOnClickListener {
            edit_title_txtview.isCursorVisible = true
        }

        edit_content_txtview.setOnClickListener {
            edit_content_txtview.isCursorVisible = true
        }

        photobtn.setOnClickListener {

        }

    }

    fun getTodayDate() : String {
        var today = Calendar.getInstance().time
        var formatter = SimpleDateFormat("yyyy.MM.dd")
        return formatter.format(today)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var imm :InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        edit_title_txtview.isCursorVisible = false
        edit_content_txtview.isCursorVisible = false
        return true
    }

    companion object {
        const val EXTRA_BOOK_ID = "EXTRA_BOOK_ID"
        const val EXTRA_BOOK_NAME = "EXTRA_BOOK_NAME"
        const val EXTRA_BOOK_CONTENT = "EXTRA_BOOK_CONTENT"
        const val EXTRA_BOOK_TIME = "EXTRA_BOOK_TIME"
        const val EXTRA_BOOK_LIKE = "EXTRA_BOOK_LIKE"
        // photo를 어떻게 넘길것인가..
    }

}