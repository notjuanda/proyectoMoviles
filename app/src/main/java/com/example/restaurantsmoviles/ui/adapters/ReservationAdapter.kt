package com.example.restaurantsmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsmoviles.databinding.ItemReservationBinding
import com.example.restaurantsmoviles.model.Reservation

class ReservationAdapter(
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
        holder.binding.root.setOnClickListener {
            onItemClick(reservation)
        }
    }

    override fun getItemCount(): Int {
        return reservations.size
    }
}
