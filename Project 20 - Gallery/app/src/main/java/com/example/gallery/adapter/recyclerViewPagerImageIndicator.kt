package com.example.gallery.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gallery.R
import com.example.gallery.utils.indicatorHolder
import com.example.gallery.utils.interfaces.imageIndicatorListener
import com.example.gallery.utils.pictureFacer


class recyclerViewPagerImageIndicator : RecyclerView.Adapter<indicatorHolder>{
    var pictureList: ArrayList<pictureFacer?>
    var pictureContx: Context? = null
    private var imageListener: imageIndicatorListener? = null

    /**
     *
     * @param pictureList ArrayList of pictureFacer objects
     * @param pictureContx The Activity of fragment context
     * @param imageListerner Interface for communication between adapter and fragment
     */
    constructor(
        pictureList: ArrayList<pictureFacer?>,
        pictureContx: Context?,
        imageListener: imageIndicatorListener?
    ) {
        this.pictureList = pictureList
        this.pictureContx = pictureContx
        this.imageListener = imageListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): indicatorHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell: View = inflater.inflate(R.layout.indicator_holder, parent, false)
        return indicatorHolder(cell)
    }

    override fun onBindViewHolder(holder: indicatorHolder, position: Int) {
        val pic = pictureList!![position]
        holder.positionController!!.setBackgroundColor(
            if (pic!!.getSelected()!!) Color.parseColor("#00000000") else Color.parseColor(
                "#8c000000"
            )
        )
        Glide.with(pictureContx!!)
            .load(pic!!.getPicturePath())
            .apply(RequestOptions().centerCrop())
            .into(holder.image!!)
        holder.image!!.setOnClickListener {
            fun onClick(v: View?) {
                //holder.card.setCardElevation(5);
                pic.setSelected(true)
                notifyDataSetChanged()
                imageListener!!.onImageIndicatorClicked(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return pictureList!!.size
    }

}