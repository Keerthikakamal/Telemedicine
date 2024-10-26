package com.yessr.telemedicine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ActivityDoctorAppointmentsBinding
import com.yessr.telemedicine.model.Appointment

class DoctorAppointments : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorAppointmentsBinding
    private val db = FirebaseFirestore.getInstance()
    private val appointments = mutableListOf<Appointment>()
    private lateinit var appointmentAdapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appointmentAdapter = AppointmentAdapter(appointments)
        binding.recyclerViewAppointments.apply {
            layoutManager = LinearLayoutManager(this@DoctorAppointments)
            adapter = appointmentAdapter
        }

        loadAppointments()
    }

    private fun loadAppointments() {
        val doctorId = intent.getStringExtra("doctorId") ?: return
        db.collection("appointments")
            .whereEqualTo("doctorId", doctorId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val appointment = document.toObject(Appointment::class.java)
                    appointments.add(appointment)
                }
                appointmentAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
