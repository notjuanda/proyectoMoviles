package com.example.restaurantsmoviles.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsmoviles.model.Restaurant
import com.example.restaurantsmoviles.repositories.RestaurantRepository

class ExploreViewModel(application: Application) : AndroidViewModel(application) {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun searchRestaurants(context: Context, city: String? = null, date: String? = null, time: String? = null) {
        val filters = mutableMapOf<String, String?>()
        city?.let { filters["city"] = it }
        date?.let { filters["selectedDate"] = it }
        time?.let { filters["selectedTime"] = it }

        RestaurantRepository.searchRestaurants(context, filters,
            success = { restaurantList ->
                _restaurants.value = restaurantList ?: emptyList()
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }
}
