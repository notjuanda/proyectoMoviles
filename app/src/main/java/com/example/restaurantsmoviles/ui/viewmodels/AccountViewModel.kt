package com.example.restaurantsmoviles.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsmoviles.model.Restaurant
import com.example.restaurantsmoviles.repositories.RestaurantRepository

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getUserRestaurants(context: Context) {
        RestaurantRepository.getUserRestaurants(context,
            success = { restaurantList ->
                _restaurants.value = restaurantList ?: emptyList()
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }
}
