package com.example.restaurantsmoviles.model

import java.io.Serializable

typealias Restaurants = ArrayList<Restaurant>

data class Restaurant(
    var name: String,
    var address: String,
    var city: String,
    var description: String,
    var logo: String?, // Asegúrate de que este campo coincida con la respuesta de la API
    var photos: List<Photo> = listOf() // Usar el nuevo modelo Photo aquí
) : Serializable {
    var id: Int? = null
}
