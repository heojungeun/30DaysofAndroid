package com.example.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.setTransitionName
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gallery.R
import com.example.gallery.utils.PicHolder
import com.example.gallery.utils.interfaces.itemClickListener
import com.example.gallery.utils.pictureFacer


class picture_Adpater : RecyclerView.Adapter<PicHolder> {

    private var pictureList: ArrayList<pictureFacer?>? = null
    private var pictureContx: Context? = null
    private var picListener: itemClickListener? = null

    /**
     *
     * @param pictureList ArrayList of pictureFacer objects
     * @param pictureContx The Activities Context
     * @param picListerner An interface for listening to clicks on the RecyclerView's items
     */
    constructor(
        pictureList: ArrayList<pictureFacer?>?,
        pictureContx: Context?,
        picListener: itemClickListener?
    ) {
        this.pictureList = pictureList
        this.pictureContx = pictureContx
        this.picListener = picListener
    }

    override fun onCreateViewHolder(container: ViewGroup, position: Int): PicHolder {
        val inflater = LayoutInflater.from(container.context)
        val cell: View = inflater.inflate(R.layout.pic_holder_item, container, false)
        return PicHolder(cell)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {
        val image = pictureList!![position]
        Glide.with(pictureContx!!)
            .load(image!!.getPicturePath())
            .apply(RequestOptions().centerCrop())
            .into(holder.picture!!)
        setTransitionName(holder.picture!!, position.toString() + "_image")
        holder.picture!!.setOnClickListener{
            picListener!!.onPicClicked(holder, position, pictureList)
        }
    }

    override fun getItemCount(): Int {
        return pictureList!!.size
    }
}