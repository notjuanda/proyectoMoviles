package com.example.restaurantsmoviles.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantsmoviles.R
import com.example.restaurantsmoviles.databinding.ActivityExploreBinding
import com.example.restaurantsmoviles.ui.adapters.RestaurantAdapter
import com.example.restaurantsmoviles.ui.viewmodels.ExploreViewModel

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    private val viewModel: ExploreViewModel by viewModels()
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantAdapter = RestaurantAdapter()
        binding.recyclerViewRestaurants.apply {
            layoutManager = LinearLayoutManager(this@ExploreActivity)
            adapter = restaurantAdapter
        }

        viewModel.restaurants.observe(this, Observer { restaurantList ->
            restaurantList?.let {
                restaurantAdapter.submitList(it)
            }
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        // Abre el diálogo de filtro al hacer clic en el campo de búsqueda
        binding.searchCity.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_filter, null)
            val cityFilter = dialogView.findViewById<EditText>(R.id.cityFilter)
            val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
            val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)
            val applyFilterButton = dialogView.findViewById<Button>(R.id.applyFilterButton)

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create()

            applyFilterButton.setOnClickListener {
                val city = cityFilter.text.toString()
                val date = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
                val time = "${timePicker.hour}:${timePicker.minute}"

                binding.searchCity.setText(city)

                viewModel.searchRestaurants(this@ExploreActivity, city, date, time)

                dialog.dismiss()
            }

            dialog.show()
        }

        // Inicialmente, obtener todos los restaurantes
        viewModel.searchRestaurants(this)
    }
}
