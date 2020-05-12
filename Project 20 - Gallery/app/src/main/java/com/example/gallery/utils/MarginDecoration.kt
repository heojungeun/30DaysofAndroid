package com.example.gallery.utils

import com.example.gallery.R
import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MarginDecoration : RecyclerView.ItemDecoration{
    private var margin = 0

    constructor(context: Context) {
        margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin)
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.set(margin, margin, margin, margin)
    }
}