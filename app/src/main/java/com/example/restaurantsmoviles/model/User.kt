package com.example.restaurantsmoviles.model

import java.io.Serializable

data class User(
    var name: String,
    var email: String,
    var password: String? = null, // Hacer que la contraseña sea opcional para el caso de uso de reservas
    var phone: String? = null // Hacer que el teléfono sea opcional para el caso de uso de reservas
) : Serializable {
    var id: Int? = null
}
