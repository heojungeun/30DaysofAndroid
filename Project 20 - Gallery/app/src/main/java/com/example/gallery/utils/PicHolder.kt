package com.example.gallery.utils

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R


class PicHolder : RecyclerView.ViewHolder {

    var picture: ImageView? = null

    constructor(itemView: View) : super(itemView){
        picture = itemView.findViewById(R.id.image)
    }
}