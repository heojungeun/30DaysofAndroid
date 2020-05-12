package com.example.gallery.utils.interfaces

interface imageIndicatorListener {
    /**
     *
     * @param ImagePosition position of an item in the RecyclerView Adapter
     */
    fun onImageIndicatorClicked(ImagePosition: Int)
}