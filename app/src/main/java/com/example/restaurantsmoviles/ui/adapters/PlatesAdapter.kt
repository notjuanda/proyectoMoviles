package com.example.restaurantsmoviles.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsmoviles.databinding.ItemPlateBinding
import com.example.restaurantsmoviles.model.Plate

class PlatesAdapter(private val context: Context, private val plates: List<Plate>) :
    RecyclerView.Adapter<PlatesAdapter.PlateViewHolder>() {

    inner class PlateViewHolder(val binding: ItemPlateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
        val binding = ItemPlateBinding.inflate(LayoutInflater.from(context), parent, false)
        return PlateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
        val plate = plates[position]
        holder.binding.textViewPlateName.text = plate.name
        holder.binding.textViewPlateDescription.text = plate.description
        holder.binding.textViewPlatePrice.text = "$${plate.price}"
    }

    override fun getItemCount(): Int {
        return plates.size
    }
}
