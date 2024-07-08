package com.example.restaurantsmoviles.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurantsmoviles.databinding.ActivityMenuBinding
import com.example.restaurantsmoviles.ui.adapters.MenuAdapter
import com.example.restaurantsmoviles.ui.viewmodels.MenuViewModel
import kotlin.random.Random

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private val viewModel: MenuViewModel by viewModels()
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantId = intent.getIntExtra("RESTAURANT_ID", -1)
        if (restaurantId == -1) {
            Toast.makeText(this, "Error: Restaurant ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        menuAdapter = MenuAdapter(this)
        binding.recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMenu.adapter = menuAdapter

        viewModel.getMenuCategories(this, restaurantId)
        viewModel.getRestaurantDetails(this, restaurantId)

        viewModel.menuCategories.observe(this, { menuCategories ->
            menuCategories?.let {
                menuAdapter.setMenuCategories(it)
            }
        })

        viewModel.restaurant.observe(this, { restaurant ->
            restaurant?.let {
                binding.textViewRestaurantName.text = it.name
                binding.textViewRestaurantDescription.text = it.description

                val randomImageUrl = it.photos.randomOrNull()?.url ?: ""
                Glide.with(this).load(randomImageUrl).into(binding.imageViewRestaurant)
            }
        })

        viewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        binding.buttonReserveTable.setOnClickListener {
            // Aquí puedes manejar la lógica para hacer una reserva
            Toast.makeText(this, "Reservation button clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
