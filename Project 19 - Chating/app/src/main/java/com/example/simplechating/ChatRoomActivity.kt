package com.example.simplechating

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_chatroom.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ChatRoomActivity : AppCompatActivity(){

    internal lateinit var preferences: SharedPreferences
    lateinit var mAdapter: ChatAdapter
    var chatList = arrayListOf<ChatModel>()

    private var hasConnection: Boolean = false
    private var thd: Thread? = null
    private var stTyping = false
    private var time = 2

    private var mSocket: Socket = IO.socket("http://0000000.ngrok.io/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        preferences = getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)

        mAdapter = ChatAdapter(this, chatList)
        chatroom_Rcview.adapter = mAdapter
        val layoutManager = LinearLayoutManager(this)
        chatroom_Rcview.layoutManager = layoutManager
        chatroom_Rcview.setHasFixedSize(true)

        if(savedInstanceState!=null){
            hasConnection = savedInstanceState.getBoolean("hasConnection")
        }

        if(hasConnection){

        }else{
            //connect socket
            mSocket.connect()

            // receive
            mSocket.on("connect user", onNewUser)
            mSocket.on("chat message", onNewMsg)

            val userid = JSONObject()
            try {
                userid.put("username",preferences.getString("name", "")+"Connected")
                userid.put("roomName","room_example")
                Log.e("username", preferences.getString("name","")+"Connected")

                // send
                mSocket.emit("connect user", userid)
            }catch (e: JSONException){
                e.printStackTrace()
            }

        }

        hasConnection = true

        chatroom_Sendbtn.setOnClickListener {
            sendMessage()
        }
    }

    internal var onNewUser: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            val length = args.size

            if(length == 0){
                return@Runnable
            }
            var username = args[0].toString()
            try{
                val obj = JSONObject(username)
                username = obj.getString("username")
            }catch (e: JSONException){
                e.printStackTrace()
            }
        })
    }

    internal var onNewMsg: Emitter.Listener = Emitter.Listener { args ->
        runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val cid:String
            val name:String
            val script:String
            val profile_img: String
            val cdate: String

            try {
                Log.e("onnewmsg",data.toString())

                cid = data.getString("id")
                name = data.getString("name")
                script = data.getString("script")
                profile_img = data.getString("profile_img")
                cdate = data.getString("cdate")

                val format = ChatModel(cid, name, script, profile_img, cdate)
                mAdapter.addItem(format)
                mAdapter.notifyDataSetChanged()
                Log.e("new me",name)
            }catch (e:Exception){
                return@Runnable
            }
        })
    }

    fun sendMessage(){
        val getTime = getTodayDate()
        val item = ChatModel(preferences.getString("id", "")!!,preferences.getString("name","")!!,
            chatroom_Text.text.toString(),"example",getTime)
        mAdapter.addItem(item)
        mAdapter.notifyDataSetChanged()

        val message = chatroom_Text.text.toString().trim {it<=' '}
        if(TextUtils.isEmpty(message)){
            return
        }
        chatroom_Text.setText("")
        val jsonObject = JSONObject()
        try{
            jsonObject.put("id",preferences.getString("id",""))
            jsonObject.put("name",preferences.getString("name",""))
            jsonObject.put("profile_img","example")
            jsonObject.put("cdate",getTime)
            jsonObject.put("roomName","room_examle")
        }catch (e:JSONException){
            e.printStackTrace()
        }
        Log.e("chatRoom","sendMessage:1"+mSocket.emit("chat message",jsonObject))
        Log.e("sendmmm",preferences.getString("name",""))
    }

    fun getTodayDate() : String {
        var today = Calendar.getInstance().time
        var formatter = SimpleDateFormat("hh:mm:ss a")
        return formatter.format(today)
    }
}