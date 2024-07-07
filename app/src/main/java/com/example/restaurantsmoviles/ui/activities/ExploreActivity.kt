package com.example.restaurantsmoviles.ui.activities

import RestaurantAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantsmoviles.databinding.ActivityExploreBinding
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

        viewModel.searchRestaurants()
    }
}
