package com.example.pulltorefresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RcvAdapter (private var pitems: ArrayList<Item>):
        RecyclerView.Adapter<RcvAdapter.ViewHolder>(){

    var items = pitems.toMutableList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RcvAdapter.ViewHolder, position: Int) {
        var view = holder as ViewHolder
        var item = items.get(position)
        view.itemimg!!.setImageResource(item.img)
        view.itemid!!.setText(item.title)
        view.itemprofile!!.setImageResource(item.profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcvAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return RcvAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        var itemimg : ImageView? = null
        var itemid : TextView? = null
        var itemprofile : ImageView? = null
        init {
            itemimg = v.findViewById(R.id.itemphoto)
            itemid = v.findViewById(R.id.itemid)
            itemprofile = v.findViewById(R.id.itemprofile)
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