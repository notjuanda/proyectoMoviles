package com.example.restaurantsmoviles.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsmoviles.model.AuthResponse
import com.example.restaurantsmoviles.model.User
import com.example.restaurantsmoviles.repositories.AuthRepository

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val _authResponse = MutableLiveData<AuthResponse>()
    val authResponse: LiveData<AuthResponse> get() = _authResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    fun loginUser(email: String, password: String) {
        val context = getApplication<Application>().applicationContext
        AuthRepository.loginUser(context, email, password,
            success = { response ->
                _authResponse.value = response
                _token.value = response.access_token
                saveToken(response.access_token)
            },
            failure = {
                _errorMessage.value = it.message
            }
        )
    }

    fun registerUser(user: User) {
        val context = getApplication<Application>().applicationContext
        AuthRepository.registerUser(context, user,
            success = { response ->
                _authResponse.value = response
                _token.value = response.access_token
                saveToken(response.access_token)
            },
            failure = { throwable ->
                _errorMessage.value = throwable.message
            }
        )
    }

    private fun saveToken(token: String) {
        val context = getApplication<Application>().applicationContext
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun loadToken() {
        val context = getApplication<Application>().applicationContext
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        _token.value = sharedPreferences.getString("auth_token", null)
    }
}
