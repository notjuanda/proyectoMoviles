package com.example.restaurantsmoviles.model

import java.io.Serializable

typealias MenuCategories = ArrayList<MenuCategory>

data class MenuCategory(
    var name: String,
    var restaurantId: Int,
    var plates: List<Plate> // Asegúrate de incluir esta lista de `Plate`
) : Serializable {
    var id: Int? = null
}
