package com.example.gallery.utils.interfaces

import com.example.gallery.utils.PicHolder
import com.example.gallery.utils.pictureFacer

interface itemClickListener {

    /**
     * Called when a picture is clicked
     * @param holder The ViewHolder for the clicked picture
     * @param position The position in the grid of the picture that was clicked
     */
    fun onPicClicked(
        holder: PicHolder?,
        position: Int,
        pics: ArrayList<pictureFacer?>?
    )

    fun onPicClicked(
        pictureFolderPath: String?,
        folderName: String?
    )
}