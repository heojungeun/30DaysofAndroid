package com.example.gallery.utils


import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R


class indicatorHolder : RecyclerView.ViewHolder {
    var image: ImageView? = null
    private var card: CardView? = null
    var positionController: View? = null

    constructor(itemView: View) : super(itemView){
        image = itemView.findViewById(R.id.imageIndicator)
        card = itemView.findViewById(R.id.indicatorCard)
        positionController = itemView.findViewById(R.id.activeImage)
    }
}