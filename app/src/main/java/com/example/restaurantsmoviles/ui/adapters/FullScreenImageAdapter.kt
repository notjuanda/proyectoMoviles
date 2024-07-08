package com.example.restaurantsmoviles.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantsmoviles.databinding.ItemFullScreenImageBinding
import com.example.restaurantsmoviles.model.Photo

class FullScreenImageAdapter(private val context: Context, private val photoList: List<Photo>) :
    RecyclerView.Adapter<FullScreenImageAdapter.FullScreenImageViewHolder>() {

    inner class FullScreenImageViewHolder(val binding: ItemFullScreenImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullScreenImageViewHolder {
        val binding = ItemFullScreenImageBinding.inflate(LayoutInflater.from(context), parent, false)
        return FullScreenImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FullScreenImageViewHolder, position: Int) {
        val photo = photoList[position]
        Glide.with(context).load(photo.url).into(holder.binding.fullScreenImageView)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}
