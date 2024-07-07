package com.example.restaurantsmoviles.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantsmoviles.databinding.ActivityRegisterBinding
import com.example.restaurantsmoviles.model.User
import com.example.restaurantsmoviles.ui.viewmodels.AuthViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.authResponse.observe(this, { authResponse ->
            // Handle successful registration, navigate to login, etc.
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
            finish() // Close the activity and return to the previous one (e.g., LoginActivity)
        })

        authViewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty()) {
                val user = User(name = name, email = email, password = password, phone = phone)
                authViewModel.registerUser(user)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
