package com.example.restaurantsmoviles.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantsmoviles.model.Reservation
import com.example.restaurantsmoviles.repositories.ReservationRepository

class ReservationDetailViewModel : ViewModel() {

    private val _reservation = MutableLiveData<Reservation>()
    val reservation: LiveData<Reservation> get() = _reservation

    private val _cancelResponse = MutableLiveData<Boolean>()
    val cancelResponse: LiveData<Boolean> get() = _cancelResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getReservationDetail(context: Context, reservationId: Int) {
        ReservationRepository.getReservation(context, reservationId,
            success = { reservation ->
                _reservation.value = reservation
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }
}
