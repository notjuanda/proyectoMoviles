package com.example.restaurantsmoviles.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsmoviles.databinding.ItemPlateSelectionBinding
import com.example.restaurantsmoviles.model.Plate
import com.example.restaurantsmoviles.repositories.PlateRepository

class PlateSelectionAdapter(private val context: Context) : RecyclerView.Adapter<PlateSelectionAdapter.PlateViewHolder>() {

    private val plates = mutableListOf<Plate>()
    private val selectedPlates = mutableSetOf<Plate>()

    init {
        Log.d("PlateSelectionAdapter", "Initializing PlateSelectionAdapter")
        fetchPlates()
    }

    private fun fetchPlates() {
        Log.d("PlateSelectionAdapter", "Fetching plates")
        PlateRepository.getPlates(context,
            success = { newPlates ->
                Log.d("PlateSelectionAdapter", "Plates fetched successfully")
                submitList(newPlates ?: emptyList())
            },
            failure = { throwable ->
                Log.e("PlateSelectionAdapter", "Error fetching plates", throwable)
            }
        )
    }

    fun submitList(newPlates: List<Plate>) {
        Log.d("PlateSelectionAdapter", "Submitting new list of plates")
        plates.clear()
        plates.addAll(newPlates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
        Log.d("PlateSelectionAdapter", "Creating view holder")
        val binding = ItemPlateSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
        val plate = plates[position]
        Log.d("PlateSelectionAdapter", "Binding plate at position $position: $plate")
        holder.bind(plate)
        holder.binding.checkboxPlate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("PlateSelectionAdapter", "Plate at position $position selected")
                selectedPlates.add(plate)
            } else {
                Log.d("PlateSelectionAdapter", "Plate at position $position deselected")
                selectedPlates.remove(plate)
            }
        }
    }

    override fun getItemCount(): Int {
        return plates.size
    }

    fun getSelectedPlates(): List<Plate> {
        Log.d("PlateSelectionAdapter", "Getting selected plates: $selectedPlates")
        return selectedPlates.toList()
    }

    class PlateViewHolder(val binding: ItemPlateSelectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plate: Plate) {
            Log.d("PlateViewHolder", "Binding plate: $plate")
            binding.textViewPlateName.text = plate.name
            binding.textViewPlateDescription.text = plate.description
            binding.textViewPlatePrice.text = "$${plate.price}"
        }
    }
}