package com.hadar.findmygif.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hadar.findmygif.R
import com.hadar.findmygif.ui.models.Gif
import java.util.*

internal class GifAdapter(private var gifsArrayList: List<Gif>) :
    RecyclerView.Adapter<GifViewHolder>() {

    var clickListener: ClickListener? = null

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gif_item, parent, false)
        return GifViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val currentItem: Gif = gifsArrayList[position]

        setGif(holder, currentItem)
        setListener(holder, currentItem)
    }

    private fun setGif(holder: GifViewHolder, currentItem: Gif) {
        Glide.with(holder.gifImageView!!.context)
            .load(currentItem.imageUrl)
            .placeholder(R.drawable.place_holder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.gifImageView!!)
    }

    private fun setListener(holder: GifViewHolder, currentItem: Gif) {
        holder.itemView.setOnClickListener {
            clickListener!!.onItemClick(currentItem)
        }
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

    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(gif: Gif?)
    }
}