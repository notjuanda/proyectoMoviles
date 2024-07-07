// ReservationRepository.kt
package com.example.restaurantsmoviles.repositories

import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReservationRepository {

    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service: ApiService = retrofit.create(ApiService::class.java)

    fun getReservationList(success: (List<Reservation>?) -> Unit, failure: (Throwable) -> Unit) {
        service.getReservations().enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                success(response.body())
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertReservation(reservation: Reservation, success: (Reservation) -> Unit, failure: (Throwable) -> Unit) {
        service.insertReservation(reservation).enqueue(object : Callback<Reservation> {
            override fun onResponse(call: Call<Reservation>, response: Response<Reservation>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getReservation(id: Int, success: (Reservation?) -> Unit, failure: (Throwable) -> Unit) {
        service.getReservation(id).enqueue(object : Callback<Reservation?> {
            override fun onResponse(call: Call<Reservation?>, response: Response<Reservation?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Reservation?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateReservation(reservation: Reservation, success: (Reservation) -> Unit, failure: (Throwable) -> Unit) {
        val reservationId = reservation.id ?: 0
        service.updateReservation(reservation, reservationId).enqueue(object : Callback<Reservation> {
            override fun onResponse(call: Call<Reservation>, response: Response<Reservation>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteReservation(id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.deleteReservation(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}
