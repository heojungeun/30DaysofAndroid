package com.example.simplechating

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_chatroom.*
import java.text.SimpleDateFormat
import java.util.*

class ChatRoomActivity : AppCompatActivity(){

    internal lateinit var preferences: SharedPreferences
    lateinit var mAdapter: ChatAdapter
    var chatList = arrayListOf<ChatModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        preferences = getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)

        mAdapter = ChatAdapter(this, chatList)
        chatroom_Rcview.adapter = mAdapter
        val layoutManager = LinearLayoutManager(this)
        chatroom_Rcview.layoutManager = layoutManager
        chatroom_Rcview.setHasFixedSize(true)

        chatroom_Sendbtn.setOnClickListener {
            sendMessage()
        }
    }

    fun sendMessage(){
        val getTime = getTodayDate()
        val item = ChatModel(preferences.getString("id", "")!!,preferences.getString("name","")!!,
            chatroom_Text.text.toString(),"example",getTime)
        mAdapter.addItem(item)
        mAdapter.notifyDataSetChanged()

        chatroom_Text.setText("")
    }

    fun getTodayDate() : String {
        var today = Calendar.getInstance().time
        var formatter = SimpleDateFormat("hh:mm:ss a")
        return formatter.format(today)
    }
}