package com.example.restaurantsmoviles.repositories

import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantRepository {

    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service: ApiService = retrofit.create(ApiService::class.java)

    fun getRestaurantList(success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit) {
        val filters = mapOf<String, String?>() // Mapa vacío para no filtrar nada
        service.searchRestaurants(filters).enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    failure(Throwable("Error ${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertRestaurant(restaurant: Restaurant, success: (Restaurant) -> Unit, failure: (Throwable) -> Unit) {
        service.insertRestaurant(restaurant).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getRestaurant(id: Int, success: (Restaurant?) -> Unit, failure: (Throwable) -> Unit) {
        service.getRestaurant(id).enqueue(object : Callback<Restaurant?> {
            override fun onResponse(call: Call<Restaurant?>, response: Response<Restaurant?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Restaurant?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateRestaurant(restaurant: Restaurant, success: (Restaurant) -> Unit, failure: (Throwable) -> Unit) {
        val restaurantId = restaurant.id ?: 0
        service.updateRestaurant(restaurant, restaurantId).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteRestaurant(id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.deleteRestaurant(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}
