package com.example.restaurantsmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantsmoviles.databinding.ActivityMainBinding
import com.example.restaurantsmoviles.ui.util.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar si el token de autenticación está presente
        val token = PreferenceHelper.getAuthToken(this)
        if (token != null) {
            // Redirigir a la pantalla de explorar restaurantes
            val intent = Intent(this, ExploreActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar acciones para los botones
        binding.exploreButton.setOnClickListener {
            val intent = Intent(this, ExploreActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            // Aquí se puede añadir lógica para verificar si el usuario está registrado o no.
            // Por ahora, solo se abre la actividad de login.
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
