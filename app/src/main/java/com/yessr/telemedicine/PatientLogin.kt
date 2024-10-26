package com.yessr.telemedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ActivityPatientLoginBinding

class PatientLogin : AppCompatActivity() {

    private lateinit var binding: ActivityPatientLoginBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout
        binding = ActivityPatientLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Login button click handler
        binding.btnPatientLogin.setOnClickListener {
            val phoneNumber = binding.etPatientPhoneNumber.text.trim().toString()
            val password = binding.etPatientPassword.text.trim().toString()

            // Validate input fields
            if (phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call login function
            loginPatient(phoneNumber, password)
        }

        // Handle 'Register' click to navigate to Patient Registration
        binding.txtPatientRegister.setOnClickListener {
            val intent = Intent(this, PatientRegistration::class.java)
            startActivity(intent)
        }
    }

    private fun loginPatient(phoneNumber: String, password: String) {
        // Query Firestore for the patient with the entered phone number
        db.collection("patients")
            .whereEqualTo("phoneNumber", phoneNumber)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Login successful
                    val intent = Intent(this, PatientHome::class.java)
                    intent.putExtra("PATIENT_PHONE_NUMBER", phoneNumber)

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                } else {
                    // Login failed
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                // Handle Firestore query failure
                Toast.makeText(this, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
