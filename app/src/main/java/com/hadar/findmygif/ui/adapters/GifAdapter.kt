package com.hadar.findmygif.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hadar.findmygif.R
import com.hadar.findmygif.ui.models.Gif
import java.util.*

internal class GifAdapter(private var gifsArrayList: List<Gif>) :
    RecyclerView.Adapter<GifViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gif_item, parent, false)
        return GifViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val currentItem: Gif = gifsArrayList[position]

        Glide.with(holder.gifImageView!!.context)
            .load(currentItem.imageUrl)
            .placeholder(R.drawable.place_holder)
            .into(holder.gifImageView!!)
    }

    override fun getItemCount(): Int {
        return gifsArrayList.size
    }

    fun setData(gifs: List<Gif>) {
        this.gifsArrayList = ArrayList(gifs)
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean {
        return (itemCount == 0)
    }
}