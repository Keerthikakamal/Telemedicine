package com.yessr.telemedicine

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.yessr.telemedicine.databinding.ActivityDoctorRegistrationBinding
import java.util.*

class DoctorRegistration : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorRegistrationBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoctorRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Time picker for 'From' time
        binding.etFromTime.setOnClickListener {
            showTimePickerDialog(binding.etFromTime)
        }

        // Time picker for 'To' time
        binding.etToTime.setOnClickListener {
            showTimePickerDialog(binding.etToTime)
        }

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this, DoctorLogin::class.java)
            startActivity(intent)
        }

        // Handle form submission
        binding.btnDoctorSubmit.setOnClickListener {

            val name = binding.etDoctorName.text.trim().toString()
            val profession = binding.etDoctorProfession.text.trim().toString()
            val location = binding.etDoctorLocation.text.trim().toString()
            val phoneNumber = binding.etDoctorPhoneNumber.text.trim().toString()
            val password = binding.etDoctorPassword.text.trim().toString()
            val fromTime = binding.etFromTime.text.trim().toString()
            val toTime = binding.etToTime.text.trim().toString()

            // Validation
            if (name.isEmpty() || profession.isEmpty() || location.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fromTime.isEmpty() || toTime.isEmpty()) {
                Toast.makeText(this, "Please select availability time.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerDoctor(name, profession, location, phoneNumber, password, fromTime, toTime)
        }
    }

    private fun registerDoctor(name: String, profession: String, location: String, phoneNumber: String, password: String, fromTime: String, toTime: String) {
        val doctorMap = hashMapOf(
            "name" to name,
            "profession" to profession,
            "location" to location,
            "phoneNumber" to phoneNumber,
            "password" to password,
            "fromTime" to fromTime,
            "toTime" to toTime,
            "type" to "doctor"
        )

        db.collection("doctors")
            .add(doctorMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Doctor registered successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, DoctorHome::class.java)
                intent.putExtra("PHONE_NUMBER", phoneNumber)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                // Handle the error
                Toast.makeText(this, "Failed to register: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            editText.setText(time)
        }, hour, minute, true)

        timePickerDialog.show()
    }
}