package com.example.horizonrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class rcvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var images = arrayOf(R.drawable.photo1,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4)
    var txts = arrayOf(
        "The Little Woman",
        "Wonder",
        "Skam France",
        "The man who mistook his wife for a hat"
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var view = holder as CustomViewHolder
        view!!.imgview!!.setImageResource(images[position])
        view!!.txtview!!.setText(txts[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.list_item,parent,false)
        var params = view.layoutParams

        return CustomViewHolder(view)

    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgview : ImageView? = null
        var txtview : TextView? = null
        init{
            imgview = view!!.findViewById(R.id.item_img)
            txtview = view!!.findViewById(R.id.item_txt)
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}