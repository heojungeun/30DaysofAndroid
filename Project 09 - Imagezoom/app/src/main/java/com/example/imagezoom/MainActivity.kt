package com.example.imagezoom

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private val PICK_IMAGE = 301

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoview.setImageResource(R.drawable.photo1)

        chbtn.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE)
            //intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, PICK_IMAGE)
        }

        robtn.setOnClickListener {
            photoview.rotation += 90f
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE){
            if (resultCode == Activity.RESULT_OK){
                try {
                    var instr : InputStream? = contentResolver.openInputStream(data?.data!!)
                    var img : Bitmap = BitmapFactory.decodeStream(instr)
                    instr?.close()

                    photoview.setImageBitmap(img)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}