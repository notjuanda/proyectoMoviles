package com.example.restaurantsmoviles.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurantsmoviles.R
import com.example.restaurantsmoviles.databinding.ActivityRestaurantDetailsBinding
import com.example.restaurantsmoviles.model.Food
import com.example.restaurantsmoviles.model.Reservation
import com.example.restaurantsmoviles.ui.adapters.PhotoAdapter
import com.example.restaurantsmoviles.ui.util.PreferenceHelper
import com.example.restaurantsmoviles.ui.viewmodels.ReservationViewModel
import com.example.restaurantsmoviles.ui.viewmodels.RestaurantDetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailsBinding
    private val detailsViewModel: RestaurantDetailsViewModel by viewModels()
    private val reservationViewModel: ReservationViewModel by viewModels()
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

        detailsViewModel.getRestaurantDetails(this, restaurantId)

        detailsViewModel.restaurant.observe(this, { restaurant ->
            restaurant?.let {
                binding.textViewName.text = it.name
                binding.textViewAddress.text = it.address
                binding.textViewCity.text = it.city
                binding.textViewDescription.text = it.description
                Glide.with(this).load(it.logo).into(binding.imageViewHeader)
                photoAdapter.setPhotos(it.photos)
            }
        })

        detailsViewModel.errorMessage.observe(this, { errorMessage ->
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

        binding.buttonReserveTable.setOnClickListener {
            if (PreferenceHelper.getAuthToken(this).isNullOrEmpty()) {
                // Redirigir a LoginActivity si no está autenticado
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Mostrar el diálogo para confirmar la fecha y la hora de la reserva
                showReservationDialog(restaurantId)
            }
        }

        reservationViewModel.reservationResponse.observe(this, { reservation ->
            reservation?.let {
                Toast.makeText(this, "Reserva realizada con éxito", Toast.LENGTH_LONG).show()
            }
        })

        reservationViewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

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
                    val intent = Intent(this, ReservationsActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_account -> {
                    // Lógica para la navegación de cuenta
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun showReservationDialog(restaurantId: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_reservation, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)
        val peopleInput = dialogView.findViewById<EditText>(R.id.peopleInput)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
        val foodButton = dialogView.findViewById<Button>(R.id.foodButton)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        foodButton.setOnClickListener {
            // Lógica para seleccionar platos
        }

        confirmButton.setOnClickListener {
            val date = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
            val time = "${timePicker.hour}:${timePicker.minute}"
            val people = peopleInput.text.toString().toIntOrNull() ?: 1

            val reservation = Reservation(
                restaurantId = restaurantId,
                date = date,
                time = time,
                people = people,
                food = listOf(Food(1, 2)) // Ejemplo de comida seleccionada
            )

            reservationViewModel.insertReservation(this, reservation)

            dialog.dismiss()
        }

        dialog.show()
    }
}