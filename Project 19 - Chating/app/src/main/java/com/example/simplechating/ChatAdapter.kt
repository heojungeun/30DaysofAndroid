package com.example.simplechating

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class ChatAdapter(val context: Context, val chatlist: ArrayList<ChatModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal lateinit var preferences: SharedPreferences

    override fun getItemCount(): Int = chatlist.size

    override fun getItemViewType(position: Int): Int {
        preferences = context.getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)
        return if (chatlist[position].id == preferences.getString("id", "")) {
            1
        } else {
            2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == 1) {
            // I
            view = LayoutInflater.from(context).inflate(R.layout.item_my_chat, parent, false)
            return Holder(view)
        } else {
            // you
            view = LayoutInflater.from(context).inflate(R.layout.item_your_chat, parent, false)
            return Holder2(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder) {
            holder.chatText?.text = chatlist[position].script
            holder.chatTime?.text = chatlist[position].cdate
        } else if (holder is Holder2) {
            holder.chatText?.text = chatlist[position].script
            holder.chatTime?.text = chatlist[position].cdate
            holder.chatYouImg?.setImageResource(R.mipmap.ic_launcher)
            holder.chatYourname?.text = chatlist[position].name
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatText = itemView.findViewById<TextView>(R.id.chat_text)
        val chatTime = itemView.findViewById<TextView>(R.id.chat_Time)
    }

    inner class Holder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatText = itemView.findViewById<TextView>(R.id.chat_text)
        val chatTime = itemView.findViewById<TextView>(R.id.chat_Time)
        val chatYouImg = itemView.findViewById<ImageView>(R.id.chat_You_img)
        val chatYourname = itemView.findViewById<TextView>(R.id.chat_Yourname)
    }

    fun addItem(item: ChatModel) {
        chatlist.add(item)
    }
}