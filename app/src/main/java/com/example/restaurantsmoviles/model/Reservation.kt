package com.example.restaurantsmoviles.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Reservation(
    @SerializedName("restaurant_id") var restaurantId: Int,
    var date: String,
    var time: String,
    var people: Int,
    var food: List<Food>? = null
) : Serializable {
    var id: Int? = null
}

data class Food(
    @SerializedName("plate_id") var plateId: Int,
    var qty: Int
) : Serializable
