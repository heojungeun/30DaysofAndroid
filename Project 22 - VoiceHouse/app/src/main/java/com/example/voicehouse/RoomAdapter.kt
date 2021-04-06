package com.example.voicehouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView

class RoomAdapter(private val dataSet: List<Room>) : RecyclerView.Adapter<RoomAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val rTitle  = view.findViewById<TextView>(R.id.room_title)
        val rProfile1 = view.findViewById<ImageView>(R.id.profile1)
        val rProfile2 = view.findViewById<ImageView>(R.id.profile2)
        val ruser1 = view.findViewById<TextView>(R.id.room_user1)
        val ruser2 = view.findViewById<TextView>(R.id.room_user2)
        val ruser3 = view.findViewById<TextView>(R.id.room_user3)
        val r_userInfo = view.findViewById<TextView>(R.id.room_userInfo)
        fun bind(room: Room){
            rTitle.text = room.RTitle
            // photo glide
            ruser1.text = room.RPart1
            ruser2.text = room.RPart2
            ruser3.text = room.RPart3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_room, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}