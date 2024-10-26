package com.yessr.telemedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ActivityDoctorLoginBinding

class DoctorLogin : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorLoginBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoctorLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnDoctorSubmit.setOnClickListener {
            val phoneNumber = binding.etDoctorPhoneNumber.text.trim().toString()
            val password = binding.etDoctorPassword.text.trim().toString()

            if (phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginDoctor(phoneNumber, password)
        }

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, DoctorRegistration::class.java)
            startActivity(intent)
        }
    }

    private fun loginDoctor(phoneNumber: String, password: String) {
        db.collection("doctors")
            .whereEqualTo("phoneNumber", phoneNumber)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Login successful
                    val intent = Intent(this, DoctorHome::class.java)
                    intent.putExtra("PHONE_NUMBER", phoneNumber)
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