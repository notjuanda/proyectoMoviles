package com.example.restaurantsmoviles.model

import java.io.Serializable

data class AuthResponse(
    val access_token: String,
    val token_type: String
) : Serializable
