package com.example.restaurantsmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantsmoviles.R
import com.example.restaurantsmoviles.databinding.ActivityReservationsBinding
import com.example.restaurantsmoviles.ui.adapters.ReservationAdapter
import com.example.restaurantsmoviles.ui.viewmodels.ReservationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReservationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservationsBinding
    private val viewModel: ReservationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewReservations.layoutManager = LinearLayoutManager(this)

        viewModel.reservations.observe(this, { reservations ->
            val adapter = ReservationAdapter(this, reservations) { reservation ->
                val intent = Intent(this, ReservationDetailsActivity::class.java)
                intent.putExtra("RESERVATION_ID", reservation.id)
                startActivity(intent)
            }
            binding.recyclerViewReservations.adapter = adapter
        })

        viewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getReservations(this)

        // Configurar el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_reservations // Establecer la pestaña correcta como seleccionada
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> {
                    val intent = Intent(this, ExploreActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_reservations -> {
                    val intent = Intent(this, ReservationsActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_account -> {
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
