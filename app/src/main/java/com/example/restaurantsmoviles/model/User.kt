package com.example.restaurantsmoviles.model

import java.io.Serializable

data class User(
    var name: String,
    var email: String,
    var password: String,
    var phone: String
) : Serializable {
    var id: Int? = null
}
