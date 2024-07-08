package com.example.restaurantsmoviles.repositories

import android.content.Context
import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantRepository {

    fun getRestaurantList(context: Context, filters: Map<String, String?>, success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

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

    fun insertRestaurant(context: Context, restaurant: Restaurant, success: (Restaurant) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.insertRestaurant(restaurant).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getRestaurant(context: Context, id: Int, success: (Restaurant?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.getRestaurant(id).enqueue(object : Callback<Restaurant?> {
            override fun onResponse(call: Call<Restaurant?>, response: Response<Restaurant?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Restaurant?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateRestaurant(context: Context, restaurant: Restaurant, success: (Restaurant) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)
        val restaurantId = restaurant.id ?: run {
            failure(Throwable("Restaurant ID is null"))
            return
        }

        service.updateRestaurant(restaurant, restaurantId).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteRestaurant(context: Context, id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.deleteRestaurant(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun searchRestaurants(context: Context, filters: Map<String, String?>, success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

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

    // Método agregado para obtener los restaurantes de un usuario
    fun getUserRestaurants(context: Context, success: (List<Restaurant>?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: ApiService = retrofit.create(ApiService::class.java)

        service.getUserRestaurants().enqueue(object : Callback<List<Restaurant>> {
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
}
