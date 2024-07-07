package com.example.restaurantsmoviles.ui.util

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val PREFERENCES_FILE_KEY = "com.example.restaurantsmoviles.PREFERENCE_FILE_KEY"
    private const val TOKEN_KEY = "auth_token"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(context: Context, token: String) {
        val preferences = getPreferences(context)
        with(preferences.edit()) {
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    fun getAuthToken(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences.getString(TOKEN_KEY, null)
    }

    fun clearAuthToken(context: Context) {
        val preferences = getPreferences(context)
        with(preferences.edit()) {
            remove(TOKEN_KEY)
            apply()
        }
    }
}
