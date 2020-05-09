package com.example.simplechating

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal lateinit var prefer: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefer = getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)
        val editor = prefer!!.edit()

        // btn click -> input name -> save sharedpreference, intent(name)
        transbtn.setOnClickListener {
            editor.putString("id", main_id.text.toString())
            editor.putString("name", main_name.text.toString())
            val intent = Intent(this, ChatRoomActivity::class.java)
            startActivity(intent)
        }
    }
}
