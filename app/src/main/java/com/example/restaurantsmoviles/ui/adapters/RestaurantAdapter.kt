package com.example.restaurantsmoviles.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.restaurantsmoviles.databinding.ItemRestaurantBinding
import com.example.restaurantsmoviles.model.Restaurant

class RestaurantAdapter(private val clickListener: (Restaurant) -> Unit) : ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(RestaurantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RestaurantViewHolder(private val binding: ItemRestaurantBinding, private val clickListener: (Restaurant) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.textViewName.text = restaurant.name
            binding.textViewAddress.text = restaurant.address
            binding.textViewDescription.text = restaurant.description

            val logoUrl = restaurant.logo
            Log.d("RestaurantViewHolder", "Loading image from URL: $logoUrl")

            if (!logoUrl.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(logoUrl)
                    .transform(CenterCrop(), RoundedCorners(10))
                    .into(binding.imageViewRestaurant)
            } else {
                Log.w("RestaurantViewHolder", "Logo URL is null or empty")
            }

            // Set the click listener
            binding.root.setOnClickListener {
                clickListener(restaurant)
            }
        }
    }

    class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }
}

