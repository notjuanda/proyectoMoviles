package com.example.restaurantsmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantsmoviles.databinding.ActivityLoginBinding
import com.example.restaurantsmoviles.ui.util.PreferenceHelper
import com.example.restaurantsmoviles.ui.viewmodels.AuthViewModel
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.authResponse.observe(this, { authResponse ->
            // Manejar el inicio de sesión exitoso
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            // Guardar el token de autenticación
            PreferenceHelper.saveAuthToken(this, authResponse.access_token)
            // Redirigir a la pantalla de explorar restaurantes
            val intent = Intent(this, ExploreActivity::class.java)
            startActivity(intent)
            finish()
        })

        authViewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
