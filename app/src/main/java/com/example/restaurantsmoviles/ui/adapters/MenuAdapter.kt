package com.example.restaurantsmoviles.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsmoviles.databinding.ItemMenuCategoryBinding
import com.example.restaurantsmoviles.model.MenuCategory

class MenuAdapter(private val context: Context) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val menuCategories = mutableListOf<MenuCategory>()

    inner class MenuViewHolder(val binding: ItemMenuCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuCategory = menuCategories[position]
        holder.binding.textViewCategoryName.text = menuCategory.name
        val platesAdapter = PlatesAdapter(context, menuCategory.plates)
        holder.binding.recyclerViewPlates.layoutManager = LinearLayoutManager(context)
        holder.binding.recyclerViewPlates.adapter = platesAdapter
    }

    override fun getItemCount(): Int {
        return menuCategories.size
    }

    fun setMenuCategories(menuCategories: List<MenuCategory>) {
        this.menuCategories.clear()
        this.menuCategories.addAll(menuCategories)
        notifyDataSetChanged()
    }
}
