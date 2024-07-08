package com.example.restaurantsmoviles.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantsmoviles.databinding.ItemReservationBinding
import com.example.restaurantsmoviles.model.Reservation
import com.example.restaurantsmoviles.repositories.ReservationRepository

class ReservationAdapter(
    private val context: Context,
    private val reservations: List<Reservation>,
    private val onItemClick: (Reservation) -> Unit
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(val binding: ItemReservationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.binding.textViewReservationDetails.text = "Reserva para ${reservation.people} el ${reservation.date} a las ${reservation.time}"
        holder.binding.textViewReservationStatus.text = "Estado: ${reservation.status ?: "Desconocido"}"
        holder.binding.textViewRestaurantName.text = reservation.restaurant?.name ?: "Nombre del restaurante no disponible"
        Glide.with(holder.binding.root.context)
            .load(reservation.restaurant?.logo)
            .into(holder.binding.imageViewRestaurantLogo)

        holder.binding.buttonCancelReservation.visibility = if (reservation.status == "canceled") View.GONE else View.VISIBLE

        holder.binding.buttonCancelReservation.setOnClickListener {
            cancelReservation(reservation, position)
        }

        holder.binding.root.setOnClickListener {
            onItemClick(reservation)
        }
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    private fun cancelReservation(reservation: Reservation, position: Int) {
        ReservationRepository.cancelReservation(context, reservation.id ?: return,
            success = {
                // Actualiza el estado de la reserva y notifica el cambio
                reservation.status = "canceled"
                notifyItemChanged(position)
                Toast.makeText(context, "Reserva cancelada con éxito", Toast.LENGTH_SHORT).show()
            },
            failure = { throwable ->
                Toast.makeText(context, "Error al cancelar la reserva: ${throwable.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
