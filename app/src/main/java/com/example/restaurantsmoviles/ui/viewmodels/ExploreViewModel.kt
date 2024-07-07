package com.example.restaurantsmoviles.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantsmoviles.model.Restaurant
import com.example.restaurantsmoviles.repositories.RestaurantRepository

class ExploreViewModel : ViewModel() {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun searchRestaurants() {
        RestaurantRepository.getRestaurantList(
            success = { restaurantList ->
                _restaurants.value = restaurantList ?: emptyList()            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }
}
