package com.example.restaurantsmoviles.repositories

import android.content.Context
import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.AuthResponse
import com.example.restaurantsmoviles.model.LoginRequest
import com.example.restaurantsmoviles.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthRepository {

    private fun getService(context: Context): ApiService {
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        return retrofit.create(ApiService::class.java)
    }

    fun registerUser(context: Context, user: User, success: (AuthResponse) -> Unit, failure: (Throwable) -> Unit) {
        getService(context).registerUser(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun loginUser(context: Context, email: String, password: String, success: (AuthResponse) -> Unit, failure: (Throwable) -> Unit) {
        val loginRequest = LoginRequest(email, password)
        getService(context).loginUser(loginRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                response.body()?.let { success(it) } ?: failure(Throwable("Response body is null"))
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                failure(t)
            }
        })
    }
}
