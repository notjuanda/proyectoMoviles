package com.example.restaurantsmoviles.api

import com.example.restaurantsmoviles.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // Autenticación
    @POST("registeruser")
    fun registerUser(@Body user: User): Call<AuthResponse>

    @POST("loginuser")
    fun loginUser(@Body email: String, @Body password: String): Call<AuthResponse>

    // Restaurantes
    @POST("restaurants/search")
    fun searchRestaurants(@Body filters: Map<String, String?>): Call<List<Restaurant>>

    @GET("restaurants")
    fun getUserRestaurants(): Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getRestaurant(@Path("id") id: Int): Call<Restaurant>

    @POST("restaurants")
    fun insertRestaurant(@Body restaurant: Restaurant): Call<Restaurant>

    @PUT("restaurants/{id}")
    fun updateRestaurant(@Body restaurant: Restaurant, @Path("id") id: Int): Call<Restaurant>

    @PATCH("restaurants/{id}")
    fun patchRestaurant(@Body restaurant: Restaurant, @Path("id") id: Int): Call<Restaurant>

    @DELETE("restaurants/{id}")
    fun deleteRestaurant(@Path("id") id: Int): Call<Void>

    @Multipart
    @POST("restaurants/{id}/photo")
    fun uploadPhoto(@Path("id") id: Int, @Part photo: MultipartBody.Part): Call<Void>

    @Multipart
    @POST("restaurants/{id}/logo")
    fun uploadLogo(@Path("id") id: Int, @Part logo: MultipartBody.Part): Call<Void>

    // Menús
    @GET("restaurants/{id}/menu")
    fun getMenuCategories(@Path("id") restaurantId: Int): Call<List<MenuCategory>>

    @POST("menu-categories")
    fun insertMenuCategory(@Body menuCategory: MenuCategory): Call<MenuCategory>

    @GET("menu-categories/{id}")
    fun getMenuCategory(@Path("id") id: Int): Call<MenuCategory>

    @PUT("menu-categories/{id}")
    fun updateMenuCategory(@Body menuCategory: MenuCategory, @Path("id") id: Int): Call<MenuCategory>

    @DELETE("menu-categories/{id}")
    fun deleteMenuCategory(@Path("id") id: Int): Call<Void>

    @GET("plates")
    fun getPlates(): Call<List<Plate>>

    @POST("plates")
    fun insertPlate(@Body plate: Plate): Call<Plate>

    @GET("plates/{id}")
    fun getPlate(@Path("id") id: Int): Call<Plate>

    @PUT("plates/{id}")
    fun updatePlate(@Body plate: Plate, @Path("id") id: Int): Call<Plate>

    @DELETE("plates/{id}")
    fun deletePlate(@Path("id") id: Int): Call<Void>

    // Reservas
    @GET("reservations")
    fun getReservations(): Call<List<Reservation>>

    @GET("reservations/{id}")
    fun getReservation(@Path("id") id: Int): Call<Reservation>

    @POST("reservations")
    fun insertReservation(@Body reservation: Reservation): Call<Reservation>

    @PUT("reservations/{id}")
    fun updateReservation(@Body reservation: Reservation, @Path("id") id: Int): Call<Reservation>

    @DELETE("reservations/{id}")
    fun deleteReservation(@Path("id") id: Int): Call<Void>
}
