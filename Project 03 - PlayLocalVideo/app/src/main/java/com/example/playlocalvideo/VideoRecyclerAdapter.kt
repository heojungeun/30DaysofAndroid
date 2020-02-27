package com.example.playlocalvideo

import android.R
import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_list_item.view.*


class VideoRecyclerAdapter(val context: Context, var videoList: ArrayList<VideoModel>) :
    RecyclerView.Adapter<VideoRecyclerAdapter.Holder> (){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.activity_list_item, parent, false)

        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val mVideo : VideoModel = videoList.get(position)

        Glide.with(holder.itemView.imgThumbnail)
            .load(mVideo.mFilePath)
            .thumbnail(0.33f)
            .centerCrop()
            .into(holder.itemView.imgThumbnail)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("filepath",mVideo.mFilePath)
            context.startActivity(intent)
        }
    }

    class Holder(itemview: View) : RecyclerView.ViewHolder(itemview){
        //val imgthumb = itemview.findViewById<ImageView>(R.id.imgThumbnail)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun setUp(vidiolist : ArrayList<VideoModel>){
        videoList = vidiolist
        notifyDataSetChanged()
    }

}