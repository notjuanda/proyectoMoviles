package com.example.restaurantsmoviles.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsmoviles.model.Restaurant
import com.example.restaurantsmoviles.repositories.RestaurantRepository

class RestaurantDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> get() = _restaurant

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getRestaurantDetails(context: Context, id: Int) {
        RestaurantRepository.getRestaurant(context, id,
            success = { restaurant ->
                restaurant?.let {
                    _restaurant.value = it
                }
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }
}
