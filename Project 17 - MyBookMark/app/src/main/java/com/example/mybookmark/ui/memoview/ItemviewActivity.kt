package com.example.mybookmark.ui.memoview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
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
    private lateinit var viewMemo : Memo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memoview)

        memoViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if(intent != null && intent.hasExtra(EXTRA_BOOK_NAME) && intent.hasExtra(EXTRA_BOOK_CONTENT)
            && intent.hasExtra(EXTRA_BOOK_PHOTO) && intent.hasExtra(EXTRA_BOOK_TIME) && intent.hasExtra(EXTRA_BOOK_LIKE)){

            id = intent.getLongExtra(EXTRA_BOOK_ID, -1)

            updateView(intent.getStringExtra(EXTRA_BOOK_TIME),intent.getStringExtra(EXTRA_BOOK_NAME),
                intent.getStringExtra(EXTRA_BOOK_CONTENT), intent.getStringExtra(EXTRA_BOOK_PHOTO), intent.getIntExtra(EXTRA_BOOK_LIKE, 0))

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
            intent.putExtra(AddActivity.EXTRA_BOOK_NAME, viewMemo.bookname)
            intent.putExtra(AddActivity.EXTRA_BOOK_TIME, viewMemo.time)
            intent.putExtra(AddActivity.EXTRA_BOOK_CONTENT, viewMemo.content)
            intent.putExtra(AddActivity.EXTRA_BOOK_ID, id)
            intent.putExtra(AddActivity.EXTRA_BOOK_LIKE, viewMemo.islike)
            intent.putExtra(AddActivity.EXTRA_BOOK_PHOTO, viewMemo.photos)
            startActivityForResult(intent, 1234)
        }

    }

    fun updateView(btime:String, bname:String, bctnt:String, bph:String, blike:Int){

        viewMemo = Memo(id,btime,bname,bctnt,bph,blike)

        edit_title_txtview.setText(bname)
        memoview_content_txtview.setText(bctnt)
        memoview_time_txtview.setText(btime)

        if(blike == 1){
            view_like.visibility = View.VISIBLE
            view_hate.visibility = View.INVISIBLE
        }else if(blike == -1){
            view_like.visibility = View.INVISIBLE
            view_hate.visibility = View.VISIBLE
        }else{
            view_like.visibility = View.INVISIBLE
            view_hate.visibility = View.INVISIBLE
        }

        if(!bph.equals("")){
            Glide.with(this)
                .load(viewMemo.photos)
                .error(R.drawable.ic_error_purple_24dp)
                .into(memoview_photo)
            memoview_photo.visibility = View.VISIBLE
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== 1234 && resultCode== RESULT_OK){
            updateView(data!!.getStringExtra(EXTRA_BOOK_TIME),data!!.getStringExtra(EXTRA_BOOK_NAME),
                data!!.getStringExtra(EXTRA_BOOK_CONTENT), data!!.getStringExtra(EXTRA_BOOK_PHOTO), data!!.getIntExtra(EXTRA_BOOK_LIKE,0))
        }
    }

    companion object {
        const val EXTRA_BOOK_ID = "EXTRA_BOOK_ID"
        const val EXTRA_BOOK_NAME = "EXTRA_BOOK_NAME"
        const val EXTRA_BOOK_CONTENT = "EXTRA_BOOK_CONTENT"
        const val EXTRA_BOOK_TIME = "EXTRA_BOOK_TIME"
        const val EXTRA_BOOK_LIKE = "EXTRA_BOOK_LIKE"
        const val EXTRA_BOOK_PHOTO = "EXTRA_BOOK_PHOTO"
    }
}