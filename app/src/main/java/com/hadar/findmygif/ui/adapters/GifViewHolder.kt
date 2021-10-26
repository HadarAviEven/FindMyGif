package com.hadar.findmygif.ui.adapters

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hadar.findmygif.R

class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var gifImageView: ImageView? = null

    init {
        gifImageView = itemView.findViewById(R.id.gif_image_view)
    }
}