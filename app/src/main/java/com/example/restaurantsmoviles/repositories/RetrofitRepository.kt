package com.example.restaurantsmoviles.repositories

import android.content.Context
import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.ui.util.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {

    fun getRetrofitInstance(context: Context): Retrofit {
        val token = PreferenceHelper.getAuthToken(context)

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }.build()

        return Retrofit.Builder()
            .baseUrl("https://restaurantes.jmacboy.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
