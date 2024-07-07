package com.example.restaurantsmoviles.repositories

import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.Plate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PlateRepository {

    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service: ApiService = retrofit.create(ApiService::class.java)

    fun getPlateList(success: (List<Plate>?) -> Unit, failure: (Throwable) -> Unit) {
        service.getPlates().enqueue(object : Callback<List<Plate>> {
            override fun onResponse(call: Call<List<Plate>>, response: Response<List<Plate>>) {
                success(response.body())
            }

            override fun onFailure(call: Call<List<Plate>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertPlate(plate: Plate, success: (Plate?) -> Unit, failure: (Throwable) -> Unit) {
        service.insertPlate(plate).enqueue(object : Callback<Plate> {
            override fun onResponse(call: Call<Plate>, response: Response<Plate>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Plate>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getPlate(id: Int, success: (Plate?) -> Unit, failure: (Throwable) -> Unit) {
        service.getPlate(id).enqueue(object : Callback<Plate?> {
            override fun onResponse(call: Call<Plate?>, response: Response<Plate?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Plate?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updatePlate(plate: Plate, success: (Plate?) -> Unit, failure: (Throwable) -> Unit) {
        val plateId = plate.id ?: 0
        service.updatePlate(plate, plateId).enqueue(object : Callback<Plate> {
            override fun onResponse(call: Call<Plate>, response: Response<Plate>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Plate>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deletePlate(id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.deletePlate(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}
