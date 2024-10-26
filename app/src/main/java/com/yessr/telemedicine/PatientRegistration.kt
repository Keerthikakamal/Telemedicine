package com.yessr.telemedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ActivityPatientRegistrationBinding

class PatientRegistration : AppCompatActivity() {

    private lateinit var binding: ActivityPatientRegistrationBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle Login text click
        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, PatientLogin::class.java))
        }

        // Handle form submission
        binding.btnPatientSubmit.setOnClickListener {
            val name = binding.etPatientName.text.trim().toString()
            val location = binding.etPatientLocation.text.trim().toString()
            val phoneNumber = binding.etPatientPhoneNumber.text.trim().toString()
            val password = binding.etPatientPassword.text.trim().toString()

            // Validate input fields
            if (name.isEmpty() || location.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerPatient(name, location, phoneNumber, password)
        }
    }

    private fun registerPatient(name: String, location: String, phoneNumber: String, password: String) {
        val patientData = hashMapOf(
            "name" to name,
            "location" to location,
            "phoneNumber" to phoneNumber,
            "password" to password,
            "type" to "patient"
        )

        db.collection("patients")
            .add(patientData)
            .addOnSuccessListener {
                Toast.makeText(this, "Patient registered successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PatientHome::class.java)
                intent.putExtra("PATIENT_PHONE_NUMBER", phoneNumber)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to register: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
