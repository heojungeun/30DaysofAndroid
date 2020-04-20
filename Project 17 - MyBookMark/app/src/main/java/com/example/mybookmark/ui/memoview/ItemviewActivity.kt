package com.example.mybookmark.ui.memoview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mybookmark.R
import com.example.mybookmark.data.model.Memo
import com.example.mybookmark.ui.addmemo.AddActivity
import com.example.mybookmark.ui.main.MainViewModel
import kotlinx.android.synthetic.main.activity_memoview.*
import kotlinx.android.synthetic.main.activity_memoview.backbtn
import kotlinx.android.synthetic.main.activity_memoview.edit_title_txtview

class ItemviewActivity: AppCompatActivity() {

    private lateinit var memoViewModel: MainViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memoview)

        memoViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if(intent != null && intent.hasExtra(EXTRA_BOOK_NAME) && intent.hasExtra(EXTRA_BOOK_CONTENT)
            && intent.hasExtra(EXTRA_BOOK_TIME)){
            edit_title_txtview.setText(intent.getStringExtra(EXTRA_BOOK_NAME))
            memoview_content_txtview.setText(intent.getStringExtra(EXTRA_BOOK_CONTENT))
            id = intent.getLongExtra(EXTRA_BOOK_ID, -1)
        }

        backbtn.setOnClickListener {
            finish()
        }

        delbtn.setOnClickListener {
            if (id != null) {
                deleteDialog(id!!)
            }
        }

        editbtn.setOnClickListener {
            // start ItemviewActivity
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_BOOK_NAME, EXTRA_BOOK_NAME)
            intent.putExtra(AddActivity.EXTRA_BOOK_TIME, EXTRA_BOOK_TIME)
            intent.putExtra(AddActivity.EXTRA_BOOK_CONTENT, EXTRA_BOOK_CONTENT)
            intent.putExtra(AddActivity.EXTRA_BOOK_ID, EXTRA_BOOK_ID)
            //intent.putExtra(AddActivity.EXTRA_BOOK_PHOTOS, memo.photos)
            startActivity(intent)
        }

    }

    private fun deleteDialog(id: Long){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("메모를 삭제하겠습니다.").setNegativeButton("취소"){_, _->}
            .setPositiveButton("삭제"){_,_->
                memoViewModel.delete(id)
                finish()
            }
        builder.show()
    }

    companion object {
        const val EXTRA_BOOK_ID = "EXTRA_BOOK_ID"
        const val EXTRA_BOOK_NAME = "EXTRA_BOOK_NAME"
        const val EXTRA_BOOK_CONTENT = "EXTRA_BOOK_CONTENT"
        const val EXTRA_BOOK_TIME = "EXTRA_BOOK_TIME"
        // photo를 어떻게 넘길것인가..
    }
}