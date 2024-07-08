package com.example.restaurantsmoviles.repositories

import android.content.Context
import android.util.Log
import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.MenuCategory
import com.example.restaurantsmoviles.model.Plate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PlateRepository {

    fun getPlates(
        context: Context,
        success: (List<Plate>?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.getPlates().enqueue(object : Callback<List<Plate>> {
            override fun onResponse(
                call: Call<List<Plate>>,
                response: Response<List<Plate>>
            ) {
                if (response.isSuccessful) {
                    Log.d("PlateRepository", "getPlates success: ${response.body()}")
                    success(response.body())
                } else {
                    Log.e("PlateRepository", "getPlates error: ${response.code()}")
                    failure(Throwable("Error fetching plates: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<Plate>>, t: Throwable) {
                Log.e("PlateRepository", "getPlates failure", t)
                failure(t)
            }
        })
    }

    fun insertPlate(context: Context, plate: Plate, success: (Plate?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.insertPlate(plate).enqueue(object : Callback<Plate> {
            override fun onResponse(call: Call<Plate>, response: Response<Plate>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Throwable("Error inserting plate: ${response.code()}"))
                }
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
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Throwable("Error fetching plate details: ${response.code()}"))
                }
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
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Throwable("Error updating plate: ${response.code()}"))
                }
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
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Throwable("Error deleting plate: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}
