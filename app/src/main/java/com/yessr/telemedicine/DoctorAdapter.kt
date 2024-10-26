package com.yessr.telemedicine

import Doctor
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ListItemDoctorBinding

class DoctorAdapter(private val context: Context, private val doctors: List<Doctor>, private val patientMobileNumber: String) :
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    inner class DoctorViewHolder(val binding: ListItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ListItemDoctorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.binding.apply {
            tvDoctorName.text = doctor.name
            tvDoctorProfession.text = doctor.profession
            tvDoctorLocation.text = doctor.location
            tvDoctorAvailability.text = "From ${doctor.fromTime} - To ${doctor.toTime}"

            btnBookAppointment.setOnClickListener {
                bookAppointment(doctor)
            }
        }
    }

    private fun bookAppointment(doctor: Doctor) {
        val appointment = hashMapOf(
            "doctorId" to doctor.phoneNumber,
            "patientMobileNumber" to patientMobileNumber,
            "doctorName" to doctor.name,
            "appointmentTime" to "${doctor.fromTime} - ${doctor.toTime}"
        )

        db.collection("appointments")
            .add(appointment)
            .addOnSuccessListener {
                Toast.makeText(context, "Appointment booked successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to book appointment: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount() = doctors.size
}