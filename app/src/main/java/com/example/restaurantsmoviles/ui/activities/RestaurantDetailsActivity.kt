package com.example.restaurantsmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurantsmoviles.R
import com.example.restaurantsmoviles.databinding.ActivityRestaurantDetailsBinding
import com.example.restaurantsmoviles.ui.adapters.PhotoAdapter
import com.example.restaurantsmoviles.ui.viewmodels.RestaurantDetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailsBinding
    private val viewModel: RestaurantDetailsViewModel by viewModels()
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantId = intent.getIntExtra("RESTAURANT_ID", -1)
        if (restaurantId == -1) {
            Toast.makeText(this, "Error: Restaurant ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        photoAdapter = PhotoAdapter(this)
        binding.recyclerViewPhotos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPhotos.adapter = photoAdapter

        viewModel.getRestaurantDetails(this, restaurantId)

        viewModel.restaurant.observe(this, { restaurant ->
            restaurant?.let {
                binding.textViewName.text = it.name
                binding.textViewAddress.text = it.address
                binding.textViewCity.text = it.city
                binding.textViewDescription.text = it.description
                Glide.with(this).load(it.logo).into(binding.imageViewHeader)
                photoAdapter.setPhotos(it.photos)
            }
        })

        viewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        binding.buttonViewMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("RESTAURANT_ID", restaurantId)
            }
            startActivity(intent)
        }

        // Configurar el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> {
                    val intent = Intent(this, ExploreActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_reservations -> {
                    // Lógica para la navegación de reservas
                    true
                }
                R.id.nav_account -> {
                    // Lógica para la navegación de cuenta
                    true
                }
                else -> false
            }
        }
    }
}
