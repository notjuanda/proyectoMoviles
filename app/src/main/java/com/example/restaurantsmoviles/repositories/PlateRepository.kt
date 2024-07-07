package com.example.restaurantsmoviles.repositories

import android.content.Context
import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.Plate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PlateRepository {

    fun getPlateList(context: Context, success: (List<Plate>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.getPlates().enqueue(object : Callback<List<Plate>> {
            override fun onResponse(call: Call<List<Plate>>, response: Response<List<Plate>>) {
                success(response.body())
            }

            override fun onFailure(call: Call<List<Plate>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertPlate(context: Context, plate: Plate, success: (Plate?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.insertPlate(plate).enqueue(object : Callback<Plate> {
            override fun onResponse(call: Call<Plate>, response: Response<Plate>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Plate>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getPlate(context: Context, id: Int, success: (Plate?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.getPlate(id).enqueue(object : Callback<Plate?> {
            override fun onResponse(call: Call<Plate?>, response: Response<Plate?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Plate?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updatePlate(context: Context, plate: Plate, success: (Plate?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)
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

    fun deletePlate(context: Context, id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

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
