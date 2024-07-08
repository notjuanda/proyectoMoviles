package com.example.restaurantsmoviles.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantsmoviles.model.MenuCategory
import com.example.restaurantsmoviles.model.Restaurant
import com.example.restaurantsmoviles.repositories.MenuCategoryRepository
import com.example.restaurantsmoviles.repositories.RestaurantRepository

class MenuViewModel : ViewModel() {

    private val _menuCategories = MutableLiveData<List<MenuCategory>>()
    val menuCategories: LiveData<List<MenuCategory>> get() = _menuCategories

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> get() = _restaurant

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getMenuCategories(context: Context, restaurantId: Int) {
        MenuCategoryRepository.getMenuCategories(context, restaurantId,
            success = { menuCategories ->
                _menuCategories.value = menuCategories ?: emptyList()
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }

    fun getRestaurantDetails(context: Context, restaurantId: Int) {
        RestaurantRepository.getRestaurant(context, restaurantId,
            success = { restaurant ->
                restaurant?.let {
                    _restaurant.value = it
                } ?: run {
                    _errorMessage.value = "Restaurant is null"
                }
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }
}
