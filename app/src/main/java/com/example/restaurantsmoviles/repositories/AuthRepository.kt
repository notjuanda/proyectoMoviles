// AuthRepository.kt
package com.example.restaurantsmoviles.repositories

import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.AuthResponse
import com.example.restaurantsmoviles.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthRepository {

    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service: ApiService = retrofit.create(ApiService::class.java)

    fun registerUser(user: User, success: (AuthResponse) -> Unit, failure: (Throwable) -> Unit) {
        service.registerUser(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun loginUser(email: String, password: String, success: (AuthResponse) -> Unit, failure: (Throwable) -> Unit) {
        service.loginUser(email, password).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                failure(t)
            }
        })
    }
}
