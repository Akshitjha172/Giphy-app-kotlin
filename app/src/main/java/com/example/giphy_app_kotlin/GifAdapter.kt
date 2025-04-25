package com.example.giphy_app_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GifAdapter : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    private val gifList = mutableListOf<GifData>()

    fun addGifs(newGifs: List<GifData>) {
        val start = gifList.size
        gifList.addAll(newGifs)
        notifyItemRangeInserted(start, newGifs.size)
    }

    fun clear() {
        gifList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = gifList[position]
        Glide.with(holder.imageView.context)
            .load(gif.images.fixed_height.url)
            .into(holder.imageView)
    }

    override fun getItemCount() = gifList.size

    class GifViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.gifImageView)
    }
}
