package com.example.restaurantsmoviles.model

import java.io.Serializable

typealias Plates = ArrayList<Plate>

data class Plate(
    var name: String,
    var description: String,
    var price: Double,
    var menuCategoryId: Int
) : Serializable {
    var id: Int? = null
}
