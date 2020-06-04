package com.example.simplechating

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefer: SharedPreferences

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefer = getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)
        val editor = prefer.edit()

        // btn click -> input name -> save sharedpreference, intent(name)
        transbtn.setOnClickListener {
            val edtId = main_id.text.toString()
            val edtName = main_name.text.toString()
            if (edtId.isEmpty() || edtName.isEmpty()) {
                Toast.makeText(this, "둘 다 작성해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                editor.putString("id", edtId)
                editor.putString("name", edtName)
                val intent = Intent(this, ChatRoomActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
