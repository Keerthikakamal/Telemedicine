package com.yessr.telemedicine

import Patient
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ActivityDoctorHomeBinding

class DoctorHome : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorHomeBinding
    private val db = FirebaseFirestore.getInstance()
    private val patients = mutableListOf<Patient>()
    private lateinit var patientAdapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        patientAdapter = PatientAdapter(patients)
        binding.patientRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DoctorHome)
            adapter = patientAdapter
        }

        loadPatients()
    }

    private fun loadPatients() {
        db.collection("patients").get().addOnSuccessListener { result ->
            for (document in result) {
                val patient = document.toObject(Patient::class.java)
                patients.add(patient)
            }
            patientAdapter.notifyDataSetChanged()
        }
    }
}
