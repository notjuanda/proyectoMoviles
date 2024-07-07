package com.example.restaurantsmoviles.model

import java.io.Serializable

typealias Reservations = ArrayList<Reservation>

data class Reservation(
    var restaurantId: Int,
    var date: String,
    var time: String,
    var people: Int,
    var food: List<Food>? = null
) : Serializable {
    var id: Int? = null
}

data class Food(
    var plateId: Int,
    var qty: Int
) : Serializable
