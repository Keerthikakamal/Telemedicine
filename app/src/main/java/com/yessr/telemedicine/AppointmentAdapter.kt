package com.yessr.telemedicine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yessr.telemedicine.databinding.ListItemAppointmentBinding
import com.yessr.telemedicine.model.Appointment

class AppointmentAdapter(private val appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    inner class AppointmentViewHolder(val binding: ListItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = ListItemAppointmentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.binding.apply {
            tvPatientId.text = appointment.patientMobileNumber
            tvAppointmentTime.text = appointment.appointmentTime
        }
    }

    override fun getItemCount() = appointments.size
}
