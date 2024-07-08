package com.example.restaurantsmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantsmoviles.R
import com.example.restaurantsmoviles.databinding.ActivityAccountBinding
import com.example.restaurantsmoviles.ui.adapters.RestaurantAdapter
import com.example.restaurantsmoviles.ui.util.PreferenceHelper
import com.example.restaurantsmoviles.ui.viewmodels.AccountViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private val viewModel: AccountViewModel by viewModels()
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar si el usuario está autenticado
        if (PreferenceHelper.getAuthToken(this).isNullOrEmpty()) {
            // Si no está autenticado, redirigir a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantAdapter = RestaurantAdapter { restaurant ->
            val intent = Intent(this, RestaurantDetailsActivity::class.java)
            intent.putExtra("RESTAURANT_ID", restaurant.id)
            startActivity(intent)
        }
        binding.recyclerViewRestaurants.apply {
            layoutManager = LinearLayoutManager(this@AccountActivity)
            adapter = restaurantAdapter
        }

        viewModel.restaurants.observe(this, { restaurantList ->
            if (restaurantList.isNullOrEmpty()) {
                binding.recyclerViewRestaurants.visibility = View.GONE
                binding.textViewNoRestaurants.visibility = View.VISIBLE
            } else {
                binding.recyclerViewRestaurants.visibility = View.VISIBLE
                binding.textViewNoRestaurants.visibility = View.GONE
                restaurantAdapter.submitList(restaurantList)
            }
        })

        viewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.buttonLogout.setOnClickListener {
            PreferenceHelper.clearAuthToken(this)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Inicialmente, obtener los restaurantes del usuario
        viewModel.getUserRestaurants(this)

        // Configurar el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_account // Establecer la pestaña correcta como seleccionada
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
                    // Ya estamos en esta pantalla
                    true
                }
                else -> false
            }
        }
    }
}
