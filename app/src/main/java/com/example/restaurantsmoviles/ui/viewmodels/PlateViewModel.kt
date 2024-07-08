package com.example.restaurantsmoviles.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantsmoviles.model.Plate
import com.example.restaurantsmoviles.repositories.PlateRepository

class PlateViewModel : ViewModel() {

    private val _plates = MutableLiveData<List<Plate>>()
    val plates: LiveData<List<Plate>> get() = _plates

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getPlates(context: Context, restaurantId: Int) {
        PlateRepository.getPlates(context,
            success = { plateList ->
                _plates.value = plateList?.filter { it.menuCategoryId == restaurantId } ?: emptyList()
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }
}
