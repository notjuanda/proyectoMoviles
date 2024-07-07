package com.example.restaurantsmoviles.model

import java.io.Serializable

typealias MenuCategories = ArrayList<MenuCategory>

data class MenuCategory(
    var name: String,
    var restaurantId: Int
) : Serializable {
    var id: Int? = null
}
