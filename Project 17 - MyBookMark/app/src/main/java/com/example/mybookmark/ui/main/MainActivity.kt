package com.example.mybookmark.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybookmark.R
import com.example.mybookmark.data.model.Memo
import com.example.mybookmark.ui.addmemo.AddActivity
import com.example.mybookmark.ui.memoview.ItemviewActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

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
        })

        addbtn.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteDialog(memo: Memo){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("선택된 항목을 삭제하겠습니다.").setNegativeButton("취소"){_, _->}
            .setPositiveButton("삭제"){_,_->
                memoViewModel.delete(memo)
            }
        builder.show()
    }
}
