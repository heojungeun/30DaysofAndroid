package com.example.playlocalvideo

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class VideoModel (
    var id : Long,
    var mFilePath : Uri,
    var name : String
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<VideoModel>() {
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel) =
                oldItem == newItem
        }
    }
}