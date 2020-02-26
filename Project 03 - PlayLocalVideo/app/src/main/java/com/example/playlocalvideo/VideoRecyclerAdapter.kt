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


class VideoRecyclerAdapter(val context: Context, var videoList:ArrayList<VideoModel>) :
    RecyclerView.Adapter<VideoRecyclerAdapter.Holder> (){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.activity_list_item, parent, false)

        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val mVideo : VideoModel = videoList.get(position)

        holder.bind(mVideo,context)
    }

    inner class Holder(view: View) : ViewHolder(view) {

        //val imageViewThumbnail = itemView.findViewById<ImageView>(R.id.imgThumbnail)

        fun bind(mVideo : VideoModel, context: Context){

            //Glide.with(context).load(mVideo.mVideoThumb).into(imageViewThumbnail)
            itemView.setOnClickListener {
                var intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra("filepath",mVideo.mFilePath)
                context.startActivity(intent)
            }
        }

    }

    fun setUp(vidiolist : ArrayList<VideoModel>){
        videoList = vidiolist
        notifyDataSetChanged()
    }



}