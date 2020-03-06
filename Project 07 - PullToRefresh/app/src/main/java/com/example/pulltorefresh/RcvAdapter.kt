package com.example.pulltorefresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RcvAdapter (private var items: ArrayList<Item>):
        RecyclerView.Adapter<RcvAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RcvAdapter.ViewHolder, position: Int) {
        var view = holder as ViewHolder
        var item = items[position]
        view.itemimg!!.setImageResource(item.img)
        view.itemid!!.setText(item.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcvAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return RcvAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        var itemimg : ImageView? = null
        var itemid : TextView? = null
        init {
            itemimg = v.findViewById(R.id.itemphoto)
            itemid = v.findViewById(R.id.itemid)
        }
    }

    // Clean all elements of the recycler
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(list : ArrayList<Item>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

}