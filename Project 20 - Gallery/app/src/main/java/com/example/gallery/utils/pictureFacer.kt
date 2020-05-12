package com.example.gallery.utils

class pictureFacer {
    private var picturName: String? = null
    private var picturePath: String? = null
    private var pictureSize: String? = null
    private var imageUri: String? = null
    private var selected = false

    fun pictureFacer() {}

    fun pictureFacer(
        picturName: String?,
        picturePath: String?,
        pictureSize: String?,
        imageUri: String?
    ) {
        this.picturName = picturName
        this.picturePath = picturePath
        this.pictureSize = pictureSize
        this.imageUri = imageUri
    }


    fun getPicturName(): String? {
        return picturName
    }

    fun setPicturName(picturName: String?) {
        this.picturName = picturName
    }

    fun getPicturePath(): String? {
        return picturePath
    }

    fun setPicturePath(picturePath: String?) {
        this.picturePath = picturePath
    }

    fun getPictureSize(): String? {
        return pictureSize
    }

    fun setPictureSize(pictureSize: String?) {
        this.pictureSize = pictureSize
    }

    fun getImageUri(): String? {
        return imageUri
    }

    fun setImageUri(imageUri: String?) {
        this.imageUri = imageUri
    }

    fun getSelected(): Boolean? {
        return selected
    }

    fun setSelected(selected: Boolean) {
        this.selected = selected
    }
}