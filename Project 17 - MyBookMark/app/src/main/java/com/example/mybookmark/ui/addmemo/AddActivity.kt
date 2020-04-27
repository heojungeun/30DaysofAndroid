package com.example.mybookmark.ui.addmemo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
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
    private var addphoto_uri = ""
    private lateinit var viewMemo : Memo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addmemo)

        memoViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // 기존 메모에서 편집모드 들어왔을 때
        if(intent != null && intent.hasExtra(EXTRA_BOOK_NAME) && intent.hasExtra(EXTRA_BOOK_CONTENT)
            && intent.hasExtra(EXTRA_BOOK_PHOTO) && intent.hasExtra(EXTRA_BOOK_TIME)&& intent.hasExtra(EXTRA_BOOK_LIKE)){

            id = intent.getLongExtra(EXTRA_BOOK_ID, -1)

            updateView(intent.getStringExtra(EXTRA_BOOK_TIME),intent.getStringExtra(EXTRA_BOOK_NAME),
                intent.getStringExtra(EXTRA_BOOK_CONTENT), intent.getStringExtra(EXTRA_BOOK_PHOTO), intent.getIntExtra(EXTRA_BOOK_LIKE, 0))

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
                val addmemo = Memo(id, date, name, ctnt, addphoto_uri, add_islike)
                memoViewModel.insert(addmemo)

                val intent = Intent()
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_NAME, addmemo.bookname)
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_TIME, addmemo.time)
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_CONTENT, addmemo.content)
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_LIKE, addmemo.islike)
                intent.putExtra(ItemviewActivity.EXTRA_BOOK_PHOTO, addmemo.photos)
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
            // check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    // 퍼미션 거부된 상태일 때
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    // 퍼미션 요청 팝업 보여주기
                    requestPermissions(permissions, PERMISSION_CODE)
                }else{
                    // 퍼미션 수락 상태
                    pickImgGallery()
                }
            }else{
                // system OS < Marshmallow
                pickImgGallery()
            }
        }
    }

    private fun pickImgGallery(){
        // 갤러리 사진 선택하러가기
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0]==
                        PackageManager.PERMISSION_GRANTED){
                    // 팝업에서 퍼미션 수락
                    pickImgGallery()
                }
                else{
                    // 팝업에서 퍼미션 거절
                    Toast.makeText(this, "권한 거부", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            addphoto_uri = data?.data.toString()
            Glide.with(this)
                .load(data?.data)
                .error(R.drawable.ic_error_purple_24dp)
                .into(add_photo)
            add_photo.visibility = View.VISIBLE
        }
    }

    fun updateView(btime:String, bname:String, bctnt:String, bph:String, blike:Int){

        viewMemo = Memo(id,btime,bname,bctnt,bph,blike)

        edit_title_txtview.setText(bname)
        edit_content_txtview.setText(bctnt)

        if(blike == 1){
            likebtn.setBackgroundResource(R.drawable.ic_thumb_up_purple_25dp)
            hatebtn.setBackgroundResource(R.drawable.ic_thumb_down_black_25dp)
        }else if(blike == -1){
            likebtn.setBackgroundResource(R.drawable.ic_thumb_up_black_25dp)
            hatebtn.setBackgroundResource(R.drawable.ic_thumb_down_purple_25dp)
        }else{
            likebtn.setBackgroundResource(R.drawable.ic_thumb_up_black_25dp)
            hatebtn.setBackgroundResource(R.drawable.ic_thumb_down_black_25dp)
        }

        if(!bph.equals("")){
            Glide.with(this)
                .load(viewMemo.photos)
                .error(R.drawable.ic_error_purple_24dp)
                .into(add_photo)
            add_photo.visibility = View.VISIBLE
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
        const val EXTRA_BOOK_PHOTO = "EXTRA_BOOK_PHOTO"
        // img pick code
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

}