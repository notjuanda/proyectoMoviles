package com.example.restaurantsmoviles.model

import java.io.Serializable

typealias Plates = ArrayList<Plate>

data class Plate(
    var id: Int? = null,
    var name: String,
    var description: String,
    var price: Double,
    var menuCategoryId: Int
) : Serializable