package com.example.restaurantsmoviles.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantsmoviles.model.Reservation
import com.example.restaurantsmoviles.repositories.ReservationRepository

class ReservationViewModel : ViewModel() {

    private val _reservations = MutableLiveData<List<Reservation>>()
    val reservations: LiveData<List<Reservation>> get() = _reservations

    private val _reservation = MutableLiveData<Reservation?>()
    val reservation: LiveData<Reservation?> get() = _reservation

    private val _reservationResponse = MutableLiveData<Reservation?>()
    val reservationResponse: LiveData<Reservation?> get() = _reservationResponse

    private val _cancelReservationResponse = MutableLiveData<Boolean>()
    val cancelReservationResponse: LiveData<Boolean> get() = _cancelReservationResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getReservations(context: Context) {
        ReservationRepository.getReservationList(context,
            success = { reservationList ->
                _reservations.value = reservationList ?: emptyList()
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }

    fun getReservation(context: Context, id: Int) {
        ReservationRepository.getReservation(context, id,
            success = { reservation ->
                _reservation.value = reservation
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }

    fun insertReservation(context: Context, reservation: Reservation) {
        ReservationRepository.insertReservation(context, reservation,
            success = { reservationResponse ->
                _reservationResponse.value = reservationResponse
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }
}
