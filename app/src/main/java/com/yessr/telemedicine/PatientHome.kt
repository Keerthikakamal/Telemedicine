package com.yessr.telemedicine

import Doctor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ActivityPatientHomeBinding

class PatientHome : AppCompatActivity() {

    private lateinit var binding: ActivityPatientHomeBinding
    private val db = FirebaseFirestore.getInstance()
    private val doctors = mutableListOf<Doctor>()
    private lateinit var doctorAdapter: DoctorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val patientMobileNumber = intent.getStringExtra("PATIENT_PHONE_NUMBER") ?: ""

        doctorAdapter = DoctorAdapter(this, doctors, patientMobileNumber)
        binding.recyclerViewDoctors.apply {
            layoutManager = LinearLayoutManager(this@PatientHome)
            adapter = doctorAdapter
        }

        loadDoctors()
    }

    private fun loadDoctors() {
        db.collection("doctors").get().addOnSuccessListener { result ->
            for (document in result) {
                val doctor = document.toObject(Doctor::class.java)
                doctors.add(doctor)
            }
            doctorAdapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
        }
    }
}