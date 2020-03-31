package com.example.firstmlkit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addbtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_add_dialog, null)
            val dialogurl = dialogView.findViewById<EditText>(R.id.dialogEdt)

            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    // 받은 url을 imgview에 적용한다.
                    Glide.with(this)
                        .load(dialogurl.text.toString())
                        .into(mlimgview)
                }
                .setNegativeButton("취소") {dialogInterface, i ->
                    // 취소 시 아무것도 하지 않음
                }

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.drawable.whiteborder)
        }

    }
}
