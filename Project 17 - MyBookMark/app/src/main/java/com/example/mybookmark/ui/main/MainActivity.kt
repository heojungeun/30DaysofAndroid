package com.example.mybookmark.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybookmark.R
import com.example.mybookmark.data.model.Memo
import com.example.mybookmark.ui.addmemo.AddActivity
import com.example.mybookmark.ui.memoview.ItemviewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var memoViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MemoListAdapter({ memo->
            // start ItemviewActivity
            val intent = Intent(this, ItemviewActivity::class.java)
            intent.putExtra(ItemviewActivity.EXTRA_BOOK_NAME, memo.bookname)
            intent.putExtra(ItemviewActivity.EXTRA_BOOK_TIME, memo.time)
            intent.putExtra(ItemviewActivity.EXTRA_BOOK_CONTENT, memo.content)
            intent.putExtra(ItemviewActivity.EXTRA_BOOK_ID, memo.id)
            intent.putExtra(ItemviewActivity.EXTRA_BOOK_LIKE, memo.islike)
            //intent.putExtra(ItemviewActivity.EXTRA_BOOK_PHOTOS, memo.photos)
            startActivity(intent)
        },{ memo->
            deleteDialog(memo)
        })

        val linearLayoutManager = LinearLayoutManager(this)
        main_rcview.adapter = adapter
        main_rcview.layoutManager = linearLayoutManager
        main_rcview.setHasFixedSize(true)

        memoViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        memoViewModel.getAll().observe(this, Observer<List<Memo>> { memos->
            adapter.setMemos(memos)
            var itemcnt: String = adapter.itemCount.toString() + "개 메모"
            main_memocount.text = itemcnt
        })

        addbtn.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        main_searchedt.setOnClickListener {
            main_searchedt.isCursorVisible = true
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        main_searchedt.isCursorVisible = false
        return true
    }

    private fun deleteDialog(memo: Memo){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("선택된 항목을 삭제하겠습니다.").setNegativeButton("취소"){_, _->}
            .setPositiveButton("삭제"){_,_->
                memoViewModel.delete(memo.id!!)
            }
        builder.show()
    }
}
