package com.example.restaurantsmoviles.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantsmoviles.databinding.ItemPhotoBinding
import com.example.restaurantsmoviles.model.Photo
import com.example.restaurantsmoviles.ui.activities.FullScreenImageActivity

class PhotoAdapter(private val context: Context) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val photoList = mutableListOf<Photo>()

    inner class PhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photoList[position]
        Glide.with(context).load(photo.url).into(holder.binding.imageViewPhoto)

        holder.binding.imageViewPhoto.setOnClickListener {
            val intent = Intent(context, FullScreenImageActivity::class.java).apply {
                putExtra("PHOTO_LIST", ArrayList(photoList))
                putExtra("CURRENT_POSITION", position)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun setPhotos(photos: List<Photo>) {
        photoList.clear()
        photoList.addAll(photos)
        notifyDataSetChanged()
    }
}
