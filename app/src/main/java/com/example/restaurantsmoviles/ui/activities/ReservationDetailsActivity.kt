package com.example.restaurantsmoviles.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantsmoviles.databinding.ActivityReservationDetailsBinding
import com.example.restaurantsmoviles.ui.viewmodels.ReservationViewModel

class ReservationDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservationDetailsBinding
    private val viewModel: ReservationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservationId = intent.getIntExtra("RESERVATION_ID", -1)
        if (reservationId == -1) {
            Toast.makeText(this, "Error: ID de reserva no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.getReservation(this, reservationId)

        viewModel.reservation.observe(this, { reservation ->
            reservation?.let {
                binding.textViewDate.text = "Fecha: ${it.date}"
                binding.textViewTime.text = "Hora: ${it.time}"
                binding.textViewPeople.text = "Número de Personas: ${it.people}"
                val foodDetails = it.food?.joinToString("\n") { food -> "Plato ID: ${food.plateId}, Cantidad: ${food.qty}" }
                binding.textViewFood.text = "Platos: \n${foodDetails ?: "Ninguno"}"
            }
        })

        viewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.buttonCancelReservation.setOnClickListener {
            viewModel.cancelReservation(this, reservationId)
        }

        viewModel.cancelReservationResponse.observe(this, { success ->
            if (success) {
                Toast.makeText(this, "Reserva cancelada con éxito", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Error al cancelar la reserva", Toast.LENGTH_LONG).show()
            }
        })

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}