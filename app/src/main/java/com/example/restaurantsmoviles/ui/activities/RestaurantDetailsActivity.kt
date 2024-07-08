package com.example.restaurantsmoviles.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantsmoviles.R
import com.example.restaurantsmoviles.databinding.ActivityRestaurantDetailsBinding
import com.example.restaurantsmoviles.model.Food
import com.example.restaurantsmoviles.model.Plate
import com.example.restaurantsmoviles.model.Reservation
import com.example.restaurantsmoviles.ui.adapters.PhotoAdapter
import com.example.restaurantsmoviles.ui.adapters.PlateSelectionAdapter
import com.example.restaurantsmoviles.ui.util.PreferenceHelper
import com.example.restaurantsmoviles.ui.viewmodels.PlateViewModel
import com.example.restaurantsmoviles.ui.viewmodels.ReservationViewModel
import com.example.restaurantsmoviles.ui.viewmodels.RestaurantDetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailsBinding
    private val detailsViewModel: RestaurantDetailsViewModel by viewModels()
    private val reservationViewModel: ReservationViewModel by viewModels()
    private val plateViewModel: PlateViewModel by viewModels()
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var plateSelectionAdapter: PlateSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantId = intent.getIntExtra("RESTAURANT_ID", -1)
        if (restaurantId == -1) {
            Log.e("RestaurantDetails", "Error: Restaurant ID not found")
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
                Log.d("RestaurantDetails", "Received restaurant details: $it")
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
            Log.d("RestaurantDetails", "View Menu button clicked")
            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("RESTAURANT_ID", restaurantId)
            }
            startActivity(intent)
        }

        binding.buttonReserveTable.setOnClickListener {
            Log.d("RestaurantDetails", "Reserve Table button clicked")
            if (PreferenceHelper.getAuthToken(this).isNullOrEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                showReservationDialog(restaurantId)
            }
        }

        reservationViewModel.reservationResponse.observe(this, { reservation ->
            reservation?.let {
                Log.d("RestaurantDetails", "Reservation made successfully: $it")
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

        var selectedPlates = listOf<Plate>()
        foodButton.setOnClickListener {
            showPlateSelectionDialog(restaurantId) { plates ->
                selectedPlates = plates
            }
        }

        confirmButton.setOnClickListener {
            val date = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
            val time = "${timePicker.hour}:${timePicker.minute}"
            val people = peopleInput.text.toString().toIntOrNull() ?: 1

            val foods = selectedPlates.map { Food(it.id!!, 1) }  // Aquí puedes añadir lógica para cantidades específicas

            val reservation = Reservation(
                restaurantId = restaurantId,
                date = date,
                time = time,
                people = people,
                food = foods
            )

            reservationViewModel.insertReservation(this, reservation)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showPlateSelectionDialog(restaurantId: Int, onPlatesSelected: (List<Plate>) -> Unit) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_plate_selection, null)
        val recyclerViewPlates = dialogView.findViewById<RecyclerView>(R.id.recyclerViewPlates)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)

        plateSelectionAdapter = PlateSelectionAdapter(this)
        recyclerViewPlates.layoutManager = LinearLayoutManager(this)
        recyclerViewPlates.adapter = plateSelectionAdapter

        plateViewModel.getPlates(this, restaurantId)
        plateViewModel.plates.observe(this, { plates ->
            plateSelectionAdapter = PlateSelectionAdapter(this)
            plateSelectionAdapter.submitList(plates)  // Asume que tienes un método submitList en tu adaptador
            recyclerViewPlates.adapter = plateSelectionAdapter
        })

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        confirmButton.setOnClickListener {
            Log.d("RestaurantDetails", "Plates selected: ${plateSelectionAdapter.getSelectedPlates()}")
            onPlatesSelected(plateSelectionAdapter.getSelectedPlates())
            dialog.dismiss()
        }

        dialog.show()
    }
}
